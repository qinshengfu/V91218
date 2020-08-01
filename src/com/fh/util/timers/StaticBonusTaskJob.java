package com.fh.util.timers;

import com.fh.controller.base.BaseController;
import com.fh.service.front.AccountManager;
import com.fh.service.record.Accum_recManager;
import com.fh.service.record.Lncome_detailsManager;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.pool.MyThreadPoolManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 功能描述：每日静态奖金定时任务
 *
 * @author Ajie
 * @date 2020/1/2 0002
 */
public class StaticBonusTaskJob extends BaseController implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("---------> 开始发放每日静态奖金 ---" + DateUtil.getTime());
        // 普通类从spring容器中拿出service
        WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

        // 用户表
        AccountManager accountService = (AccountManager) webctx.getBean("accountService");
        // 投资记录表
        Lncome_detailsManager lncome_detailsService = (Lncome_detailsManager) webctx.getBean("lncome_detailsService");
        try {
            // 获取所有用户列表
            List<PageData> userAll = accountService.listAll(null);
            PageData pd;
            // 循环查询
            for (PageData map : userAll) {
                System.out.println("轮到用户ID ：" + map.get("ACCOUNT_ID") + "  用户名： " + map.get("USER_NAME"));
                String isRank = map.getString("USER_RANK");
                System.out.println("用户等级：" + isRank);

                // 判断是否有用户等级
                if (Tools.isEmpty(isRank)) {
                    continue;
                }
                // 获取用户等级有什么收益
                PageData rank = (PageData) applicati.getAttribute(Const.APP_RANK + map.getString("USER_RANK"));
                // 获取全球收益
                double globalBonus = Double.parseDouble(rank.get("GLOBAL_LUCRE").toString());
                // 判断是否有全球分红收益
                globalBonus = globalBonus > 0 ? globalBonus / 100 : -1;
                if (globalBonus > 0) {
                    // 发放全球分红收益奖
                    globalBonusJob(map, globalBonus, webctx);
                }
                // 获取最新的一条投资记录
                PageData record = lncome_detailsService.getNewOrderByUserId(map);
                if (record == null || record.size() <= 0) {
                    continue;
                }

                // 判断用户是否出局
                if (!"未出局".equals(record.getString("STATE"))) {
                    continue;
                }
                // 取出局收益
                double outBonus = Double.parseDouble(record.get("PROFIT").toString());
                // 取创建订单时间
                String createTime = record.getString("GMT_CREATE");
                // 获取后台设置的时间间隔
                PageData par = (PageData) applicati.getAttribute(Const.PAR);
                int timeInterval = Integer.parseInt(par.get("TIME_INTERVAL").toString());
                // 计算相差多少个小时  判断投资时间是否超过24小时
                int hour = DateUtil.getHour1(createTime, DateUtil.getTime());
                if (hour < timeInterval) {
                    continue;
                }
                // 执行奖金计算
                // 我的等级ID
                int myRankId = Integer.parseInt(rank.get("RANK_ID").toString());
                // 我的投资金额
                double amountSum = Double.parseDouble(record.get("MONEY").toString());
                if (amountSum < 50) {
                    continue;
                }
                // 获取自身每日分红、团队每日分红
                double myBonus = Double.parseDouble(rank.get("MY_STATIC_LUCRE").toString()) / 100;
                double teamBonus = Double.parseDouble(rank.get("TRAM_STATIC_LUCRE").toString()) / 100;
                // 计算自身收益
                double sumBonus = amountSum * myBonus;
                // 计算团队收益
                if (teamBonus > 0) {
                    // 获取直推下级
                    List<PageData> subUserAll = accountService.listByRecommender(map);
                    for (PageData sub : subUserAll) {
                        // 获取用户等级
                        PageData subRank = (PageData) applicati.getAttribute(Const.APP_RANK + sub.getString("USER_RANK"));
                        if (subRank != null && subRank.size() > 0) {
                            // 这个下级的用户等级id
                            int hisRankId = Integer.parseInt(subRank.get("RANK_ID").toString());
                            // 获取这个下级的投资金额
                            pd = lncome_detailsService.getNewOrderByUserId(sub);
                            if (pd == null || pd.size() <= 0) {
                                continue;
                            }
                            if (!"未出局".equals(pd.getString("STATE"))){
                                continue;
                            }
                            double subSumAmount = Double.parseDouble(pd.get("MONEY").toString());
                            // 获取他的自身利息、团队收益
                            double subBonus = Double.parseDouble(subRank.get("MY_STATIC_LUCRE").toString()) / 100;
                            double subTeamBonus = Double.parseDouble(subRank.get("TRAM_STATIC_LUCRE").toString()) / 100;
                            // 计算这个下级的自身每日奖励
                            double bouns = subSumAmount * subBonus;
                            // 产生平级奖
                            if (myRankId == hisRankId) {
                                // 调用多线程异步发放奖金
                                MyThreadPoolManager.getsInstance().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        levelBonusJob(map, sub, bouns, webctx);
                                    }
                                });
                                continue;
                            }

                            // 定义是否出局
                            boolean isout = false;
                            // 计算这个下级的极差
                            double profit = teamBonus - subTeamBonus;
                            // 发放极差奖
                            double money = Tools.afterDecimal(bouns * profit, 2);
                            System.out.println("我的直推下级自身产生的团队收益： " + Tools.afterDecimal(bouns * profit, 2));
                            if (money <= 0) {
                                continue;
                            }
                            PageData user = (PageData) applicati.getAttribute(map.getString("USER_NAME"));
                            // 获取用户已累积的动静态奖金
                            double acquired = Double.parseDouble(user.get("ACCUMULA").toString());
                            // 差多少出局
                            double differ = outBonus - acquired;
                            // 极差（动态）奖每日分红手续费
                            double serviceCharge = Double.parseDouble(par.getString("LEVEL_BONUS")) / 100;
                            // 计算手续费
                            double charge = money * serviceCharge;
                            money -= charge;
                            money = Tools.afterDecimal(money, 2);
                            if (differ <= money) {
                                money = differ;
                                // 更改状态 已出局 并清除 动静态累积
                                record.put("GMT_MODIFIED", DateUtil.getTime());
                                record.put("STATE", "已出局");
                                lncome_detailsService.edit(record);
                                pd.put("ACCUMULA", 0);
                                accountService.updateFor(pd);
                                isout = true;
                            } else {
                                // 未出局发放本次奖金
                                System.out.println("本次团队分红:" + money);
                                // 增加动静态累积
                                pd = new PageData();
                                pd.put("ACCOUNT_ID", map.get("ACCOUNT_ID"));
                                pd.put("money", money + charge);
                                accountService.addAccumula(pd);
                            }
                            // 调用 发放奖金
                            double finalMoney = money;
                            bonusJob(map, "團隊收益", finalMoney, charge, sub.getString("USER_NAME"), webctx);
                            // 出局后 循环下一个
                            if (isout) {
                                continue;
                            }

                            // 如果这个下级的团队收益大于0
                            if (subTeamBonus > 0) {
                                // 获取他的所有下级
                                List<PageData> hisSubUserAll = accountService.listByDownUserId(sub);
                                for (PageData hisSub : hisSubUserAll) {
                                    // 获取用户等级
                                    PageData hisSubRank = (PageData) applicati.getAttribute(Const.APP_RANK + hisSub.getString("USER_RANK"));
                                    if (hisSubRank != null && hisSubRank.size() > 0) {
                                        // 他的下级的用户等级id
                                        int hisSubRankId = Integer.parseInt(hisSubRank.get("RANK_ID").toString());
                                        if (hisSubRankId == hisRankId) {
                                            continue;
                                        }
                                        // 获取他的下级的投资金额
                                        pd = lncome_detailsService.getNewOrderByUserId(hisSub);
                                        if (pd == null || pd.size() <= 0) {
                                            continue;
                                        }
                                        if (!"未出局".equals(pd.getString("STATE"))){
                                            continue;
                                        }
                                        double hisSubSumAmount = Double.parseDouble(pd.get("MONEY").toString());
                                        // 获取他的下级的自身利息
                                        double hisSubBonus = Double.parseDouble(hisSubRank.get("MY_STATIC_LUCRE").toString()) / 100;
                                        // 计算他的下级的自身每日奖励
                                        double hisBouns = hisSubSumAmount * hisSubBonus;
                                        // 计算极差
                                        double hisProfit = teamBonus - subTeamBonus;
                                        // 发放极差奖
                                        double hisMoney = Tools.afterDecimal(hisBouns * hisProfit, 2);
                                        System.out.println("他的下级：" + hisSub.getString("USER_NAME") + "   产生团队收益：" + hisMoney);
                                        if (hisMoney <= 0) {
                                            continue;
                                        }
                                        user = (PageData) applicati.getAttribute(map.getString("USER_NAME"));
                                        // 获取用户已累积的动静态奖金
                                        acquired = Double.parseDouble(user.get("ACCUMULA").toString());
                                        // 差多少出局
                                        differ = outBonus - acquired;
                                        // 计算手续费
                                        charge = hisMoney * serviceCharge;
                                        hisMoney -= charge;
                                        if (differ <= hisMoney) {
                                            hisMoney = differ;
                                            // 更改状态 已出局 并清除 动静态累积
                                            record.put("GMT_MODIFIED", DateUtil.getTime());
                                            record.put("STATE", "已出局");
                                            lncome_detailsService.edit(record);
                                            pd.put("ACCUMULA", 0);
                                            accountService.updateFor(pd);
                                            // 表示已经出局
                                            isout = true;
                                        } else {
                                            // 未出局发放本次奖金
                                            System.out.println("本次团队分红:" + hisMoney);
                                            // 增加动静态累积
                                            pd = new PageData();
                                            pd.put("ACCOUNT_ID", map.get("ACCOUNT_ID"));
                                            pd.put("money", hisMoney + charge);
                                            accountService.addAccumula(pd);
                                        }
                                        // 调用 发放奖金
                                        bonusJob(map, "團隊收益", hisMoney, charge, hisSub.getString("USER_NAME"), webctx);
                                        // 出局后 直接结束循环
                                        if (isout) {
                                            break;
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
                /*下面的每日自身分红*/
                if (sumBonus <= 0) {
                    continue;
                }
                // 获取用户已累积的动静态奖金
                double acquired = Double.parseDouble(map.get("ACCUMULA").toString());
                // 每日分红手续费
                double serviceCharge = Double.parseDouble(par.getString("DAY_BONUS")) / 100;
                System.out.println("====> 每日手续费:" + serviceCharge);

                // 定义最终获得的利益和手续费
                double money, charge;
                // 计算手续费
                charge = sumBonus * serviceCharge;
                money = sumBonus - charge;
                // 差多少出局
                double differ = outBonus - acquired;
                pd = new PageData();
                pd.put("ACCOUNT_ID", map.get("ACCOUNT_ID"));
                // 出局 发放差额
                if (differ <= money) {
                    money = differ;
                    // 更改状态 已出局 并清除 动静态累积
                    record.put("GMT_MODIFIED", DateUtil.getTime());
                    record.put("STATE", "已出局");
                    lncome_detailsService.edit(record);
                    pd.put("ACCUMULA", 0);
                    accountService.updateFor(pd);
                } else {
                    // 未出局发放本次奖金
                    System.out.println("本次分红:" + money);
                    // 增加动静态累积
                    pd.put("money", money + charge);
                    accountService.addAccumula(pd);
                }

                // 调用多线程异步发放奖金
                double finalMoney = money;
                MyThreadPoolManager.getsInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        bonusJob(map, "分紅", finalMoney, charge, "", webctx);
                    }
                });

            }
            System.out.println("-----------> 奖金奖金发放完毕：" + DateUtil.getTime());
        } catch (Exception e) {
            System.out.println("静态奖金发放定时任务失败");
            e.printStackTrace();
        }

    }

    /**
     * 功能描述：平级奖发放
     *
     * @param user    我的用户信息
     * @param tihUser 我的这个下级的用户信息
     * @param amount  他的每日自身分红金额
     * @param webctx  web上下文
     * @author Ajie
     * @date 2020/1/10 0010
     */
    public void levelBonusJob(PageData user, PageData tihUser, double amount, WebApplicationContext webctx) {

        // 用户表
        AccountManager accountService = (AccountManager) webctx.getBean("accountService");
        // 投资记录表
        Lncome_detailsManager lncome_detailsService = (Lncome_detailsManager) webctx.getBean("lncome_detailsService");
        PageData pd = new PageData();
        double sumMoney = 0;
        try {
            int myRankId = Integer.parseInt(tihUser.get("RANK_ID").toString());
            double teamBonus = Double.parseDouble(tihUser.get("TRAM_STATIC_LUCRE").toString()) / 100;
            // 获取我这个下级的直推下级
            List<PageData> subUserAll = accountService.listByRecommender(tihUser);
            for (PageData sub : subUserAll) {
                // 获取用户等级
                PageData subRank = (PageData) applicati.getAttribute(Const.APP_RANK + sub.getString("USER_RANK"));
                if (subRank != null && subRank.size() > 0) {
                    // 这个下级的用户等级id
                    int hisRankId = Integer.parseInt(subRank.get("RANK_ID").toString());
                    // 获取这个下级的投资金额
                    pd = lncome_detailsService.getNewOrderByUserId(sub);
                    if (pd == null || pd.size() <= 0) {
                        continue;
                    }
                    if (!"未出局".equals(pd.getString("STATE"))){
                        continue;
                    }
                    double subSumAmount = Double.parseDouble(pd.get("MONEY").toString());
                    // 获取他的自身利息
                    double subBonus = Double.parseDouble(subRank.get("MY_STATIC_LUCRE").toString()) / 100;
                    // 计算这个下级的自身每日奖励
                    double bouns = subSumAmount * subBonus;
                    // 产生平级奖
                    if (myRankId == hisRankId) {
                        continue;
                    }
                    // 计算极差奖
                    double money = bouns * teamBonus;
                    if (money <= 0) {
                        continue;
                    }
                    sumMoney += money;
                }
            }
            sumMoney += amount;
            // 获取系统参数
            PageData par = (PageData) applicati.getAttribute(Const.PAR);
            // 获取后台设置的平级奖励百分比
            double communityAward = Double.parseDouble(par.get("COMMUNITY_AWARD").toString()) / 100;
            // 平级奖手续费比例
            double levelCharge = Double.parseDouble(par.get("LEVEL_CHARGE").toString()) / 100;
            // 计算奖励
            double bonus = sumMoney * communityAward;
            // 计算手续费
            double charge = bonus * levelCharge;
            bonus -= charge;
            checkOut(user, bonus, "平級", tihUser.getString("USER_NAME"), charge, webctx);
        } catch (Exception e) {
            System.out.println("平级奖金发放任务失败");
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：全球分红奖发放
     *
     * @param user   用户信息
     * @param ratio  分红比例
     * @param webctx web上下文
     * @author Ajie
     * @date 2020/1/10 0010
     */
    public void globalBonusJob(PageData user, double ratio, WebApplicationContext webctx) {

        // 投资记录表
        Lncome_detailsManager lncome_detailsService = (Lncome_detailsManager) webctx.getBean("lncome_detailsService");
        try {
            // 获取系统参数
            PageData par = (PageData) applicati.getAttribute(Const.PAR);
            // 根据成为拥有全球新增分红之后的时间查询 所有投资金额
            user.put("TIME", user.getString("GLOBAL_TIME"));
            PageData pd = lncome_detailsService.getAmountSumByTime(user);
            double allInvestment = Double.parseDouble(pd.get("ALL_INVESTMENT").toString());
            // 全球每日分红手续费
            double serviceCharge = Double.parseDouble(par.get("GLOBAL_DAY_BONUS").toString()) / 100;
            // 计算全球分红收益手续费 和 收益
            double charge = allInvestment * ratio * serviceCharge;
            double money = allInvestment * ratio - charge;
            System.out.println("全球投资累计：" + allInvestment + "本次获得全球分红收益：" + money);

            bonusJob(user, "全球分紅", money, charge, "", webctx);
        } catch (Exception e) {
            System.out.println("全球分红奖金发放任务失败");
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：给用户增加usdt 钱包累积并创建记录
     *
     * @param user   用户信息
     * @param type   奖金名称
     * @param amount 数额
     * @param charge 手续费
     * @param source 来源
     * @author Ajie
     * @date 2020/1/10 0010
     */
    public void bonusJob(PageData user, String type, double amount, double charge, String source, WebApplicationContext webctx) {
        try {
            // 累积钱包记录表
            Accum_recManager accum_recService = (Accum_recManager) webctx.getBean("accum_recService");
            // 用户表
            AccountManager accountService = (AccountManager) webctx.getBean("accountService");

            PageData pd = new PageData();
            pd.put("ACCOUNT_ID", user.get("ACCOUNT_ID"));
            // 更新usdt累积钱包余额
            String time = DateUtil.getTime();
            pd.put("GMT_MODIFIED", time);
            pd.put("tag", "+");
            pd.put("USDT_COUNT", amount);
            accountService.updateMoney(pd);
            // 更新缓存
            pd = accountService.findById(pd);
            applicati.setAttribute(pd.getString("USER_NAME"), pd);
            // 创建累积钱包账单记录
            pd = new PageData();
            pd.put("GMT_CREATE", time);
            pd.put("GMT_MODIFIED", time);
            pd.put("USER_NAME", user.getString("USER_NAME"));
            pd.put("USER_ID", user.get("ACCOUNT_ID"));
            // 金额类型
            pd.put("AMOUNT_TYPE", type);
            pd.put("TAG", "+");
            pd.put("MONEY", amount);
            // 钱包类型 0：XMC、1：USDT
            pd.put("WALLET_TYPE", 1);
            // 手续费
            pd.put("CHARGE", charge);
            pd.put("SOURCE", source);
            pd.put("ACCUM_REC_ID", this.get32UUID());
            accum_recService.save(pd);
        } catch (Exception e) {
            System.out.println("奖金发放任务失败");
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：检查是否出局
     *
     * @param pd        用户信息
     * @param amount    本次获得奖金数量
     * @param bonusType 奖金类型
     * @param source    来源
     * @param charge    手续费
     * @author Ajie
     * @date 2020/1/10 0002
     */
    public void checkOut(PageData pd, double amount, String bonusType, String source, double charge, WebApplicationContext webctx) {

        // 用户表
        AccountManager accountService = (AccountManager) webctx.getBean("accountService");
        // 投资记录表
        Lncome_detailsManager lncome_detailsService = (Lncome_detailsManager) webctx.getBean("lncome_detailsService");

        String userId = pd.get("ACCOUNT_ID").toString();
        // 获取用户已累积的动静态奖金
        double sumCount = Double.parseDouble(pd.get("ACCUMULA").toString());
        try {
            // 获取最新的投资记录
            PageData record = lncome_detailsService.getNewOrderByUserId(pd);
            // 取出局收益
            double outBonus = Double.parseDouble(record.get("PROFIT").toString());
            // 差多少出局
            double differ = outBonus - sumCount;
            // 如果未出局 发放本次奖金
            if (differ > amount) {
                bonusJob(pd, bonusType, amount, charge, source, webctx);
                // 增加动静态累积
                pd.put("money", amount);
                accountService.addAccumula(pd);
            } else {
                // 出局 发放差额
                bonusJob(pd, bonusType, differ, charge, source, webctx);
                // 更改状态 已出局 并清除 动静态累积
                pd = new PageData();
                pd.put("ACCOUNT_ID", userId);
                pd.put("STATE", "已出局");
                lncome_detailsService.edit(record);
                pd.put("ACCUMULA", 0);
                accountService.updateFor(pd);
            }
            // 更新缓存
            pd = accountService.findById(pd);
            applicati.setAttribute(pd.getString("USER_NAME"), pd);
        } catch (Exception e) {
            System.out.println("检查是否出局任务失败");
            e.printStackTrace();
        }


    }

}
