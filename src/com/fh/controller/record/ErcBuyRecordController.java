package com.fh.controller.record;

import com.fh.controller.base.BaseController;
import com.fh.entity.MemUser;
import com.fh.entity.Page;
import com.fh.service.front.AccountManager;
import com.fh.service.record.ErcBuyRecordManager;
import com.fh.service.record.USDT_wallet_RECManager;
import com.fh.util.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：法币交易之购买记录
 * 创建人：
 * 创建时间：2019-12-30
 */
@Controller
@RequestMapping(value = "/ercbuyrecord")
public class ErcBuyRecordController extends BaseController {

    String menuUrl = "ercbuyrecord/list.do"; //菜单地址(权限用)
    @Resource(name = "ercbuyrecordService")
    private ErcBuyRecordManager ercbuyrecordService;
    // 前台用户
    @Resource(name = "accountService")
    private AccountManager accountService;
    // USDT钱包记录
    @Resource(name = "usdt_wallet_recService")
    private USDT_wallet_RECManager usdt_wallet_recService;

    /**
     * 保存
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "新增ErcBuyRecord");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ERCBUYRECORD_ID", this.get32UUID());    //主键
        pd.put("GMT_CREATE", Tools.date2Str(new Date()));    //创建时间
        pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));    //更新时间
        pd.put("ORDER_ID", "");    //订单ID
        pd.put("AMOUNT", "");    //数量
        pd.put("USER_ID", "0");    //用户ID
        pd.put("USER_NAME", "");    //用户名
        pd.put("AMOUNT_TYPE", "");    //金额类型
        pd.put("TAG", "");    //标签 + 或者 -
        pd.put("PAYMENT_TYPE", "");    //打款类型
        pd.put("PAYMENT_VOUCHER", "");    //打款凭证
        pd.put("SHOP_NAME", "");    //商铺名称
        ercbuyrecordService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 功能描述：确认收到打款
     *
     * @author Ajie
     * @date 2019/12/31 0031
     */
    @RequestMapping(value = "/confirm", produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String confirm() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "确认收到打款");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return "无权限";
        } //校验权限
        PageData pd = this.getPageData();
        pd = ercbuyrecordService.findById(pd);
        String id = pd.getString("ERCBUYRECORD_ID");
        // 获取用户ID、数量
        String userId = pd.get("USER_ID").toString();
        String amount = pd.get("AMOUNT").toString();
        // 根据用户ID查询信息
        pd.put("ACCOUNT_ID", userId);
        MemUser user = accountService.findByIdReturnEntity(pd);
        // 增加用户USDT钱包余额、和创建收益记录
        addUsdtMoney(userId, amount);
        addUsdtWalletRec(user.getUSER_NAME(), user.getACCOUNT_ID(), "购买", "+", amount);
        // 更改订单状态为 已完成
        pd = new PageData();
        pd.put("STATE", "已完成");
        pd.put("ERCBUYRECORD_ID", id);
        ercbuyrecordService.edit(pd);
        return "success";
    }


    /**
     * 功能描述：给usdt钱包加钱 和 usdt累积钱包加钱
     *
     * @param userId 用户id
     * @param amount 数额
     * @author Ajie
     * @date 2019/12/31 0028
     */
    private void addUsdtMoney(String userId, String amount) throws Exception {
        PageData pd = new PageData();
        pd.put("GMT_MODIFIED", DateUtil.getTime());
        pd.put("ACCOUNT_ID", userId);
        pd.put("tag", "+");
        pd.put("USDT_WALLET", amount);
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
     * @author Ajie
     * @date 2019/12/27 0027
     */
    private void addUsdtWalletRec(String userName, String userId, String type, String tag, String amount) throws Exception {
        String time = DateUtil.getTime();
        PageData pd = new PageData();
        pd.put("GMT_CREATE", time);
        pd.put("GMT_MODIFIED", time);
        pd.put("USER_NAME", userName);
        pd.put("USER_ID", userId);
        // 金额类型
        pd.put("AMOUNT_TYPE", type);
        pd.put("WALLET_SITE", "");
        pd.put("TAG", tag);
        pd.put("MONEY", amount);
        pd.put("STAUS", "已完成");
        // id 自增
        pd.put("USDT_WALLET_REC_ID", "");
        usdt_wallet_recService.save(pd);
    }

    /**
     * 删除
     *
     * @param out
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "删除ErcBuyRecord");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        ercbuyrecordService.delete(pd);
        out.write("success");
        out.close();
    }

    /**
     * 修改
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "修改ErcBuyRecord");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        ercbuyrecordService.edit(pd);
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
        logBefore(logger, Jurisdiction.getUsername() + "列表ErcBuyRecord");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords");                //关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List<PageData> varList = ercbuyrecordService.list(page);    //列出ErcBuyRecord列表
        mv.setViewName("record/ercbuyrecord/ercbuyrecord_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 去新增页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("record/ercbuyrecord/ercbuyrecord_edit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去修改页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = ercbuyrecordService.findById(pd);    //根据ID读取
        mv.setViewName("record/ercbuyrecord/ercbuyrecord_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 批量删除
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "批量删除ErcBuyRecord");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            ercbuyrecordService.deleteAll(ArrayDATA_IDS);
            pd.put("msg", "ok");
        } else {
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 导出到excel
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出ErcBuyRecord到excel");
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
        titles.add("订单ID");    //3
        titles.add("数量");    //4
        titles.add("用户ID");    //5
        titles.add("用户名");    //6
        titles.add("金额类型");    //7
        titles.add("标签 + 或者 -");    //8
        titles.add("状态");    //9
        titles.add("打款类型");    //10
        titles.add("打款凭证");    //11
        titles.add("商铺名称");    //12
        dataMap.put("titles", titles);
        List<PageData> varOList = ercbuyrecordService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("GMT_CREATE"));        //1
            vpd.put("var2", varOList.get(i).getString("GMT_MODIFIED"));        //2
            vpd.put("var3", varOList.get(i).getString("ORDER_ID"));        //3
            vpd.put("var4", varOList.get(i).getString("AMOUNT"));        //4
            vpd.put("var5", varOList.get(i).get("USER_ID").toString());    //5
            vpd.put("var6", varOList.get(i).getString("USER_NAME"));        //6
            vpd.put("var7", varOList.get(i).getString("AMOUNT_TYPE"));        //7
            vpd.put("var8", varOList.get(i).getString("TAG"));        //8
            vpd.put("var9", varOList.get(i).getString("STATE"));        //9
            vpd.put("var10", varOList.get(i).getString("PAYMENT_TYPE"));        //10
            vpd.put("var11", varOList.get(i).getString("PAYMENT_VOUCHER"));        //11
            vpd.put("var12", varOList.get(i).getString("SHOP_NAME"));        //12
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
