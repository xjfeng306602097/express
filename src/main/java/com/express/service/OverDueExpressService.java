package com.express.service;

import com.express.model.OverDueExpress;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/8.
 */
public interface OverDueExpressService {

    public OverDueExpress queryShelfByParams(OverDueExpress overDueExpress);

    /**
     * 获取所有隔日快递的收件人联系方式
     * @return
     */
    public List<String> getContactsWithOverDue();

}
