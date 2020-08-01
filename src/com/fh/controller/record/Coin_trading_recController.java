package com.fh.controller.record;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.record.Coin_trading_recManager;
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
 * 说明：币币交易记录
 * 创建人：
 * 创建时间：2020-01-03
 */
@Controller
@RequestMapping(value="/coin_trading_rec")
public class Coin_trading_recController extends BaseController {
	
	String menuUrl = "coin_trading_rec/list.do"; //菜单地址(权限用)
	@Resource(name="coin_trading_recService")
	private Coin_trading_recManager coin_trading_recService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Coin_trading_rec");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("COIN_TRADING_REC_ID", this.get32UUID());	//主键
		pd.put("GMT_CREATE", Tools.date2Str(new Date()));	//创建时间
		pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));	//更新时间
		pd.put("USDT_AMOUNT", "0");	//USDT数量
		pd.put("MY_USER_ID", "0");	//我的用户ID
		pd.put("MY_USER_NAME", "");	//我的用户名
		pd.put("AMOUNT_TYPE", "");	//金额类型
		pd.put("TAG", "");	//标签 + 或者 -
		pd.put("XMC_PRICE", "0");	//XMC价格
		pd.put("HIS_USER_ID", "0");	//他的用户id
		pd.put("HIS_USER_NAME", "");	//他的用户名
		coin_trading_recService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Coin_trading_rec");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		coin_trading_recService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Coin_trading_rec");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		coin_trading_recService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Coin_trading_rec");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = coin_trading_recService.list(page);	//列出Coin_trading_rec列表
		mv.setViewName("record/coin_trading_rec/coin_trading_rec_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("record/coin_trading_rec/coin_trading_rec_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = coin_trading_recService.findById(pd);	//根据ID读取
		mv.setViewName("record/coin_trading_rec/coin_trading_rec_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Coin_trading_rec");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			coin_trading_recService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Coin_trading_rec到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("创建时间");	//1
		titles.add("更新时间");	//2
		titles.add("USDT数量");	//3
		titles.add("我的用户ID");	//4
		titles.add("我的用户名");	//5
		titles.add("金额类型");	//6
		titles.add("标签 + 或者 -");	//7
		titles.add("状态");	//8
		titles.add("XMC价格");	//9
		titles.add("他的用户id");	//10
		titles.add("他的用户名");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = coin_trading_recService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GMT_CREATE"));	    //1
			vpd.put("var2", varOList.get(i).getString("GMT_MODIFIED"));	    //2
			vpd.put("var3", varOList.get(i).get("USDT_AMOUNT").toString());	//3
			vpd.put("var4", varOList.get(i).get("MY_USER_ID").toString());	//4
			vpd.put("var5", varOList.get(i).getString("MY_USER_NAME"));	    //5
			vpd.put("var6", varOList.get(i).getString("AMOUNT_TYPE"));	    //6
			vpd.put("var7", varOList.get(i).getString("TAG"));	    //7
			vpd.put("var8", varOList.get(i).getString("STATE"));	    //8
			vpd.put("var9", varOList.get(i).get("XMC_PRICE").toString());	//9
			vpd.put("var10", varOList.get(i).get("HIS_USER_ID").toString());	//10
			vpd.put("var11", varOList.get(i).getString("HIS_USER_NAME"));	    //11
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
