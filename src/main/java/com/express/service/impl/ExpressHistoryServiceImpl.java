package com.express.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.express.dao.ExpressHistoryDao;
import com.express.model.ExpressHistory;
import com.express.service.ExpressHistoryService;

@Service
public class ExpressHistoryServiceImpl implements ExpressHistoryService {
	
	@Resource
	private ExpressHistoryDao expressHistoryDao;
	
	@Transactional
	@Override
	public int insertExpressHistory(ExpressHistory expressHistory){
		return expressHistoryDao.insertExpressHistory(expressHistory);
	};

	@Override
	public ExpressHistory queryHistoryByParams(ExpressHistory expressHistory) {
		return expressHistoryDao.queryHistoryByParams(expressHistory);
	}

	@Override
	public List<ExpressHistory> queryAllExpressHistory() {
		return expressHistoryDao.queryAllExpressHistory();
	}
	
}
