package com.fh.util.timers;

import com.fh.controller.base.BaseController;
import com.fh.service.front.AccountManager;
import com.fh.service.record.ErcSellManager;
import com.fh.service.record.ErcSellRecordManager;
import com.fh.service.record.USDT_wallet_RECManager;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.QuartzManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * 说明：法币交易之出售确认收款方式定时任务 到时间后 如果未选择收款方式，增加库存，退回USDT数额 并删除此定时任务
 * 创建人：Ajie
 * 创建时间：2020年1月3日10:27:09
 */
public class ErcSellTaskJob extends BaseController implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        String time = DateUtil.getTime();
        // 获取参数
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Map<String,Object> parameter = (Map<String,Object>)dataMap.get("parameterList");
        String orderId = parameter.get("orderId").toString();

        // 普通类从spring容器中拿出service
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

        // 法币交易之出售记录表
        ErcSellRecordManager ercsellrecordService = (ErcSellRecordManager) webctx.getBean("ercsellrecordService");
        // 法币交易之商家求购表
        ErcSellManager ercsellService = (ErcSellManager) webctx.getBean("ercsellService");
        // 用户表
        AccountManager accountService = (AccountManager) webctx.getBean("accountService");
        // USDT 钱包记录
        USDT_wallet_RECManager usdt_wallet_recService = (USDT_wallet_RECManager) webctx.getBean("usdt_wallet_recService");

        PageData pd = new PageData();
        // 赋值ID 查询信息
        pd.put("ERCSELLRECORD_ID", orderId);
        try {
            pd = ercsellrecordService.findById(pd);
            // 获取状态
            String status = pd.getString("STATE");
            String userId = pd.get("USER_ID").toString();
            if ("待确认".equals(status)) {
                // 获取数量
                double num = Double.parseDouble(pd.get("AMOUNT").toString());
                String SellOrderId = pd.getString("ORDER_ID");
                // 赋值订单ID、 数量
                pd.put("ERCSELL_ID", SellOrderId);
                pd.put("amount", num);
                // 执行增加库存
                ercsellService.addSurplus(pd);
                // 更改订单状态
                pd = new PageData();
                pd.put("GMT_MODIFIED", DateUtil.getTime());
                pd.put("STATE" , "過期未確認");
                pd.put("ERCSELLRECORD_ID", orderId);
                ercsellrecordService.edit(pd);
                // 增加用户USDT余额
                pd.put("USDT_WALLET", num);
                pd.put("tag", "+");
                pd.put("ACCOUNT_ID",userId);
                accountService.updateMoney(pd);
                // 更新用户缓存
                pd = accountService.findById(pd);
                applicati.setAttribute(pd.getString("USER_NAME"), pd);
                // 增加usdt账单记录】
                pd.put("GMT_CREATE", time);
                pd.put("GMT_MODIFIED", time);
                pd.put("USER_NAME", pd.getString("USER_NAME"));
                pd.put("USER_ID", pd.get("ACCOUNT_ID"));
                // 金额类型
                pd.put("AMOUNT_TYPE", "過期未確認");
                pd.put("WALLET_SITE", "");
                pd.put("TAG", "+");
                pd.put("MONEY", num);
                pd.put("STATUS", "已完成");
                // id 自增
                pd.put("USDT_WALLET_REC_ID", "");
                usdt_wallet_recService.save(pd);
                System.out.println("确认时间到退回库存定时任务执行完毕");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("确认时间到退回库存定时任务失败");
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            // 移除本次定时任务
            String jobName = orderId;
            QuartzManager.removeJob(jobName);
        }

    }
}
