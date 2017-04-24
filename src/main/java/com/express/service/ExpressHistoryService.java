package com.express.service;

import java.util.List;

import com.express.model.ExpressHistory;

public interface ExpressHistoryService {
	
	int insertExpressHistory(ExpressHistory expressHistory);
	
	ExpressHistory queryHistoryByParams(ExpressHistory expressHistory);
	
	List<ExpressHistory> queryAllExpressHistory();
	
}
