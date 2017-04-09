package com.express.test;

import com.express.model.Express;
import com.express.model.ExpressShelf;
import com.express.service.ExpressService;
import com.express.service.ExpressShelfService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by wshibiao on 2017/4/8.
 */
@ContextConfiguration(locations={"classpath*:config/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExpressShelfServiceTest {
    @Autowired
    private  ExpressShelfService expressShelfService;
    @Autowired
    private ExpressService  expressService;
    @Test
    public  void moveExpressToOverDue(){
        expressShelfService.moveExpressToOverDue();
    }

    @Test
    public  void updateExpress(){
        ExpressShelf expressShelf=new ExpressShelf();
        Express express=expressService.getExpressInfoById(new Long(1));
        expressShelf.setShelfId(new Long(1));
        expressShelf.setExpress(express);
        expressShelf.setCreateDate(new Date());
        expressShelf.setShelfStatus("2");
        expressShelfService.updateExpressShelf(expressShelf);
    }
}