package com.express.service.impl;

import com.express.dao.ExpressDao;
import com.express.model.Express;
import com.express.service.ExpressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * @param contact
     * @param expressNo
     * @return
     */
    public List<Express> queryExpressInfo(String contact, String expressNo){
        return expressDao.queryExpressInfo(contact,expressNo);
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
}
