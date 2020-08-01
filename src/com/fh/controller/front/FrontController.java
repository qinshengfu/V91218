package com.fh.controller.front;

import com.fh.controller.base.BaseController;
import com.fh.entity.MemUser;
import com.fh.service.front.AccountManager;
import com.fh.service.front.RankManager;
import com.fh.service.record.*;
import com.fh.util.*;
import com.fh.util.pool.MyThreadPoolManager;
import com.fh.util.timers.ErcBuyTaskJob;
import com.fh.util.timers.ErcSellTaskJob;
import com.fh.util.timers.StaticBonusTaskJob;
import com.fh.util.timers.ValidityTaskJob;
import com.fh.util.verificationCode.EmailVerificaCodeUtil;
import com.fh.util.verificationCode.RandomCodeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述：前台页面登录后需要跳转的页面
 *
 * @author Ajie
 * @date 2019/11/26 0026
 */
@Controller
@RequestMapping(value = "/front")
@Slf4j
public class FrontController extends BaseController {

    // 前台用户
    @Resource(name = "accountService")
    private AccountManager accountService;
    // 等级表
    @Resource(name = "rankService")
    private RankManager rankService;
    // 日线图
    @Resource(name = "daily_chartService")
    private Daily_chartManager daily_chartService;
    // 对冲钱包记录
    @Resource(name = "hedging_recService")
    private Hedging_recManager hedging_recService;
    // 投资记录
    @Resource(name = "lncome_detailsService")
    private Lncome_detailsManager lncome_detailsService;
    // 投资列表
    @Resource(name = "lnvestment_listService")
    private Lnvestment_listManager lnvestment_listService;
    // 关于我们
    @Resource(name = "sys_aboutService")
    private Sys_AboutManager sys_aboutService;
    // 轮播图
    @Resource(name = "sys_chartService")
    private Sys_chartManager sys_chartService;
    // 新闻公告
    @Resource(name = "sys_newsService")
    private Sys_newsManager sys_newsService;
    // USDT钱包记录
    @Resource(name = "usdt_wallet_recService")
    private USDT_wallet_RECManager usdt_wallet_recService;
    // 幸运大转盘
    @Resource(name = "wheel_fortuneService")
    private Wheel_fortuneManager wheel_fortuneService;
    // XMC钱包记录
    @Resource(name = "xmc_wallet_recService")
    private XMC_wallet_RECManager xmc_wallet_recService;
    // 抽奖记录
    @Resource(name = "luckydrawrecService")
    private LuckyDrawRecManager luckydrawrecService;
    // 理财券列表
    @Resource(name = "voucher_listService")
    private Voucher_listManager voucher_listService;
    // 留言反馈
    @Resource(name = "message_feedbackService")
    private Message_FeedbackManager message_feedbackService;
    // 法币交易之购买
    @Resource(name = "ercbuyService")
    private ErcBuyManager ercbuyService;
    // 法币交易之出售
    @Resource(name = "ercsellService")
    private ErcSellManager ercsellService;
    // 法币交易之购买记录
    @Resource(name = "ercbuyrecordService")
    private ErcBuyRecordManager ercbuyrecordService;
    // 法币交易之出售记录
    @Resource(name = "ercsellrecordService")
    private ErcSellRecordManager ercsellrecordService;
    // 币币交易记录
    @Resource(name = "coin_trading_recService")
    private Coin_trading_recManager coin_trading_recService;
    // 累积钱包账单记录
    @Resource(name = "accum_recService")
    private Accum_recManager accum_recService;


    /**
     * 功能描述：访问【首页】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_index")
    private ModelAndView toIndex() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 获取投资列表
        List<PageData> investList = (List<PageData>) applicati.getAttribute(Const.APP_LNVESTMENT);
        PageData pd = new PageData();
        pd.put("USER_ID", user.getACCOUNT_ID());
        // 获取已购买列表
        List<PageData> buyList = lncome_detailsService.listByUserId(pd);
        // 新闻公告
        List<PageData> newsList = (List<PageData>) applicati.getAttribute(Const.APP_NEWS);
        // 轮播图
        List<PageData> chartList = (List<PageData>) applicati.getAttribute(Const.APP_CHART);
        // 理财券列表
        List<PageData> voucherList = voucher_listService.listByUserId(pd);
        // 获取所有等级
        List<PageData> rankAll = rankService.listAll(null);
        // 定义最小值和最大值为该数组的第一个数
        int min = Integer.parseInt(rankAll.get(0).get("MIN_COST").toString());
        int max = Integer.parseInt(rankAll.get(0).get("MIN_COST").toString());
        for (int i = 0; i < rankAll.size(); i++) {
            //  如果是后台指派的就跳过
            if ("1".equals(rankAll.get(i).getString("IS_ASSIGNED"))) {
                continue;
            }
            int min1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            int max1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            if (min > min1) {
                min = min1;
            }
            if (max < max1) {
                max = max1;
            }
        }
        pd.put("min", min);
        pd.put("max", max);

        mv.setViewName("front/index/home");
        mv.addObject("investList", investList);
        mv.addObject("buyList", buyList);
        mv.addObject("newsList", newsList);
        mv.addObject("chartList", chartList);
        mv.addObject("voucherList", voucherList);
        mv.addObject("pd", pd);
        mv.addObject("tag", "index");
        return mv;
    }

    /**
     * 功能描述：访问前台【新闻公告】页面
     *
     * @author Ajie
     * @date 2019-12-25
     */
    @RequestMapping(value = "to_news")
    private ModelAndView toNews() {
        ModelAndView mv = getModelAndView();
        // 新闻公告
        List<PageData> newsList = (List<PageData>) applicati.getAttribute(Const.APP_NEWS);
        mv.setViewName("front/index-ui/news");
        mv.addObject("newsList", newsList);
        return mv;
    }

    /**
     * 功能描述：访问前台【新闻详情】页面
     *
     * @author Ajie
     * @date 2019-12-25
     */
    @RequestMapping(value = "to_newsDetails")
    private ModelAndView toNewsDetails() throws Exception {
        ModelAndView mv = getModelAndView();
        // 获取传过来的新闻ID
        PageData news = this.getPageData();
        news = sys_newsService.findById(news);
        mv.setViewName("front/index-ui/news-details");
        mv.addObject("news", news);
        return mv;
    }

    /**
     * 功能描述：访问【投资列表】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_bill")
    private ModelAndView toInves() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        // 从session 获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("USER_ID", user.getACCOUNT_ID());
        // 获取已购买列表
        List<PageData> buyList = lncome_detailsService.listByUserId(pd);
        mv.setViewName("front/my/bill");
        mv.addObject("buyList", buyList);
        return mv;
    }

    /**
     * 功能描述：访问【应用页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_app")
    private ModelAndView toApp() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/my/app");
        mv.addObject("tag", "app");
        return mv;
    }

    /**
     * 功能描述：访问【法币交易页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_legalTrade")
    private ModelAndView toTrans() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 查询所有余额大于0 的列表
        List<PageData> buyList = ercbuyService.listAll(null);
        List<PageData> sellList = ercsellService.listAll(null);
        // 查询实名认证是否设置完毕
        String result = checkRealName();
        if ("success".equals(result)) {
            mv.addObject("status", 1);
        } else {
            mv.addObject("status", result);
        }

        mv.setViewName("front/index-ui/legalTrade");
        // 这个标识是 页脚导航栏的图标亮起
        mv.addObject("tag", "trans");

        mv.addObject("buyList", buyList);
        mv.addObject("sellList", sellList);
        return mv;
    }

    /**
     * 功能描述：访问【法币交易订单页面】
     *
     * @author Ajie
     * @date 2020-1-3 15:41:44
     */
    @RequestMapping("/to_legalOrder")
    private ModelAndView toLegalOrder() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = (PageData) applicati.getAttribute(user.getUSER_NAME());

        // 查表查询法币交易的订单列表
        List<PageData> buyList = ercbuyrecordService.listByUsdtId(pd);
        List<PageData> sellList = ercsellrecordService.listByUsdtId(pd);
        List<PageData> orderList = new ArrayList<>();
        orderList.addAll(buyList);
        orderList.addAll(sellList);
        //时间降序排序
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderList.sort((PageData m1, PageData m2) -> {
            try {
                // 排序，首先将字符型的日期转换为日期类型。
                return df.parse(m2.getString("GMT_CREATE")).compareTo(df.parse(m1.getString("GMT_CREATE")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 1;
        });
        mv.setViewName("front/index-ui/legalOrder");
        mv.addObject("orderList", orderList);
        return mv;
    }


    /**
     * 功能描述：访问【币币交易页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_coinTrade")
    private ModelAndView toCoinTrade() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 日线图
        List<PageData> chartList = daily_chartService.listAll(null);
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 获取不是我发布的购买和出售列表
        List<PageData> orderList = coin_trading_recService.listByState(user);
        // 最新单价
        String price = null;
        if (chartList.size() > 0) {
            price = chartList.get(chartList.size() - 1).get("LATEST_PRICE").toString();
        }
        mv.setViewName("front/index-ui/coinTrade");
        mv.addObject("tag", "trans");
        mv.addObject("chartList", chartList);
        mv.addObject("user", user);
        mv.addObject("dayPrice", price);
        mv.addObject("orderList", orderList);
        return mv;
    }

    /**
     * 功能描述：访问【币币交易订单页面】
     *
     * @author Ajie
     * @date 2020-1-3 15:41:44
     */
    @RequestMapping("/to_coinOrder")
    private ModelAndView toCoinOrder() throws Exception {
        ModelAndView mv = this.getModelAndView();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = (PageData) applicati.getAttribute(user.getUSER_NAME());
        // 获取我交易的订单列表
        List<PageData> orderList = coin_trading_recService.listByMyTrade(pd);
        mv.setViewName("front/index-ui/coinOrder");
        mv.addObject("orderList", orderList);
        return mv;
    }

    /**
     * 功能描述：访问【我的发布的币币交易订单页面】
     *
     * @author Ajie
     * @date 2020-1-3 15:41:44
     */
    @RequestMapping("/to_myCoinOrder")
    private ModelAndView toMyCoinOrder() throws Exception {
        ModelAndView mv = this.getModelAndView();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = (PageData) applicati.getAttribute(user.getUSER_NAME());
        // 获取我发布的订单列表
        List<PageData> orderList = coin_trading_recService.listByMySale(pd);
        mv.setViewName("front/index-ui/coinMyOrder");
        mv.addObject("orderList", orderList);
        return mv;
    }

    /**
     * 功能描述：访问【日线图】
     *
     * @author Ajie
     * @date 2019/12/28 0025
     */
    @RequestMapping("/to_chart")
    private ModelAndView toChart() throws Exception {
        ModelAndView mv = this.getModelAndView();
        List<PageData> chartList = daily_chartService.listAll(null);
        mv.setViewName("front/index-ui/chart");
        mv.addObject("chartList", chartList);
        return mv;
    }

    /**
     * 功能描述：访问【钱包页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_wallet")
    private ModelAndView toWallet() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 从session 获取当前登录用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        PageData pd = new PageData();
        pd.put("USER_ID", user.get("ACCOUNT_ID"));
        // 理财券列表
        List<PageData> voucherList = voucher_listService.listByUserId(pd);
        // 获取最新的一条投资记录
        pd = lncome_detailsService.getNewOrderByUserId(user);
        if (pd != null) {
            // 获取用户已累积的动静态奖金
            double acquired = Double.parseDouble(user.get("ACCUMULA").toString());
            if (!"未出局".equals(pd.getString("STATE"))) {
                pd.put("PROFIT", 0);
            } else {
                double profit = Double.parseDouble(pd.get("PROFIT").toString());
                pd.put("PROFIT", Tools.afterDecimal(profit - acquired, 2));
            }
        } else {
            pd = new PageData();
            pd.put("PROFIT", 0);
        }
        // 获取投资列表
        List<PageData> investList = (List<PageData>) applicati.getAttribute(Const.APP_LNVESTMENT);
        // 获取所有等级
        List<PageData> rankAll = rankService.listAll(null);
        // 定义最小值和最大值为该数组的第一个数
        int min = Integer.parseInt(rankAll.get(0).get("MIN_COST").toString());
        int max = Integer.parseInt(rankAll.get(0).get("MIN_COST").toString());
        for (int i = 0; i < rankAll.size(); i++) {
            //  如果是后台指派的就跳过
            if ("1".equals(rankAll.get(i).getString("IS_ASSIGNED"))) {
                continue;
            }
            int min1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            int max1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            if (min > min1) {
                min = min1;
            }
            if (max < max1) {
                max = max1;
            }
        }
        pd.put("min", min);
        pd.put("max", max);
        mv.setViewName("front/my/wallet");
        mv.addObject("tag", "wallet");
        mv.addObject("voucherList", voucherList);
        mv.addObject("user", user);
        mv.addObject("pd", pd);
        mv.addObject("investList", investList);
        return mv;
    }

    /**
     * 功能描述：访问【XMC钱包账单页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_XmcwalletBill")
    private ModelAndView toXmcWalletBill() throws Exception {
        ModelAndView mv = this.getModelAndView();
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = new PageData();
        pd.put("USER_ID", memUser.getACCOUNT_ID());
        List<PageData> billList = xmc_wallet_recService.listAllByUserId(pd);
        mv.setViewName("front/my/xmcBill");
        mv.addObject("billList", billList);
        return mv;
    }

    /**
     * 功能描述：访问【UDST钱包账单页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_UsdtWalletBill")
    private ModelAndView toUsdtWalletBill() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/my/usdtBill");
        return mv;
    }

    /**
     * 功能描述：访问【UDST钱包账单页面】页面 分页查询
     *
     * @author Ajie
     * @date 2020/1/8 0008
     */
    @RequestMapping(value = "to_UsdtWalletBillPage")
    @ResponseBody
    private Object toProfitPage() throws Exception {
        Pager<PageData> pager = new Pager<>();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = this.getPageData();
        // 页码
        int num = Integer.parseInt(pd.get("num").toString());
        // 每页数据条数
        int pageSize = Integer.parseInt(pd.get("size").toString());
        pd.put("USER_ID", user.getACCOUNT_ID());
        // 得到全部数据
        List<PageData> billList = usdt_wallet_recService.listAllByUserId(pd);
        // 第 N 页
        pager.setCurentPageIndex(num);
        // 每页 N 条
        pager.setCountPerpage(pageSize);
        pager.setBigList(billList);
        // 得到小的集合(分页的当前页的记录)
        List<PageData> curPageData = pager.getSmallList();
        // 得到总页数
        int totalPage = pager.getPageCount();
        pd.put("curPageData", curPageData);
        pd.put("totalPage", totalPage);
        return pd;
    }

    /**
     * 功能描述：访问【对冲钱包账单页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_heWalletBill")
    private ModelAndView toHeWalletBill() throws Exception {
        ModelAndView mv = this.getModelAndView();
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = new PageData();
        pd.put("USER_ID", memUser.getACCOUNT_ID());
        List<PageData> billList = hedging_recService.listAllByUserId(pd);
        mv.setViewName("front/my/heBill");
        mv.addObject("billList", billList);
        return mv;
    }


    /**
     * 功能描述：访问【累积钱包账单页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_countWalletBill")
    private ModelAndView toCountWalletBill() throws Exception {
        ModelAndView mv = this.getModelAndView();
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData pd = new PageData();
        pd.put("USER_ID", memUser.getACCOUNT_ID());
        List<PageData> billList = accum_recService.listAllByUserId(pd);
        List<PageData> resultList = new ArrayList<PageData>();
        for (PageData map : billList) {
            String time = map.getString("GMT_CREATE");
            map.put("GMT_CREATE", time.substring(time.length() - 8));
            resultList.add(map);
        }
        mv.setViewName("front/my/countBill");
        mv.addObject("billList", resultList);
        return mv;
    }

    /**
     * 功能描述：访问【XMC钱包充值页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_walletRecharge")
    private ModelAndView toWalletRecharge() {
        ModelAndView mv = this.getModelAndView();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData appUser = (PageData) applicati.getAttribute(user.getUSER_NAME());
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        mv.setViewName("front/my/recharge");
        mv.addObject("pd", appUser);
        mv.addObject("par", par);
        return mv;
    }

    /**
     * 功能描述：访问【USDT钱包充值页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_UsdtWalletRecharge")
    private ModelAndView toUsdtWalletRecharge() {
        ModelAndView mv = this.getModelAndView();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        mv.setViewName("front/my/usdtRecharge");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【USDT钱包充值记录页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_UsdtWalletRecord")
    private ModelAndView toUsdtWalletRecord() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/index-ui/usdtRecord");
        return mv;
    }

    /**
     * 功能描述：访问【USDT钱包提现记录页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_usdtWalletCash")
    private ModelAndView toWalletRecord() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("USER_ID", user.getACCOUNT_ID());
        pd.put("type", "'提幣'");
        List<PageData> list = usdt_wallet_recService.listAllByUserId(pd);
        mv.setViewName("front/index-ui/usdtCashRec");
        mv.addObject("list", list);
        return mv;
    }

    /**
     * 功能描述：访问【XMC钱包提现页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_walletCash")
    private ModelAndView toWalletCash() {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/index-ui/cash");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【XMC钱包提现记录页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_xmcWalletCash")
    private ModelAndView toxmcWalletRecord() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("USER_ID", user.getACCOUNT_ID());
        pd.put("type", "'提幣'");
        List<PageData> list = xmc_wallet_recService.listAllByUserId(pd);
        mv.setViewName("front/index-ui/cashRec");
        mv.addObject("list", list);
        return mv;
    }

    /**
     * 功能描述：访问【USDT钱包提现页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_UsdtWalletCash")
    private ModelAndView toUsdtWalletCash() {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/index-ui/usdtCash");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【对冲钱包划入页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_heWalletEnterIn")
    private ModelAndView toHeWalletEnterIn() {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/index-ui/cut-in");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【累积钱包划出页面】
     *
     * @author Ajie
     * @date 2020年1月9日
     */
    @RequestMapping("/to_countWalletEnterOut")
    private ModelAndView toCountWalletEnterOut() {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/index-ui/count-out");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【转账页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_transfer")
    private ModelAndView toTransfer() {
        ModelAndView mv = this.getModelAndView();
        //  从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/index-ui/transfer");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【我的页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_my")
    private ModelAndView toMy() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/index/main");
        // 从缓存获取当前登录用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        PageData rank = null;
        if (Tools.notEmpty(user.getString("USER_RANK"))) {
            // 获取用户等级
            rank = (PageData) applicati.getAttribute(Const.APP_RANK + user.get("USER_RANK").toString());
        }
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 查询实名认证是否设置完毕
        String result = checkRealName();
        if ("success".equals(result)) {
            mv.addObject("status", 1);
        } else {
            mv.addObject("status", result);
        }
        mv.addObject("tag", "my");
        mv.addObject("user", user);
        mv.addObject("rank", rank);
        mv.addObject("par", par);
        return mv;
    }

    /**
     * 功能描述：访问【大转盘抽奖页面】
     *
     * @author Ajie
     * @date 2019/12/25 0026
     */
    @RequestMapping(value = "/to_turntable")
    private ModelAndView toTurntable() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 从session获取用户信息 然后从服务器缓存获取用户最新信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 获取轮盘商品列表
        List<PageData> allPrize = (List<PageData>) applicati.getAttribute(Const.APP_WHEEL);
        // 获取中奖记录列表
        List<PageData> luckList = luckydrawrecService.listAll(null);
        mv.setViewName("front/index-ui/turntable");
        mv.addObject("goodsList", allPrize);
        mv.addObject("luckList", luckList);
        mv.addObject("user", user);
        mv.addObject("par", par);
        return mv;
    }

    /**
     * 功能描述：访问【邀请好友页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_share")
    private ModelAndView toInviteFriends() {
        ModelAndView mv = this.getModelAndView();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        mv.setViewName("front/my/share");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【安全中心页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_safetyCenter")
    private ModelAndView toSafetyCenter() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/my/safetyCenter");
        return mv;
    }

    /**
     * 功能描述：访问【修改登录密码页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_changeLoginPass")
    private ModelAndView toChangeLoginPass() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/my/loginPass");
        return mv;
    }

    /**
     * 功能描述：访问【修改安全密码页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_changeSafePass")
    private ModelAndView toChangeSafePass() {
        ModelAndView mv = this.getModelAndView();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData appUser = (PageData) applicati.getAttribute(user.getUSER_NAME());
        mv.setViewName("front/my/safePass");
        mv.addObject("user", appUser);
        return mv;
    }

    /**
     * 功能描述：访问【我的团队页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_team")
    private ModelAndView toTeam() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        // 查找直接推荐人是我的 所有下级
        List<PageData> userList = accountService.listByRecommender(pd);
        mv.setViewName("front/my/team");
        mv.addObject("userList", userList);
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【钱包地址页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_walletAdress")
    private ModelAndView toWalletAdress() {
        ModelAndView mv = this.getModelAndView();
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/my/address");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【关于我们页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_aboutUs")
    private ModelAndView toAboutUs() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/my/about");
        return mv;
    }

    /**
     * 功能描述：访问【理财券页面】
     *
     * @author Ajie
     * @date 2020年1月6日16:11:13
     */
    @RequestMapping("/to_financial")
    private ModelAndView toFinancial() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 查询理财券列表
        PageData pd = new PageData();
        pd.put("USER_ID", user.getACCOUNT_ID());
        List<PageData> list = voucher_listService.listByUserId(pd);
        mv.setViewName("front/my/financial");
        mv.addObject("list", list);
        return mv;
    }

