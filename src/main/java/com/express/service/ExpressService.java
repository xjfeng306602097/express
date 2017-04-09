package com.express.service;

import com.express.model.Express;

import java.util.List;

public interface ExpressService {

	public List<Express> queryExpressInfo(String contact, String expressNo);

	public Express getExpressInfoById(Long id);

	public void createExpress(Express express);

	public void updateExpress(Express express);

	public void deleteExpress(Express express);

	boolean affirmCode(Express express, String verificationCode);

}
