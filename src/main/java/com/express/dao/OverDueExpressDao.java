package com.express.dao;

import com.express.model.OverDueExpress;

import java.util.List;

public interface OverDueExpressDao {
    /**
     * 更新隔日快递信息
     * @param overDueExpress
     */
    public void updateExpressShelf(OverDueExpress overDueExpress);

    /**
     * 插入隔日快递
     * @param overDueExpress
     */
    public void insertOverDueExpress(OverDueExpress overDueExpress);
    /**
     * 查找隔日快递
     * @param overDueExpress
     */
    public OverDueExpress queryShelfByParams(OverDueExpress overDueExpress);

    /**
     * 查询所有隔日快递
     * @return
     */
    public List<OverDueExpress> queryALLShelf();
    
    /**
     * 查询特定状态下的快递
     * @return list
     */
    public List<OverDueExpress> queryShelfListByParams(OverDueExpress overDueExpress);
}