    /**
     * 功能描述：访问【用户协议页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_userAgreement")
    private ModelAndView toUserAgreement() throws Exception {
        ModelAndView mv = this.getModelAndView();
        List<PageData> protocolList = sys_aboutService.listAll(null);
        mv.setViewName("front/index-ui/protocol");
        mv.addObject("list", protocolList);
        return mv;
    }

    /**
     * 功能描述：访问【历史版本页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_historyVersion")
    private ModelAndView toHistoryVersion() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("");
        return mv;
    }

    /**
     * 功能描述：访问【我的信息页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_myInfo")
    private ModelAndView toMyInfo() {
        ModelAndView mv = this.getModelAndView();
        // 从缓存获取当前登录用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/my/myInfo");
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：访问【留言页面】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_messaeg")
    private ModelAndView toMessaeg() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("front/my/message");
        return mv;
    }

    /**
     * 功能描述：访问【法币交易之购买订单详情页（付款页）】
     *
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping("/to_buyOrder")
    private ModelAndView toBuyOrderDetails() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 获取前台传过来的订单ID 和 数量
        PageData pd = this.getPageData();
        double num = Double.parseDouble(pd.getString("num"));
        String orderId = pd.getString("ERCBUYRECORD_ID");
        // 根据出售商品的订单id查信息
        pd = ercbuyService.findById(pd);
        PageData orderRec = new PageData();
        orderRec.put("ERCBUYRECORD_ID", orderId);
        // 根据生成的这条订单记录查询信息
        orderRec = ercbuyrecordService.findById(orderRec);
        // 取这条订单的创建时间
        String time = orderRec.getString("GMT_CREATE");
        // 限制N分钟内付款
        String minute = "30";
        time = DateUtil.getAddMinuteDate(time, minute);
        pd.put("time", time);
        mv.setViewName("front/index-ui/orderBuyDetails");
        mv.addObject("num", num);
        mv.addObject("pd", pd);
        mv.addObject("orderId", orderId);
        return mv;
    }

    /**
     * 功能描述：访问【法币交易之出售订单详情页（确认页）】
     *
     * @author Ajie
     * @date 2020-1-3 10:35:05
     */
    @RequestMapping("/to_SellOrder")
    private ModelAndView toSellOrderDetails() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 获取前台传过来的订单ID 和 数量
        PageData pd = this.getPageData();
        double num = Double.parseDouble(pd.getString("num"));
        String orderId = pd.getString("ERCSELLRECORD_ID");
        // 根据出售商品的订单id查信息
        pd = ercsellService.findById(pd);
        PageData orderRec = new PageData();
        orderRec.put("ERCSELLRECORD_ID", orderId);
        // 根据生成的这条订单记录查询信息
        orderRec = ercsellrecordService.findById(orderRec);
        // 取这条订单的创建时间
        String time = orderRec.getString("GMT_CREATE");
        // 限制N分钟内付款
        String minute = "30";
        time = DateUtil.getAddMinuteDate(time, minute);
        pd.put("time", time);
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        mv.setViewName("front/index-ui/orderSellDetails");
        mv.addObject("num", num);
        mv.addObject("pd", pd);
        mv.addObject("orderId", orderId);
        mv.addObject("user", user);
        return mv;
    }

    /**
     * 功能描述：发送邮箱验证码
     *
     * @author Ajie
     * @date 2019-12-25
     */
    @RequestMapping(value = "/sendEmail", produces = "text/html;charset=UTF-8")
    public String sendEmail() throws Exception {
        // 从session缓存获取当前登录用户
        Session session = Jurisdiction.getSession();
        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
        if (Tools.isEmpty(user.getMAILBOX())) {
            return "请先绑定邮箱";
        }
        // 目标邮箱
        String email = "1243206485@qq.com";
        // 有效期
        int validity = 5;
        String time = validity + "分钟";
        // 获取六位随机数字验证码
        String code = RandomCodeUtil.getInvitaCode(6, 1);
        EmailVerificaCodeUtil.setEmail(email, time, code);
        // 放入session中
        session.setAttribute(Const.SESSION_EMAIL_CHECK_CODE, code);
        log.info("本次邮箱验证码：{}", code);
        // TimerTask实现N分钟后从session中删除验证码
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                session.removeAttribute(Const.SESSION_EMAIL_CHECK_CODE);
                System.out.println("邮箱验证码--> 删除成功");
                timer.cancel();
            }
        }, validity * 60 * 1000);
        return "success";
    }

    /**
     * 功能描述：请求修改登录密码
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping(value = "/updateLoginPass", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String checkLoginPass() {
        PageData pd = this.getPageData();
        if (pd.size() != 3) {
            return "参数不足";
        }
        // 获取前台传过来的旧密码、新密码、确认新密码、验证码
        String oidpass = StringUtil.applySha256(pd.getString("oidPass"));
        String newPass = StringUtil.applySha256(pd.getString("newPass"));
        String confirmPass = StringUtil.applySha256(pd.getString("confirmPass"));
        // String verifyCode = StringUtil.applySha256(pd.getString("verifyCode"));
        // 从session缓存获取当前登录用户
        Session session = Jurisdiction.getSession();
        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
        /*// 缓存中的验证码
        String sessionCode = (String) session.getAttribute(Const.SESSION_EMAIL_CHECK_CODE);
        if (!sessionCode.equalsIgnoreCase(verifyCode)) {
            return "验证码错误";
        }*/
        if (!oidpass.equals(user.getLOGIN_PASSWORD())) {
            return "旧密码错误";
        }
        if (!newPass.equals(confirmPass)) {
            return "新密码不一致";
        }
        // 更新session
        user.setLOGIN_PASSWORD(newPass);
        session.setAttribute(Const.SESSION_MEMUSER, user);
        // 调用多线程异步修改密码
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-修改登录密码");
                updateLoginPassword(newPass, user.getACCOUNT_ID(), "0");
            }
        });
        return "success";
    }

    /**
     * 功能描述：请求修改安全密码
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/25 0025
     */
    @RequestMapping(value = "/updateSecurityPass", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String checkSecurityPass() {
        PageData pd = this.getPageData();
        if (pd.size() != 3) {
            return "参数不足";
        }
        // 获取前台传过来的新密码、确认新密码、验证码
        String newPass = StringUtil.applySha256(pd.getString("newPass"));
        String confirmPass = StringUtil.applySha256(pd.getString("confirmPass"));
        String verifyCode = pd.getString("verifyCode");
        // 从session缓存获取当前登录用户
        Session session = Jurisdiction.getSession();
        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
        PageData appUser = (PageData) applicati.getAttribute(user.getUSER_NAME());
        // 缓存中的验证码
        String sessionCode = (String) session.getAttribute(Const.SESSION_EMAIL_CHECK_CODE);
        // 缓存中 发送验证码的邮箱
        PageData info = (PageData) session.getAttribute(Const.SESSION_MAILBOX);
        if (!info.getString("MAILBOX").equals(appUser.getString("MAILBOX"))) {
            return "非法請求";
        }
        if (!sessionCode.equalsIgnoreCase(verifyCode)) {
            return "验证码错误";
        }
        if (!newPass.equals(confirmPass)) {
            return "新密码不一致";
        }
        // 更新session
        user.setSECURITY_PASSWORD(newPass);
        session.setAttribute(Const.SESSION_MEMUSER, user);
        // 调用多线程异步修改密码
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-修改安全密码");
                updateLoginPassword(newPass, user.getACCOUNT_ID(), "1");
            }
        });
        return "success";
    }

    /**
     * 功能描述：修改密码
     *
     * @param newPass 新密码
     * @param userId  用户id
     * @param tag     密码类型 为空: 登录密码和安全密码 0：登录密码 1：安全密码
     * @author Ajie
     * @date 2019/12/25 0025
     */
    private void updateLoginPassword(String newPass, String userId, String tag) throws Exception {
        PageData pd = new PageData();
        // 如果等于 null 就更改登录和安全密码
        if (Tools.isEmpty(tag)) {
            pd.put("LOGIN_PASSWORD", newPass);
            pd.put("SECURITY_PASSWORD", newPass);
        }
        if ("0".equals(tag)) {
            pd.put("LOGIN_PASSWORD", newPass);
        }
        if ("1".equals(tag)) {
            pd.put("SECURITY_PASSWORD", newPass);
        }
        pd.put("ACCOUNT_ID", userId);
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：抽奖接口
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/24 0024
     */
    @RequestMapping(value = "/doLottery")
    @ResponseBody
    public Object checkLuckDraw() throws Exception {
        // 定义反馈信息 status 0:失败 1 ：成功
        PageData result = new PageData();
        result.put("status", 0);
        String msg;
        // 获取前台传过来的用户名
        PageData pd = this.getPageData();
        String userName = pd.getString("userName");
        // 从session获取当前登录用户
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 验证是否本人操作
        if (pd.size() != 1 || !user.getUSER_NAME().equals(userName)) {
            msg = "非法参数";
            result.put("msg", msg);
            return result;
        }
        // 从缓存获取用户信息
        pd = (PageData) applicati.getAttribute(userName);
        if (null == pd) {
            msg = "用户不存在";
            result.put("msg", msg);
            return result;
        }
        // 验证XMC余额 可抽奖次数
        double xmcNum = Double.parseDouble(pd.get("XMC_WALLET").toString());
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 每次抽奖需要的xmc
        double xmcCost = Double.parseDouble(par.get("XMC_COST").toString());
        if (xmcNum / xmcCost < 1) {
            msg = "XMC不足 " + xmcCost;
            result.put("msg", msg);
            return result;
        }
        // 调用抽奖方法
        result = startLuckDraw(xmcNum, xmcCost);
        return result;
    }

    /**
     * 功能描述：开始抽奖
     *
     * @param xmcNum  xmc余额
     * @param xmcCost 每次抽奖需要的成本
     * @return 返回结果
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private PageData startLuckDraw(double xmcNum, double xmcCost) throws Exception {
        PageData pd = new PageData();
        // 从session获取当前登录用户
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 获取所有奖品
        List<PageData> allPrize = wheel_fortuneService.listAll(null);
        //构造概率集合
        List<Double> list = new ArrayList<Double>();
        for (PageData map : allPrize) {
            list.add(Double.parseDouble(map.getString("WINNING_PROBABILITY")));
        }
        LotteryUtil lottery = new LotteryUtil(list);
        // 开始抽奖
        int index = lottery.randomColunmIndex();
        // 记录成功
        pd.put("status", 1);
        pd.put("msg", index + 1);
        // 商品名称、数量
        String name = allPrize.get(index).getString("NAME_GOODS");
        String num = allPrize.get(index).getString("NUM_OR_AGIO");
        // 是否为理财券 0：不是、1：是
        String isVoucher = allPrize.get(index).getString("IS_VOUCHER");
        // 调用多线程更新数据库
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-创建抽奖等记录并更新余额");
                PageData pd = new PageData();
                // xmc钱包 减去 本次抽奖消耗的XMC数
                pd.put("GMT_MODIFIED", DateUtil.getTime());
                pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
                pd.put("tag", "-");
                pd.put("XMC_WALLET", xmcCost);
                accountService.updateMoney(pd);
                // 更新缓存
                pd = accountService.findById(pd);
                applicati.setAttribute(pd.getString("USER_NAME"), pd);
                // 创建一条xmc钱包账单记录
                addXmcWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "抽奖", "-", String.valueOf(xmcCost), "已完成", "");
                addWinningRecord(user.getUSER_NAME(), user.getACCOUNT_ID(), name, isVoucher, num);
            }
        });
        return pd;

    }

    /**
     * 功能描述：添加抽奖记录并更新余额
     *
     * @param userName  用户名
     * @param userId    用户id
     * @param goods     商品名
     * @param isVoucher 是否为理财券 0：不是、1：是
     * @param amount    数量or折扣率
     * @author Ajie
     * @date 2019/12/24 0024
     */
    private void addWinningRecord(String userName, String userId, String goods, String isVoucher, String amount) throws Exception {
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        // 创建中奖记录
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 定义商品名称
        String goodsName;
        if (goods.contains("谢谢")) {
            goodsName = goods;
        } else {
            goodsName = amount + goods;
        }
        pd.put("NAME_GOODS", goodsName);
        pd.put("LUCKYDRAWREC_ID", this.get32UUID());
        luckydrawrecService.save(pd);
        // 执行用户钱包相关操作
        pd = new PageData();
        // 判断是否为理财券  0：不是、1：是
        if ("1".equals(isVoucher)) {
            // 增加一张理财券
            addVoucher(goodsName, userName, userId, amount);
            // 如果是理财券 说明不会是XMC 直接返回即可
            return;
        }
        // 判断是否为XMC
        if (goods.contains("XMC")) {
            // 给xmc钱包加钱 和 xmc累积钱包加钱
            addXmcMoney(userId, amount);
            // 给XMC钱包增加一条记录
            addXmcWalletRec(userName, userId, "抽奖", "+", amount, "已完成", "");
        }
    }

    /**
     * 功能描述：给xmc钱包加钱
     *
     * @param userId 用户id
     * @param amount 数额
     * @author Ajie
     * @date 2019/12/28 0028
     */
    private void addXmcMoney(String userId, String amount) throws Exception {
        PageData pd = new PageData();
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("ACCOUNT_ID", userId);
        pd.put("tag", "+");
        pd.put("XMC_WALLET", amount);
        accountService.updateMoney(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：减少xmc钱包余额
     *
     * @param userId 用户id
     * @param amount 数额
     * @author Ajie
     * @date 2020年1月4日09:03:23
     */
    private void reduceXmcMoney(String userId, String amount) throws Exception {
        PageData pd = new PageData();
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("ACCOUNT_ID", userId);
        pd.put("tag", "-");
        pd.put("XMC_WALLET", amount);
        accountService.updateMoney(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：添加xmc钱包流水账单记录
     *
     * @param userName 用户名
     * @param userId   用户id
     * @param type     金额类型 直接写中文 例： 充值、提现
     * @param tag      标识 + or -
     * @param amount   数量
     * @param state    状态
     * @param site     钱包地址 转账等需要用到 可为空
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private void addXmcWalletRec(String userName, String userId, String type, String tag, String amount, String state, String site) throws Exception {
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 金额类型
        pd.put("AMOUNT_TYPE", type);
        pd.put("WALLET_SITE", site);
        pd.put("TAG", tag);
        pd.put("MONEY", amount);
        pd.put("STATUS", state);
        // id 自增
        pd.put("XMC_WALLET_REC_ID", "");
        xmc_wallet_recService.save(pd);
    }

    /**
     * 功能描述：增加一张理财券
     *
     * @param goods    理财券名称
     * @param userName 用户名
     * @param userId   用户ID
     * @param discount 折扣 整数 百分比
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private void addVoucher(String goods, String userName, String userId, String discount) throws Exception {
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 后台设置的理财券有效期 天数
        String validity = par.getString("VALIDITY_VOUCHER");
        String time = DateUtil.getTime();
        // 计算有期限
        validity = DateUtil.getAfterDayDate(validity);
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("VOUCHER_NAME", goods);
        // 状态 0：未使用、1：已使用、2：已过期
        pd.put("STATUS", "0");
        // 到期时间
        pd.put("DUE_TIME", validity);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 折扣 百分比 取到值后 ×100 例： 95折： 95 × 100 = 0.95
        pd.put("DISCOUNT", discount);
        String id = this.get32UUID();
        pd.put("VOUCHER_LIST_ID", id);
        voucher_listService.save(pd);
        // 增加理财券后 创建过期的定时任务
        addVoucherTimedTask(validity, id);
    }

    /**
     * 功能描述：添加理财券有效期定时任务
     *
     * @param dueTime 到期时间
     * @param id      主表主键
     * @return
     * @author Ajie
     * @date 2019/12/27 0027
     */
    public static void addVoucherTimedTask(String dueTime, String id) {
        // 设置任务名
        String name = Const.TIMED_TASK_VALIDITY + id;
        // 日期转cron 表达式
        String cron = DateUtil.getCron(dueTime);
        // 设置参数
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("tableID", id);
        QuartzManager.addJob(name, ValidityTaskJob.class, cron, parameter);

        QuartzManager.taskMethod();
    }

    /**
     * 功能描述：请求修改我的资料，参数校验
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/27 0027
     */
    @RequestMapping(value = "/updateMyinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String checkMyinfo() throws Exception {
        PageData pd = this.getPageData();
        // 从缓存获取信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData appUser = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 安全密码
        String pass = pd.getString("password");
        pd.remove("password");
        if (Tools.isEmpty(pass)) {
            return "非法請求";
        }
        pass = StringUtil.applySha256(pass);
        String myPass = appUser.getString("SECURITY_PASSWORD");
        if (!pass.equals(myPass)) {
            return "安全密碼錯誤";
        }
        String name = pd.getString("NAME");
        String mailbox = pd.getString("MAILBOX");
        String phone = pd.getString("PHONE");
        if (Tools.isEmpty(name)) {
            return "请输入姓名";
        }
        if (Tools.isEmpty(mailbox)) {
            return "请输入邮箱";
        }
        if (!Tools.checkEmail(mailbox)) {
            return "邮箱格式错误";
        }
        if (Tools.isEmpty(phone)) {
            return "请输入手机号";
        }
        if (!Tools.checkMobileNumber(phone)) {
            return "手机号格式错误";
        }
        PageData user = accountService.findByEmail(pd);
        // 如果用过邮箱能查询到用户，并且要更改的邮箱不等于原本的那个
        if (user != null && !appUser.getString("MAILBOX").equals(pd.getString("MAILBOX"))) {
            return "邮件已被注册";
        }
        String holder = pd.getString("HOLDER");
        String bankName = pd.getString("BANK_NAME");
        String bankNumber = pd.getString("BANK_NUMBER");
        String alipay = pd.getString("ALIPAY");
        // 如果选择的是支付宝收款
        if ("0".equals(pd.getString("PAYMENT"))) {
            if (Tools.isEmpty(alipay)) {
                return "请输入支付宝";
            }
        } else {
            if (Tools.isEmpty(holder)) {
                return "请输入开户人";
            }
            if (Tools.isEmpty(bankName)) {
                return "请输入银行名称";
            }
            if (Tools.isEmpty(bankNumber)) {
                return "请输入银行卡号";
            }
        }
        // 逐个查数据库
        String result = findMyInfo(name, phone, bankNumber, holder, alipay, memUser.getACCOUNT_ID());
        if (!"success".equals(result)) {
            return result;
        }
        // 操作数据库
        updateMyInfo(pd);
        return "success";
    }

    /**
     * 功能描述：查找数据库是否有重复的实名信息
     *
     * @param name       姓名
     * @param phone      电话
     * @param bankNumber 银行卡号
     * @param holder     开户人
     * @param alipay     支付宝
     * @param userId     用户ID
     * @return 结果
     * @author Ajie
     * @date 2020/2/15 0015
     */
    private String findMyInfo(String name, String phone, String bankNumber, String holder, String alipay, String userId) throws Exception {
        PageData pd = new PageData();
        pd.put("ACCOUNT_ID", userId);
        pd.put("NAME", name);
        List<PageData> resultList = accountService.listByFor(pd);
        if (resultList.size() > 0) {
            return "姓名已被注册";
        }
        pd.remove("NAME");
        pd.put("PHONE", phone);
        resultList = accountService.listByFor(pd);
        if (resultList.size() > 0) {
            return "手机号已被注册";
        }
        pd.remove("PHONE");
        pd.put("BANK_NUMBER", bankNumber);
        resultList = accountService.listByFor(pd);
        if (resultList.size() > 0) {
            return "银行卡号已被注册";
        }
        pd.remove("BANK_NUMBER");
        pd.put("HOLDER", holder);
        resultList = accountService.listByFor(pd);
        if (resultList.size() > 0) {
            return "开户人已被注册";
        }
        pd.remove("HOLDER");
        pd.put("ALIPAY", alipay);
        resultList = accountService.listByFor(pd);
        if (resultList.size() > 0) {
            return "支付宝已被注册";
        }
        return "success";
    }

    /**
     * 功能描述：更新用户资料信息
     *
     * @param pd 更新资料
     * @return
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private void updateMyInfo(PageData pd) throws Exception {
        // 从session取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(user.getUSER_NAME(), pd);
    }

    /**
     * 功能描述： 请求签到
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/28 0028
     */
    @RequestMapping(value = "/signIn")
    @ResponseBody
    private PageData signIn() throws Exception {
        PageData pd = new PageData();
        pd.put("status", "1");
        pd.put("msg", "已签到");
        // 从session获取当前登录用户信息
        Session session = Jurisdiction.getSession();
        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        user = accountService.findByIdReturnEntity(pd);
        // 验证是否签到
        if ("1".equals(user.getIS_SIGN_IN())) {
            return pd;
        }
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        double xmcMin = Double.parseDouble(par.get("MIN_XMC_NUMBER").toString());
        double xmcMax = Double.parseDouble(par.get("MAX_XMC_NUMBER").toString());
        // 在此范围随机生成一个数
        double result = Tools.getRandomRange(xmcMin, xmcMax);
        pd.put("status", "0");
        pd.put("msg", result);
        // 更新用户状态已签到
        user.setIS_SIGN_IN("1");
        session.setAttribute(Const.SESSION_MEMUSER, user);
        // 调用多线程更新数据库
        MemUser finalUser = user;
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-签到成功，更新数据库");
                // 给用户加钱
                addXmcMoney(finalUser.getACCOUNT_ID(), String.valueOf(result));
                // 给XMC钱包增加一条记录
                addXmcWalletRec(finalUser.getUSER_NAME(), finalUser.getACCOUNT_ID(), "签到", "+", String.valueOf(result), "已完成", "");
                // 更新数据库 已签到
                PageData pd = new PageData();
                pd.put("ACCOUNT_ID", finalUser.getACCOUNT_ID());
                pd.put("IS_SIGN_IN", 1);
                accountService.updateFor(pd);
                // 更新缓存
                pd = accountService.findById(pd);
                applicati.setAttribute(pd.getString("USER_NAME"), pd);
            }
        });
        return pd;
    }

    /**
     * 功能描述：留言反馈
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/30 0030
     */
    @RequestMapping(value = "/feedback", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String feedback() throws Exception {
        PageData pd = this.getPageData();
        if (pd.size() != 2) {
            return "非法参数";
        }
        // 获取前台传过来的标题和内容
        String title = pd.getString("title");
        String content = pd.getString("content");
        if (Tools.isEmpty(title)) {
            return "标题不可为空";
        }
        if (Tools.isEmpty(content)) {
            return "内容不可为空";
        }
        // 从session获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 创建留言记录
        String time = DateUtil.getTime();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", user.getUSER_NAME());
        pd.put("USER_ID", user.getACCOUNT_ID());
        pd.put("CONTENT", content);
        pd.put("TITLE", content);
        pd.put("MESSAGE_FEEDBACK_ID", this.get32UUID());
        message_feedbackService.save(pd);
        return "success";
    }

    /**
     * 功能描述：添加钱包提现地址
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/30 0030
     */
    @RequestMapping(value = "/addWalletAdress", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String addWalletAdress() throws Exception {
        PageData pd = this.getPageData();
        // 获取前台传过来的地址
        String usdt = pd.getString("usdt");
        String xmc = pd.getString("xmc");
        String password = pd.getString("password");
        pd = new PageData();
        if (Tools.notEmpty(usdt)) {
            pd.put("CASH_SITE", usdt);
        }
        if (Tools.notEmpty(xmc)) {
            pd.put("XMC_SITE", xmc);
        }
        if (Tools.isEmpty(password)) {
            return "非法請求";
        }
        password = StringUtil.applySha256(password);
        // 从session获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData appUser = (PageData) applicati.getAttribute(user.getUSER_NAME());
        if (!password.equals(appUser.getString("SECURITY_PASSWORD"))) {
            return "安全密碼錯誤";
        }
        // 更新钱包地址
        String time = DateUtil.getTime();

        pd.put("GMT_MODIFIED", time);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(user.getUSER_NAME(), pd);
        return "success";
    }

    /**
     * 功能描述：创建投资记录
     *
     * @param userName   用户名
     * @param userId     用户ID
     * @param type       金额类型
     * @param tag        + 或 -
     * @param amount     数额
     * @param walletType 钱包类型 0:XMC、1：UDFY
     * @param profit     收益
     * @param state      状态
     * @author Ajie
     * @date 2019/12/30 0030
     */
    private void addBillRecord(String userName, String userId, String type, String tag, String amount, String walletType, String profit, String state) throws Exception {
        PageData pd = new PageData();
        String time = DateUtil.getTime();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        pd.put("AMOUNT_TYPE", type);
        pd.put("TAG", tag);
        pd.put("MONEY", amount);
        pd.put("WALLET_TYPE", walletType);
        pd.put("PROFIT", profit);
        pd.put("STATE", state);
        pd.put("LNCOME_DETAILS_ID", "");
        lncome_detailsService.save(pd);
    }

    /**
     * 功能描述：用户在首页请求投资
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/30 0030
     */
    @RequestMapping(value = "/homeInvest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private synchronized String homeInvest() throws Exception {
        PageData pd = this.getPageData();
        if (pd.size() != 3) {
            return "非法参数";
        }
        // 获取前台传过来的投资数额、 理财券信息、投资产品ID
        double amount = Double.parseDouble(pd.getString("num"));
        String id = pd.getString("id");
        // 获取投资产品信息
        PageData product = lnvestment_listService.findById(pd);
        // 投资产品不存在或者 已售完
        if (product == null || "1".equals(product.get("SALE_STATUS").toString())) {
            return "今日已售完，明日再來投資";
        }
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 判断用户是否投资过
        int investNum = Integer.parseInt(user.get("INVEST_NUMBER").toString());
        if (investNum >= 1) {
            return "已投資過，請復投或者升級";
        }
        double discount = 0;
        if (!"0".equals(id)) {
            // 查询理财券信息
            pd.put("VOUCHER_LIST_ID", id);
            pd = voucher_listService.findById(pd);
            if (!"0".equals(pd.getString("STATUS")) && !pd.get("USER_ID").toString().equals(memUser.getACCOUNT_ID())) {
                // 理财券已过期或者已使用 或者不是他的理财券
                return "非法请求";
            }
            // 获取理财券折扣
            discount = Double.parseDouble(pd.getString("DISCOUNT")) / 100;
        }
        // 获取最新的一条投资记录
        pd = lncome_detailsService.getNewOrderByUserId(user);
        if (pd != null) {
            // 判断用户是否出局
            if (!"已出局".equals(pd.getString("STATE"))) {
                return "上次投资未出局";
            }
        }
        // 获取所有等级
        List<PageData> rankAll = rankService.listAll(null);
        // 定义最小值和最大值为该数组的第一个数
        int min = 50;
        int max = Integer.parseInt(rankAll.get(0).get("MIN_COST").toString());
        for (int i = 0; i < rankAll.size(); i++) {
            //  如果是后台指派的就跳过
            if ("1".equals(rankAll.get(i).getString("IS_ASSIGNED"))) {
                continue;
            }
            int min1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            int max1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            if (min > min1) {
                min = min1;
            }
            if (max < max1) {
                max = max1;
            }
        }
        // 用户usdt余额
        double usdt = Double.parseDouble(user.get("USDT_WALLET").toString());
        if (amount < min) {
            return "最少投資 " + min;
        }
        if (amount % 50 != 0) {
            return "請投資50的倍數";
        }
        if (amount > max) {
            return "最大投資 " + max;
        }
        // 如果使用的理财券 计算折扣后金额
        if (discount != 0) {
            amount *= discount;
        }
        // 如果用户余额少于 投资数额
        if (usdt < amount) {
            return "USDT不足";
        }
        if (discount != 0) {
            // 调用创建购买记录的函数
            addBillRecord(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "投资", "-", String.valueOf(amount / discount), "1", String.valueOf((amount / discount) * 2), "未出局");
        } else {
            // 调用创建购买记录的函数
            addBillRecord(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "投资", "-", String.valueOf(amount), "1", String.valueOf(amount * 2), "未出局");
        }
        // 减少usdt钱余额
        reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(amount), "USDT_WALLET");
        // 增加钱包记录
        addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "投资", "-", String.valueOf(amount), "", "已完成");
        // 给自己和所有上级增加团队业绩
        user.put("money", amount);
        user.put("tag", '+');
        accountService.updateTeamSum(user);
        // 增加投资次数
        investNum++;
        pd = new PageData();
        pd.put("INVEST_NUMBER", investNum);
        pd.put("ACCOUNT_ID", memUser.getACCOUNT_ID());
        accountService.updateFor(pd);
        pd = accountService.findById(pd);
        // 给所有上级增加 有效会员人数
        accountService.updatEmemberAmount(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(memUser.getUSER_NAME(), pd);
        if (discount != 0) {
            // 更新理财券状态 已使用
            pd.put("VOUCHER_LIST_ID", id);
            pd.put("GMT_MODIFIED", DateUtil.getTime());
            pd.put("STATUS", "1");
            voucher_listService.updateStatus(pd);
            // 移除过期定时任务
            QuartzManager.removeJob(Const.TIMED_TASK_VALIDITY + id);
            amount /= discount;
        }
        // 调用多线程判断能达到什么等级
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-投资判断能达到什么等级和发放奖金");
                checkRank(memUser.getRE_PATH() + "," + memUser.getACCOUNT_ID());
            }
        });
        return "success";
    }


    /**
     * 功能描述：用户在对冲钱包请求投资
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020年1月11日11:42:47
     */
    @RequestMapping(value = "/walletInvest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String walletInvest() throws Exception {
        PageData pd = this.getPageData();
        if (pd.size() != 3) {
            return "非法参数";
        }
        // 获取前台传过来的投资数额、 理财券信息
        double amount = Double.parseDouble(pd.getString("num"));
        String id = pd.getString("id");
        // 获取投资产品信息
        PageData product = lnvestment_listService.findById(pd);
        // 投资产品不存在或者 已售完
        if (product == null || "1".equals(product.get("SALE_STATUS").toString())) {
            return "今日已售完，明日再來投資";
        }
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 判断用户是否投资过
        int investNum = Integer.parseInt(user.get("INVEST_NUMBER").toString());
        if (investNum >= 1) {
            return "已投資過，請復投或者升級";
        }
        double discount = 0;
        if (!"0".equals(id)) {
            // 查询理财券信息
            pd.put("VOUCHER_LIST_ID", id);
            pd = voucher_listService.findById(pd);
            if (!"0".equals(pd.getString("STATUS")) && !pd.get("USER_ID").toString().equals(memUser.getACCOUNT_ID())) {
                // 理财券已过期或者已使用 或者不是他的理财券
                return "非法请求";
            }
            // 获取理财券折扣
            discount = Double.parseDouble(pd.getString("DISCOUNT")) / 100;
        }
        // 获取最新的一条投资记录
        pd = lncome_detailsService.getNewOrderByUserId(user);
        if (pd != null) {
            // 判断用户是否出局
            if (!"已出局".equals(pd.getString("STATE"))) {
                return "上次投资未出局";
            }
        }

        // 获取所有等级
        List<PageData> rankAll = rankService.listAll(null);
        // 定义最小值和最大值为该数组的第一个数
        int min = 50;
        int max = Integer.parseInt(rankAll.get(0).get("MIN_COST").toString());
        for (int i = 0; i < rankAll.size(); i++) {
            //  如果是后台指派的就跳过
            if ("1".equals(rankAll.get(i).getString("IS_ASSIGNED"))) {
                continue;
            }
            int min1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            int max1 = Integer.parseInt(rankAll.get(i).get("MIN_COST").toString());
            if (min > min1) {
                min = min1;
            }
            if (max < max1) {
                max = max1;
            }
        }
        // 用户usdt余额
        double usdt = Double.parseDouble(user.get("HEDGING_USDT").toString());
        if (amount < min) {
            return "最少投資 " + min;
        }
        if (amount % 50 != 0) {
            return "請投資50的倍數";
        }
        if (amount > max) {
            return "最大投資 " + max;
        }
        // 如果使用的理财券 计算折扣后金额
        if (discount != 0) {
            amount *= discount;
        }
        // 如果用户余额少于 投资数额
        if (usdt < amount) {
            return "USDT不足";
        }
        if (discount != 0) {
            // 调用创建购买记录的函数
            addBillRecord(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "投資", "-", String.valueOf(amount / discount), "1", String.valueOf((amount / discount) * 2), "未出局");
        } else {
            // 调用创建购买记录的函数
            addBillRecord(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "投資", "-", String.valueOf(amount), "1", String.valueOf(amount * 2), "未出局");
        }
        // 减少对冲钱包usdt钱余额
        reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(amount), "HEDGING_USDT");
        // 增加钱包记录
        addHedgWhalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "投資", "", "-", String.valueOf(amount), "1", "", "");
        // 给自己和所有上级增加团队业绩
        user.put("money", amount);
        user.put("tag", '+');
        accountService.updateTeamSum(user);
        // 增加投资次数
        investNum++;
        pd = new PageData();
        pd.put("INVEST_NUMBER", investNum);
        pd.put("ACCOUNT_ID", memUser.getACCOUNT_ID());
        accountService.updateFor(pd);
        pd = accountService.findById(pd);
        // 给所有上级增加 有效会员人数
        accountService.updatEmemberAmount(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(memUser.getUSER_NAME(), pd);
        if (discount != 0) {
            // 更新理财券状态 已使用
            pd.put("VOUCHER_LIST_ID", id);
            pd.put("GMT_MODIFIED", DateUtil.getTime());
            pd.put("STATUS", "1");
            voucher_listService.updateStatus(pd);
            // 移除过期定时任务
            QuartzManager.removeJob(Const.TIMED_TASK_VALIDITY + id);
            amount /= discount;
        }
        // 调用多线程判断能达到什么等级
        double finalAmount = amount;
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-在对冲钱包投资判断能达到什么等级");
                checkRank(memUser.getRE_PATH() + "," + memUser.getACCOUNT_ID());
            }
        });
        return "success";
    }

    /**
     * 功能描述：检查购买订单
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/31 0031
     */
    @RequestMapping(value = "/checkBuyOrder")
    @ResponseBody
    private PageData checkBuyOrder() throws Exception {
        // 获取前台传过来的 购买订单ID 和 购买数量
        PageData pd = this.getPageData();
        double num = Double.parseDouble(pd.getString("num"));
        int minAmount = 100;
        if (num < minAmount) {
            pd.put("msg", "最少購買 " + minAmount);
            return pd;
        }
        // 根据订单id查信息
        pd = ercbuyService.findById(pd);
        // 取订单剩余数量
        double surplus = Double.parseDouble(pd.get("SURPLUS").toString());
        if (surplus < num) {
            pd.put("msg", "最多可购买：" + surplus);
            return pd;
        }
        // 执行扣库存
        pd.put("amount", num);
        ercbuyService.reduceSurplus(pd);
        // 创建订单
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 创建订单记录
        String orderId = addErcBuyRecord(pd.getString("ERCBUY_ID"), String.valueOf(num), user.getACCOUNT_ID(), user.getUSER_NAME(), "购买",
                "+", "待付款", "", "", pd.getString("SHOP_NAME"));
        pd.put("status", 1);
        pd.put("data", orderId);
        pd.put("msg", "创建订单成功");
        // 跳转支付页面
        return pd;
    }

    /**
     * 功能描述：检查出售订单
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/31 0031
     */
    @RequestMapping(value = "/checkSellOrder")
    @ResponseBody
    private PageData checkSellOrder() throws Exception {
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 查询最新一条出售订单记录
        PageData pd1 = new PageData();
        pd1.put("USER_ID", memUser.getACCOUNT_ID());
        PageData sellRec = ercsellrecordService.getNewOrderByUserId(pd1);
        if (null != sellRec) {
            if (!"過期未確認".equals(sellRec.getString("STATE")) && !"已完成".equals(sellRec.getString("STATE"))) {
                pd1.put("msg", "上一筆訂單未完成");
                return pd1;
            }
        }
        // 获取前台传过来的 出售订单ID 和 出售数量
        PageData pd = this.getPageData();
        double num = Double.parseDouble(pd.getString("num"));
        // 单次最少出售
        int minAmount = 20;
        if (num < minAmount) {
            pd.put("msg", "最少出售 " + minAmount);
            return pd;
        }
        // 倍数
        int muitiple = 10;
        if (num % muitiple != 0) {
            pd.put("msg", "请输入 " + muitiple + " 的倍数");
            return pd;
        }
        // 单次最多出售
        int maxAmount = 20000;
        if (num > maxAmount) {
            pd.put("msg", "最高出售 " + maxAmount);
            return pd;
        }
        // 根据订单id查信息
        pd = ercsellService.findById(pd);
        double price = Double.parseDouble(pd.get("UNIT_PRICE").toString());
        // 获取用户USDT余额
        double usdtWallet = Double.parseDouble(user.get("USDT_WALLET").toString());
        if (usdtWallet < num) {
            pd.put("msg", "账户当前余额剩余：" + usdtWallet);
            return pd;
        }
        // 取订单剩余数量
        double surplus = Double.parseDouble(pd.get("SURPLUS").toString());
        if (surplus < num) {
            pd.put("msg", "最多可出售：" + surplus);
            return pd;
        }
        // 执行扣库存
        pd.put("amount", num);
        ercsellService.reduceSurplus(pd);
        // 执行扣除用户USDT
        reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "USDT_WALLET");
        addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "出售", "-", String.valueOf(num), "", "已完成");
        // 创建订单记录
        String orderId = addErcSellRecord(pd.getString("ERCSELL_ID"), String.valueOf(num), memUser.getACCOUNT_ID(), memUser.getUSER_NAME(), "出售", "-",
                "待确认", "", "", "", "", "", pd.getString("SHOP_NAME"), String.valueOf(price), String.valueOf(num * price));
        pd.put("status", 1);
        pd.put("data", orderId);
        pd.put("msg", "创建订单成功");
        // 跳转确认并选择收款方式页面
        return pd;
    }

    /**
     * 功能描述：法币交易之购买，确认付款更改状态
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/31 0031
     */
    @RequestMapping(value = "/recPayment", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String recPayment() throws Exception {
        PageData pd = this.getPageData();
        // 获取前台传过来的 订单ID 和支付方式、付款凭证、数量
        String payType = pd.getString("payType");
        String picPath = pd.getString("picPath");
        double amount = Double.parseDouble(pd.getString("amount"));
        // 单次最少购买
        int minAmount = 100;
        if (amount < minAmount) {
            return "最少購買 " + minAmount;
        }
        if ("0".equals(payType)) {
            return "请选择支付方式";
        }
        if (Tools.isEmpty(picPath)) {
            return "请上传支付凭证";
        }
        // 根据ID查询订单信息
        pd = ercbuyrecordService.findById(pd);
        // 获取这条订单的数量
        double num = Double.parseDouble(pd.get("AMOUNT").toString());
        if (num > amount) {
            return "非法参数，数量错误";
        }
        // 执行更改订单状态
        pd.put("STATE", "待确认");
        pd.put("PAYMENT_TYPE", payType);
        pd.put("PAYMENT_VOUCHER", picPath);
        ercbuyrecordService.edit(pd);
        // 清除定时任务
        QuartzManager.removeJob(pd.getString("ERCBUYRECORD_ID"));
        return "success";
    }

    /**
     * 功能描述：法币交易之出售，确认收款方式更改状态
     *
     * @return 处理结果
     * @author Ajie
     * @date 2019/12/31 0031
     */
    @RequestMapping(value = "/receiptType", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String receiptType() throws Exception {
        PageData pd = this.getPageData();
        PageData rec = new PageData();
        // 获取前台传过来的 订单ID 和 收款方式、数额
        String payType = pd.getString("payType");
        double amount = Double.parseDouble(pd.getString("amount"));
        // 单次最少出售
        int minAmount = 20;
        if (amount < minAmount) {
            return "最少出售 " + minAmount;
        }
        // 倍数
        int muitiple = 10;
        if (amount % muitiple != 0) {
            return "请输入 " + muitiple + " 的倍数";
        }
        // 单次最大出售
        int maxAmount = 20000;
        if (amount > maxAmount) {
            return "最少出售 " + maxAmount;
        }

        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 0：支付宝 1：银行卡
        if ("0".equals(payType)) {
            payType = "支付宝";
        } else {
            payType = "银行卡";
        }
        rec.put("ALIPAY_ACCOUNT", user.getString("ALIPAY"));
        rec.put("HOLDER", user.getString("HOLDER"));
        rec.put("BANK_NUMBER", user.getString("BANK_NAME"));
        rec.put("BANK_NAME", user.getString("BANK_NUMBER"));
        // 根据ID查询订单信息
        pd = ercsellrecordService.findById(pd);
        // 获取这条订单的数量
        double num = Double.parseDouble(pd.get("AMOUNT").toString());
        if (num > amount) {
            return "非法参数，数量错误";
        }
        // 执行更改订单状态,并添加收款方式
        rec.put("ERCSELLRECORD_ID", pd.getString("ERCSELLRECORD_ID"));
        rec.put("GMT_MODIFIED", DateUtil.getTime());
        rec.put("STATE", "待打款");
        rec.put("RECEIPT_TYPE", payType);
        ercsellrecordService.edit(rec);
        // 清除定时任务
        QuartzManager.removeJob(pd.getString("ERCSELLRECORD_ID"));
        return "success";
    }

    /**
     * 功能描述： 创建法币交易之购买记录 并添加付款时间定时任务
     *
     * @param orderId    订单ID是哪条订单产生的
     * @param amount     数额
     * @param userId     用户ID
     * @param userName   用户名
     * @param amountType 金额类型 填写中文
     * @param tag        + 或者 -
     * @param state      状态填写中文
     * @param payType    支付方式
     * @param picPath    支付凭证
     * @param shopName   商铺名称
     * @return 订单ID
     * @author Ajie
     * @date 2019/12/31 0031
     */
    private String addErcBuyRecord(String orderId, String amount, String userId, String userName, String amountType,
                                   String tag, String state, String payType, String picPath, String shopName) throws Exception {
        String uuid = this.get32UUID();
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("ORDER_ID", orderId);
        pd.put("AMOUNT", amount);
        pd.put("USER_ID", userId);
        pd.put("USER_NAME", userName);
        pd.put("AMOUNT_TYPE", amountType);
        pd.put("TAG", tag);
        pd.put("STATE", state);
        pd.put("PAYMENT_TYPE", payType);
        pd.put("PAYMENT_VOUCHER", picPath);
        pd.put("SHOP_NAME", shopName);
        pd.put("ERCBUYRECORD_ID", uuid);
        ercbuyrecordService.save(pd);
        // 创建付款时间定时任务 限制M分钟内付款
        String minute = "30";
        String addTime = DateUtil.getAddMinuteDate(time, minute);
        // 下面添加定时任务 订单ID作为任务名
        addErcTimedTask(addTime, "1", uuid);
        return uuid;
    }

    /**
     * 功能描述： 创建法币交易之出售记录 并添加确认时间定时任务
     *
     * @param orderId    订单ID是哪条订单产生的
     * @param amount     数额
     * @param userId     用户ID
     * @param userName   用户名
     * @param amountType 金额类型 填写中文
     * @param tag        + 或者 -
     * @param state      状态填写中文
     * @param recType    收款方式
     * @param Alipay     支付宝账号
     * @param holder     开户人
     * @param banknumber 银行卡号
     * @param bankName   银行名称
     * @param shopName   挂买商铺名称
     * @param price      单价
     * @param sumPrice   总价
     * @return 订单ID
     * @author Ajie
     * @date 2019/12/31 0031
     */
    private String addErcSellRecord(String orderId, String amount, String userId, String userName, String amountType,
                                    String tag, String state, String recType, String Alipay, String holder,
                                    String banknumber, String bankName, String shopName, String price, String sumPrice) throws Exception {
        String uuid = this.get32UUID();
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("ORDER_ID", orderId);
        pd.put("AMOUNT", amount);
        pd.put("USER_ID", userId);
        pd.put("USER_NAME", userName);
        pd.put("AMOUNT_TYPE", amountType);
        pd.put("TAG", tag);
        pd.put("STATE", state);
        pd.put("RECEIPT_TYPE", recType);
        pd.put("ALIPAY_ACCOUNT", Alipay);
        pd.put("HOLDER", holder);
        pd.put("BANK_NUMBER", banknumber);
        pd.put("BANK_NAME", bankName);
        pd.put("SHOP_NAME", shopName);
        pd.put("PRICE", price);
        pd.put("SUM_PRICE", sumPrice);
        pd.put("ERCSELLRECORD_ID", uuid);
        ercsellrecordService.save(pd);
        // 创建确认时间定时任务 限制N分钟内确认
        String minute = "30";
        String addTime = DateUtil.getAddMinuteDate(time, minute);
        // 下面添加定时任务 订单ID作为任务名
        addErcTimedTask(addTime, "0", uuid);
        return uuid;
    }

    /**
     * 功能描述：添加法币交易定时任务
     *
     * @param dueTime 到期时间
     * @param type    类型 1:购买 0:出售
     * @param orderId 订单ID
     * @author Ajie
     * @date 2019/12/31 0031
     */
    public static void addErcTimedTask(String dueTime, String type, String orderId) {
        if ("1".equals(type)) {
            // 设置任务名
            String name = orderId;
            // 日期转cron 表达式
            String cron = DateUtil.getCron(dueTime);
            // 设置参数
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("orderId", orderId);
            QuartzManager.addJob(name, ErcBuyTaskJob.class, cron, parameter);
        }
        if ("0".equals(type)) {
            // 设置任务名
            String name = orderId;
            // 日期转cron 表达式
            String cron = DateUtil.getCron(dueTime);
            // 设置参数
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("orderId", orderId);
            QuartzManager.addJob(name, ErcSellTaskJob.class, cron, parameter);
        }


        QuartzManager.taskMethod();
    }

    /**
     * 功能描述：减少钱包余额
     *
     * @param userId 用户id
     * @param amount 数额
     * @param type   类型
     * @author Ajie
     * @date 2019/12/31 0028
     */
    private void reduceUsdtMoney(String userId, String amount, String type) throws Exception {
        PageData pd = new PageData();
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("ACCOUNT_ID", userId);
        pd.put("tag", "-");
        pd.put(type, amount);
        accountService.updateMoney(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：增加钱包余额
     *
     * @param userId 用户id
     * @param amount 数额
     * @param type   钱包类型
     * @author Ajie
     * @date 2020-1-4 10:58:30
     */
    private void addUsdtMoney(String userId, String amount, String type) throws Exception {
        PageData pd = new PageData();
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("ACCOUNT_ID", userId);
        pd.put("tag", "+");
        if (Tools.notEmpty(type)) {
            pd.put(type, amount);
        } else {
            pd.put("USDT_WALLET", amount);
        }
        accountService.updateMoney(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：添加Usdt钱包流水账单记录
     *
     * @param userName 用户名
     * @param userId   用户id
     * @param type     金额类型 直接写中文 例： 充值、提现
     * @param tag      标识 + or -
     * @param amount   数量
     * @param site     钱包地址 转账等需要用到 可为空
     * @param status   状态
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private void addUsdtWalletRec(String userName, String userId, String type, String tag, String amount, String site, String status) throws Exception {
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 金额类型
        pd.put("AMOUNT_TYPE", type);
        pd.put("WALLET_SITE", site);
        pd.put("TAG", tag);
        pd.put("MONEY", amount);
        pd.put("STATUS", status);
        // id 自增
        pd.put("USDT_WALLET_REC_ID", "");
        usdt_wallet_recService.save(pd);
    }

    /**
     * 功能描述：添加累积钱包流水账单记录
     *
     * @param userName 用户名
     * @param userId   用户id
     * @param type     金额类型 直接写中文 例： 充值、提现
     * @param tag      标识 + or -
     * @param amount   数量
     * @param wallet   钱包类型 0：XMC、1：USDT
     * @param charge   手续费
     * @param source   来源
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private void addCountWalletRec(String userName, String userId, String type, String tag, String amount, String wallet, Object charge, String source) throws Exception {
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 金额类型
        pd.put("AMOUNT_TYPE", type);
        pd.put("WALLET_TYPE", wallet);
        pd.put("TAG", tag);
        pd.put("MONEY", amount);
        pd.put("CHARGE", charge);
        pd.put("SOURCE", source);
        pd.put("ACCUM_REC_ID", this.get32UUID());
        accum_recService.save(pd);
    }


    /**
     * 功能描述：判断用户达到什么级别了
     *
     * @param pathId 所有上级ID
     * @author Ajie
     * @date 2020/1/10 0010
     */
    private void checkRank(String pathId) throws Exception {
        // 所有上级ID分割成数组
        String[] usderIdList = StringUtil.StrList(pathId);
        // 循环判断
        for (String usderId : usderIdList) {
            // 调用是否达到M8的函数
            checkMaxRank(usderId);
            PageData pd = new PageData();
            pd.put("ACCOUNT_ID", usderId);
            MemUser user = accountService.findByIdReturnEntity(pd);
            // 查询累计投资金额
            pd.put("USER_ID", usderId);
            pd = lncome_detailsService.getAmountSum(pd);
            double amountSum = Double.parseDouble(pd.get("AMOUNT_SUM").toString());
            // 获取有效会员人数
            int teamCount = user.getEFFECTIVE_MEMBER();
            // 获取所有等级表
            List<PageData> rankList = rankService.listAll(null);
            // 定义能达到的等级
            PageData rank = null;
            for (PageData map : rankList) {
                // 如果我的等级是M8 直接退出循环
                if ("9".equals(user.getUSER_RANK())) {
                    break;
                }
                // 如果是后台指派的就继续循环 1：是 0 ：不是
                if ("1".equals(map.getString("IS_ASSIGNED"))) {
                    continue;
                }
                // 获取达到这个等级的 最少累积投资额、直推人数、团队人数
                double minCost = Double.parseDouble(map.get("MIN_COST").toString());
                int reNumber = Integer.parseInt(map.get("RE_NUMBER").toString());
                int teanNumber = Integer.parseInt(map.get("TRAM_NUMBER").toString());
                // 如果累积投资金额 大于等于 这个等级的条件 并且 推荐人数要求 少于等于 0
                if (amountSum >= minCost && reNumber <= 0) {
                    rank = map;
                    continue;
                }
                if (amountSum >= minCost && reNumber > 0) {
                    // 如果 有效会员人数 大于等于 这个等级的条件 并且 团队人数要求 少于等于 0
                    if (teamCount >= reNumber && teanNumber <= 0) {
                        rank = map;
                        continue;
                    }
                    // 如果 有效会员人数 大于等于 这个等级的条件 并且 团队人数要求 大于 0
                    if (teamCount >= reNumber && teanNumber > 0) {
                        // 如果团队人数 大于等于 这个等级的条件
                        if (teamCount >= teanNumber) {
                            rank = map;
                        }
                    }
                }

            }
            // 赋予用户等级
            if (rank != null) {
                pd = new PageData();
                pd.put("ACCOUNT_ID", usderId);
                pd.put("USER_RANK", rank.get("RANK_ID"));
                accountService.updateFor(pd);
                // 更新上下文缓
                pd = accountService.findById(pd);
                applicati.setAttribute(user.getUSER_NAME(), pd);
            }

        }
    }

    /**
     * 功能描述：用户请求提现 线上
     *
     * @author Ajie
     * @date 2020/1/2 0002
     */
  /* @RequestMapping(value = "/withdrawCash", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String withdrawCash() throws Exception {
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 提现时间段校验
        String[] timeSlot = StringUtil.strList(par.getString("CASH_TIME"));
        //调用判断方法
        boolean flag = DateUtil.isBelongTime(timeSlot[0], timeSlot[1]);
        if (!flag) {
            return "请在" + timeSlot[0] + "~" + timeSlot[1] + "提现";
        }
        PageData pd = this.getPageData();
        // 获取用户提现金额、安全密码
        double cashNumber = Double.parseDouble(pd.getString("num"));
        String safePassword = pd.getString("password");
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        user = accountService.findByIdReturnEntity(pd);
        if (cashNumber <= 0) {
            return "不可为负数";
        }
        if (cashNumber > user.getXMC_WALLET()) {
            return "余额不足";
        }
        if (Tools.isEmpty(user.getCASH_SITE())) {
            return "请完善提现地址";
        }
        if (Tools.isEmpty(safePassword)) {
            return "安全密码不可为空";
        }
        if (!user.getSECURITY_PASSWORD().equals(StringUtil.applySha256(safePassword))) {
            return "安全密码错误";
        }
        // 调用公司代转币API
        Map<String, Object> result = BlockUtil.usdtTransfer(user.getUSDT_SITE(), user.getCASH_SITE(), cashNumber);
        if ("0".equals(result.get("statuses").toString())) {
            return result.get("msg").toString();
        }
        String orderox = result.get("orderox").toString();
        // 执行扣除用户USDT
        reduceUsdtMoney(user.getACCOUNT_ID(), String.valueOf(cashNumber));
        addUsdtWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "提币", "-", String.valueOf(cashNumber), user.getCASH_SITE(), orderox);
        // 创建隔5分钟执行一次的定时任务
        String taskName = this.get32UUID();
        String cron = "0 /5 * * * ? *";
        // 设置参数
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("taskName", taskName);
        parameter.put("userId", user.getACCOUNT_ID());
        parameter.put("amount", cashNumber);
        parameter.put("orderox", orderox);
        QuartzManager.addJob(taskName, ValidityTaskJob.class, cron, parameter);
        return "success";
    }*/


    /**
     * 功能描述：用户请求USDT提现 线下
     *
     * @author Ajie
     * @date 2020/1/2 0002
     */
    @RequestMapping(value = "/usdtWithdrawCash", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String usdtWithdrawCash() throws Exception {
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 提现时间段校验
        String[] timeSlot = StringUtil.strList(par.getString("CASH_TIME"));
        //调用判断方法
        boolean flag = DateUtil.isBelongTime(timeSlot[0], timeSlot[1]);
        if (!flag) {
            return "請在" + timeSlot[0] + "~" + timeSlot[1] + "提現";
        }
        PageData pd = this.getPageData();
        // 获取用户提现金额、安全密码
        double cashNumber = Double.parseDouble(pd.getString("num"));
        String safePassword = pd.getString("password");
        // 定义USDT提现 最小数、倍数、最大数、一天最高提现数
        double min = Double.parseDouble(par.get("USDT_MIN").toString());
        int muitiple = Integer.parseInt(par.get("USDT_MUITIPLE").toString());
        double max = Double.parseDouble(par.get("USDT_MAX").toString());
        double dayMax = Double.parseDouble(par.get("USDT_MAX_DAY").toString());
        if (min > cashNumber) {
            return "最少提現：" + min;
        }
        if (cashNumber % muitiple != 0) {
            return "請輸入【" + muitiple + "】的倍數";
        }
        if (max < cashNumber) {
            return "最多提現：" + max;
        }
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        // 查询今天提币数量
        pd.put("USER_ID", user.getACCOUNT_ID());
        pd = usdt_wallet_recService.getDayNumByUserId(pd);
        double count = Double.parseDouble(pd.get("MONEY").toString());
        if (count == dayMax) {
            return "今日提現上限";
        }
        if (count + cashNumber > dayMax) {
            return "還差" + (dayMax - count) + "達到今天提現上限";
        }
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        user = accountService.findByIdReturnEntity(pd);
        if (cashNumber > user.getUSDT_WALLET()) {
            return "余额不足";
        }
        if (Tools.isEmpty(user.getCASH_SITE())) {
            return "请完善提现地址";
        }
        if (Tools.isEmpty(safePassword)) {
            return "安全密碼不可為空";
        }
        if (safePassword.length() < 6) {
            return "密碼最少六位數";
        }
        if (!user.getSECURITY_PASSWORD().equals(StringUtil.applySha256(safePassword))) {
            return "安全密碼錯誤";
        }
        // 执行扣除用户USDT 并创建记录
        reduceUsdtMoney(user.getACCOUNT_ID(), String.valueOf(cashNumber), "USDT_WALLET");
        addUsdtWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "提幣", "-", String.valueOf(cashNumber), user.getCASH_SITE(), "待确认");
        return "success";
    }

    /**
     * 功能描述：用户请求xmc提现 线下
     *
     * @author Ajie
     * @date 2020/1/10 0002
     */
    @RequestMapping(value = "/xmcWithdrawCash", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String xmcWithdrawCash() throws Exception {
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 提现时间段校验
        String[] timeSlot = StringUtil.strList(par.getString("CASH_TIME"));
        //调用判断方法
        boolean flag = DateUtil.isBelongTime(timeSlot[0], timeSlot[1]);
        if (!flag) {
            return "請在" + timeSlot[0] + "~" + timeSlot[1] + "提現";
        }
        PageData pd = this.getPageData();
        // 获取用户提现金额、安全密码
        double cashNumber = Double.parseDouble(pd.getString("num"));
        String safePassword = pd.getString("password");
        // 定义xmc提现 最小数 手续费
        double min = Double.parseDouble(par.get("XMC_MIN").toString());
        double charge = Double.parseDouble(par.get("XMC_CHARGE").toString()) / 100;
        // 计算手续费
        double service = cashNumber * charge;

        if (min > cashNumber) {
            return "最少提現：" + min;
        }
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        user = accountService.findByIdReturnEntity(pd);
        if (cashNumber > user.getXMC_WALLET()) {
            return "余额不足";
        }
        if (Tools.isEmpty(user.getXMC_SITE())) {
            return "请完善提现地址";
        }
        if (Tools.isEmpty(safePassword)) {
            return "安全密碼不可為空";
        }
        if (safePassword.length() < 6) {
            return "密碼最少六位數";
        }
        if (!user.getSECURITY_PASSWORD().equals(StringUtil.applySha256(safePassword))) {
            return "安全密碼錯誤";
        }
        // 执行扣除用户xmc 并创建记录
        reduceXmcMoney(user.getACCOUNT_ID(), String.valueOf(cashNumber));
        addXmcWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "提幣", "-", String.valueOf(cashNumber - service), "待确认", user.getXMC_SITE());
        return "success";
    }

    /**
     * 功能描述：用户请求xm充值 线下
     *
     * @author Ajie
     * @date 2020/1/10 0002
     */
    @RequestMapping(value = "/xmcWithdrawRechage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String xmcWithdrawRechage() throws Exception {
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        /*// 提现时间段校验
        String[] timeSlot = StringUtil.strList(par.getString("CASH_TIME"));
        //调用判断方法
        boolean flag = DateUtil.isBelongTime(timeSlot[0], timeSlot[1]);
        if (!flag) {
            return "請在" + timeSlot[0] + "~" + timeSlot[1] + "提現";
        }*/
        PageData pd = this.getPageData();
        // 获取用户充值金额、安全密码、支付凭证
        double cashNumber = Double.parseDouble(pd.getString("num"));
        String safePassword = pd.getString("password");
        String picPath = pd.getString("picPath");
        // 定义xmc充值 最小数 手续费
        double min = 1;
        double charge = 0;
        // 计算手续费
        double service = cashNumber * charge;
        if (min > cashNumber) {
            return "最少充值：" + min;
        }
        if (Tools.isEmpty(picPath)) {
            return "請上傳支付憑證";
        }
        // 从缓存获取用户信息
        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        user = accountService.findByIdReturnEntity(pd);
        if (Tools.isEmpty(safePassword)) {
            return "安全密碼不可為空";
        }
        if (safePassword.length() < 6) {
            return "密碼最少六位數";
        }
        if (!user.getSECURITY_PASSWORD().equals(StringUtil.applySha256(safePassword))) {
            return "安全密碼錯誤";
        }
        // 创建记录
        addXmcWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "充值", "+", String.valueOf(cashNumber - service), "待确认", picPath);
        return "success";
    }

    /**
     * 功能描述：用户请求取消订单
     *
     * @author Ajie
     * @date 2020/1/3 0003
     */
    @RequestMapping(value = "/cancel", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String cancelOrder() throws Exception {
        // 获取前台传过来的订单ID 和 数量
        PageData pd = this.getPageData();
        String orderId = pd.getString("orderId");
        String num = pd.getString("num");
        pd.put("amount", num);
        if ("buy".equals(pd.getString("tag"))) {
            // 增加库存并移除定时任务
            ercbuyService.addSurplus(pd);
            QuartzManager.removeJob(pd.getString("ERCBUY_ID"));
            // 更改订单状态 已取消
            pd = new PageData();
            pd.put("ERCBUYRECORD_ID", orderId);
            pd.put("STATE", "已取消");
            ercbuyrecordService.edit(pd);
        }
        if ("sell".equals(pd.getString("tag"))) {
            // 增加库存并移除定时任务
            ercsellService.addSurplus(pd);
            QuartzManager.removeJob(pd.getString("ERCSELL_ID"));
            // 更改订单状态 已取消
            pd.put("ERCSELLRECORD_ID", orderId);
            pd = ercsellrecordService.findById(pd);
            pd.put("STATE", "已取消");
            ercsellrecordService.edit(pd);
            // 增加用户user余额,并创建记录
            addUsdtMoney(pd.get("USER_ID").toString(), num, "");
            addUsdtWalletRec(pd.getString("USER_NAME"), pd.get("USER_ID").toString(), "取消訂單", "+", num, "", "已完成");
        }

        return "success";
    }

    /**
     * 功能描述：检查用户是否实名认证
     *
     * @return 哪些没有设置
     * @author Ajie
     * @date 2020/1/3 0003
     */
    private String checkRealName() throws Exception {
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = new PageData();
        user.put("ACCOUNT_ID", memUser.getACCOUNT_ID());
        user = accountService.findById(user);
        if (Tools.isEmpty(user.getString("NAME"))) {
            return "姓名未设置";
        }
        if (Tools.isEmpty(user.getString("PHONE"))) {
            return "手机号未设置";
        }
        if (Tools.isEmpty(user.getString("PAYMENT"))) {
            return "收款方式未设置";
        }
        // 支付宝
        if (Tools.isEmpty(user.getString("ALIPAY"))) {
            return "支付宝账号未设置";
        }
        // 银行卡
        if (Tools.isEmpty(user.getString("HOLDER"))) {
            return "开户人未设置";
        }
        if (Tools.isEmpty(user.getString("BANK_NUMBER"))) {
            return "银行卡号未设置";
        }
        if (Tools.isEmpty(user.getString("BANK_NAME"))) {
            return "银行名称未设置";
        }
        return "success";
    }

    /**
     * 功能描述：前台用户请求发布币币交易
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/3 0003
     */
    @RequestMapping(value = "/issueTransac", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String issueTransac() throws Exception {
        // 获取前台传过来的 usdt单价 和 xmc数量 及 类型 挂买 还是 挂卖
        PageData pd = this.getPageData();
        double usdtPrice = Double.parseDouble(pd.getString("usdtPrice"));
        double xmc = Double.parseDouble(pd.getString("xmc"));
        double minNum = 10;
        if (xmc < minNum) {
            return "最少發佈：" + minNum;
        }
        String tag = pd.getString("tag");
        // 获取系统设置的最少单价
        pd = daily_chartService.getNewRecord(pd);
        double price = Double.parseDouble(pd.get("LATEST_PRICE").toString());

        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());

        if ("掛買".equals(tag)) {
            // 判断是否低于 后台设置价格的幅度
            double min = 0.2;
            double minPrice = Tools.afterDecimal(price + price * min, 2);
            // 判断是否高于 后台设置价格的幅度
            double max = 0.5;
            double maxPrice = Tools.afterDecimal(price + price * max, 2);

            if (usdtPrice < minPrice) {
                return "今日價格不得低於：" + minPrice;
            }
            if (usdtPrice > maxPrice) {
                return "今日價格不得高於：" + maxPrice;
            }
            // 获取用户 usdt数量
            double usdtNumber = Double.parseDouble(user.get("USDT_WALLET").toString());
            if (usdtPrice * xmc > usdtNumber) {
                return "最大可发布：" + usdtNumber + " USDT";
            }
            // 减少USDT数量
            reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(usdtPrice * xmc), "USDT_WALLET");
            // 创建钱包记录
            addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), tag, "-", String.valueOf(usdtPrice * xmc), "", "已完成");
        }

        if ("掛賣".equals(tag)) {
            // 判断是否低于 后台设置价格的幅度
            double min = 0;
            double minPrice = Tools.afterDecimal(price * (1 - min), 2);
            // 判断是否高于 后台设置价格的幅度
            double max = 0.05;
            double maxPrice = Tools.afterDecimal(price + price * max, 2);

            if (usdtPrice < minPrice) {
                return "今日價格不得低於：" + minPrice;
            }
            if (usdtPrice > maxPrice) {
                return "今日價格不得高於：" + maxPrice;
            }
            // 获取用户 xmc数量
            double xmcNumber = Double.parseDouble(user.get("XMC_WALLET").toString());
            if (xmc > xmcNumber) {
                return "最大可发布：" + xmcNumber + " XMC";
            }
            // 减少XMC余额
            reduceXmcMoney(memUser.getACCOUNT_ID(), String.valueOf(xmc));
            // 创建钱包记录
            addXmcWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), tag, "-", String.valueOf(xmc), "已完成", "");
        }
        // 创建币币交易记录
        addCoinRec(xmc, memUser.getACCOUNT_ID(), memUser.getUSER_NAME(), tag, "-", "待交易", usdtPrice, "", "", "");
        return "success";
    }

    /**
     * 功能描述：前台用户进行币币交易
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/4 0004
     */
    @RequestMapping(value = "/userTransac", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String userTransac() throws Exception {
        // 获取前台传过来的 订单ID 和 类型 购买 还是 出售 还有 安全密码
        PageData pd = this.getPageData();
        String tag = pd.getString("tag");
        String safePass = StringUtil.applySha256(pd.getString("pass"));
        // 根据订单ID查询信息
        pd = coin_trading_recService.findById(pd);
        if ("已完成".equals(pd.getString("STATE"))) {
            return "訂單被別人搶先交易";
        }
        // usdt 价格 xmc 数量
        double usdtPrice = Double.parseDouble(pd.get("XMC_PRICE").toString());
        double xmc = Double.parseDouble(pd.get("USDT_AMOUNT").toString());
        // 扣除手续费 币币交易手续费
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        double charge = Double.parseDouble(par.getString("BB_SERVICE_CHARGE")) / 100;
        // 订单总价
        double money;
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        if (!memUser.getSECURITY_PASSWORD().equals(safePass)) {
            return "交易密码错误";
        }
        if ("購買".equals(tag)) {
            // 获取用户 usdt数量
            double usdtNumber = Double.parseDouble(user.get("USDT_WALLET").toString());
            money = xmc * usdtPrice;
            if (money > usdtNumber) {
                return "USDT余额不足：" + money;
            }
            // 减少我的USDT数量
            reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(money), "USDT_WALLET");
            addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), tag, "-", String.valueOf(money), "", "已完成");
            // 增加我的XMC余额
            // xmc *= (1 - charge);
            addXmcMoney(memUser.getACCOUNT_ID(), String.valueOf(xmc));
            addXmcWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), tag, "+", String.valueOf(xmc), "已完成", "");
            // 增加卖家的USDT数量
            money *= (1 - charge);
            addUsdtMoney(pd.get("MY_USER_ID").toString(), String.valueOf(money), "");
            addUsdtWalletRec(pd.get("MY_USER_NAME").toString(), pd.get("MY_USER_ID").toString(), "出售XMC", "+", String.valueOf(money), "", "已完成");
        }
        if ("出售".equals(tag)) {
            // 获取用户 xmc数量
            double xmcNumber = Double.parseDouble(user.get("XMC_WALLET").toString());
            money = xmc * usdtPrice;
            if (xmc > xmcNumber) {
                return "XMC余额不足：" + xmc;
            }
            // 减少我的XMC余额
            reduceXmcMoney(memUser.getACCOUNT_ID(), String.valueOf(xmc));
            addXmcWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), tag, "-", String.valueOf(xmc), "已完成", "");
            // 增加我的USDT数量
            // money *= (1 - charge);
            addUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(money), "USDT_WALLET");
            addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), tag, "+", String.valueOf(money), "", "已完成");
            xmc *= (1 - charge);
            // 增加卖家的xmc余额
            addXmcMoney(pd.get("MY_USER_ID").toString(), String.valueOf(xmc));
            addXmcWalletRec(pd.get("MY_USER_NAME").toString(), pd.get("MY_USER_ID").toString(), "出售USDT", "+", String.valueOf(xmc), "已完成", "");
        }
        // 更新订单状态并添加交易人ID和用户名
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("HIS_USER_ID", memUser.getACCOUNT_ID());
        pd.put("HIS_USER_NAME", memUser.getUSER_NAME());
        pd.put("STATE", "已完成");
        coin_trading_recService.edit(pd);
        // 创建币币交易记录
        addCoinRec(xmc, memUser.getACCOUNT_ID(), memUser.getUSER_NAME(), tag, "-", "已完成", usdtPrice, pd.get("MY_USER_ID").toString(), pd.getString("MY_USER_NAME"), (xmc / (1 - charge) - xmc));
        return "success";
    }

    /**
     * 功能描述：创建币币交易记录
     *
     * @param xmc        XMC数量
     * @param myUserId   我的用户ID
     * @param myUserName 我的用户名
     * @param type       类型 填写中文即可 如 挂卖 挂买 买入出售 等
     * @param tag        + 或者 -
     * @param state      状态 填写中文
     * @param usdtPrice  usdt价格
     * @param hisID      对方用户ID
     * @param hisName    对方用户名
     * @param charge     手续费
     * @return 订单ID
     * @author Ajie
     * @date 2020/1/3 0003
     */
    private String addCoinRec(double xmc, String myUserId, String myUserName, String type, String tag, String state, double usdtPrice, String hisID, String hisName, Object charge) throws Exception {
        String uuid = this.get32UUID();
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USDT_AMOUNT", xmc);
        pd.put("MY_USER_ID", myUserId);
        pd.put("MY_USER_NAME", myUserName);
        pd.put("AMOUNT_TYPE", type);
        pd.put("TAG", tag);
        pd.put("STATE", state);
        pd.put("XMC_PRICE", usdtPrice);
        pd.put("HIS_USER_ID", hisID);
        pd.put("HIS_USER_NAME", hisName);
        pd.put("CHARGE", charge);
        pd.put("COIN_TRADING_REC_ID", uuid);
        coin_trading_recService.save(pd);
        return uuid;
    }

    /**
     * 功能描述：用户请求复投
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/4 0004
     */
    @RequestMapping(value = "/reinvestment", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String reinvestment() throws Exception {
        // 缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 获取投资额最高的一条投资记录
        PageData pd = lncome_detailsService.getMaxOrderByUserId(user);
        if (pd == null || pd.size() <= 0) {
            return "没有投资记录";
        }
        if ("待确认".equals(pd.getString("STATE"))) {
            return "申請退本，後台未確認";
        }
        // 判断用户是否出局to_walletAdress
        if (!"已出局".equals(pd.getString("STATE"))) {
            return "上次投資未出局";
        }
        // 取投资额、 用户USDT数量
        double invest = Double.parseDouble(pd.get("MONEY").toString());
        double usdt = Double.parseDouble(user.get("USDT_WALLET").toString());
        if (usdt < invest) {
            return "USDT不足 " + invest;
        }
        // 调用创建购买记录的函数
        addBillRecord(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "复投", "-", String.valueOf(invest), "1", String.valueOf(invest * 2), "未出局");
        // 减少usdt钱余额
        reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(invest), "USDT_WALLET");
        // 增加钱包记录
        addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "复投", "-", String.valueOf(invest), "", "已完成");
        // 给自己和所有上级团队业绩
        user.put("money", invest);
        user.put("tag", '+');
        accountService.updateTeamSum(user);
        // 调用多线程判断能达到什么等级 和 发放平级奖和极差奖
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-复投判断能达到什么等级和发放奖金");
                checkRank(memUser.getRE_PATH() + "," + memUser.getACCOUNT_ID());
            }
        });
        return "success";
    }

    /**
     * 功能描述：用户请求升级
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/4 0004
     */
    @RequestMapping(value = "/upgrade", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String upgrade() throws Exception {
        // 获取前台传过来的 升级数额
        PageData pd = this.getPageData();
        double addAmount = Double.parseDouble(pd.get("num").toString());
        // 缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 获取最新的一条投资记录
        pd = lncome_detailsService.getNewOrderByUserId(user);
        if (pd == null || pd.size() <= 0) {
            return "没有投资记录";
        }
        // 判断用户是否出局
        if ("已出局".equals(pd.getString("STATE"))) {
            return "上次投資已出局";
        }
        if ("待确认".equals(pd.getString("STATE"))) {
            return "申請退本，後台未確認";
        }
        // 取订单投资额、 用户USDT数量
        double invest = Double.parseDouble(pd.get("MONEY").toString());
        double usdt = Double.parseDouble(user.get("USDT_WALLET").toString());
        //  倍数验证
        int muitiple = 100;
        if (addAmount % muitiple != 0) {
            return "请输入" + muitiple + "的倍数";
        }
        if (usdt < addAmount) {
            return "USDT不足 " + addAmount;
        }
        // 更新投资金额和收益
        pd.put("MONEY", invest + addAmount);
        pd.put("PROFIT", (invest + addAmount) * 2);
        lncome_detailsService.edit(pd);
        // 减少usdt钱余额
        reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(addAmount), "USDT_WALLET");
        // 增加钱包记录
        addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "升级", "-", String.valueOf(addAmount), "", "已完成");
        // 给自己和所有上级增加团队业绩
        user.put("money", addAmount);
        user.put("tag", '+');
        accountService.updateTeamSum(user);
        // 调用多线程判断能达到什么等级 和 发放平级奖和极差奖
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-复投判断能达到什么等级和发放奖金");
                checkRank(memUser.getRE_PATH() + "," + memUser.getACCOUNT_ID());
            }
        });
        return "success";
    }

    /**
     * 功能描述：用户请求退本
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/4 0004
     */
    @RequestMapping(value = "/refund", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String refund() throws Exception {
        PageData pd;
        // 缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 获取最新的一条投资记录
        pd = lncome_detailsService.getNewOrderByUserId(user);
        if (pd == null || pd.size() <= 0) {
            return "沒有投資記錄";
        }
        // 判断用户是否出局
        if (!"未出局".equals(pd.getString("STATE"))) {
            return "上次投資已出局或者已申請退本";
        }
        // 获取已获得的分红收益、这次投资的金额
        double sumCount = Double.parseDouble(user.get("ACCUMULA").toString());
        double amount = Double.parseDouble(pd.get("MONEY").toString());
        if (sumCount >= amount) {
            return "分紅已回本，無法退本";
        }
        // 更新投资 状态为 待确认
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATE", "待确认");
        lncome_detailsService.edit(pd);
        return "success";
    }

    /**
     * 后台确认退本
     *
     * @throws Exception
     */
    @RequestMapping(value = "/adopt", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String adopt() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "确认退本");
        PageData pd = this.getPageData();
        // 查询投资订单信息
        pd = lncome_detailsService.findById(pd);
        // 投资时间
        String InvestmentTime = pd.getString("GMT_CREATE");
        // 获取投资数额
        double amount = Double.parseDouble(pd.get("MONEY").toString());
        // 获取用户信息
        PageData pd1 = new PageData();
        pd1.put("ACCOUNT_ID", pd.get("USER_ID"));
        PageData user = accountService.findById(pd1);
        // 用户已获得累积收益
        double sumCount = Double.parseDouble(user.get("ACCUMULA").toString());
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 退本手续费
        double outCharge = amount * Double.parseDouble(par.get("OUT_CHARGE").toString()) / 100;
        // 減去已得到的分紅
        double money = amount - sumCount;
        //  減去手續費
        money -= outCharge;
        // 增加用户累积USDT余额 并创建记录
        addUsdtMoney(pd.get("USER_ID").toString(), String.valueOf(money), "USDT_COUNT");
        addCountWalletRec(pd.getString("USER_NAME"), pd.get("USER_ID").toString(), "退本", "+", String.valueOf(money), "1", outCharge, "");
        // 更改状态已出局
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATE", "已出局");
        lncome_detailsService.edit(pd);
        // 减少自身团队业绩 和所有上级的团队业绩
        user.put("money", amount);
        user.put("tag", '-');
        accountService.updateTeamSum(user);
        // 累积收益清0
        pd = new PageData();
        pd.put("ACCOUNT_ID", user.get("ACCOUNT_ID"));
        pd.put("ACCUMULA", 0);
        accountService.updateFor(pd);
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
        // 多线程异步执行 扣除所有上级 从我投资后的时间开始 获取的收益
        MyThreadPoolManager.getsInstance().execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.currentThread().setName("pool-扣除所有上级,从我投资后的时间开始,获取的收益");
                deductionAllSuperior(InvestmentTime, user);
            }
        });
        return "success";
    }

    /**
     * 功能描述：扣除所有上级 某个时间后从某个用户上 获取的所有收益
     *
     * @param startTime 开始时间
     * @return user 用户
     * @author Ajie
     * @date 2020/2/5 0005
     */
    public void deductionAllSuperior(String startTime, PageData user) throws Exception {
        user = (PageData) applicati.getAttribute(user.getString("USER_NAME"));
        System.out.println("退本用户：" + user.getString("USER_NAME"));
        // 获取所有上级
        List<PageData> allUser = accountService.listByUpperUserId(user);
        user.put("TIME", startTime);
        for (PageData pd : allUser) {
            String userId = pd.get("ACCOUNT_ID").toString();
            String userName = pd.getString("USER_NAME");
            // 获取 开始时间 后从这个用户 给我来带的收益总和
            user.put("USER_ID", pd.get("ACCOUNT_ID"));
            PageData result = accum_recService.getByTimeAndUser(user);
            if (result == null) {
                continue;
            }
            System.out.println("用户：" + userName + "  收益累积： " + result);
            double totalIncome = Double.parseDouble(result.get("SUM_MONEY").toString());
            if (totalIncome <= 0) {
                continue;
            }
            // 扣除上级累积钱包余额 并创建记录
            reduceUsdtMoney(userId, String.valueOf(totalIncome), "USDT_COUNT");
            addCountWalletRec(userName, userId, "團隊退本", "-", String.valueOf(totalIncome), "1", 0, user.getString("USER_NAME"));
        }

    }

    /**
     * 后台驳回退本
     *
     * @throws Exception
     */
    @RequestMapping(value = "/reject", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String reject() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "驳回退本");
        PageData pd = this.getPageData();
        // 查询投资订单信息
        pd = lncome_detailsService.findById(pd);
        // 更改状态未出局
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATE", "未出局");
        lncome_detailsService.edit(pd);
        return "success";
    }

    /**
     * 功能描述：前台用户请求从usdt或者xmc钱包划入对冲钱包
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/6 0006
     */
    @RequestMapping(value = "/cutIn", produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional
    public String updateCutIn() throws Exception {
        PageData pd = this.getPageData();
        // 获取划入类型、数额、安全密码
        String inType = pd.getString("tag");
        double num = Double.parseDouble(pd.get("num").toString());
        String safePass = pd.getString("pass");
        if (safePass.length() < 6) {
            return "密碼最少六位數";
        }
        safePass = StringUtil.applySha256(safePass);
        if (num < 0) {
            return "請輸入大於 0 的數額";
        }
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        if (!safePass.equals(user.getString("SECURITY_PASSWORD"))) {
            return "交易密码错误";
        }
        double usdt = Double.parseDouble(user.get("USDT_WALLET").toString());
        double xmc = Double.parseDouble(user.get("XMC_WALLET").toString());
        // 如果选择的是 划入usdt
        if ("usdt".equals(inType)) {
            if (num > usdt) {
                return "USDT 不足";
            }

            // 减少USDT钱包余额并创建记录
            reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "USDT_WALLET");
            addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "轉出", "-", String.valueOf(num), "", "已完成");
            // 增加对冲钱包之USDT余额并创建记录
            addUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "HEDGING_USDT");
            addHedgWhalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "劃入", "", "-", num, "1", "", "");
        }
        // 如果选择的是 划入xmc
        if ("xmc".equals(inType)) {
            if (num > xmc) {
                return "XMC 不足";
            }
            // 减少XMC钱包余额 并创建记录
            reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "XMC_WALLET");
            addXmcWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "轉出", "-", String.valueOf(num), "已完成", "");
            // 增加对冲钱包之xmc钱包余额
            addUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "HEDGING_XMC");
            addHedgWhalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "劃入", "", "-", num, "0", "", "");
        }
        return "success";
    }

    /**
     * 功能描述：前台用户请求从累积收益钱包划出
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/9 0006
     */
    @RequestMapping(value = "/countOut", produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional
    public String updateCountOut() throws Exception {
        PageData pd = this.getPageData();
        // 获取划出类型、数额、安全密码
        String inType = pd.getString("tag");
        double num = Double.parseDouble(pd.get("num").toString());
        String safePass = pd.getString("pass");
        if (safePass.length() < 6) {
            return "密碼最少六位數";
        }
        safePass = StringUtil.applySha256(safePass);
        if (num < 0) {
            return "請輸入大於 0 的數額";
        }
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        if (!safePass.equals(user.getString("SECURITY_PASSWORD"))) {
            return "交易密碼錯誤";
        }
        double usdt = Double.parseDouble(user.get("USDT_COUNT").toString());
        double xmc = Double.parseDouble(user.get("XMC_COUNT").toString());
        // 如果选择的是 划入usdt
        if ("usdt".equals(inType)) {
            if (num > usdt) {
                return "USDT 不足";
            }
            // 减少USDT累积钱包余额并创建记录
            reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "USDT_COUNT");
            addCountWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "劃出", "-", String.valueOf(num), "1", "", "");
            // 增加usdt钱包余额
            addUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "");
            addUsdtWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "劃入", "+", String.valueOf(num), "", "已完成");
        }
        // 如果选择的是 划入xmc
        if ("xmc".equals(inType)) {
            if (num > xmc) {
                return "XMC 不足";
            }
            // 减少XMC累积钱包余额并创建记录
            reduceUsdtMoney(memUser.getACCOUNT_ID(), String.valueOf(num), "XMC_COUNT");
            addCountWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "劃出", "-", String.valueOf(num), "0", "", "");
            // 增加xmc钱包余额
            addXmcMoney(memUser.getACCOUNT_ID(), String.valueOf(num));
            addXmcWalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "劃入", "+", String.valueOf(num), "已完成", "");
        }
        return "success";
    }

    /**
     * 功能描述：更新对冲钱包余额
     *
     * @param userId 用户ID
     * @param amount 数额
     * @param tag    + 或者 -
     * @param type   钱包类型 1:usdt、0：xmc
     * @author Ajie
     * @date 2020/1/6 0006
     */
    private void updateHedgingMoney(String userId, Object amount, String tag, int type) throws Exception {
        PageData pd = new PageData();
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("ACCOUNT_ID", userId);
        pd.put("tag", tag);
        if (type == 1) {
            pd.put("HEDGING_USDT", amount);
        }
        if (type == 0) {
            pd.put("HEDGING_XMC", amount);
        }
        accountService.updateMoney(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：创建对冲钱包账单记录
     *
     * @param userName   我的用户名
     * @param userId     我的用户ID
     * @param type       类型 填写中文 如划入 划出 转账等
     * @param site       钱包地址
     * @param tag        + 或者 -
     * @param amount     数额
     * @param walletType 钱包类型 0：XMC、1：USDT
     * @param hisName    对方用户名
     * @param hisId      对方用户ID
     * @author Ajie
     * @date 2020/1/6 0006
     */
    private void addHedgWhalletRec(String userName, String userId, String type, String site, String tag, Object amount, String walletType, String hisName, String hisId) throws Exception {
        PageData pd = new PageData();
        String time = DateUtil.getTime();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 金额类型
        pd.put("AMOUNT_TYPE", type);
        pd.put("WALLET_SITE", site);
        pd.put("TAG", tag);
        pd.put("MONEY", amount);
        pd.put("WALLET_TYPE", walletType);
        pd.put("NAME_IN", hisName);
        pd.put("ACCOUNT_ID", hisId);
        // 主键自增
        pd.put("HEDGING_REC_ID", "");
        hedging_recService.save(pd);
    }

    /**
     * 功能描述：前台用户请求转账
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/6 0006
     */
    @RequestMapping(value = "/transfer", produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional
    public String updateTransfer() throws Exception {
        PageData pd = this.getPageData();
        // 获取转账类型、数额、安全密码、对方用户名
        String inType = pd.getString("tag");
        double num = Double.parseDouble(pd.get("num").toString());
        String safePass = StringUtil.applySha256(pd.getString("pass"));
        String hisName = pd.getString("hisName");
        if (num < 0) {
            return "请输入大于 0 的数额";
        }
        // 从缓存获取用户信息
        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
        PageData user = (PageData) applicati.getAttribute(memUser.getUSER_NAME());
        // 查询转入方信息
        PageData hisInfo = (PageData) applicati.getAttribute(hisName);
        if (hisInfo == null) {
            return "用户未注册";
        }
        String hisId = hisInfo.get("ACCOUNT_ID").toString();
        // 下级往顶点账号转账 不用验证是否为团队内
        if (!"10000".equals(hisId)) {
            // 如果转出方不是我的团队内
            boolean lock = hisInfo.getString("RE_PATH").contains(memUser.getACCOUNT_ID());
            if (!lock) {
                return "只能团队内互转";
            }
        }
        if (!safePass.equals(user.getString("SECURITY_PASSWORD"))) {
            return "交易密码错误";
        }
        //  获取对冲钱包的 余额
        double usdt = Double.parseDouble(user.get("HEDGING_USDT").toString());
        double xmc = Double.parseDouble(user.get("HEDGING_XMC").toString());
        // 如果选择的是 划入usdt
        if ("usdt".equals(inType)) {
            if (num > usdt) {
                return "USDT 不足";
            }
            // 减少对冲钱包余额并创建记录
            updateHedgingMoney(memUser.getACCOUNT_ID(), num, "-", 1);
            addHedgWhalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "转出", "", "-", num, "1", hisInfo.getString("USER_NAME"), hisId);
            // 增加转入方的对冲钱包余额并创建记录
            addUsdtMoney(hisId, String.valueOf(num), "HEDGING_USDT");
            addHedgWhalletRec(hisInfo.getString("USER_NAME"), hisId, "转入", "", "+", String.valueOf(num), "1", memUser.getUSER_NAME(), memUser.getACCOUNT_ID());
        }
        // 如果选择的是 划入xmc
        if ("xmc".equals(inType)) {
            if (num > xmc) {
                return "XMC 不足";
            }
            // 减少对冲钱包余额并创建记录
            updateHedgingMoney(memUser.getACCOUNT_ID(), num, "-", 0);
            addHedgWhalletRec(memUser.getUSER_NAME(), memUser.getACCOUNT_ID(), "转出", "", "-", num, "0", hisInfo.getString("USER_NAME"), hisId);
            // 增加转入方的对冲钱包余额并创建记录
            addUsdtMoney(hisId, String.valueOf(num), "HEDGING_XMC");
            addHedgWhalletRec(hisInfo.getString("USER_NAME"), hisId, "转入", "", "+", String.valueOf(num), "0", memUser.getUSER_NAME(), memUser.getACCOUNT_ID());
        }
        return "success";
    }

    /**
     * 功能描述：后台指派 某用户为 M8等级
     *
     * @return 处理结果
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/appoint", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String appoint() throws Exception {
        PageData pd = this.getPageData();
        // 根据传过来的用户ID获取信息
        MemUser user = accountService.findByIdReturnEntity(pd);
        if ("9".equals(user.getUSER_RANK())) {
            return "当前用户已是M8级别";
        }
        PageData rank = (PageData) applicati.getAttribute(Const.APP_RANK + "9");
        if (rank == null) {
            return "找不到M8这个等级";
        }
        // 设置用户为M8
        pd = new PageData();
        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
        pd.put("GLOBAL_TIME", DateUtil.getTime());
        pd.put("USER_RANK", "9");
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(user.getUSER_NAME(), pd);
        return "success";
    }

    /**
     * 功能描述：后台确认USDT提现完成
     *
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/usdtComplete", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUsdtComplete() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "确认USDT提现完成");
        PageData pd = this.getPageData();
        // 查询钱包账单记录
        pd = usdt_wallet_recService.findById(pd);
        // 获取状态
        String status = pd.getString("STATUS");
        if (!"待确认".equals(status)) {
            return "该订单完成或者已驳回";
        }
        // 更改状态
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATUS", "已完成");
        usdt_wallet_recService.edit(pd);
        return "success";
    }

    /**
     * 功能描述：后台确认xmc提现完成
     *
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/xmcComplete", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateXmcComplete() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "确认XMC提现完成");
        PageData pd = this.getPageData();
        // 查询钱包账单记录
        pd = xmc_wallet_recService.findById(pd);
        // 获取状态
        String status = pd.getString("STATUS");
        if (!"待确认".equals(status)) {
            return "该订单完成或者已驳回";
        }
        // 更改状态
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATUS", "已完成");
        xmc_wallet_recService.edit(pd);
        return "success";
    }

    /**
     * 功能描述：后台确认xmc充值完成
     *
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/xmcPayComplete", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateXmcPayComplete() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "确认XMC充值完成");
        PageData pd = this.getPageData();
        // 查询钱包账单记录
        pd = xmc_wallet_recService.findById(pd);
        // 获取状态
        String status = pd.getString("STATUS");
        if (!"待确认".equals(status)) {
            return "该订单完成或者已驳回";
        }
        // 更改状态
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATUS", "已完成");
        xmc_wallet_recService.edit(pd);
        // 获取订单数量、用户ID
        double num = Double.parseDouble(pd.get("MONEY").toString());
        String userId = pd.get("USER_ID").toString();
        // 用用户增加余额
        addXmcMoney(userId, String.valueOf(num));
        return "success";
    }

    /**
     * 功能描述：后台驳回xmc充值
     *
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/xmcRecharge", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateXmcRecharge() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "驳回XMC充值");
        PageData pd = this.getPageData();
        // 查询钱包账单记录
        pd = xmc_wallet_recService.findById(pd);
        // 获取状态
        String status = pd.getString("STATUS");
        if (!"待确认".equals(status)) {
            return "该订单完成或者已驳回";
        }
        // 更改状态
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATUS", "驳回");
        xmc_wallet_recService.edit(pd);
        return "success";
    }

    /**
     * 功能描述：后台驳回usdt提现
     *
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/usdtReject", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUsdtReject() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "驳回usdt提现");
        PageData pd = this.getPageData();
        // 查询钱包账单记录
        pd = usdt_wallet_recService.findById(pd);
        PageData pd1 = new PageData();
        pd1.put("ACCOUNT_ID", pd.get("USER_ID"));
        MemUser user = accountService.findByIdReturnEntity(pd1);
        // 更改状态
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATUS", "駁回");
        usdt_wallet_recService.edit(pd);
        // 给用户增加余额
        double amount = Double.parseDouble(pd.get("MONEY").toString());
        addUsdtMoney(user.getACCOUNT_ID(), String.valueOf(amount), "");
        addUsdtWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "提幣駁回", "+", String.valueOf(amount), "", "已完成");
        return "success";
    }

    /**
     * 功能描述：后台驳回xmc提现
     *
     * @author Ajie
     * @date 2020/1/7 0007
     */
    @RequestMapping(value = "/xmcReject", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateXmcReject() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "驳回XMC提现");
        PageData pd = this.getPageData();
        // 查询钱包账单记录
        pd = xmc_wallet_recService.findById(pd);
        PageData pd1 = new PageData();
        pd1.put("ACCOUNT_ID", pd.get("USER_ID"));
        MemUser user = accountService.findByIdReturnEntity(pd1);
        // 更改状态
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATUS", "駁回");
        xmc_wallet_recService.edit(pd);
        // 给用户增加余额
        double amount = Double.parseDouble(pd.get("MONEY").toString());
        double charge = 0.05;
        charge = 1 - charge;
        // 计算扣除手续费前的金额
        amount = amount / charge;
        addXmcMoney(user.getACCOUNT_ID(), String.valueOf(amount));
        addXmcWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "提幣駁回", "+", String.valueOf(amount), "已完成", "");
        return "success";
    }

    /**
     * 功能描述：检查用户是的能达到M8
     *
     * @param userId 用户ID
     * @author Ajie
     * @date 2020/1/8 0008
     */
    private void checkMaxRank(String userId) throws Exception {
        PageData pd = new PageData();
        // 根据用户ID 获取信息
        pd.put("ACCOUNT_ID", userId);
        pd = accountService.findById(pd);
        // 获取用户当前等级
        String userRank = pd.getString("USER_RANK");
        // 如果已经是M8 直接返回
        if ("9".equals(userRank)) {
            return;
        }
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        // 获取 多少个下级是M8等级才能 晋级M8
        int num = Integer.parseInt(par.get("PROMOTE").toString());
        // 查询数据库 获取伞下有多少人是M8的
        pd.put("rank", 9);
        List<PageData> userList = accountService.listByDownUserId(pd);
        // 伞下M8人数少于后台设置的人数 直接返回
        if (userList.size() < num) {
            return;
        }
        //  设置用户为M8
        pd = new PageData();
        pd.put("ACCOUNT_ID", userId);
        pd.put("GLOBAL_TIME", DateUtil.getTime());
        pd.put("USER_RANK", 9);
        accountService.updateFor(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
    }

    /**
     * 功能描述：每日静态奖测试
     *
     * @param
     * @return
     * @author Ajie
     * @date 2020/1/10 0010
     */
    @RequestMapping(value = "test")
    public void jobTest() {
        QuartzManager.removeJob("testStaticBouns");
        String cron = "2020-1-10 14:59:08";
        cron = DateUtil.getCron(cron);
        // 添加执行一次的定时任务
        QuartzManager.addJob("testStaticBouns", StaticBonusTaskJob.class, cron);
        QuartzManager.taskMethod();

    }

    /**
     * 功能描述：取消币币交易订单
     * @author Ajie
     * @date 2020/2/24 0024
     * @return 处理结果
     */
    @RequestMapping(value = "/cancelCoinOrder", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String cancellationOrder() throws Exception {
        // 获取传过来的订单ID
        PageData pd = this.getPageData();
        // 查询订单信息
        pd = coin_trading_recService.findById(pd);
        if (!"待交易".equals(pd.getString("STATE"))){
            return "订单交易中或已完成交易";
        }
        // 执行判断退回
        double price = Double.parseDouble(pd.get("XMC_PRICE").toString());
        double num = Double.parseDouble(pd.get("USDT_AMOUNT").toString());
        double money = price * num;
        String userId = pd.get("MY_USER_ID").toString();
        String userName = pd.getString("MY_USER_NAME");
        if ("掛買".equals(pd.getString("AMOUNT_TYPE"))) {
            // 退回USDT
            addUsdtMoney(userId, String.valueOf(money), "");
            addUsdtWalletRec(userName, userId, "取消订单", "+", String.valueOf(money), "", "已完成");
        } else {
            // 退回XMC
            addXmcMoney(userId, String.valueOf(money));
            addXmcWalletRec(userName, userId, "取消订单", "+", String.valueOf(money), "已完成", "");
        }
        // 更改订单状态为 已取消
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("STATE", "已取消");
        coin_trading_recService.edit(pd);

        return "success";
    }





















}


