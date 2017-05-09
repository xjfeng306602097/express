package com.express.dao;

import com.express.model.Express;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/6.
 */
public interface ExpressDao {
    /**
     * 录入快递信息
     * @param express
     */
    public void insertExpressInfo(Express express);

    /**
     * 更新快递信息
     * @param express

     */
    public void updateExpressInfo(Express express);
    /**
     * 查询未领取的快递信息
     * @return
     */
    public Express getExpressInfoById(@Param("id") Long id);
    /**
     * 通过手机号和快递号查询快递的信息
     * @param express
     * @return
     */
    public List<Express> queryExpressInfo(Express express);

    /**
     *删除快递
     * @param expressNo
     */
    public void deleteExpress(@Param("expressNo")String expressNo);
    
    /**
     * 查询单个快件
     * @param express
     * @return
     */
    public Express queryExpressDetail(Express express);
    
    /**
     * 查询快件列表，并升序排序
     * @param contact
     * @param expressNo
     * @param status
     * @return
     */
    public List<Express> queryExpressInfoOrderByDate(@Param("contact")String contact, @Param("expressNo")String expressNo, @Param("status")String status);
    
    /**
     * 分页查询存在货柜的快件
     * @param express
     * @return
     */
    public List<Express> queryExpressInShelfByPage(Express express);
}
