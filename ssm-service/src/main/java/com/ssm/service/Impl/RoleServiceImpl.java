package com.ssm.service.Impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.IRoleDao;
import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import com.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 角色业务层接口实现类
@Service
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    // 查询所有角色
    @Override
    public List<Role> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return roleDao.findAll();
    }

    // 保存角色
    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }

    // 删除角色
    @Override
    public void deleteRoleById(Integer id) throws Exception {
        // 从 user_role表删除
        roleDao.deleteFromUsers_RoleByRoleId(id);
        // 从 role_permission表删除
        roleDao.deleteFromRole_PermissionById(id);
        // 从 role表删除
        roleDao.deleteRoleById(id);
    }

    // 根据Id查询角色信息
    @Override
    public Role findById(Integer id) throws Exception {
        return roleDao.findById(id);
    }

    // 1.根据角色Id查询可添加的权限
    @Override
    public List<Permission> findOtherPermissions(Integer id) throws Exception {
       return roleDao.findOtherPermissions(id);
    }
    // 2.给角色添加权限
    @Override
    public void addPermissionToRole(Integer roleId, String[] permissionIds) throws Exception {
        for (String permissionId : permissionIds) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }
}
