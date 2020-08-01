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
 * 如果查到的余额小于 本地记录的余额  就减掉本地余额
 * 创建人：Ajie
 * 创建时间：2020年2月12日08:47:15
 */
public class CheckingOutTaskJob extends BaseController implements Job {

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
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("查询余额定时任务，并减余额失败");
            e.getMessage();
        }



    }


}
