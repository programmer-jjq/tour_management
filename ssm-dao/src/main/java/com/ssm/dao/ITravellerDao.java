package com.ssm.dao;

import com.ssm.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 旅客持久层接口
public interface ITravellerDao {

    // 根据订单Id查询旅客(根据中间表 order_traveller)
    @Select("SELECT * FROM traveller WHERE id IN (SELECT travellerId FROM order_traveller WHERE orderId=#{id})")
    public List<Traveller> findByOrdersId(Integer id) throws Exception;

}



