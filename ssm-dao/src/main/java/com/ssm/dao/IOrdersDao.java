package com.ssm.dao;

import com.ssm.domain.Member;
import com.ssm.domain.Orders;
import com.ssm.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// 订单持久层接口
public interface IOrdersDao {

    // 查询所有订单
    @Select("select * from orders")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ssm.dao.IProductDao.findById"))
    })
    public List<Orders> findAll() throws Exception;

    // 根据id查询订单
    @Select("select * from orders where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),

            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.ssm.dao.IProductDao.findById")),
            @Result(property = "member", column = "memberId", javaType = Member.class, one = @One(select = "com.ssm.dao.IMemberDao.findById")),
            @Result(property = "travellers",column = "id",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.ITravellerDao.findByOrdersId"))
    })
    public Orders findById(Integer id) throws Exception;

    // 删除订单
    //1. 根据订单id到 order_traveller 表中，删除旅客信息
    @Delete("delete from order_traveller where orderId=#{id}")
    public void deleteTravellers(Integer id) throws Exception;
    //2. 根据订单id到 orders订单表中，删除订单
    @Delete("delete from orders where id=#{id}")
    public void deleteById(Integer id) throws Exception;
}
