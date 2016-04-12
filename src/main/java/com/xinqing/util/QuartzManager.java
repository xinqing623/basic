package com.xinqing.util;

//简单的任务管理类
//QuartzManager.java

import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** */
/**
 * @Title:Quartz管理类
 * 
 * @Description:
 * 
 * @Copyright:
 * @author zz 2008-10-8 14:19:01
 * @version 1.00.000
 *
 */
public class QuartzManager {
	private static Logger log = LoggerFactory.getLogger(QuartzManager.class);
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "group1";
	private static String TRIGGER_GROUP_NAME = "trigger1";

	/** */
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param job
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static boolean addJob(String jobName, String jobClassName, String time) {
		boolean isSuccess = false;
		try {
			Scheduler sched = sf.getScheduler();
			JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
			Class<Job> clazz = (Class<Job>) Class.forName(jobClassName);
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).build();// 任务名，任务组，任务执行类

			// 触发器
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).startNow().build();

			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!sched.isShutdown())
				sched.start();
			isSuccess = true;
			log.info("新增作业触发时间=> [作业名称：" + jobName + " 作业组：" + JOB_GROUP_NAME + "] ");
		} catch (SchedulerException e) {
			log.error("新增作业触发时间=> [作业名称：" + jobName + " 作业组：" + JOB_GROUP_NAME + "]=> [失败]", e);
		} catch (ClassNotFoundException e) {
			log.error("新增作业触发时间=> [作业名称：" + jobName + " 作业组：" + JOB_GROUP_NAME + "]=> [失败]", e);
		}
		return isSuccess;
	}

	/** */
	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param job
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static boolean addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			String jobClassName, String time) {
		boolean isSuccess = false;
		try {
			Scheduler sched = sf.getScheduler();
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			Class<Job> clazz = (Class<Job>) Class.forName(jobClassName);
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).build();// 任务名，任务组，任务执行类

			// 触发器
			TriggerKey triggerKey = new TriggerKey(jobName, triggerGroupName);
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).startNow().build();
			sched.scheduleJob(jobDetail, trigger);
			if (!sched.isShutdown())
				sched.start();
			isSuccess = true;
			log.info("新增作业触发时间=> [作业名称：" + jobName + " 作业组：" + jobGroupName + "] ");
		} catch (SchedulerException e) {
			log.error("新增作业触发时间=> [作业名称：" + jobName + " 作业组：" + jobGroupName + "]=> [失败]", e);
		} catch (ClassNotFoundException e) {
			log.error("新增作业触发时间=> [作业名称：" + jobName + " 作业组：" + jobGroupName + "]=> [失败]", e);
		}
		return isSuccess;
	}

	/** */
	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static boolean modifyJobTime(String jobName, String time) throws SchedulerException, ParseException {
		boolean isSuccess = false;
		try {
			Scheduler scheduler = sf.getScheduler();
			TriggerKey tk = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
			// 构造任务触发器
			Trigger trg = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
			scheduler.rescheduleJob(tk, trg);
			isSuccess = true;
			log.info("修改作业触发时间=> [作业名称：" + jobName + " 作业组：" + TRIGGER_GROUP_NAME + "] ");
		} catch (SchedulerException e) {
			log.error("修改作业触发时间=> [作业名称：" + jobName + " 作业组：" + TRIGGER_GROUP_NAME + "]=> [失败]", e);
		}
		return isSuccess;
	}

	/** */
	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static boolean modifyJobTime(String jobName, String triggerGroupName, String time) {
		boolean isSuccess = false;
		try {
			Scheduler scheduler = sf.getScheduler();
			TriggerKey tk = TriggerKey.triggerKey(jobName, triggerGroupName);
			// 构造任务触发器
			Trigger trg = TriggerBuilder.newTrigger().withIdentity(jobName, triggerGroupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
			scheduler.rescheduleJob(tk, trg);
			isSuccess = true;
			log.info("修改作业触发时间=> [作业名称：" + jobName + " 作业组：" + triggerGroupName + "] ");
		} catch (SchedulerException e) {
			log.error("修改作业触发时间=> [作业名称：" + jobName + " 作业组：" + triggerGroupName + "]=> [失败]", e);
		}
		return isSuccess;
	}

	/** */
	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static boolean removeJob(String jobName) throws SchedulerException {
		boolean isSuccess = false;
		try {
			JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			Scheduler sched = sf.getScheduler();
			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(jobKey);// 删除任务
			isSuccess = true;
			log.info("删除作业=> [作业名称：" + jobName + " 作业组：" + JOB_GROUP_NAME + "] ");
		} catch (SchedulerException e) {
			log.error("删除作业=> [作业名称：" + jobName + " 作业组：" + JOB_GROUP_NAME + "]=> [失败]", e);
		}
		return isSuccess;
	}

	/** */
	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @throws SchedulerException
	 */
	public static boolean removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		boolean isSuccess = false;
		try {
			Scheduler sched = sf.getScheduler();
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			TriggerKey triggerKey = new TriggerKey(jobName, triggerGroupName);
			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(jobKey);// 删除任务
			isSuccess = true;
			log.info("删除作业=> [作业名称：" + jobName + " 作业组：" + jobGroupName + "] ");
		} catch (SchedulerException e) {
			log.error("删除作业=> [作业名称：" + jobName + " 作业组：" + jobGroupName + "]=> [失败]", e);
		}
		return isSuccess;
	}

	public static void main(String[] args) throws SchedulerException, ParseException {
		// addJob("testJob", new MyJob(), "0/1 * * * * ?");
		// modifyJobTime("testJob", "0/3 * * * * ?");
		// removeJob("testJob");
	}

	public static boolean checkExist(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			Scheduler scheduler = sf.getScheduler();
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
			return scheduler.checkExists(jobKey) || scheduler.checkExists(triggerKey);
		}catch (SchedulerException e) {
			log.error("查询是否存在作业出错",e);
		}
		return false;
	}
	
	public static boolean checkExist(String jobName, String triggerName) {
		return checkExist(jobName, JOB_GROUP_NAME, triggerName, TRIGGER_GROUP_NAME);
	}
}
