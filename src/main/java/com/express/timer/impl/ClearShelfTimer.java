package com.express.timer.impl;

import com.express.service.ExpressShelfService;
import com.express.timer.IExecuteTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearShelfTimer implements IExecuteTimer {
	@Autowired
	private ExpressShelfService expressShelfService;
	
	// 每天凌晨12点00分00秒清除
	@Scheduled(cron = "0 0 0 * * ?")
	@Override
	public void execute() {
		expressShelfService.moveExpressToOverDue();
		System.out.println("This is the timer for clearing shelf");
	}

}
