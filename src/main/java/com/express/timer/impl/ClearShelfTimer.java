package com.express.timer.impl;

import com.express.service.ExpressShelfService;
import com.express.timer.IExecuteTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearShelfTimer implements IExecuteTimer {
	
	private static final Logger logger = LoggerFactory.getLogger(SendSMSTimer.class);
	@Autowired
	private ExpressShelfService expressShelfService;
	
	// 每天凌晨12点00分00秒清除
	@Scheduled(cron = "0 0 0 * * ?")
	@Override
	public void execute() {
		expressShelfService.moveExpressToOverDue();
		logger.info("Clear expressShelf success");
	}

}
