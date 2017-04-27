package com.express.service;

import com.express.model.ExpressShelf;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
public interface ExpressShelfService {
    // public ExpressShelf getShelfByExpressId(Long id);

    public void moveExpressToOverDue();
    
    void updateExpressShelf(ExpressShelf expressShelf);
    
    ExpressShelf queryShelfByParams(ExpressShelf expressShelf);
    List<ExpressShelf> queryShelfsByParams(ExpressShelf expressShelf);
    ExpressShelf queryUnusedShelf();


    List<ExpressShelf> queryShelfListByParams(ExpressShelf expressShelf);
}
