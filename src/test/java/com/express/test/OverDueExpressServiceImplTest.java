package com.express.test;

import com.express.dao.OverDueExpressDao;
import com.express.model.Express;
import com.express.model.OverDueExpress;
import com.express.service.ExpressService;
import com.express.service.OverDueExpressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/8.
 */
@ContextConfiguration(locations={"classpath*:config/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OverDueExpressServiceImplTest {
    @Resource
    private OverDueExpressService overDueExpressService;
    @Resource
    private ExpressService expressService;
    @Resource
    private OverDueExpressDao overDueExpressDao;
    @Test
    public void queryShelf(){
        List<Express> contactsWithOverDue=overDueExpressService.getExpressWithOverDue();

        for(int i=0;i<contactsWithOverDue.size();i++){
            System.out.println(contactsWithOverDue.get(i));
        }
    }

    @Test
    public  void updateExpress(){
        OverDueExpress overDueExpress=new OverDueExpress();
        Express express=expressService.getExpressInfoById(new Long(1));
        overDueExpress.setExpress(express);
        overDueExpress.setCreateDate(new Date());
        overDueExpress.setStatus("N");
        overDueExpressDao.updateOverDueExpressShelf(overDueExpress);
    }


}