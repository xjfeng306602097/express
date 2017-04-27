package com.express.dao;

import com.express.model.ExpressShelf;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/7.
 */
public interface ExpressShelfDao {

    /**
     *将快递放入货架
     * @param expressShelf
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
    /**
     * 更新货柜
     * @param expressShelf
     */
    public void updateExpressShelf(ExpressShelf expressShelf);

    /**
     * 查询特定货柜
     * @param expressShelf
     * @return
     */
    public ExpressShelf queryShelfByParams(ExpressShelf expressShelf);

    /**
     * 查找未使用的货柜
     * @return
     */
    public ExpressShelf queryUnusedShelf();

    /**
     * 根据特定条件查出list
     * @param expressShelf
     * @return
     */
    public List<ExpressShelf> queryShelfListByParams(ExpressShelf expressShelf);
}
