package com.express.service.impl;

import com.express.dao.ExpressDao;
import com.express.model.Express;
import com.express.service.ExpressService;
import com.express.util.PropertyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
@Service
public class ExpressServiceImpl implements ExpressService {

	@Resource
	private ExpressDao expressDao;

	/**
	 * 查询快递的信息
	 *
	 * @param express
	 * @return
	 */
	public List<Express> queryExpressInfo(Express express) {
		return expressDao.queryExpressInfo(express);
	}

	@Override
	public Express getExpressInfoById(Long id) {
		return expressDao.getExpressInfoById(id);
	}

	@Override
	public void createExpress(Express express) {
		expressDao.insertExpressInfo(express);
	}
	
	@Transactional
	@Override
	public void updateExpress(Express express) {
		expressDao.updateExpressInfo(express);
	}
	
	@Override
	public void deleteExpress(Express express) {

	}
	
	@Override
	public Express queryExpressDetail(Express express) {
		return expressDao.queryExpressDetail(express);
	}

	/**
	 * 确认密码是否正确
	 * @throws IOException 
	 */
	@Override
	public boolean affirmCode(Express express, String verificationCode) throws IOException {
		String code = DigestUtils.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes()).substring(0, 6);
		if (verificationCode.equals(code)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Express> queryExpressInfoOrderByDate(String contact, String expressNo, String status) {
		return expressDao.queryExpressInfoOrderByDate(contact, expressNo, status);
	}
	
}
