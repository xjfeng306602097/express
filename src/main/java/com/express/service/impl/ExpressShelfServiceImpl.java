package com.express.service.impl;

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
public class ExpressShelfServiceImpl implements ExpressShelfService{
    @Resource
    ExpressShelfDao expressShelfDao;
    @Resource
    OverDueExpressDao overDueExpressDao;
    // @Override
    // public ExpressShelf getShelfByExpressId(Long id) {
    //     return expressShelfDao.getShelfByExpressId(new Long(1));
    // }

    /**
     * 将过期快件移入隔日货柜
     */
    @Transactional
    @Override
    public void moveExpressToOverDue() {
        List<ExpressShelf> expressList=expressShelfDao.queryAllShelfExpress();
        if (expressList.size()>0){
            for (ExpressShelf e:expressList) {
                Express express=e.getExpress();
                OverDueExpress overDueExpress=new OverDueExpress();
                overDueExpress.setExpress(express);
                overDueExpress.setCreateDate(new Date());
                overDueExpress.setStatus("F");
                overDueExpressDao.insertOverDueExpress(overDueExpress);
                expressShelfDao.clearExpressShelf(e);
            }
        }
    }
}
