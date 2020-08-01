package com.fh.util.timers;

import com.fh.controller.base.BaseController;
import com.fh.service.record.ErcBuyManager;
import com.fh.service.record.ErcBuyRecordManager;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.QuartzManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 说明：法币交易之购买付款时间定时任务 到时间后 如果未付款增加库存，并删除此定时任务
 * 创建人：Ajie
 * 创建时间：2019年12月31日16:52:02
 */
public class ErcBuyTaskJob extends BaseController implements Job {

    @Override
    public void execute(JobExecutionContext context)  {

        // 获取参数
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Map<String,Object> parameter = (Map<String,Object>)dataMap.get("parameterList");
        String orderId = parameter.get("orderId").toString();

        // 普通类从spring容器中拿出service
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

        // 法币交易之购买记录表
        ErcBuyRecordManager ercbuyrecordService = (ErcBuyRecordManager) webctx.getBean("ercbuyrecordService");
        // 法币交易之商家出售表
        ErcBuyManager ercbuyService = (ErcBuyManager) webctx.getBean("ercbuyService");

        PageData pd = new PageData();
        // 赋值ID 查询信息
        pd.put("ERCBUYRECORD_ID", orderId);
        try {
            pd = ercbuyrecordService.findById(pd);
            // 获取状态
            String status = pd.getString("STATE");
            if ("待付款".equals(status)) {
                // 获取数量
                String num = pd.get("AMOUNT").toString();
                String buyOrderId = pd.getString("ORDER_ID");
                // 赋值订单ID、 数量
                pd.put("ERCBUY_ID", buyOrderId);
                pd.put("amount", num);
                // 执行增加库存
                ercbuyService.addSurplus(pd);
                // 更改订单状态
                pd = new PageData();
                pd.put("GMT_MODIFIED", DateUtil.getTime());
                pd.put("STATE" , "过期未付款");
                pd.put("ERCBUYRECORD_ID", orderId);
                ercbuyrecordService.edit(pd);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("付款时间到退回库存定时任务失败");
        } finally {
            // 移除本次定时任务
            String jobName = orderId;
            QuartzManager.removeJob(jobName);
        }
    }
}
