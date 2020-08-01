package com.fh.util.timers;

import com.fh.controller.base.BaseController;
import com.fh.service.front.AccountManager;
import com.fh.service.record.USDT_wallet_RECManager;
import com.fh.util.BlockUtil;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.pool.MyThreadPoolManager;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 说明：USDT查余额充值定时任务
 * 如果查到的余额大于 本地记录的余额  就增加到钱包里
 * 创建人：Ajie
 * 创建时间：2019年12月30日 12:28:00
 */
public class CheckingInTaskJob extends BaseController implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // 普通类从spring容器中拿出service
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

        // usdt钱包记录表
        USDT_wallet_RECManager usdt_wallet_recService = (USDT_wallet_RECManager) webctx.getBean("usdt_wallet_recService");
        // 用户表
        AccountManager accountService = (AccountManager) webctx.getBean("accountService");
        try {
            // 获取所有用户列表
            List<PageData> userAll = accountService.listAll(null);
            // 循环查询
            for (PageData map : userAll) {
                // 获取usdt 钱包地址
                String address = map.getString("USDT_SITE");
                // 调用区块链查询余额api
                String result = BlockUtil.usdtBalance(address);
                // 转双精度布尔型
                double amount = Double.parseDouble(result);
                // 本地记录的余额
                double localAmount = Double.parseDouble(map.get("USDT_WALLET_BALANCE").toString());
                // 如果查到的余额大于本地记录的余额 加钱到钱包上
                if (amount > localAmount) {
                    String time = DateUtil.getTime();
                    //异步执行数据库操作
                    MyThreadPoolManager.getsInstance().execute(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            PageData pd = new PageData();
                            // 先更新本地记录的usdt余额
                            pd.put("ACCOUNT_ID", map.get("ACCOUNT_ID"));
                            pd.put("USDT_WALLET_BALANCE", amount);
                            accountService.updateFor(pd);
                            // 更新钱包余额
                            pd.put("GMT_MODIFIED", time);
                            pd.put("USDT_WALLET", amount - localAmount);
                            pd.put("tag", "+");
                            accountService.updateMoney(pd);
                            // 更新缓存
                            pd = accountService.findById(pd);
                            applicati.setAttribute(pd.getString("USER_NAME"), pd);
                            // 创建USDT钱包账单记录
                            pd = new PageData();
                            pd.put("GMT_CREATE", time);
                            pd.put("GMT_MODIFIED", time);
                            pd.put("USER_NAME", map.getString("USER_NAME"));
                            pd.put("USER_ID", map.get("ACCOUNT_ID"));
                            // 金额类型
                            pd.put("AMOUNT_TYPE", "充值");
                            pd.put("WALLET_SITE", map.get("USDT_SITE"));
                            pd.put("TAG", "+");
                            pd.put("MONEY", amount - localAmount);
                            pd.put("STATUS", "已完成");
                            // id 自增
                            pd.put("USDT_WALLET_REC_ID", "");
                            usdt_wallet_recService.save(pd);
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("查询余额定时任务，并增加余额失败");
            e.getMessage();
        }



    }


}
