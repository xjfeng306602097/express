package com.express.service;

import java.util.List;

import com.express.model.ExpressShelf;

/**
 * Created by wshibiao on 2017/4/7.
 */
public interface ExpressShelfService {
    // public ExpressShelf getShelfByExpressId(Long id);

    public void moveExpressToOverDue();
    
    void updateExpressShelf(ExpressShelf expressShelf);
    
    ExpressShelf queryShelfByParams(ExpressShelf expressShelf);
    
    List<ExpressShelf> queryShelfListByParams(ExpressShelf expressShelf);
}
