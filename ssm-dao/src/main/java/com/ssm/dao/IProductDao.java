package com.ssm.dao;

import com.ssm.domain.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
// 商品的持久层接口
public interface IProductDao {

    // 查询所有产品信息
    @Select("select * from product")
    public List<Product> findAll() throws Exception;

    // 根据ID查询产品
    @Select("select * from product where id=#{id}")
    public Product findById(Integer id) throws Exception;

    // 添加产品
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values (#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void save(Product product) throws Exception;

    // 删除产品(四步)
    // 先根据产品Id搭配 order 中查找出关联该产品的订单的订单Id
    @Select("select id from orders where productId=#{id}")
    public Integer selectOrderId(Integer id);
    // 再根据订单Id到 order_traveller 中删除该订单对应的游客信息
    @Delete("delete from order_traveller where orderId=#{orderId}")
    public void deleteTravellers(Integer orderId);
    // 先从 orders订单表删除关联产品的订单
    @Delete("delete from orders where productId = #{id}")
    public void deleteFromOrdersByProdcutId(Integer id);
    // 再从 product表删除产品
    @Delete("delete from product where id=#{id}")
    public void deleteById(Integer id);
}
