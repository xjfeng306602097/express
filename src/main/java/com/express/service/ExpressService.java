package com.express.service;

import com.express.model.Express;

import java.io.IOException;
import java.util.List;

public interface ExpressService {

	public List<Express> queryExpressInfo(Express express);

	public Express getExpressInfoById(Long id);

	public void createExpress(Express express);

	public void updateExpress(Express express);

	public void deleteExpress(Express express);

	boolean affirmCode(Express express, String verificationCode) throws IOException;
	
	public Express queryExpressDetail(Express express);
	
	public List<Express> queryExpressInfoOrderByDate(String contact, String expressNo, String status);
}
