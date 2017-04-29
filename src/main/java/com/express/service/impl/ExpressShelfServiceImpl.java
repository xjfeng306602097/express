package com.express.service.impl;

import com.express.dao.ExpressDao;
import com.express.dao.ExpressShelfDao;
import com.express.dao.OverDueExpressDao;
import com.express.model.Express;
import com.express.model.ExpressShelf;
import com.express.model.OverDueExpress;
import com.express.service.ExpressShelfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
@Service
public class ExpressShelfServiceImpl implements ExpressShelfService {
	@Resource
	ExpressDao expressDao;
	@Resource
	ExpressShelfDao expressShelfDao;
	@Resource
	OverDueExpressDao overDueExpressDao;

	/**
	 * 将过期快件移入隔日货柜
	 */
	@Transactional
	@Override
	public void moveExpressToOverDue() {
		ExpressShelf expressShelf = new ExpressShelf();
		expressShelf.setShelfStatus("E");
		List<ExpressShelf> expressList = expressShelfDao.queryShelfListByParams(expressShelf);
		if (expressList.size() > 0) {
			for (ExpressShelf e : expressList) {
				Express express = e.getExpress();
				OverDueExpress overDueExpress = new OverDueExpress();
				overDueExpress.setOverDueShelfId(e.getShelfId());
				overDueExpress.setExpress(express);
				overDueExpress.setCreateDate(new Date());
				overDueExpress.setStatus("O");
				overDueExpressDao.insertOverDueExpress(overDueExpress);
				expressShelfDao.clearExpressShelf(e);
				express.setStatus("O");
				expressDao.updateExpressInfo(express);
			}
		}
	}

	/**
	 * 手动移除快件操作
	 * @param expressShelf
	 */
	public void removeExpress(ExpressShelf expressShelf){
		expressShelfDao.removeExpress(expressShelf);
	}

	@Override
	public void updateExpressShelf(ExpressShelf expressShelf) {
		expressShelfDao.updateExpressShelf(expressShelf);
	}

	@Override
	public ExpressShelf queryShelfByParams(ExpressShelf expressShelf) {
		return expressShelfDao.queryShelfByParams(expressShelf);
	}


	@Override
	public ExpressShelf queryUnusedShelf() {
		return expressShelfDao.queryUnusedShelf();
	}

	@Override
	public List<ExpressShelf> queryShelfListByParams(ExpressShelf expressShelf) {
		return expressShelfDao.queryShelfListByParams(expressShelf);
	}
}
