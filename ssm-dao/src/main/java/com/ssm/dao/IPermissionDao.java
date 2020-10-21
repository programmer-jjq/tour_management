package com.ssm.dao;

import com.ssm.domain.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
//资源权限持久层
public interface IPermissionDao {

    // 根据角色Id查询权限信息
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findPermissionByRoleId(Integer roleId) throws Exception;

    // 查询所有权限
    @Select("select * from permission")
    public List<Permission> findAll() throws Exception;

    // 添加权限
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    public void save(Permission permission) throws Exception;

    // 删除权限(两步)
    //1.先从 role_permission 表中删除
    @Delete("delete from role_permission where permissionId=#{id}")
    public void deleteFromRole_Permission(Integer id);
    //2.再从 permission 表中删除
    @Delete("delete from permission where id=#{id}")
    public void deleteById(Integer id) throws Exception;

    // 根据权限Id查询权限信息
    @Select("select * from permission where id=#{id}")
    Permission findById(Integer id);

}
