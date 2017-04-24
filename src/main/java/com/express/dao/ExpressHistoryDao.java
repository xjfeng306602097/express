package com.express.dao;

import java.util.List;

import com.express.model.ExpressHistory;

public interface ExpressHistoryDao {

	public int insertExpressHistory(ExpressHistory expressHistory);

	public ExpressHistory queryHistoryByParams(ExpressHistory expressHistory);

	public List<ExpressHistory> queryAllExpressHistory();
}
