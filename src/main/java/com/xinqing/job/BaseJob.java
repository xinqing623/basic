package com.xinqing.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class BaseJob implements Job {
	
	private static boolean isComplete = true;

	private void jobStart() {
		this.isComplete = false;
	}

	private void jobDone() {
		this.isComplete = true;
	}

	private boolean isJobComplete() {
		return isComplete;
	}
	
	protected abstract void doJob(JobExecutionContext arg0);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		if (isJobComplete()) {
//			jobStart();
			doJob(context);
//			jobDone();
//		}

	}

}
