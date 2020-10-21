package com.ssm.service;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;

import java.util.List;

// 角色业务层接口
public interface IRoleService {

    // 查询所有角色
    // 未分页：public List<Role> findAll() throws Exception;
    public List<Role> findAll(int page,int size) throws Exception;

    // 添加角色
    public void save(Role role) throws Exception;

    // 删除角色
    public void deleteRoleById(Integer id) throws Exception;

    // 根据Id查询角色信息
    public Role findById(Integer id) throws Exception;

    // 根据Id查询可添加的权限
    public List<Permission> findOtherPermissions(Integer id) throws Exception;
    // 给角色添加权限
    public void addPermissionToRole(Integer roleId, String[] permissionIds) throws Exception;
}
