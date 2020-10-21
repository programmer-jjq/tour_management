package com.ssm.service;

import com.ssm.domain.Orders;

import java.util.List;

// 订单业务层接口
public interface IOrdersService {

    // 查询所有订单
    public List<Orders> findAll(int pages,int size) throws Exception;

    // 根据id查询订单
    public Orders findById(Integer id) throws Exception;

    // 删除订单
    public void deleteById(Integer id) throws Exception;
}
