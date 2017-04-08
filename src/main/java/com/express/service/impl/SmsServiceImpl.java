package com.express.service.impl;

import com.express.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendSMS(String smsContent, List<String> contacts) {
        for(int i=0;i<contacts.size();i++){
            System.out.println(contacts.get(i));
        }
    }
}
