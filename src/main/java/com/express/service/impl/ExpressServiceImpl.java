package com.express.service.impl;

import com.express.dao.ExpressDao;
import com.express.model.Express;
import com.express.service.ExpressService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
@Service
public class ExpressServiceImpl implements ExpressService {

	private static final String SALT = "avadfa%^%#!&#%^fdafafa~@$%^$&&^%&erere}{}*(*&*^";

	@Resource
	private ExpressDao expressDao;

	/**
	 * 查询快递的信息
	 * 
	 * @param contact
	 * @param expressNo
	 * @return
	 */
	public List<Express> queryExpressInfo(String contact, String expressNo) {
		return expressDao.queryExpressInfo(contact, expressNo);
	}

	@Override
	public Express getExpressInfoById(Long id) {
		return expressDao.getExpressInfoById(id);
	}

	@Override
	public void createExpress(Express express) {
		expressDao.insertExpressInfo(express);
	}

	@Override
	public void updateExpress(Express express) {
		expressDao.updateExpressInfo(express);
	}

	@Override
	public void deleteExpress(Express express) {

	}

	/**
	 * 确认密码是否正确
	 */
	@Override
	public boolean affirmCode(Express express, String verificationCode) {
		Express queryResult = expressDao.getExpressInfoById(express.getId());
		String code = DigestUtils.md5DigestAsHex((queryResult.getVerificationCode() + SALT).getBytes()).substring(0, 6);
		if (verificationCode.equals(express)) {
			return true;
		} else {
			return false;
		}
	}

}
