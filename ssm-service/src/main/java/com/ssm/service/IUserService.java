package com.ssm.service;

import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

// 用户业务层接口，需要继承 UserDetailsService接口
public interface IUserService extends UserDetailsService {

    // 查询所有用户
    // 未分页：public List<UserInfo> findAll() throws Exception;
    public List<UserInfo> findAll(int page,int size) throws Exception;

    // 添加用户
    public void save(UserInfo userInfo) throws Exception;

    // 根据Id查询用户详情
    public UserInfo findById(Integer id) throws Exception;

    // 根据Id查询可添加的角色
    public List<Role> findOtherRoles(Integer id) throws Exception;
    // 给用户添加角色
    public void addRoleToUser(Integer userId, String[] roleIds) throws Exception;

}
