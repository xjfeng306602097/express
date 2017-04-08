package com.express.test;

import com.express.service.OverDueExpressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/8.
 */
@ContextConfiguration(locations={"classpath*:config/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OverDueExpressServiceImplTest {
    @Resource
    OverDueExpressService overDueExpressService;
    @Test
    public void queryShelf(){
        List<String> contactsWithOverDue=overDueExpressService.getContactsWithOverDue();

        for(int i=0;i<contactsWithOverDue.size();i++){
            System.out.println(contactsWithOverDue.get(i));
        }
    }


}