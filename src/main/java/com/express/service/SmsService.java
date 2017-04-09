package com.express.service;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
public interface SmsService {
	public void sendSMS(String smsContent, List<String> contacts);

	public void sendMessage(String contacts, String smsContent);
}
