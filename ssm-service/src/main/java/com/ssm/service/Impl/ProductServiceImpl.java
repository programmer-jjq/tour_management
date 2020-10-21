package com.ssm.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.dao.IProductDao;
import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service("productService")
@Transactional
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PRODUCT')") //spring的EL表达式注解
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    // 查询所有产品
    // @RolesAllowed({"ADMIN","PRODUCT"}) //JSR250注解
    // @Secured({"ROLE_ADMIN","ROLE_PRODUCT"}) // springsecurity自带注解
    @Override
    public List<Product> findAll(int page,int sizes) throws Exception {
        // page:当前页数 sizes:每页显示条数
        PageHelper.startPage(page,sizes);
        return productDao.findAll();
    }

    // 添加产品
    @Override
    public void save(Product product) throws Exception {
        productDao.save(product);
    }

    // 删除产品（四步）
    @Override
    public void deleteById(Integer id) throws Exception {
        // 1.先查关联产品的订单的Id
        Integer orderId = productDao.selectOrderId(id);
        // 2.根据订单Id到 order_traveller表中删除旅客信息
        productDao.deleteTravellers(orderId);
        // 3.从 orders表删除
        productDao.deleteFromOrdersByProdcutId(id);
        // 4.从 product表删除
        productDao.deleteById(id);
    }
}
