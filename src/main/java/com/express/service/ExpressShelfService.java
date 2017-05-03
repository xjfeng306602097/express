package com.express.service;

import com.express.model.ExpressShelf;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
public interface ExpressShelfService {

    void moveExpressToOverDue();

    /**
     * 货柜维护页面删除快件
     * @param expressShelf
     */
    void removeExpress(ExpressShelf expressShelf);

    void updateExpressShelf(ExpressShelf expressShelf);
    
    ExpressShelf queryShelfByParams(ExpressShelf expressShelf);

    ExpressShelf queryUnusedShelf();

    void createExpressShelf(ExpressShelf expressShelf);

    List<ExpressShelf> queryShelfListByParams(ExpressShelf expressShelf);
}
