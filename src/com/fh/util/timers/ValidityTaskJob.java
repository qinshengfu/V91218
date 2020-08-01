package com.fh.util.timers;

import com.fh.controller.base.BaseController;
import com.fh.service.record.Voucher_listManager;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.QuartzManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 说明：理财券有效期定时任务 到时间后 如果未使用设置为 已过期，并删除此定时任务
 * 创建人：Ajie
 * 创建时间：2019年12月27日16:52:02
 */
public class ValidityTaskJob extends BaseController implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // 获取参数
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Map<String,Object> parameter = (Map<String,Object>)dataMap.get("parameterList");
        String tableID = parameter.get("tableID").toString();

        // 普通类从spring容器中拿出service
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

        // 理财券表
        Voucher_listManager voucherListService = (Voucher_listManager) webctx.getBean("voucher_listService");

        PageData pd = new PageData();
        // 赋值ID 查询信息
        pd.put("VOUCHER_LIST_ID", tableID);
        try {
            pd = voucherListService.findById(pd);
            pd.put("GMT_MODIFIED", DateUtil.getTime());
            // 状态 0：未使用、1：已使用、2：已过期
            pd.put("STATUS", "2");
            // 执行修改 在sql 使用乐观锁完成 where status != 1
            voucherListService.updateStatus(pd);
        } catch (Exception e) {
            System.out.println("定时任务更新状态失败");
            e.getMessage();
        } finally {
            // 移除本次定时任务
            String jobName = Const.TIMED_TASK_VALIDITY + tableID;
            QuartzManager.removeJob(jobName);
        }
    }
}
