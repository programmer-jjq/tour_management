package com.ssm.dao;

import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// 用户持久层接口
public interface IUserDao {

    // 根据用户名查询用户信息+对应角色信息
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true,property ="id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            // 根据 UserId，调用 IRoleDao 的 findRoleByUserId 方法，查询到所有角色信息
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findByUsername(String username) throws Exception;

    // 查询所有用户
    @Select("select * from users")
    public List<UserInfo> findAll() throws Exception;

    // 添加用户
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    public void save(UserInfo userInfo) throws Exception;

    // 根据用户Id查询用户详情
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            // 根据 UserId，调用 IRoleDao 的 findRoleByUserId 方法，查询到对应角色信息
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findById(Integer id) throws Exception;

    // 根据用户Id查询可添加的角色
    @Select("select * from role where id not in (select roleId from users_role where userId=#{id})")
    List<Role> findOtherRoles(Integer id) throws Exception;
    // 选择可添加角色进行添加
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    public void addRoleToUser(@Param("userId")Integer userId, @Param("roleId")String roleId) throws Exception;

}
