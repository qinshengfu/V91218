package com.fh.controller.front;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.front.AccountManager;
import com.fh.service.record.Online_Wallet_RecManager;
import com.fh.util.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：前台用户表
 * 创建人：
 * 创建时间：2019-12-20
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    String menuUrl = "account/list.do"; //菜单地址(权限用)
    @Resource(name = "accountService")
    private AccountManager accountService;
    // 线上钱包记录
    @Resource(name = "online_wallet_recService")
    private Online_Wallet_RecManager online_wallet_recService;

    /**
     * 修改
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "修改Account");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        if (Tools.notEmpty(pd.getString("LOGIN_PASSWORD"))) {
            pd.put("LOGIN_PASSWORD", StringUtil.applySha256(pd.getString("LOGIN_PASSWORD")));
        } else {
            pd.remove("LOGIN_PASSWORD");
        }
        if (Tools.notEmpty(pd.getString("SECURITY_PASSWORD"))) {
            pd.put("SECURITY_PASSWORD", StringUtil.applySha256(pd.getString("SECURITY_PASSWORD")));
        } else {
            pd.remove("SECURITY_PASSWORD");
        }
        accountService.edit(pd);
        // 更新缓存
        pd = accountService.findById(pd);
        applicati.setAttribute(pd.getString("USER_NAME"), pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表Account");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords");                //关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List<PageData> varList = accountService.list(page);    //列出Account列表
        mv.setViewName("front/account/account_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 钱包管理列表
     *
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/usdtList")
    public ModelAndView usdtlist(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "usdt提现列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String keywords = pd.getString("keywords");                //关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        pd.put("name", null);
        page.setPd(pd);
        List<PageData> varList = accountService.list(page);    //列出Account列表
        // 循环查询钱包余额
        for (int i = 0; i < varList.size(); i++) {
            PageData map = varList.get(i);
            HashMap resule = BlockUtil.selectBalance(map.getString("USDT_SITE"));
            double usdt = Double.parseDouble(resule.get("token_numbers").toString());
            map.put("ETH", resule.get("eth_numbers"));
            map.put("USDT", usdt * Math.pow(10, 12));
            varList.set(i, map);
        }
        mv.setViewName("front/account/usdtWalletList");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 去编辑页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit1")
    public ModelAndView goEdit1() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        // 根据ID读取
        pd = accountService.findById(pd);
        mv.setViewName("front/account/account_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去转出输入框页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        Object num = pd.get("num");
        // 根据ID读取
        pd = accountService.findById(pd);
        pd.put("num", num);
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        mv.setViewName("front/account/usdt_edit");
        mv.addObject("msg", "outTurn");
        mv.addObject("pd", pd);
        mv.addObject("par", par);
        return mv;
    }
    /**
     * 去ETH转出输入框页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEthEdit")
    public ModelAndView goEthEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        Object num = pd.get("num");
        // 根据ID读取
        pd = accountService.findById(pd);
        pd.put("num", num);
        // 获取系统参数
        PageData par = (PageData) applicati.getAttribute(Const.PAR);
        mv.setViewName("front/account/usdt_edit");
        mv.addObject("msg", "ethOutTurn");
        mv.addObject("pd", pd);
        mv.addObject("par", par);
        return mv;
    }

    /**
     * 确认转出
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/outTurn")
    @ResponseBody
    public PageData outTurn() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "usdt转出");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        PageData pd = this.getPageData();
        // 获取转出地址、转入地址、数量
        String outAddress = pd.getString("USDT_SITE");
        String inAddress = pd.getString("transfer");
        double num = Double.parseDouble(pd.get("num").toString());
        pd = new PageData();
        // 调用代转币
        HashMap result = BlockUtil.usdtTransfer(outAddress, inAddress, num);
        // 如果成功
        if ("1".equals(result.get("statuses").toString())){
            String orderox = result.get("orderox").toString();
            // 调用查询交易哈希 statuses: 0交易中；1成功；2失败  msg: 返回信息
            Map<String, String> info = BlockUtil.getTradingStatus(orderox);
            if ("1".equals(info.get("statuses"))) {
                addWalletRec("转出USDT", orderox, String.valueOf(num), outAddress, inAddress);
                pd.put("msg", "转出成功");
            } else {
                pd.put("msg", "转出中");
            }
            if ("2".equals(info.get("statuses"))) {
                pd.put("msg", "转出失败");
            }
        } else {
            pd.put("msg", "转出失败");
        }
        return pd;
    }

    /**
     * Eth确认转出
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/ethOutTurn")
    @ResponseBody
    public PageData ethOutTurn() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "ETH转出");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        PageData pd = this.getPageData();
        // 获取转出地址、转入地址、数量
        String outAddress = pd.getString("USDT_SITE");
        String inAddress = pd.getString("transfer");
        double num = Double.parseDouble(pd.get("num").toString());
        // 调用代转币
        HashMap result = BlockUtil.usdtTransferEth(outAddress, inAddress, num);
        pd = new PageData();
        // 如果成功
        if ("1".equals(result.get("statuses").toString())){
            String orderox = result.get("orderox").toString();
            // 调用查询交易哈希 statuses: 0交易中；1成功；2失败  msg: 返回信息
            Map<String, String> info = BlockUtil.getTradingStatus(orderox);
            System.out.println("============》EHT回调 " + info);
            if ("1".equals(info.get("statuses"))) {
                addWalletRec("转出ETH", orderox, String.valueOf(num), outAddress, inAddress);
                pd.put("msg", "转出成功");
            } else {
                pd.put("msg", "转出中");
            }
            if ("2".equals(info.get("statuses"))) {
                pd.put("msg", "转出失败");
            }
            System.out.println("===========》pd信息 " + pd);
        } else {
            pd.put("msg", "转出失败");
        }
        return pd;
    }

    /**
     * 功能描述：增加线上钱包操作记录
     *
     * @param type    类型中文 （转出、转入）
     * @param orderox 交易哈希
     * @param money   数量
     * @param from    转出地址
     * @param to      转入地址
     * @author Ajie
     * @date 2020/2/11 0011
     */
    public void addWalletRec(String type, String orderox, String money, String from, String to) throws Exception {
        PageData pd = new PageData();
        pd.put("ONLINE_WALLET_REC_ID", this.get32UUID());    //主键
        pd.put("GMT_CREATE", Tools.date2Str(new Date()));    //创建时间
        pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));    //更新时间
        pd.put("PD_TYPE", type);    //数据类型（中文）
        pd.put("ORDEROX", orderox);    //交易哈希
        pd.put("MONEY", money);    //金额
        pd.put("FROM_ADDRESS", from);    //转出地址
        pd.put("TO_ADDRESS", to);    //转入地址
        online_wallet_recService.save(pd);
    }

    /**
     * 导出到excel
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出Account到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("创建时间");    //1
        titles.add("更新时间");    //2
        titles.add("用户名");    //3
        titles.add("登录密码");    //4
        titles.add("安全密码");    //5
        titles.add("邮箱");    //6
        titles.add("邀请码");    //7
        titles.add("XMC钱包");    //8
        titles.add("USDT钱包");    //9
        titles.add("USDT动态");    //10
        titles.add("USDT静态");    //11
        titles.add("对冲钱包之USDT");    //12
        titles.add("对冲钱包之XMC");    //13
        titles.add("推荐人数");    //14
        titles.add("推荐人");    //15
        titles.add("推荐路径");    //16
        titles.add("代数");    //17
        titles.add("会员等级");    //18
        titles.add("用户状态");    //19
        titles.add("团队人数");    //20
        titles.add("团队业绩");    //21
        titles.add("姓名");    //22
        titles.add("支付宝账号");    //23
        titles.add("银行名称");    //24
        titles.add("银行卡号");    //25
        titles.add("开户行地址");    //26
        titles.add("USDT钱包地址");    //27
        dataMap.put("titles", titles);
        List<PageData> varOList = accountService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("GMT_CREATE"));        //1
            vpd.put("var2", varOList.get(i).getString("GMT_MODIFIED"));        //2
            vpd.put("var3", varOList.get(i).getString("USER_NAME"));        //3
            vpd.put("var4", varOList.get(i).getString("LOGIN_PASSWORD"));        //4
            vpd.put("var5", varOList.get(i).getString("SECURITY_PASSWORD"));        //5
            vpd.put("var6", varOList.get(i).getString("MAILBOX"));        //6
            vpd.put("var7", varOList.get(i).getString("INVITATION_CODE"));        //7
            vpd.put("var8", varOList.get(i).get("XMC_WALLET").toString());    //8
            vpd.put("var9", varOList.get(i).get("USDT_WALLET").toString());    //9
            vpd.put("var10", varOList.get(i).get("USDT_DYNAMIC").toString());    //10
            vpd.put("var11", varOList.get(i).get("USDT_STATIC").toString());    //11
            vpd.put("var12", varOList.get(i).get("HEDGING_USDT").toString());    //12
            vpd.put("var13", varOList.get(i).get("HEDGING_XMC").toString());    //13
            vpd.put("var14", varOList.get(i).get("RECOMMENDED_NUMBER").toString());    //14
            vpd.put("var15", varOList.get(i).getString("RECOMMENDER"));        //15
            vpd.put("var16", varOList.get(i).getString("RE_PATH"));        //16
            vpd.put("var17", varOList.get(i).get("ALGEBRA").toString());    //17
            vpd.put("var18", varOList.get(i).getString("USER_RANK"));        //18
            vpd.put("var19", varOList.get(i).getString("USER_STATE"));        //19
            vpd.put("var20", varOList.get(i).get("TEAM_AMOUNT").toString());    //20
            vpd.put("var21", varOList.get(i).get("TEAM_ACHIEVE").toString());    //21
            vpd.put("var22", varOList.get(i).getString("NAME"));        //22
            vpd.put("var23", varOList.get(i).getString("ALIPAY"));        //23
            vpd.put("var24", varOList.get(i).getString("BANK_NAME"));        //24
            vpd.put("var25", varOList.get(i).getString("BANK_NUMBER"));        //25
            vpd.put("var26", varOList.get(i).getString("BANK_ADDRESS"));        //26
            vpd.put("var27", varOList.get(i).getString("USDT_SITE"));        //27
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
