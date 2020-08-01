package com.fh.controller.front;

import com.fh.controller.base.BaseController;
import com.fh.service.front.AccountManager;
import com.fh.service.front.Sys_configManager;
import com.fh.util.*;
import com.fh.util.pool.MyThreadPoolManager;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/** 
 * 说明：系统参数配置
 * 创建人：
 * 创建时间：2019-12-20
 */
@Controller
@RequestMapping(value="/sys_config")
public class Sys_configController extends BaseController {
	
	String menuUrl = "sys_config/list.do"; //菜单地址(权限用)
	@Resource(name="sys_configService")
	private Sys_configManager sys_configService;
	// 前台用户
	@Resource(name="accountService")
	private AccountManager accountService;

	/**
	 * 功能描述：清空数据,保留顶点用户
	 * @author Ajie
	 * @date 2019/12/23 0023
	 */
	@RequestMapping(value="/wipeAllData")
	@ResponseBody
	public String delete(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"清空数据");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		// 删除上传的凭证图片
		String fullPath = request.getServletContext().getRealPath(Const.FILEPATHIMG);
		DelAllFile.delAllFile(fullPath);
		List<PageData> allUser = accountService.listAll(null);
		// 需要清空数据的表名
		pd.put("FT_DAILY_CHART", "FT_DAILY_CHART");
		pd.put("FT_HEDGING_REC", "FT_HEDGING_REC");
		pd.put("FT_LNCOME_DETAILS", "FT_LNCOME_DETAILS");
		pd.put("FT_USDT_WALLET_REC", "FT_USDT_WALLET_REC");
		pd.put("FT_XMC_WALLET_REC", "FT_XMC_WALLET_REC");
		pd.put("FT_VOUCHER_LIST", "FT_VOUCHER_LIST");
		pd.put("FT_LUCKYDRAWREC", "FT_LUCKYDRAWREC");
		pd.put("FT_MESSAGE_FEEDBACK", "FT_MESSAGE_FEEDBACK");
		pd.put("FT_ERCBUYRECORD", "FT_ERCBUYRECORD");
		pd.put("FT_ERCSELLRECORD", "FT_ERCSELLRECORD");
		pd.put("FT_ERCBUY", "FT_ERCBUY");
		pd.put("FT_ERCSELL", "FT_ERCSELL");
		pd.put("FT_COIN_TRADING_REC", "FT_COIN_TRADING_REC");
		pd.put("FT_ACCUM_REC", "FT_ACCUM_REC");
		// 调用多线程清缓存清空表
		PageData finalPd = pd;
		MyThreadPoolManager.getsInstance().execute(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				Thread.currentThread().setName("pool-清空数据");
				accountService.deleteAllData(null);
				// 清缓存
				for (PageData var : allUser) {
				  	if (!"Ajie".equals(var.getString("USER_NAME"))) {
				  		applicati.removeAttribute(var.getString("USER_NAME"));
					}
				}
				// 清空表
				sys_configService.deleteAllTable(finalPd);
				// 重置序列
				resetSeq();
			}
		});
		// 重置顶点账号信息
		accountService.resetAccount(null);
		// 更新缓存
		pd = new PageData();
		pd.put("ACCOUNT_ID", 10000);
		pd = accountService.findById(pd);
		applicati.setAttribute(pd.getString("USER_NAME"), pd);
		return "success";
	}

	/**
	 * 功能描述：调用存储过程重置序列
	 * @author Ajie
	 * @date 2019年12月23日11:28:14
	 */
	private void resetSeq() throws Exception {
		PageData pd = new PageData();
		pd.put("seqName", "FT_ACCOUNT_SEQ");
		pd.put("seqStart", "10001");
		sys_configService.reset_seq(pd);
		pd.put("seqName", "FT_DAILY_CHART_SEQ");
		pd.put("seqStart", "1");
		sys_configService.reset_seq(pd);
		pd.put("seqName", "FT_HEDGING_REC_SEQ");
		pd.put("seqStart", "1");
		sys_configService.reset_seq(pd);
		pd.put("seqName", "FT_LNCOME_DETAILS_SEQ");
		pd.put("seqStart", "1");
		sys_configService.reset_seq(pd);
		pd.put("seqName", "FT_USDT_WALLET_REC_SEQ");
		pd.put("seqStart", "1");
		sys_configService.reset_seq(pd);
		pd.put("seqName", "FT_XMC_WALLET_REC_SEQ");
		pd.put("seqStart", "1");
		sys_configService.reset_seq(pd);
	}


	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public String edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Sys_config");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd;
		pd = this.getPageData();
		pd.put("GMT_MODIFIED", Tools.date2Str(new Date()));	//更新时间
		sys_configService.edit(pd);
		System.out.println("修改系统参数："+pd);
		System.out.println("系统参数长度为："+pd.size());
		// 更新缓存
		applicati.setAttribute(Const.PAR, pd);
		// 修改每日静态分红定时任务触发时间
		String time = pd.getString("TASK_TIME");
		time = "2020-01-01 " + time;
		System.out.println("时间：" + time);
		time = DateUtil.getCronDay(time);
		QuartzManager.modifyJobTime(Const.TIMED_TASK_STATIC, time);
		// 查看所有任务 下次执行时间
		QuartzManager.taskMethod();
		return "success";
	}
	
	/**列表
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Sys_config");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd;
		pd = sys_configService.findById(null);
		mv.setViewName("front/sys_config/sys_config_list");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC());	//按钮权限
		return mv;
	}

}
