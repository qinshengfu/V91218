package com.fh.controller.record;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.record.ErcSellRecordManager;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;
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
 * 说明：法币交易之出售记录
 * 创建人：Ajie
 * 创建时间：2020-01-03
 */
@Controller
@RequestMapping(value="/ercsellrecord")
public class ErcSellRecordController extends BaseController {
	
	String menuUrl = "ercsellrecord/list.do"; //菜单地址(权限用)
	@Resource(name="ercsellrecordService")
	private ErcSellRecordManager ercsellrecordService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ErcSellRecord");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ERCSELLRECORD_ID", this.get32UUID());	//主键
		pd.put("GMT_CREATE", Tools.date2Str(new Date()));	//创建时间
		pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));	//更新时间
		pd.put("ORDER_ID", "");	//订单ID
		pd.put("AMOUNT", "");	//数量
		pd.put("USER_ID", "0");	//用户ID
		pd.put("USER_NAME", "");	//用户名
		pd.put("AMOUNT_TYPE", "");	//金额类型
		pd.put("TAG", "");	//标签 + 或者 -
		pd.put("RECEIPT_TYPE", "");	//收款类型
		pd.put("ALIPAY_ACCOUNT", "");	//支付宝账号
		pd.put("HOLDER", "");	//开户人
		pd.put("BANK_NUMBER", "");	//银行卡号
		pd.put("BANK_NAME", "");	//开户银行
		pd.put("SHOP_NAME", "");	//商铺名称
		pd.put("PRICE", "");	//单价
		pd.put("SUM_PRICE", "");	//总价
		ercsellrecordService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 功能描述：确认打款
	 * @author Ajie
	 * @date 2020/1/3 0003
	 */
	@RequestMapping(value="/confirmPay", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"确认打款");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return "无权限";} //校验权限
		PageData pd = this.getPageData();
		pd = ercsellrecordService.findById(pd);
		String status = pd.getString("STATE");
		if (!"待打款".equals(status)) {
			return "此订单已完成或者未确认收款方式";
		}
		// 更改状态为已完成
		pd.put("STATE", "已完成");
		ercsellrecordService.edit(pd);
		return "success";
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ErcSellRecord");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = ercsellrecordService.list(page);	//列出ErcSellRecord列表
		mv.setViewName("record/ercsellrecord/ercsellrecord_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出ErcSellRecord到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("创建时间");	//1
		titles.add("更新时间");	//2
		titles.add("订单ID");	//3
		titles.add("数量");	//4
		titles.add("用户ID");	//5
		titles.add("用户名");	//6
		titles.add("金额类型");	//7
		titles.add("标签 + 或者 -");	//8
		titles.add("状态");	//9
		titles.add("收款类型");	//10
		titles.add("支付宝账号");	//11
		titles.add("开户人");	//12
		titles.add("银行卡号");	//13
		titles.add("开户银行");	//14
		titles.add("商铺名称");	//15
		titles.add("单价");	//16
		titles.add("总价");	//17
		dataMap.put("titles", titles);
		List<PageData> varOList = ercsellrecordService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GMT_CREATE"));	    //1
			vpd.put("var2", varOList.get(i).getString("GMT_MODIFIED"));	    //2
			vpd.put("var3", varOList.get(i).getString("ORDER_ID"));	    //3
			vpd.put("var4", varOList.get(i).getString("AMOUNT"));	    //4
			vpd.put("var5", varOList.get(i).get("USER_ID").toString());	//5
			vpd.put("var6", varOList.get(i).getString("USER_NAME"));	    //6
			vpd.put("var7", varOList.get(i).getString("AMOUNT_TYPE"));	    //7
			vpd.put("var8", varOList.get(i).getString("TAG"));	    //8
			vpd.put("var9", varOList.get(i).getString("STATE"));	    //9
			vpd.put("var10", varOList.get(i).getString("RECEIPT_TYPE"));	    //10
			vpd.put("var11", varOList.get(i).getString("ALIPAY_ACCOUNT"));	    //11
			vpd.put("var12", varOList.get(i).getString("HOLDER"));	    //12
			vpd.put("var13", varOList.get(i).getString("BANK_NUMBER"));	    //13
			vpd.put("var14", varOList.get(i).getString("BANK_NAME"));	    //14
			vpd.put("var15", varOList.get(i).getString("SHOP_NAME"));	    //15
			vpd.put("var16", varOList.get(i).getString("PRICE"));	    //16
			vpd.put("var17", varOList.get(i).getString("SUM_PRICE"));	    //17
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
