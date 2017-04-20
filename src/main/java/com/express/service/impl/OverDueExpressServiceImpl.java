package com.express.service.impl;

import com.express.dao.OverDueExpressDao;
import com.express.model.Express;
import com.express.model.OverDueExpress;
import com.express.service.OverDueExpressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/8.
 */
@Service
public class OverDueExpressServiceImpl implements OverDueExpressService {
	@Resource
	OverDueExpressDao overDueExpressDao;

	@Override
	public OverDueExpress queryShelfByParams(OverDueExpress overDueExpress) {
		return overDueExpressDao.queryShelfByParams(overDueExpress);
	}

	@Override
	public List<Express> getExpressWithOverDue() {
		// 获取所有隔日件
		List<OverDueExpress> overDueExpresses = overDueExpressDao.queryALLShelf();
		// 获取所有联系方式
		List<Express> expresses = new ArrayList<Express>();
		if (overDueExpresses.size() > 0) {
			for (OverDueExpress overDueExpress : overDueExpresses) {
				expresses.add(overDueExpress.getExpress());
			}
		}
		return expresses;
	}

	@Override
	public List<OverDueExpress> queryShelfListByParams(OverDueExpress overDueExpress) {
		return overDueExpressDao.queryShelfListByParams(overDueExpress);
	}
}
