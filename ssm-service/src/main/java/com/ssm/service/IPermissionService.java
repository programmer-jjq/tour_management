package com.ssm.service;

import com.ssm.domain.Permission;

import java.util.List;

//权限业务层接口
public interface IPermissionService {

    // 查询所有权限
    // 未分页：public List<Permission> findAll() throws Exception;
    public List<Permission> findAll(int page,int size) throws Exception;

    // 添加权限
    public void save(Permission permission) throws Exception;

    // 删除权限
    public void deleteById(Integer id) throws Exception;

    // 根据Id查询权限信息
    public Permission findById(Integer id) throws Exception;
}
