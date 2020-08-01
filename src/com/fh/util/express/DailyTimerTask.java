package com.fh.util.express;

import com.fh.service.front.AccountManager;
import com.fh.service.front.impl.AccountService;
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
 * 功能描述：每天定时任务 0 点执行
 * @author Ajie
 * @date 2019/12/6 0006
 */
public class DailyTimerTask extends TimerTask {

	private static Logger log = Logger.getLogger(DailyTimerTask.class);
	
	@Override
	public void run() {
		try {
			ServletContext applicati = ContextLoader.getCurrentWebApplicationContext().getServletContext();

			ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
					"classpath:spring/ApplicationContext-dataSource.xml",
					"classpath:spring/ApplicationContext-main.xml",
					"classpath:spring/ApplicationContext-redis.xml"}); 
			
			// 用户表
			AccountManager accountService = context.getBean("accountService", AccountService.class);

			PageData pd = new PageData();

			// 把签到重置
			accountService.resetDaily(pd);

			// 用户列表
			List<PageData> userList = accountService.listAll(null);

			// 用户名当作键 单独存缓存中
			for (PageData map : userList) {
				applicati.setAttribute(map.getString("USER_NAEM"), map);
			}

			((ConfigurableApplicationContext)context).close();
		} catch (Exception e) {
			log.info("-------------解析信息发生异常--------------");
			e.printStackTrace();
		}
	}

}
