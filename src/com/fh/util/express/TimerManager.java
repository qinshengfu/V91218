package com.fh.util.express;

import com.fh.util.Const;
import com.fh.util.QuartzManager;
import com.fh.util.timers.CheckingInTaskJob;
import com.fh.util.timers.CheckingOutTaskJob;
import com.fh.util.timers.StaticBonusTaskJob;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


/**
 * 定时任务时间控制
 *
 * @author liming
 *
 */
public class TimerManager {

	// 时间间隔一天  刷新一次
	private static final long PERIOD_DAY =  24 * 60 * 60 * 1000;
	// 时间间隔5分钟  刷新一次
	private static final long PERIOD_MIN =  5 * 60 * 1000;
	// 时间间隔 2小时 刷新一次
	private static final long PERIOD_HOUR = 2 * 60 * 60 * 1000;

	private static ServletContext sContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();

	public TimerManager() {

		// 添加每日0点释放静态奖金任务
		String dayTime = "0 0 0 * * ?";
		QuartzManager.addJob(Const.TIMED_TASK_STATIC, StaticBonusTaskJob.class, dayTime);
		// 隔3分钟查一次区块链余额并增加到用户钱包
		String inTime = "0 /3 * * * ? *";
		QuartzManager.addJob("TimingQuery_In", CheckingInTaskJob.class, inTime);

		// 从6分钟后开始，隔3分钟查一次区块链余额并增加到用户钱包
		String outTiam = "0 6/3 * * * ? *";
		QuartzManager.addJob("TimingQuery_Out", CheckingOutTaskJob.class, outTiam);

		Calendar calendar = Calendar.getInstance();

		Calendar calendar1 = Calendar.getInstance();

		Date date = calendar.getTime(); //执行定时任务的时间
		// 在启动服务器时如果第一次执行定时任务的时间小于当前的时间任务会立即执行。
		// 因此为了防止重启服务器造成任务重复执行，需要将执行定时任务的时间修改为第二天。
		/*if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}*/

		Timer timer = new Timer();

		DailyDataTimerTask task = new DailyDataTimerTask();

		// 任务执行间隔。
		//timer.schedule(task, date, PERIOD_DAY);//时间间隔
		timer.schedule(task, date);//执行一次


		/*** 定制每日00:00执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		/*** 定制每日02:00执行方法 ***/
		calendar1.set(Calendar.HOUR_OF_DAY, 2);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);

		DailyTimerTask timerTask = new DailyTimerTask();

		// 获取当天的0点
		Date need = calendar.getTime();
		// 获取当天的凌晨2点
		Date need1 = calendar.getTime();

		if (need.before(new Date())) {
			need = this.addDay(need, 1);
		}

		if (need1.before(new Date())) {
			need1 = this.addDay(need1, 1);
		}
		// 添加任务
		timer.scheduleAtFixedRate(timerTask, need1, PERIOD_DAY); // 每天执行

	}

	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

}
