package com.fh.controller.front;

import com.fh.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能描述：前台页面登录后需要跳转的页面
 *
 * @author Ajie
 * @date 2019/11/26 0026
 */
@Controller
@RequestMapping(value = "/front")
public class Frontoid extends BaseController {

//
//    /**
//     * 功能描述：访问前台【首页】
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_index")
//    private ModelAndView toIndex() throws Exception {
//        ModelAndView mv = getModelAndView();
//        PageData pd = new PageData();
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        // 系统参数配置
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 新闻公告
//        List<PageData> newsList = (List<PageData>) applicati.getAttribute(Const.APP_NEWS);
//        // 轮播图
//        List<PageData> chartList = (List<PageData>) applicati.getAttribute(Const.APP_CHART);
//        // 申购SMD列表
//        pd.put("PHONE", user.getPHONE());
//        List<PageData> buySmdList = purchase_smdService.listAllByPhone(pd);
//        // 赎回SMD列表
//        List<PageData> sellSmdList = sell_smdService.listByPhone(pd);
//        pd.put("PHONE", user.getPHONE());
//        pd = accountService.findByPhone(pd);
//        mv.setViewName("front/index/home");
//        mv.addObject("par", par);
//        mv.addObject("newsList", newsList);
//        mv.addObject("chartList", chartList);
//        mv.addObject("buyList", buySmdList);
//        mv.addObject("sellList", sellSmdList);
//        mv.addObject("pd", pd);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【我的】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_mine")
//    private ModelAndView toMine() throws Exception {
//        ModelAndView mv = getModelAndView();
//        // 从session中获取当前登录用户信息
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = new PageData();
//        user.put("PHONE", memUser.getPHONE());
//        user = accountService.findByPhone(user);
//        mv.setViewName("front/index/mine");
//        mv.addObject("user", user);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【申购SMD】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_apply")
//    private ModelAndView toApply() {
//        ModelAndView mv = getModelAndView();
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        mv.setViewName("front/index-ui/apply");
//        mv.addObject("par", par);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【新闻公告】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_news")
//    private ModelAndView toNews() {
//        ModelAndView mv = getModelAndView();
//        // 新闻公告
//        List<PageData> newsList = (List<PageData>) applicati.getAttribute(Const.APP_NEWS);
//        mv.setViewName("front/index-ui/news");
//        mv.addObject("newsList", newsList);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【新闻详情】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_newsDetails")
//    private ModelAndView toNewsDetails() throws Exception {
//        ModelAndView mv = getModelAndView();
//        PageData news = new PageData();
//        // 获取传过来的新闻ID
//        news = this.getPageData();
//        news = sys_newsService.findById(news);
//        mv.setViewName("front/index-ui/news-details");
//        mv.addObject("news", news);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【赎回SMD】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_redeem")
//    private ModelAndView toRedeem() {
//        ModelAndView mv = getModelAndView();
//        // 从session获取当前登录用户信息，然后在从上下文缓存获取用户信息，防止后台直接修改账号钱包
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = (PageData) applicati.getAttribute(memUser.getPHONE());
//        // 获取系统参数配置
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        mv.setViewName("front/index-ui/redeem");
//        mv.addObject("user", user);
//        mv.addObject("par", par);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【修改密码】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_changedPassword")
//    private ModelAndView toChangedPassword() {
//        ModelAndView mv = getModelAndView();
//        mv.setViewName("front/my/changed-password");
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【冻结明细】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_frozen")
//    private ModelAndView toFrozen() throws Exception {
//        ModelAndView mv = getModelAndView();
//        // 从缓存获取当前登录用户信息
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData pd = new PageData();
//        pd.put("PHONE", user.getPHONE());
//        mv.setViewName("front/my/frozen");
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【冻结明细】页面 分页查询
//     *
//     * @author Ajie
//     * @date 2019年12月14 10:54:08
//     */
//    @RequestMapping(value = "toFrozenPage")
//    @ResponseBody
//    private Object toFrozenPage() throws Exception {
//        Pager<PageData> pager = new Pager<>();
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData pd = this.getPageData();
//        // 页码
//        int num = Integer.parseInt(pd.get("num").toString());
//        // 每页数据条数
//        int pageSize = Integer.parseInt(pd.get("size").toString());
//        pd.put("PHONE", user.getPHONE());
//        // 得到全部数据
//        List<PageData> recList = freezing_detailsService.listByPhone(pd);
//        // 第 N 页
//        pager.setCurentPageIndex(num);
//        // 每页 N 条
//        pager.setCountPerpage(pageSize);
//        pager.setBigList(recList);
//        // 得到小的集合(分页的当前页的记录)
//        List<PageData> curPageData = pager.getSmallList();
//        // 得到总页数
//        int totalPage = pager.getPageCount();
//        // 查询冻结总额
//        pd = freezing_detailsService.getSumByPhone(pd);
//        pd.put("curPageData", curPageData);
//        pd.put("totalPage", totalPage);
//        return pd;
//    }
//
//    /**
//     * 功能描述：访问前台【会员互转】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_interturn")
//    private ModelAndView toInterturn() {
//        ModelAndView mv = getModelAndView();
//        mv.setViewName("front/my/interturn");
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【个人资料】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_personalData")
//    private ModelAndView toPersonalData() throws Exception {
//        ModelAndView mv = getModelAndView();
//        // 从session获取当前登录用户信息
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = new PageData();
//        System.out.println("==============》现在登录手机号：" + memUser.getPHONE());
//        user.put("PHONE", memUser.getPHONE());
//        user = accountService.findByPhone(user);
//        mv.setViewName("front/my/personal-data");
//        mv.addObject("user", user);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【收益明细】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_profit")
//    private ModelAndView toProfit() throws Exception {
//        ModelAndView mv = getModelAndView();
//        mv.setViewName("front/my/profit");
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【收益明细】页面 分页查询
//     *
//     * @author Ajie
//     * @date 2019年12月13日15:54:08
//     */
//    @RequestMapping(value = "to_profitPage")
//    @ResponseBody
//    private Object toProfitPage() throws Exception {
//        Pager<PageData> pager = new Pager<>();
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData pd = this.getPageData();
//        // 页码
//        int num = Integer.parseInt(pd.get("num").toString());
//        // 每页数据条数
//        int pageSize = Integer.parseInt(pd.get("size").toString());
//        // 关键字查询
//        pd.put("PHONE", user.getPHONE());
//        // 得到全部数据
//        List<PageData> recList = income_detailsService.listByPhone(pd);
//        // 第 N 页
//        pager.setCurentPageIndex(num);
//        // 每页 N 条
//        pager.setCountPerpage(pageSize);
//        pager.setBigList(recList);
//        // 得到小的集合(分页的当前页的记录)
//        List<PageData> curPageData = pager.getSmallList();
//        // 得到总页数
//        int totalPage = pager.getPageCount();
//        pd.put("curPageData", curPageData);
//        pd.put("totalPage", totalPage);
//        return pd;
//    }
//
//    /**
//     * 功能描述：访问前台【推广二维码】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_qrcode")
//    private ModelAndView toQrcode() {
//        ModelAndView mv = getModelAndView();
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        mv.setViewName("front/my/qrcode");
//        mv.addObject("user", user);
//        return mv;
//    }
//
//    /**
//     * 功能描述：二维码
//     *
//     * @param ：用户手机号
//     * @return :返回输出流
//     * @author Ajie
//     * @date 2019/10/22 0022
//     */
//    @RequestMapping(value = "/qr_code")
//    public void qrCode(HttpServletResponse response, HttpServletRequest request, @RequestParam("phone") String phone) throws Exception {
//        // 获取请求路径
//        String path = request.getContextPath();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//                + path + "/";
//        String context = basePath + "release/toRegister.do?tag=" + phone;
//        // 通过输出流把二维码输出到页面
//        ServletOutputStream out = response.getOutputStream();
//        // 调用工具类
//        TwoDimensionCode.encoderQRCode(context, out);
//    }
//
//    /**
//     * 功能描述：访问前台【我的团队】页面
//     *
//     * @author Ajie
//     * @date 2019/11/26 0026
//     */
//    @RequestMapping(value = "to_team")
//    private ModelAndView toTeam() throws Exception {
//        ModelAndView mv = getModelAndView();
//        PageData pd = new PageData();
//        // 从缓存获取当前登录用户
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        // 根据用户ID查所有下级
//        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
//        List<PageData> userList = accountService.recommendationMap(pd);
//        // 查直推人数
//        pd = accountService.getReCount(pd);
//        int reCount = Integer.parseInt(pd.get("RE_COUNT").toString());
//        int teamCount = userList.size();
//        mv.setViewName("front/my/team");
//        mv.addObject("userList", userList);
//        mv.addObject("user", user);
//        mv.addObject("reCount", reCount);
//        mv.addObject("teamCount", teamCount);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【申购匹配管理详细页 】页面
//     *
//     * @author Ajie
//     * @date 2019年12月2日16:00:49
//     */
//    @RequestMapping(value = "toBuyDetail")
//    private ModelAndView toBuyDetail() throws Exception {
//        ModelAndView mv = getModelAndView();
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 从session获取当前登录用户信息
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = (PageData) applicati.getAttribute(memUser.getPHONE());
//        PageData pd = new PageData();
//        pd = this.getPageData();
//        // 获取查询申购订单还是赎回订单，0：申购、1：赎回
//        String tag = pd.getString("TAG");
//        // 查询匹配记录
//        List<PageData> match = smd_matchService.findByOrderId(pd);
//        if ("0".equals(tag)) {
//            pd = purchase_smdService.findByOrderId(pd);
//            mv.addObject("buy", pd);
//        }
//        mv.setViewName("front/index-ui/buy-detail");
//        mv.addObject("match", match);
//        mv.addObject("user", user);
//        mv.addObject("par", par);
//        return mv;
//    }
//
//    /**
//     * 功能描述：访问前台【赎回匹配管理详细页 】页面
//     *
//     * @author Ajie
//     * @date 2019年12月2日16:00:49
//     */
//    @RequestMapping(value = "toSellDetail")
//    private ModelAndView toSellDetail() throws Exception {
//        ModelAndView mv = getModelAndView();
//        // 系统参数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 从session获取当前登录用户信息
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = (PageData) applicati.getAttribute(memUser.getPHONE());
//        PageData pd;
//        pd = this.getPageData();
//        // 获取查询申购订单还是赎回订单，0：申购、1：赎回
//        String tag = pd.getString("TAG");
//        List<PageData> match = smd_matchService.findByOrderId(pd);
//        if ("1".equals(tag)) {
//            pd = sell_smdService.findByOrderId(pd);
//            mv.addObject("sell", pd);
//        }
//        mv.setViewName("front/index-ui/sell-detail");
//        mv.addObject("match", match);
//        mv.addObject("user", user);
//        mv.addObject("par", par);
//        return mv;
//    }
//
//    /**
//     * 功能描述：清除缓存
//     *
//     * @author Ajie
//     * @date 2019/11/27 0027
//     */
//    public void removeCache() {
//        Session session = Jurisdiction.getSession();
//        // 以下清除session缓存
//        session.removeAttribute(Const.SESSION_MEMUSER);
//    }
//
//    /**
//     * 功能描述：退出登录，清理缓存和登录序列
//     *
//     * @author Ajie
//     * @date 2019/11/27 0027
//     */
//    @RequestMapping(value = "/outLogin")
//    @ResponseBody
//    public String outLogin() throws Exception {
//        removeCache();
//        return "success";
//    }
//
//    /**
//     * 功能描述：请求修改密码，验证参数是否正确
//     *
//     * @param record 密码信息
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    @RequestMapping(value = "/changePassword")
//    @ResponseBody
//    public String changePassword(@RequestParam(name = "record") String[] record) throws Exception {
//        String info = "";
//        Session session = Jurisdiction.getSession();
//        // 获取短信验证码
//        String sessionCode = (String) session.getAttribute(Const.SESSION_PHONE_CHECK_CODE);
//        // 验证码失效
//        if (Tools.isEmpty(sessionCode)) {
//            return "4";
//        }
//        // 参数是否够
//        if (record.length != 4) {
//            return "1";
//        }
//        // 密码是否一致
//        if (!record[0].equals(record[1])) {
//            return "2";
//        }
//        // 验证码是否正确
//        if (!sessionCode.equals(record[2])) {
//            return "3";
//        }
//        // 1：表示修改登录密码
//        if ("1".equals(record[3])) {
//            confirmChange(record, 1);
//            info = "login";
//        }
//        // 2：表示修改交易密码
//        if ("2".equals(record[3])) {
//            confirmChange(record, 2);
//            info = "success";
//        }
//        return info;
//    }
//
//    /**
//     * 功能描述：发送短信验证码，修改密码用
//     *
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    @RequestMapping(value = "/sendPhoneSms", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String phoneSms() {
//        Session session = Jurisdiction.getSession();
//        // 从缓存获取当前登录用户信息
//        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
//        // 设置验证码
////        String yzm = RandomCodeUtil.getInvitaCode(6, 1);
//        String yzm = "666";
//        // 存session中
//        session.setAttribute(Const.SESSION_PHONE_CHECK_CODE, yzm);
//        // 发短信
//        String content = "您本次修改密码的验证码为：【" + yzm + "】。(该验证码300秒内有效)";
//        String info = "";
//        String phone = "13977128507";
//        /*try {
//            info = SendSMS.sendSMS(phone.trim(), content);
//        } catch (Exception e) {
//            return info;
//        }*/
//        //TimerTask实现N分钟后从session中删除验证码
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Jurisdiction.getSession().removeAttribute(Const.SESSION_PHONE_CHECK_CODE);
//                System.out.println("短信验证码 5分钟已过==========》【删除成功】");
//                timer.cancel();
//            }
//        }, 5 * 60 * 1000);
//        return "success";
//    }
//
//    /**
//     * 功能描述：密码确认修改
//     *
//     * @param record 经过验证后的参数
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    private void confirmChange(String[] record, int tag) throws Exception {
//        Session session = Jurisdiction.getSession();
//        PageData pd = new PageData();
//        // 从session获取当前登录用户信息
//        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
//        // 通过手机号从上下文应用缓存获取用户信息赋值到pd
//        pd = (PageData) applicati.getAttribute(user.getPHONE());
//        // 执行修改
//        String newPass = StringUtil.applySha256(record[0]);
//        if (tag == 1) {
//            pd.put("LOGIN_PASSWORD", newPass);
//            user.setLOGIN_PASSWORD(newPass);
//        }
//        if (tag == 2) {
//            pd.put("TRANSACTION_PASSWORD", newPass);
//            user.setTRANSACTION_PASSWORD(newPass);
//        }
//        accountService.editPassword(pd);
//        // 更新缓存
//        session.setAttribute(Const.SESSION_MEMUSER, user);
//        applicati.setAttribute(user.getPHONE(), pd);
//    }
//
//    /**
//     * 功能描述：请求申购SMD,校验参数是否符合
//     *
//     * @param record 购买信息
//     * @return 处理结果
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    @RequestMapping(value = "/buySmd", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    protected String buySmd(@RequestParam(name = "record") String[] record) throws Exception {
//        // 获取数额、交易密码、周期、打款类型
//        int amount = Integer.parseInt(record[0]);
//        String password = StringUtil.applySha256(record[1]);
//        int cycle = Integer.parseInt(record[2]);
//        int payMode = Integer.parseInt(record[3]);
//        PageData pd = new PageData();
//        // 从session获取当前登录用户信息
//        MemUser user = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        pd.put("PHONE", user.getPHONE());
//        user = accountService.findByPhone1(pd);
//        // 开始校验参数
//        if (record.length != 4) {
//            return "参数不足";
//        }
//        if (user.getREST_TIME() > 0) {
//            return "禁止匹配天数剩余：" + user.getREST_TIME() + "天";
//        }
//        String result = isMyInfo();
//        if (!"success".equals(result)) {
//            return result;
//        }
//        result = isAmount(amount);
//        if (!"success".equals(result)) {
//            return result;
//        }
//        if (!user.getTRANSACTION_PASSWORD().equals(password)) {
//            return "交易密码错误";
//        }
//        if (cycle < 0 || payMode <= -1) {
//            return "非法参数";
//        }
//        if (user.getTICKET() <= 0) {
//            return "入场券不足";
//        }
//        // 时间间隔校验
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        pd.put("PHONE", user.getPHONE());
//        pd = purchase_smdService.getNewOrderByPhone(pd);
//        if (pd == null) {
//            // 创建购买记录
//            addBuyRecord(user, amount, cycle, payMode);
//        } else {
//            String oidTime = pd.getString("GMT_CREATE");
//            int hourCount = DateUtil.getHour(oidTime);
//            int intervalTime = Integer.parseInt(par.getString("INTERVAL_TIME"));
//            if (intervalTime > hourCount) {
//                hourCount = intervalTime - hourCount;
//                return "请在" + hourCount + "小时后在排单";
//            }
//            // 创建购买记录
//            addBuyRecord(user, amount, cycle, payMode);
//        }
//        return "success";
//    }
//
//    /**
//     * 功能描述：打款金额校验
//     *
//     * @param num 数额
//     * @return 处理信息
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    private String isAmount(int num) {
//        // 从缓存获取系统参数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        int min = Integer.parseInt(par.getString("PAY_MIN"));
//        int max = Integer.parseInt(par.getString("PAY_MAX"));
//        int multiple = Integer.parseInt(par.getString("PAY_MULTIPLE"));
//        if (num < min) {
//            return "最小申请" + min + "的数额";
//        }
//        if (num > max) {
//            return "最大申请" + max + "的数额";
//        }
//        if (num % multiple != 0) {
//            return "只能申请" + multiple + "的倍数";
//        }
//        return "success";
//    }
//
//    /**
//     * 功能描述：创建购买记录
//     *
//     * @param user    用户资料
//     * @param amount  数额
//     * @param cycle   周期(天数)
//     * @param payMode 支付类型 1:人民币 0:ETH
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    private void addBuyRecord(MemUser user, int amount, int cycle, int payMode) throws Exception {
//        PageData pd = new PageData();
//        // 生成订单号
//        String orderNumber = getOrderNumber(user.getACCOUNT_ID(), 0);
//        // 从缓存获取系统参数信息
//        PageData par = new PageData();
//        par = (PageData) applicati.getAttribute(Const.PAR);
//        // 计算预付款和尾款比例
//        double downPaymentsScale = Double.parseDouble(par.getString("ADVANCE_CHARGE")) / 100;
//        double tailMoneyScale = Double.parseDouble(par.getString("TAIL_MONEY")) / 100;
//        // 计算首尾款数额
//        double downAmount = amount * downPaymentsScale;
//        double tailAmount = amount * tailMoneyScale;
//        // 执行写入数据库操作
//        String time = DateUtil.getTime();
//        pd.put("GMT_CREATE", time);
//        pd.put("GMT_MODIFIED", "");
//        pd.put("NUMBER", downAmount);
//        pd.put("PHONE", user.getPHONE());
//        pd.put("MODE_PAYMENT", payMode);
//        pd.put("INTEREST_CYCLE", cycle);
//        // 1：预付款、2：尾款
//        pd.put("ORDER_TYPE", 1);
//        pd.put("ORDER_NUMBER", orderNumber);
//        pd.put("PAYMENT_TIME", "");
//        // 状态：0：预付款已付、1：尾款已付、2:撤单、3：待付款、4：待收款、5：等待匹配
//        pd.put("PAYMENT_STATE", 5);
//        // 释放时间
//        pd.put("RELEASE_TIME", "");
//        pd.put("BALANCE", downAmount);
//        pd.put("PURCHASE_SMD_ID", "");
//        // 创建预付款记录
//        purchase_smdService.save(pd);
//        pd.put("ORDER_TYPE", 2);
//        pd.put("NUMBER", tailAmount);
//        pd.put("BALANCE", tailAmount);
//        // 创建尾款记录
//        purchase_smdService.save(pd);
//        // 扣除一张入场券
//        int cost = 1;
//        pd.put("amount", cost);
//        pd.put("MY_PHONE", user.getPHONE());
//        accountService.reduceTicket(pd);
//        // 更新缓存
//        pd = accountService.findByPhone(pd);
//        applicati.setAttribute(user.getPHONE(), pd);
//        // 创建扣除入场券记录
//        addIncomeDetails("入场券", user.getPHONE(), cost, "申购SMD", "-");
//    }
//
//    /**
//     * 功能描述：生成订单号
//     *
//     * @param userId 用户id
//     * @param tag    0:申请订单号、1：赎回订单号
//     * @return 新的订单号
//     * @author Ajie
//     * @date 2019/11/28 0028
//     */
//    private String getOrderNumber(String userId, int tag) throws Exception {
//        // 创建字符串缓冲区
//        StringBuffer sb = new StringBuffer();
//        PageData pd = new PageData();
//        if (tag == 0) {
//            pd = purchase_smdService.getMaxOrderId();
//        }
//        if (tag == 1) {
//            pd = sell_smdService.getMaxOrderId();
//        }
//        int maxId = 1;
//        if (pd != null) {
//            maxId = Integer.parseInt(pd.get("MAX_ID").toString());
//        }
//        // 时间戳
//        sb.append(System.currentTimeMillis() / 1000);
//        // 最大订单ID
//        sb.append(maxId);
//        // 用户id
//        sb.append(userId);
//        return sb.toString();
//    }
//
//    /**
//     * 功能描述：请求赎回Smd,参数校验
//     *
//     * @param record 0：钱包类型、1：数额、2：交易密码、3:收款类型
//     * @return 处理结果
//     * @author Ajie
//     * @date 2019/11/29 0029
//     */
//    @RequestMapping(value = "/redeemSMD", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String redeemSmd(@RequestParam(name = "record") String[] record) throws Exception {
//        // 获取数额、二次加密的交易密码、
//        double amount = Double.parseDouble(record[1]);
//        String password = StringUtil.applySha256(record[2]);
//        // 通过系统参数配置，获取提现倍数、动、静钱包提现可提现额度、每天可提现次数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        double multiple = Double.parseDouble(par.getString("CASH_MULTIPLIER"));
//        double dynamicCash = Double.parseDouble(par.getString("DYNAMIC_WALLET"));
//        double staticCash = Double.parseDouble(par.getString("STATIC_WALLET"));
//        int withdrawDay = Integer.parseInt(par.getString("WITHDRAW_TODAY"));
//        // 从session获取当前登录用户信息，然后在从上下文缓存获取用户信息，防止后台直接修改账号钱包
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        if (memUser.getREST_TIME() > 0) {
//            return "禁止匹配天数剩余：" + memUser.getREST_TIME() + "天";
//        }
//        PageData user = (PageData) applicati.getAttribute(memUser.getPHONE());
//        // 获取用户动、静态钱包余额、今日提现次数
//        double dynamicNumber = Double.parseDouble(user.get("DYNAMIC_WALLET").toString());
//        double staticNumber = Double.parseDouble(user.get("STATIC_WALLET").toString());
//        int withdrawCount = Integer.parseInt(user.get("IS_WITHDRAW").toString());
//        // 获取用户状态
//        int userState = Integer.parseInt(user.get("USER_STATE").toString());
//        // 开始校验
//        if (record.length != 4) {
//            return "参数不足";
//        }
//        if (userState == 0) {
//            return "资金冻结中无法操作";
//        }
//        String result = isMyInfo();
//        if (!"success".equals(result)) {
//            return result;
//        }
//        if (withdrawCount < withdrawDay) {
//            if (amount < 0) {
//                return "数额为负数";
//            }
//            if (amount % multiple != 0) {
//                return "只能申请[ " + multiple + " ]的倍数";
//            }
//            if ("1".equals(record[0])) {
//                if (amount > dynamicCash && amount < dynamicNumber) {
//                    return "动态钱包最大可申请[ " + dynamicCash + " ]的数额";
//                }
//                if (amount > 0 && dynamicNumber < amount) {
//                    return "动态钱包余额不足";
//                }
//            }
//            if ("0".equals(record[0])) {
//                if (amount > staticCash && amount < staticNumber) {
//                    return "静态钱包最大可申请[ " + staticCash + " ]的数额";
//                }
//                if (amount > 0 && staticNumber < amount) {
//                    return "静态钱包余额不足";
//                }
//            }
//            if (!password.equals(user.getString("TRANSACTION_PASSWORD"))) {
//                return "交易密码错误";
//            }
//        } else {
//            return "今日提现次数上限";
//        }
//        // 创建赎回SMD记录
//        addRedeemRecord(record[0], amount, record[3], memUser);
//        return "success";
//    }
//
//    /**
//     * 功能描述：参数校验通过，创建赎回SMD记录
//     *
//     * @param type        0：静态钱包、1：动态钱包
//     * @param amount      提现金额
//     * @param user        用户资料
//     * @param receiveType 收款方式：1:人民币、0：ETH
//     * @author Ajie
//     * @date 2019/11/29 0029
//     */
//    private void addRedeemRecord(String type, double amount, String receiveType, MemUser user) throws Exception {
//        PageData pd = new PageData();
//        // 扣除用户钱包余额
//        pd.put("type", type);
//        pd.put("money", amount);
//        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
//        accountService.reduceMoney(pd);
//        // 更新缓存数据
//        pd = accountService.findById(pd);
//        applicati.setAttribute(pd.getString("PHONE"), pd);
//        // 生成订单号
//        String orderNumber = getOrderNumber(user.getACCOUNT_ID(), 0);
//        // 执行写入数据库操作
//        pd.put("GMT_CREATE", DateUtil.getTime());
//        pd.put("GMT_MODIFIED", "");
//        pd.put("NUMBER", amount);
//        pd.put("PHONE", user.getPHONE());
//        pd.put("RECEIVE_TYPE", receiveType);
//        // 状态: 0:未匹配、1:已匹配
//        pd.put("RECEIVE_STATE", 0);
//        pd.put("ORDER_NUMBER", orderNumber);
//        pd.put("BALANCE", amount);
//        pd.put("PURSE_TYPE", type);
//        pd.put("SELL_SMD_ID", "");
//        // 创建申购SMD记录
//        sell_smdService.save(pd);
//        pd.put("BONUS_TYPE", "提现");
//        pd.put("MONEY", amount);
//        // 来源
//        pd.put("SOURCE", "赎回SMD");
//        pd.put("TAG","-");
//        pd.put("RELEASE_TIME", "");
//        pd.put("INCOME_DETAILS_ID", "");
//        // 创建赎回（提现记录）
//        income_detailsService.save(pd);
//        // 增加提现次数
//        accountService.addCashCount(pd);
//        // 更新缓存信息
//        pd = (PageData) applicati.getAttribute(user.getPHONE());
//        int cashCount = Integer.parseInt(pd.get("IS_WITHDRAW").toString());
//        cashCount++;
//        pd.put("IS_WITHDRAW", cashCount);
//        applicati.setAttribute(user.getPHONE(), pd);
//    }
//
//    /**
//     * 功能描述：请求修改个人资料信息
//     *
//     * @param record 个人资料信息：0：姓名、1：支付宝、2：银行名称、3：银行卡号、4：开户行地址、5：ETH钱包地址
//     * @return 处理结果
//     * @author Ajie
//     * @date 2019/11/30 0030
//     */
//    @RequestMapping(value = "/myInfo", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String personalDataModification(@RequestParam(name = "record") String[] record) throws Exception {
//        PageData pd = new PageData();
//        // 参数长度校验
//        if (record.length < 6) {
//            return "参数不足";
//        }
//        pd.put("NAME", record[0]);
//        pd.put("ALIPAY", record[1]);
//        pd.put("BANK_NAME", record[2]);
//        pd.put("BANK_NUMBER", record[3]);
//        pd.put("BANK_ADDRESS", record[4]);
//        pd.put("ETH_ADDRESS", record[5]);
//        PageData finalPd = pd;
//        pd = accountService.findByMyInfo(pd);
//        if (null != pd) {
//            return "这套资料已被注册";
//        }
//        // 保存新的个人资料信息
//        updateMyInfo(finalPd);
//        return "success";
//    }
//
//    /**
//     * 功能描述：参数校验通过，更新个人资料信息
//     *
//     * @param pd 最新的个人资料信息
//     * @author Ajie
//     * @date 2019/11/30 0030
//     */
//    private void updateMyInfo(PageData pd) throws Exception {
//        // 从session 获取当前登录用户信息
//        Session session = Jurisdiction.getSession();
//        MemUser user = (MemUser) session.getAttribute(Const.SESSION_MEMUSER);
//        pd.put("ACCOUNT_ID", user.getACCOUNT_ID());
//        // 执行修改
//        accountService.editMyInfo(pd);
//        // 更新缓数据
//        pd = accountService.findById(pd);
//        applicati.setAttribute(user.getPHONE(), pd);
//    }
//
//    /**
//     * 功能描述：判断是否完善个人资料
//     *
//     * @return 完善 返回【success】,否则返回原因
//     * @author Ajie
//     * @date 2019/11/30 0030
//     */
//    private String isMyInfo() throws Exception {
//        // 获取当前登录用户信息
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = (PageData) applicati.getAttribute(memUser.getPHONE());
//        if (Tools.isEmpty(user.getString("NAME"))) {
//            return "个人资料姓名未填写";
//        }
//        if (Tools.isEmpty(user.getString("ALIPAY"))) {
//            return "个人资料支付宝未填写";
//        }
//        if (Tools.isEmpty(user.getString("BANK_NAME"))) {
//            return "个人资料银行名称未填写";
//        }
//        if (Tools.isEmpty(user.getString("BANK_NUMBER"))) {
//            return "个人资料银行卡号未填写";
//        }
//        if (Tools.isEmpty(user.getString("BANK_ADDRESS"))) {
//            return "个人资料开户行地址未填写";
//        }
//        if (Tools.isEmpty(user.getString("ETH_ADDRESS"))) {
//            return "个人资料ETH钱包地址未填写";
//        }
//        return "success";
//    }
//
//    /**
//     * 功能描述：申购订单请求打款
//     *
//     * @param record 信息：0：匹配订单ID、1：申购ID、2：赎回订单号、3：图片路径
//     * @return 处理结果
//     * @author Ajie
//     * @date 2019/11/30 0030
//     */
//    @RequestMapping(value = "/buyPayment", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String buyPayment(@RequestParam(name = "record") String[] record) throws Exception {
//        PageData pd = new PageData();
//        // 参数长度校验
//        if (record.length < 4) {
//            return "参数不足";
//        }
//        if (Tools.isEmpty(record[2])) {
//            return "请上传支付凭证";
//        }
//        // 调用多线程 更新订单状态
//        ThreadManagers.getThreadPollProxy().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    updateOrderState1(record);
//                } catch (Exception e) {
//                    System.out.println("==============》多线程：更新申购订单和匹配订单状态失败");
//                    e.printStackTrace();
//                }
//            }
//        });
//        return "success";
//    }
//
//    /**
//     * 功能描述：更新订单和匹配订单状态【待收款】
//     *
//     * @param record 信息：0：匹配订单ID、1：申购ID、2:赎回订单号、3：图片路径
//     * @author Ajie
//     * @date 2019/12/3 0003
//     */
//    private void updateOrderState1(String[] record) throws Exception {
//        // 更新申购订单状态 等待收款
//        PageData pd = new PageData();
//        pd.put("GMT_MODIFIED", DateUtil.getTime());
//        pd.put("PAYMENT_TIME", DateUtil.getTime());
//        pd.put("PAYMENT_STATE", 4);
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        purchase_smdService.edit(pd);
//        // 更新赎回订单状态 等待收款
//        pd = new PageData();
//        pd.put("GMT_MODIFIED", DateUtil.getTime());
//        pd.put("RECEIVE_STATE", 2);
//        pd.put("SELL_SMD_ID", record[2]);
//        sell_smdService.edit(pd);
//        // 更改匹配订单状态并添加图片路径 等待收款
//        pd = new PageData();
//        pd.put("GMT_MODIFIED", DateUtil.getTime());
//        pd.put("ORDER_STATE", 2);
//        pd.put("PIC_PATH", record[3]);
//        pd.put("SMD_MATCH_ID", record[0]);
//        smd_matchService.edit(pd);
//        // 移除限制打款时间定时任务
//        String name = Const.USER_FROZEN_TASK + record[0];
//        QuartzManager.removeJob(name);
//    }
//
//    /**
//     * 功能描述：赎回订单请求确认收款
//     *
//     * @param record 信息：0：匹配订单ID、1：申购ID、2：赎回订单号
//     * @return 处理结果
//     * @author Ajie
//     * @date 2019/11/30 0030
//     */
//    @RequestMapping(value = "/sellConfirmReceipt", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String sellConfirmReceipt(@RequestParam(name = "record") String[] record) throws Exception {
//        PageData pd = new PageData();
//        // 参数长度校验
//        if (record.length < 3) {
//            return "参数不足";
//        }
//
//        updateOrderState2(record);
//        return "success";
//    }
//
//    /**
//     * 功能描述：更新订单和匹配订单状态【确认收款】
//     *
//     * @param record 信息：0：匹配订单ID、1：申购ID、2:赎回订单号
//     * @author Ajie
//     * @date 2019/12/3 0003
//     */
//    private void updateOrderState2(String[] record) throws Exception {
//        PageData pd = new PageData();
//        // 更改匹配订单状态 确认收款，交易完成
//        pd.put("GMT_MODIFIED", DateUtil.getTime());
//        pd.put("ORDER_STATE", 4);
//        pd.put("SMD_MATCH_ID", record[0]);
//        smd_matchService.edit(pd);
//        // 更新申购订单状态
//        pd = new PageData();
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        pd = purchase_smdService.findById(pd);
//        double balance = Double.parseDouble(pd.get("BALANCE").toString());
//        String orderNumber = pd.getString("ORDER_NUMBER");
//        // 如果申购订单余额为0 更新状态为已完成并添加释放时间
//        if (balance <= 0) {
//            // 获取释放时间
//            String cycle = pd.get("INTEREST_CYCLE").toString();
//            String releaseTime = DateUtil.getAfterDayDate(cycle);
//            pd = new PageData();
//            pd.put("PAYMENT_STATE", 6);
//            pd.put("RELEASE_TIME", releaseTime);
//            // 创建静态奖冻结记录
//            addFreezingRecored(record, releaseTime);
//        } else {
//            pd = new PageData();
//            // 更新状态为 待匹配
//            pd.put("PAYMENT_STATE", 5);
//        }
//        pd.put("GMT_MODIFIED", DateUtil.getTime());
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        purchase_smdService.edit(pd);
//        // 如果是尾款交易完成，结算动态奖金
//        dynamicAwardCheck(orderNumber);
//        // 发放算力
//        addMyPower(orderNumber);
//        addLeaderPower(orderNumber);
//        // 更新赎回订单状态 如果余额为0 交易完成 否则 已收款
//        pd.put("SELL_SMD_ID", record[2]);
//        pd = sell_smdService.findById(pd);
//        balance = Double.parseDouble(pd.get("BALANCE").toString());
//        pd = new PageData();
//        pd.put("GMT_MODIFIED", DateUtil.getTime());
//        if (0 < balance) {
//            pd.put("RECEIVE_STATE", 3);
//        } else {
//            pd.put("RECEIVE_STATE", 4);
//        }
//        pd.put("SELL_SMD_ID", record[2]);
//        sell_smdService.edit(pd);
//    }
//
//    /**
//     * 功能描述： 给直推上级增加算力
//     *
//     * @param orderNumber 订单号
//     * @author Ajie
//     * @date 2019/12/5 0005
//     */
//    private void addLeaderPower(String orderNumber) throws Exception {
//        PageData pd = new PageData();
//        // 根据订单号 查询预付款和尾款记录
//        pd.put("ORDER_NUMBER", orderNumber);
//        List<PageData> buyList = purchase_smdService.listByOrderNumber(pd);
//        int i = 0;
//        // 用户手机号
//        String phone = "";
//        for (PageData map : buyList) {
//            // 如果状态是交易完成
//            if ("6".equals(map.getString("PAYMENT_STATE"))) {
//                phone = map.getString("PHONE");
//                i++;
//            }
//        }
//        // 如果只有一单完成 不发放算力
//        if (i < 2) {
//            return;
//        }
//        // 查交易了多少条订单
//        pd.put("PHONE", phone);
//        pd = purchase_smdService.getCountByPhoneAndFulfil(pd);
//        int count = Integer.parseInt(pd.get("COUNT_ID").toString());
//        // 大于 2 说明不是首单
//        if (count < 2) {
//            // 获取用户信息
//            pd = (PageData) applicati.getAttribute(phone);
//            // 推荐人手机号
//            String rePhone = pd.getString("RECOMMENDER");
//            // 推荐人为空 直接返回
//            if (Tools.isEmpty(rePhone)) {
//                return;
//            }
//            // 给上级增加算力
//            // 获取系统参数
//            PageData par = (PageData) applicati.getAttribute(Const.PAR);
//            int power = Integer.parseInt(par.getString("FIRST_POWER"));
//            pd = new PageData();
//            pd.put("NUMBER", power);
//            pd.put("PHONE", rePhone);
//            accountService.addPowerNumber(pd);
//            // 更新上级 缓存信息
//            pd = (PageData) applicati.getAttribute(rePhone);
//            int sum = Integer.parseInt(pd.get("COUNT_BALANCE").toString());
//            sum += power;
//            pd.put("COUNT_BALANCE", sum);
//            applicati.setAttribute(rePhone, pd);
//            // 创建收益记录
//            addIncomeDetails("算力", rePhone, power, phone, "+");
//        }
//    }
//
//    /**
//     * 功能描述： 给自己增加算力
//     *
//     * @param orderNumber 订单号
//     * @author Ajie
//     * @date 2019/12/5 0005
//     */
//    private void addMyPower(String orderNumber) throws Exception {
//        PageData pd = new PageData();
//        // 根据订单号 查询预付款和尾款记录
//        pd.put("ORDER_NUMBER", orderNumber);
//        List<PageData> buyList = purchase_smdService.listByOrderNumber(pd);
//        int i = 0;
//        String phone = "";
//        for (PageData map : buyList) {
//            // 如果状态是交易完成
//            if ("6".equals(map.getString("PAYMENT_STATE"))) {
//                phone = map.getString("PHONE");
//                i++;
//            }
//        }
//        // 如果只有一单完成 不发放算力
//        if (i < 2) {
//            return;
//        }
//        // 通过手机号在缓存查询用户信息
//        pd = (PageData) applicati.getAttribute(phone);
//        // 获取系统参数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 定义加多少算力
//        int power = Integer.parseInt(par.getString("MY_POWER"));
//        pd.put("NUMBER", power);
//        accountService.addPowerNumber(pd);
//        // 更新 缓存信息
//        int sum = Integer.parseInt(pd.get("COUNT_BALANCE").toString());
//        sum += power;
//        pd.put("COUNT_BALANCE", sum);
//        applicati.setAttribute(pd.getString("PHONE"), pd);
//        // 创建收益记录
//        addIncomeDetails("算力", phone, power, phone, "+");
//    }
//
//    /**
//     * 功能描述：发放动态奖参数查询校验，
//     *
//     * @param orderNumber 订单号
//     * @author Ajie
//     * @date 2019/12/5 0005
//     */
//    private void dynamicAwardCheck(String orderNumber) throws Exception {
//        PageData pd = new PageData();
//        // 根据订单号 查询预付款和尾款记录
//        pd.put("ORDER_NUMBER", orderNumber);
//        List<PageData> buyList = purchase_smdService.listByOrderNumber(pd);
//
//        double amount = 0;
//        // 排单金额统计
//        int i = 0;
//        for (PageData map : buyList) {
//            amount += Double.parseDouble(map.get("NUMBER").toString());
//            // 判断预付款和尾款是否都已经完成交易
//            if ("6".equals(map.getString("PAYMENT_STATE"))) {
//                i++;
//            }
//        }
//        // 如果只完成了一单，则不发放
//        if (2 > i) {
//            return;
//        }
//        // 取用户手机号
//        String phone = buyList.get(0).getString("PHONE");
//        pd.put("PHONE", phone);
//        // 根据手机号获取到用户信息
//        MemUser user = accountService.findByPhone1(pd);
//        // 查所有上级
//        if (Tools.isEmpty(user.getRE_PATH())) {
//            return;
//        }
//        pd.put("RE_PATH", user.getRE_PATH());
//        List<PageData> userList = accountService.listByRePath(pd);
//        // 如果需要发放奖金的列表 大于 0发放动态奖
//        if (userList.size() > 0) {
//            grantDynamicAward(userList, amount, orderNumber, phone);
//        }
//
//    }
//
//    /**
//     * 功能描述：发放第一代动态奖，剩下的进入冻结列表
//     *
//     * @param userList    需要发放动态的用户列表、
//     * @param money       排单金额
//     * @param orderNumber 所属订单
//     * @param myPhone     产生这笔订单的用户手机号
//     * @author Ajie
//     * @date 2019/12/5 0005
//     */
//    private void grantDynamicAward(List<PageData> userList, double money, String orderNumber, String myPhone) throws Exception {
//        // 获取系统参数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 普通会员最高代数
//        int ordinaryAlgebra = Integer.parseInt(par.getString("ORDINARY_ALGEBRA"));
//        PageData pd = new PageData();
//        // 定义动态奖比例，需要发放的数量
//        double ratio = 0;
//        double amount = 0;
//        // 定义代数
//        int i = 1;
//        for (PageData map : userList) {
//            // 0:普通会员、1：高级经理
//            int userRank = Integer.parseInt(map.get("USER_RANK").toString());
//            // 获取用户状态 1：封号 0:资金冻结
//            int userState = Integer.parseInt(map.get("USER_STATE").toString());
//            // 判断代数及用户等级 超过普通会员最高代 且 用户等级不是高级会员 或者用户被封号的 直接进入下一个
//            if (ordinaryAlgebra < i && 1 != userRank || 1 == userState) {
//                i++;
//                continue;
//            }
//            // 动态烧伤机制判断, 根据手机号统计申购订单尾款完成的数据
//            String phone = map.getString("PHONE");
//            pd.put("PHONE", phone);
//            pd = purchase_smdService.getCountByPhoneAndFulfil(pd);
//            int countId = Integer.parseInt(pd.get("COUNT_ID").toString());
//            // 如果没有尾款交易完成就不发放了,查询下一个用户
//            if (0 >= countId) {
//                i++;
//                continue;
//            }
//            // 获取需要发放动态奖的用户排单总额
//            pd.put("PHONE", phone);
//            pd = purchase_smdService.getSumAmount(pd);
//            double sumAmount = Double.parseDouble(pd.get("SUM_AMOUNT").toString());
//            // 没有排单不发动态奖
//            if (0 >= sumAmount) {
//                i++;
//                continue;
//            }
//            // 获取普通会员动态奖比例 SENIOR_1_REWARD
//            if (0 == userRank && 3 < i) {
//                ratio = Double.parseDouble(par.getString("ORDINARY_" + 3 + "_REWARD"));
//                ratio /= 100;
//            }
//            if (0 == userRank && 3 >= i) {
//                ratio = Double.parseDouble(par.getString("ORDINARY_" + i + "_REWARD"));
//                ratio /= 100;
//            }
//            // 获取高级会员动态奖比例
//            if (1 == userRank && 8 < i) {
//                ratio = Double.parseDouble(par.getString("SENIOR_" + 8 + "_REWARD"));
//                ratio /= 100;
//            }
//            if (1 == userRank && 8 >= i) {
//                ratio = Double.parseDouble(par.getString("SENIOR_" + i + "_REWARD"));
//                ratio /= 100;
//            }
//            pd = new PageData();
//            // 钱包类型 1：动态钱包 0：静态钱包
//            pd.put("type", 1);
//            pd.put("GMT_MODIFIED", DateUtil.getTime());
//            pd.put("PHONE", phone);
//            // 执行第一代奖金发放
//            if (1 == i) {
//                // 动态烧伤机制
//                if (sumAmount >= money) {
//                    amount = money * ratio;
//                    pd.put("money", amount);
//                    accountService.addMoney(pd);
//                } else {
//                    amount = sumAmount * ratio;
//                    pd.put("money", amount);
//                    accountService.addMoney(pd);
//                }
//                // 更新缓存信息
//                pd = (PageData) applicati.getAttribute(phone);
//                double dynamicWallet = Double.parseDouble(pd.get("DYNAMIC_WALLET").toString());
//                dynamicWallet += money;
//                pd.put("DYNAMIC_WALLET", dynamicWallet);
//                applicati.setAttribute(phone, pd);
//                // 创建奖金记录
//                addIncomeDetails("动态奖金", phone, amount, myPhone, "+");
//            } else {
//                // 动态烧伤机制
//                if (sumAmount >= money) {
//                    amount = money * ratio;
//                } else {
//                    amount = sumAmount * ratio;
//                }
//                // 进入冻结列表
//                addFreezingRecored1(i, amount, phone, orderNumber);
//            }
//            i++;
//        }
//    }
//
//    /**
//     * 功能描述：创建收入明细
//     *
//     * @param bounsType 奖金类型
//     * @param phone     用户手机号
//     * @param money     金额
//     * @param source    来源
//     * @param tag       标识 + 或者 -
//     * @author Ajie
//     * @date 2019/12/5 0005
//     */
//    private void addIncomeDetails(String bounsType, String phone, double money, String source, String tag) throws Exception {
//        PageData pd = new PageData();
//        pd.put("GMT_CREATE", DateUtil.getTime());
//        pd.put("GMT_MODIFIED", "");
//        pd.put("BONUS_TYPE", bounsType);
//        pd.put("PHONE", phone);
//        pd.put("MONEY", money);
//        pd.put("SOURCE", source);
//        pd.put("TAG", tag);
//        pd.put("INCOME_DETAILS_ID", "");
//        income_detailsService.save(pd);
//    }
//
//    /**
//     * 功能描述：创建静态奖冻结记录
//     *
//     * @param record      信息：0：匹配订单ID、1：申购ID、2:赎回订单号
//     * @param releaseTime 释放时间
//     * @author Ajie
//     * @date 2019/12/4 0004
//     */
//    private void addFreezingRecored(String[] record, String releaseTime) throws Exception {
//        String time = DateUtil.getTime();
//        PageData pd = new PageData();
//        // 获取系统参数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 预付款每天释放比例
//        double advanceRatio = Double.parseDouble(par.get("ADVANCE_CHARGE_INTEREST").toString());
//        advanceRatio /= 100;
//        // 根据申购订单ID查数据
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        pd = purchase_smdService.findById(pd);
//        // 获取手机号和打款时间及订单创建时间
//        String phone = pd.getString("PHONE");
//        String payTime = pd.getString("PAYMENT_TIME");
//        String createTime = pd.getString("GMT_CREATE");
//        // 判断相差几小时
//        int interval = DateUtil.getHour1(createTime, payTime);
//        // 获取匹配后支付时间及额外增加的利息
//        int payTimeMatching = Integer.parseInt(par.getString("PAY_TIME_MATCHING"));
//        double incentiveInterest = Double.parseDouble(par.getString("INCENTIVE_INTEREST"));
//        incentiveInterest /= 100;
//        // 获取排单金额、利息周期（天数）
//        double money = Double.parseDouble(pd.get("NUMBER").toString());
//        int cycle = Integer.parseInt(pd.get("INTEREST_CYCLE").toString());
//        // 获取尾款释放比例
//        double tailRatio = getTailRatio(cycle, par);
//        pd.put("GMT_CREATE", time);
//        pd.put("GMT_MODIFIED", "");
//        pd.put("ORDER_NUMBER", pd.getString("ORDER_NUMBER"));
//        pd.put("MONEY", money);
//        // 0：本金、1利息
//        pd.put("TYPE", 0);
//        pd.put("RELEASE_TIME", releaseTime);
//        pd.put("STATE", "0");
//        pd.put("FREEZING_DETAILS_ID", "");
//        // 创建本金记录
//        freezing_detailsService.save(pd);
//        // 创建定时释放任务
//        releaseTime = DateUtil.getCron(releaseTime);
//        inspectOrder(phone, 0, releaseTime);
//        // 判断预付款还是尾款 计算利息
//        String type = pd.get("ORDER_TYPE").toString();
//        // 如果是系统规定打款时间内额外奖励利息
//        if (interval <= payTimeMatching) {
//            // 1:预付款 2：尾款
//            if ("1".equals(type)) {
//                money *= (advanceRatio * cycle + incentiveInterest);
//            }
//            if ("2".equals(type)) {
//                money *= (tailRatio + incentiveInterest);
//            }
//        } else {
//            // 1:预付款 2：尾款
//            if ("1".equals(type)) {
//                money *= advanceRatio * cycle;
//            }
//            if ("2".equals(type)) {
//                money *= tailRatio;
//            }
//        }
//
//        pd.put("TYPE", 1);
//        pd.put("MONEY", money);
//        // 创建冻结利息记录
//        freezing_detailsService.save(pd);
//        // 创建定时释放任务
//        inspectOrder(phone, 0, releaseTime);
//    }
//
//    /**
//     * 功能描述：创建动态奖冻结记录
//     *
//     * @param algebra     代数
//     * @param money       金额
//     * @param phone       手机号
//     * @param orderNumber 那条订单产生的
//     * @author Ajie
//     * @date 2019/12/5 0004
//     */
//    private void addFreezingRecored1(int algebra, double money, String phone, String orderNumber) throws Exception {
//        String time = DateUtil.getTime();
//        PageData pd = new PageData();
//        // 获取系统参数
//        PageData par = (PageData) applicati.getAttribute(Const.PAR);
//        // 获取冻结天数
//        String dynamicFreeze = "";
//        if (2 == algebra) {
//            dynamicFreeze = par.getString("DYNAMIC_FREEZE2");
//        }
//        if (3 <= algebra) {
//            dynamicFreeze = par.getString("DYNAMIC_FREEZE3");
//        }
//        // 释放时间
//        String releaseTime = DateUtil.getAfterDayDate(dynamicFreeze);
//        pd.put("GMT_CREATE", time);
//        pd.put("GMT_MODIFIED", "");
//        pd.put("ORDER_NUMBER", orderNumber);
//        pd.put("PHONE", phone);
//        pd.put("MONEY", money);
//        // 0：本金、1利息、2：动态奖
//        pd.put("TYPE", 2);
//        pd.put("RELEASE_TIME", releaseTime);
//        pd.put("STATE", "0");
//        pd.put("FREEZING_DETAILS_ID", "");
//        // 创建动态奖记录
//        freezing_detailsService.save(pd);
//        // 创建定时任务
//        releaseTime = DateUtil.getCron(releaseTime);
//        inspectOrder(phone, 1, releaseTime);
//    }
//
//    /**
//     * 功能描述：获取尾款利息
//     *
//     * @param cycle 周期
//     * @param par   系统参数
//     * @return 利息
//     * @author Ajie
//     * @date 2019/12/4 0004
//     */
//    private double getTailRatio(int cycle, PageData par) {
//        // 获取系统设置的利息周期天数
//        int interestCycle1 = Integer.parseInt(par.get("INTEREST_CYCLE1").toString());
//        int interestCycle2 = Integer.parseInt(par.get("INTEREST_CYCLE2").toString());
//        int interestCycle3 = Integer.parseInt(par.get("INTEREST_CYCLE3").toString());
//        // 判断尾款利息
//        double result = 0;
//        if (cycle == interestCycle1) {
//            result = Double.parseDouble(par.get("INTEREST_7").toString());
//        }
//        if (cycle == interestCycle2) {
//            result = Double.parseDouble(par.get("INTEREST_15").toString());
//        }
//        if (cycle == interestCycle2) {
//            result = Double.parseDouble(par.get("INTEREST_30").toString());
//        }
//        result /= 100;
//        return result;
//    }
//
//    /**
//     * 功能描述：创建冻结明细记录后, 添加定时任务
//     *
//     * @param phone       用户手机号
//     * @param tag         1:动态钱包 0：静态钱包
//     * @param releaseTime 释放时间
//     * @author Ajie
//     * @date 2019/12/4 0004
//     */
//    private void inspectOrder(String phone, int tag, String releaseTime) throws Exception {
//        // 取刚刚创建的冻结明细ID
//        PageData rec = new PageData();
//        rec.put("PHONE", phone);
//        rec = freezing_detailsService.findByPhone(rec);
//        String orderId = rec.get("FREEZING_DETAILS_ID").toString();
//        // 设置参数
//        Map<String, Object> parameter = new HashMap<String, Object>();
//        parameter.put("orderId", orderId);
//        // 添加定时任务
//        if (0 == tag) {
//            String name = Const.STATIC_REWARD_TASK;
//            name += orderId;
//            System.out.println("创建静态奖定时任务为：" + name);
//            System.out.println("id：" + orderId);
//            QuartzManager.addJob(name, StaticBonusRelease.class, releaseTime, parameter);
//        }
//        if (1 == tag) {
//            String name = Const.DYNAMIC_REWARD_TASK;
//            name += orderId;
//            System.out.println("创建动态奖定时任务为：" + name);
//            System.out.println("id：" + orderId);
//            QuartzManager.addJob(name, DynamicBonusRelease.class, releaseTime, parameter);
//        }
//    }
//
//    /**
//     * 功能描述：添加定时任务测试
//     *
//     * @author Ajie
//     * @date 2019年12月3日19:44:06
//     */
//    @RequestMapping(value = "/addscheduledtask", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String addscheduledtask(HttpServletRequest request) throws Exception {
//        System.out.println("添加定时任务");
////        // 添加一个定位任务 每隔2分钟执行一次
////        String id = "1";
////        String name = "timedTask" + id;
////        String cron = "0 */2 * * * ? *";
////        // 设置参数
////        Map<String, Object> parameter = new HashMap<String, Object>();
////        parameter.put("buyId", id);
////        QuartzManager.addJob(name, TaskTest.class, cron, parameter);
//        QuartzManager.taskMethod();
//
//        return "success";
//    }
//
//    /**
//     * 功能描述：移除定时任务测试
//     *
//     * @author Ajie
//     * @date 2019年12月3日19:44:06
//     */
//    @RequestMapping(value = "/removescheduledtasks", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String removescheduledtasks() throws Exception {
//        System.out.println("移除定时任务");
//        // 移除定时任务
//        String name = "staticRewardTask9";
//        QuartzManager.removeJob(name);
//
//        return "success";
//    }
//
//    /**
//     * 功能描述：用户投诉【虚假凭证】
//     *
//     * @author Ajie
//     * @date 2019/12/7 0007
//     */
//    @RequestMapping(value = "/complain")
//    @ResponseBody
//    private String falseEvidence() throws Exception {
//        PageData pd = this.getPageData();
//        // 取匹配订单id
//        String orderId = pd.getString("orderId");
//        // 更改订单状态为 等待收款，被投诉中 状态码为：5
//        pd = new PageData();
//        pd.put("ORDER_STATE", 5);
//        pd.put("SMD_MATCH_ID", orderId);
//        smd_matchService.edit(pd);
//        return "success";
//    }
//
//    /**
//     * 功能描述：后台操作【撤销订单】
//     *
//     * @param record 0：匹配订单ID、1：申购ID、2:赎回订单号、3：数额
//     * @author Ajie
//     * @date 2019/12/7 0007
//     */
//    @RequestMapping(value = "/revoke")
//    @ResponseBody
//    private String orderRevoke(@RequestParam(name = "record") String[] record) throws Exception {
//        PageData pd = new PageData();
//        double amount = Double.parseDouble(record[3]);
//        // 更改匹配订单状态为【订单已撤销】 状态码为：6
//        pd.put("ORDER_STATE", 6);
//        pd.put("SMD_MATCH_ID", record[0]);
//        smd_matchService.edit(pd);
//        // 更改申购订单状态为 等待匹配 状态码为：5, 并且退回余额
//        pd = new PageData();
//        pd.put("PAYMENT_STATE", 5);
//        pd.put("money", amount);
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        purchase_smdService.editStateAndMoney(pd);
//        // 更改赎回订单状态为 等待匹配 状态码为：0, 并且退回余额
//        pd = new PageData();
//        pd.put("RECEIVE_STATE", 0);
//        pd.put("money", amount);
//        pd.put("SELL_SMD_ID", record[2]);
//        sell_smdService.editStateAndMoney(pd);
//        return "success";
//    }
//
//    /**
//     * 功能描述：后台操作【回滚订单】
//     *
//     * @param record 0：匹配订单ID、1：申购ID、2:赎回订单号、3：数额
//     * @author Ajie
//     * @date 2019/12/7 0007
//     */
//    @RequestMapping(value = "/rollBackOrder")
//    @ResponseBody
//    private String rollBackOrder(@RequestParam(name = "record") String[] record) throws Exception {
//        PageData pd = new PageData();
//        double amount = Double.parseDouble(record[3]);
//        // 更改匹配订单状态为【订单已回滚】 状态码为：7
//        pd.put("ORDER_STATE", 7);
//        pd.put("SMD_MATCH_ID", record[0]);
//        smd_matchService.edit(pd);
//        // 更改申购订单状态为 等待匹配 状态码为：5, 并且退回余额
//        pd = new PageData();
//        pd.put("PAYMENT_STATE", 5);
//        pd.put("money", amount);
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        purchase_smdService.editStateAndMoney(pd);
//        // 更改赎回订单状态为 等待匹配 状态码为：0, 并且退回余额
//        pd = new PageData();
//        pd.put("RECEIVE_STATE", 0);
//        pd.put("money", amount);
//        pd.put("SELL_SMD_ID", record[2]);
//        sell_smdService.editStateAndMoney(pd);
//        // 解封用户
//        pd = new PageData();
//        pd.put("PURCHASE_SMD_ID", record[1]);
//        pd = purchase_smdService.findById(pd);
//        String phone = pd.getString("PHONE");
//        pd = new PageData();
//        pd.put("USER_STATE", -1);
//        pd.put("PHONE", phone);
//        accountService.edit1(pd);
//        return "success";
//    }
//
//    /**
//     * 功能描述：会员【互转入场券】参数校验
//     *
//     * @param record 0：对方账号、1：数额、2:交易密码
//     * @author Ajie
//     * @date 2019/12/7 0007
//     */
//    @RequestMapping(value = "/transfer", produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    private String transfer(@RequestParam(name = "record") String[] record) throws Exception {
//        if (record.length != 3) {
//            return "参数不足";
//        }
//        PageData pd = new PageData();
//        MemUser memUser = (MemUser) Jurisdiction.getSession().getAttribute(Const.SESSION_MEMUSER);
//        PageData user = (PageData) applicati.getAttribute(memUser.getPHONE());
//        int amount = Integer.parseInt(record[1]);
//        // 不能给自己转账
//        if (record[0].equals(memUser.getPHONE())) {
//            return "不能给自己转账";
//        }
//        pd = (PageData) applicati.getAttribute(record[0]);
//        if (pd == null) {
//            return "对方账号未注册";
//        }
//        // 验证交易密码
//
//        if (!memUser.getTRANSACTION_PASSWORD().equals(StringUtil.applySha256(record[2]))) {
//            return "交易密码错误";
//        }
//        // 验证用户状态 1:账号冻结、0：资金冻结 -1：正常
//        int userState = Integer.parseInt(user.get("USER_STATE").toString());
//        if (userState == 0) {
//            return "资金冻结中无法操作";
//        }
//        // 查询登录用户的入场券
//        pd.put("PHONE", memUser.getPHONE());
//        memUser = accountService.findByPhone1(pd);
//        if (amount > memUser.getTICKET()) {
//            return "入场券不足";
//        }
//        String myPhone = memUser.getPHONE();
//        // 执行入场券增加
//        String result = addTransfer(record[0], amount, myPhone);
//        if (Tools.notEmpty(result)) {
//            return result;
//        }
//        return "success";
//    }
//
//    /**
//     * 功能描述：扣除自身入场券，给对方增加入场券
//     *
//     * @param phone   对方手机号
//     * @param amount  入场券数额
//     * @param myPhone 我的手机号
//     * @author Ajie
//     * @date 2019/12/9 0009
//     */
//    private String addTransfer(String phone, int amount, String myPhone) {
//        String result = "";
//        try {
//            PageData pd = new PageData();
//            // 执行转账入场券
//            pd.put("amount", amount);
//            pd.put("MY_PHONE", myPhone);
//            pd.put("PHONE", phone);
//            accountService.updateTicket(pd);
//            // 更新缓存
//            pd = accountService.findByPhone(pd);
//            applicati.setAttribute(phone, pd);
//            pd.put("PHONE", myPhone);
//            pd = accountService.findByPhone(pd);
//            applicati.setAttribute(myPhone, pd);
//            // 创建收益记录
//            addIncomeDetails("入场券", myPhone, amount, phone, "-");
//            addIncomeDetails("入场券", phone, amount, myPhone, "+");
//        } catch (Exception e) {
//            result = "转账入场券异常，已回滚";
//            // 异常时执行回滚
//            System.out.println("转账入场券异常，已回滚");
//            System.out.println(e.getMessage());
//        }
//        return result;
//    }

}
