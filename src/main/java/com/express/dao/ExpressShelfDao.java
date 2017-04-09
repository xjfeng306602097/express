package com.express.dao;

import com.express.model.ExpressShelf;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
public interface ExpressShelfDao {

    /**
     *将快递放入货架
     * @param shelf
     */
    public void  insertExpressShelf(ExpressShelf expressShelf);
    /**
     *清空货柜
     * @param
     */
     public void clearExpressShelf(ExpressShelf expressShelf);
    /**
     *查询货架上的所有快递
     */
    public List<ExpressShelf> queryAllShelfExpress();
    
    int updateExpressShelf(ExpressShelf expressShelf);
}
