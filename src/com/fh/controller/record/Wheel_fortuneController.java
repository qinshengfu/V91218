package com.fh.controller.record;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.record.Wheel_fortuneManager;
import com.fh.util.*;
import lombok.extern.slf4j.Slf4j;
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
 * 说明：幸运大转盘
 * 创建人：Ajie
 * 创建时间：2019-12-21
 */
@Controller
@RequestMapping(value="/wheel_fortune")
@Slf4j
public class Wheel_fortuneController extends BaseController {
	
	String menuUrl = "wheel_fortune/list.do"; //菜单地址(权限用)
	@Resource(name="wheel_fortuneService")
	private Wheel_fortuneManager wheel_fortuneService;

	/**
	 * 功能描述：抽奖接口
	 * @author Ajie
	 * @date 2019/12/24 0024
	 * @return 处理结果
	 */
	@RequestMapping(value = "/doLottery", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object luckDraw() throws Exception {
		// 获取前台传过来的用户id
		PageData pd = this.getPageData();
		String userId = pd.getString("userId");
		if (!"10000".equals(userId)) {
			return "非法参数";
		}
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
		System.out.println("中奖结果" + allPrize.get(index) );
		// 商品名称、数量
 		String name = allPrize.get(index).getString("NAME_GOODS");
		String num = allPrize.get(index).getString("NUM_OR_AGIO");
		// 是否为理财券 0：不是、1：是
		String isVoucher = allPrize.get(index).getString("IS_VOUCHER");
		pd = allPrize.get(index);
		return pd;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Wheel_fortune");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WHEEL_FORTUNE_ID", "");	//主键
		pd.put("GMT_CREATE", Tools.date2Str(new Date()));	//创建时间
		pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));	//更新时间
		wheel_fortuneService.save(pd);
		// 更新缓存信息
		List<PageData> wheelList = wheel_fortuneService.listAll(null);
		applicati.setAttribute(Const.APP_WHEEL, wheelList);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Wheel_fortune");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));	//更新时间
		wheel_fortuneService.delete(pd);
		// 更新缓存信息
		List<PageData> wheelList = wheel_fortuneService.listAll(null);
		applicati.setAttribute(Const.APP_WHEEL, wheelList);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Wheel_fortune");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));	//更新时间
		wheel_fortuneService.edit(pd);
		// 更新缓存信息
		List<PageData> wheelList = wheel_fortuneService.listAll(null);
		applicati.setAttribute(Const.APP_WHEEL, wheelList);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Wheel_fortune");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = wheel_fortuneService.list(page);	//列出Wheel_fortune列表
		mv.setViewName("record/wheel_fortune/wheel_fortune_list");
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
		mv.setViewName("record/wheel_fortune/wheel_fortune_edit");
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
		pd = wheel_fortuneService.findById(pd);	//根据ID读取
		mv.setViewName("record/wheel_fortune/wheel_fortune_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Wheel_fortune");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			wheel_fortuneService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Wheel_fortune到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("创建时间");	//1
		titles.add("更新时间");	//2
		titles.add("物品名称");	//3
		titles.add("中奖几率");	//4
		titles.add("数量or折扣");	//5
		titles.add("是否为理财券 0:不是、1：是");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = wheel_fortuneService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GMT_CREATE"));	    //1
			vpd.put("var2", varOList.get(i).getString("GMT_MODIFIED"));	    //2
			vpd.put("var3", varOList.get(i).getString("NAME_GOODS"));	    //3
			vpd.put("var4", varOList.get(i).getString("WINNING_PROBABILITY"));	    //4
			vpd.put("var5", varOList.get(i).getString("NUM_OR_AGIO"));	    //5
			vpd.put("var6", varOList.get(i).getString("IS_VOUCHER"));	    //6
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
