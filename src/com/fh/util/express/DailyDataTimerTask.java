package com.fh.util.express;

import com.fh.service.fhoa.impl.FhfileService;
import com.fh.service.front.AccountManager;
import com.fh.service.front.RankManager;
import com.fh.service.front.Sys_configManager;
import com.fh.service.front.impl.AccountService;
import com.fh.service.front.impl.RankService;
import com.fh.service.record.*;
import com.fh.service.record.impl.*;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Logger;
import com.fh.util.PageData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.TimerTask;

/**
 * 定时任务操作主体
 *
 * @author liming
 */
public class DailyDataTimerTask extends TimerTask {

    private static Logger log = Logger.getLogger(DailyDataTimerTask.class);


    @Override
    public void run() {
        try {
            //在这里写你要执行的内容
            System.out.println("执行时间：//" + DateUtil.getTime().toString());
            ServletContext applicati = ContextLoader.getCurrentWebApplicationContext().getServletContext();
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                    "classpath:spring/ApplicationContext-dataSource.xml",
                    "classpath:spring/ApplicationContext-main.xml",
                    "classpath:spring/ApplicationContext-redis.xml"});
            FhfileService fhfileService = context.getBean("fhfileService", FhfileService.class);

            AccountManager accountService = context.getBean("accountService", AccountService.class);
            Sys_chartManager sys_chartService = context.getBean("sys_chartService", Sys_chartService.class);
            Sys_newsManager sys_newsService = context.getBean("sys_newsService", Sys_newsService.class);
            Sys_configManager sys_configService = context.getBean("sys_configService", Sys_configManager.class);
            Sys_AboutManager sys_aboutService = context.getBean("sys_aboutService", Sys_AboutService.class);
            RankManager rankService = context.getBean("rankService", RankService.class);
            Wheel_fortuneManager wheel_fortuneService = context.getBean("wheel_fortuneService", Wheel_fortuneService.class);
            Lnvestment_listManager lnvestment_listService = context.getBean("lnvestment_listService", Lnvestment_listService.class);

            PageData par = new PageData();
            par.put("SYS_CONFIG_ID", "1");
            par = sys_configService.findById(par);

            // 用户列表
            List<PageData> userList = accountService.listAll(null);
            // 前台轮播图
            List<PageData> chartList = sys_chartService.listAll(null);
            // 前台新闻公告
            List<PageData> newsList = sys_newsService.listAll(null);
            // 前台关于我们
            List<PageData> aboutList = sys_aboutService.listAll(null);
            // 前台用户等级表
            List<PageData> rankList = rankService.listAll(null);
            // 前台幸运大转盘
            List<PageData> wheelList = wheel_fortuneService.listAll(null);
            // 前台投资列表
            List<PageData> lnvestmentList = lnvestment_listService.listAll(null);


            List<PageData> lbt = fhfileService.listAll(null);


            // 存到缓存中
            applicati.setAttribute(Const.PAR, par); // 参数配置
            applicati.setAttribute(Const.APP_NEWS, newsList);
            applicati.setAttribute(Const.APP_CHART, chartList);
            applicati.setAttribute(Const.APP_ABOUT, aboutList);
            applicati.setAttribute(Const.APP_WHEEL, wheelList);
            applicati.setAttribute(Const.APP_LNVESTMENT, lnvestmentList);

            applicati.setAttribute("lbt", lbt);// 后台轮播图

            // 用户名当作键 单独存缓存中
            for (PageData map : userList) {
                applicati.setAttribute(map.getString("USER_NAME"), map);
            }
            // ID作为键 单独存缓存中
            for (PageData map : rankList) {
                System.out.println("把等级放入缓存中： " + map);
                String key = Const.APP_RANK + map.get("RANK_ID").toString();
                applicati.setAttribute(key, map);
            }

            ((ConfigurableApplicationContext) context).close();
        } catch (Exception e) {
            log.info("-------------解析信息发生异常--------------");
            e.printStackTrace();
        }
    }


}

