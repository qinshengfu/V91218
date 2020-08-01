package com.fh.util.express;

import com.fh.util.QuartzManager;
import com.fh.util.pool.MyThreadPoolManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 定时任务监听器
 * 
 * @author liming
 *
 */
 
public class DailyDataTaskListener implements ServletContextListener {
 
	@Override
	public void contextInitialized(ServletContextEvent event) {
		new TimerManager();
	}
 
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// 关闭线程池
		MyThreadPoolManager.shutdown();
		// 关闭所有定时任务
		QuartzManager.shutdownJobs();
	}
 
}
