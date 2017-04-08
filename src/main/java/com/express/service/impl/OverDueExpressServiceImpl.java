package com.express.service.impl;

import com.express.dao.OverDueExpressDao;
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
       return null;
    }

    @Override
    public List getContactsWithOverDue() {
        //获取所有隔日件
        List<OverDueExpress> overDueExpresses=overDueExpressDao.queryALLShelf();
        //获取所有联系方式
        List contacts=new ArrayList();
        if (overDueExpresses.size()>0) {
            for (OverDueExpress overDueExpress :
                    overDueExpresses) {
                contacts.add(overDueExpress.getExpress().getContact());
            }
        }
       return contacts;
    }
}
