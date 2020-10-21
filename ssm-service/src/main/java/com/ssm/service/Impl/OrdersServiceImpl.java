package com.ssm.service.Impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.IOrdersDao;
import com.ssm.dao.IProductDao;
import com.ssm.domain.Orders;
import com.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.List;

// 订单业务层接口实现类
@Service
@Transactional
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ORDER')")
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao ordersDao;

    // 查询所有订单
    @Override
    public List<Orders> findAll(int page,int size) throws Exception {
        // 参数：page:页码值，pageSize:每页显示条数
        PageHelper.startPage(page, size);
        return ordersDao.findAll();
    }

    // 根据Id查询订单
    @Override
    public Orders findById(Integer id) throws Exception {
        return ordersDao.findById(id);
    }

    // 删除订单(二步)
    @Override
    public void deleteById(Integer id) throws Exception {
        //1.先到 order_traveller 表中，删除关联订单的旅客
        ordersDao.deleteTravellers(id);
        //2.再去 orders订单表中，根据订单Id删除订单
        ordersDao.deleteById(id);
    }
}
