package com.fh.util.timers;

import com.fh.controller.base.BaseController;
import com.fh.entity.MemUser;
import com.fh.service.front.AccountManager;
import com.fh.service.record.USDT_wallet_RECManager;
import com.fh.util.BlockUtil;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 说明：USDT查交易哈希定时任务
 * 【注：提现是线下的方式，此函数作废。暂缺少存交易哈希的字段】
 * 如果成功就移除任务 如果失败就退回账户
 * 创建人：Ajie
 * 创建时间：2020年1月2日18:42:30
 */
@Slf4j
public class CashTaskJob extends BaseController implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String time = DateUtil.getTime();
        System.out.println("---------> 查询交易状态定时任务开始：" + time);
        // 获取参数
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Map<String,Object> parameter = (Map<String,Object>)dataMap.get("parameterList");
        String taskName = parameter.get("taskName").toString();
        String userId = parameter.get("userId").toString();
        // 交易哈希
        String orderox = parameter.get("orderox").toString();
        double amount = Double.parseDouble(parameter.get("amount").toString());

        // 普通类从spring容器中拿出service
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

        // usdt钱包记录表
        USDT_wallet_RECManager usdt_wallet_recService = (USDT_wallet_RECManager) webctx.getBean("usdt_wallet_recService");
        // 用户表
        AccountManager accountService = (AccountManager) webctx.getBean("accountService");
        try {
            // 获取用户信息
            PageData pd = new PageData();
            pd.put("ACCOUNT_ID", userId);
            MemUser user = accountService.findByIdReturnEntity(pd);
            // 调用区块查询状态api
            Map<String, String> result =  BlockUtil.getTradingStatus(orderox);
            // 交易中 直接返回
            if ("0".equals(result.get("statuses"))) {
                return;
            }
            // 交易成功移除任务
            if ("1".equals(result.get("statuses"))) {
                QuartzManager.removeJob(taskName);
                return;
            }
            // 交易失败退回用户USDT钱包 并 移除任务
            if ("2".equals(result.get("statuses"))) {
                log.info("---------> 提现失败原因" + result.get("msg"));
                // 更新钱包余额
                pd.put("GMT_MODIFIED", time);
                pd.put("USDT_WALLET", amount);
                pd.put("tag", "+");
                accountService.updateMoney(pd);
                // 更新缓存
                pd = accountService.findById(pd);
                applicati.setAttribute(pd.getString("USER_NAME"), pd);
                // 创建USDT钱包账单记录
                pd = new PageData();
                pd.put("GMT_CREATE", time);
                pd.put("GMT_MODIFIED", time);
                pd.put("USER_NAME", user.getUSER_NAME());
                pd.put("USER_ID", userId);
                // 金额类型
                pd.put("AMOUNT_TYPE", "提现失败");
                pd.put("WALLET_SITE", user.getCASH_SITE());
                pd.put("TAG", "+");
                pd.put("MONEY", amount);
                pd.put("STATUS", "已完成");
                // id 自增
                pd.put("USDT_WALLET_REC_ID", "");
                usdt_wallet_recService.save(pd);
                QuartzManager.removeJob(taskName);
            }
        } catch (Exception e) {
            System.out.println("查询交易状态定时任务失败");
            e.getMessage();
        }



    }


}
