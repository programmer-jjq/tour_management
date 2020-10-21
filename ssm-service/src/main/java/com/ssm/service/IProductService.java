package com.ssm.service;

import com.ssm.domain.Product;

import java.util.List;

// 业务层接口
public interface IProductService {

    // 查询所有产品
    public List<Product> findAll(int page,int sizes) throws Exception;

    // 添加产品
    public void save(Product product) throws Exception;

    // 删除产品
    public void deleteById(Integer id) throws Exception;
}
