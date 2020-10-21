package com.ssm.dao;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.tools.JavaCompiler;
import java.util.List;

@Repository
// 角色持久层接口
public interface IRoleDao {

    //根据用户Id查找所有对应角色信息 + 根据角色Id查询所有对应权限信息
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            // 根据 RoleId ，调用IPermissionDao 的 findPermissionByRoleId 方法，查询所对应的权限信息
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IPermissionDao.findPermissionByRoleId")),
    })
    public List<Role> findRoleByUserId(Integer userId) throws Exception;


    // 查询所有角色
    @Select("select * from role")
    public List<Role> findAll() throws Exception;

    // 添加角色
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role) throws Exception;

    // 删除角色（三步）
    //1.先从 user_role删除
    @Delete("delete from users_role where roleId=#{id}")
    public void deleteFromUsers_RoleByRoleId(Integer id) throws Exception;
    //2.再从 role_permission删除
    @Delete("delete from role_permission where roleId=#{id}")
    public void deleteFromRole_PermissionById(Integer id) throws Exception;
    //3.最后从 role删除
    @Delete("delete from role where id=#{id}")
    public void deleteRoleById(Integer id) throws Exception;

    // 根据角色Id查询角色详情
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
    public Role findById(Integer id);

    // 1.根据角色Id查询可添加的权限信息
    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findOtherPermissions(Integer id) throws Exception;
    // 2.给角色添加权限
    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId}) ")
    public void addPermissionToRole(@Param("roleId") Integer roleId,@Param("permissionId") String permissionId);
}
