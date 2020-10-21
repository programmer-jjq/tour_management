package com.ssm.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.dao.IUserDao;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.IUserService;
import com.ssm.utils.BCryptPasswordEncoderUtils;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 处理自己的用户对象封装成UserDetails
        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));
        // 手动加密：User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),userInfo.getStatus() == 0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        UserDetails userDetails = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus() == 0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        return userDetails;
    }
    //作用：返回一个List集合，装入的是角色描述
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;
    }

    // 查询所有用户(未分页)
    /*@Override
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }*/
    // 查询所有用户(分页)
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfo> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return userDao.findAll();
    }

    // 添加用户
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void save(UserInfo userInfo) throws Exception {
        // 调用加密工具类，对密码进行加密处理
        /*userInfo.setPassword(BCryptPasswordEncoderUtils.EncodePassword(userInfo.getPassword()));
        userDao.save(userInfo);*/

        // 对密码进行加密处理(获取表单初始密码加密后再设置)
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    // 根据Id查询用户详情
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserInfo findById(Integer id) throws Exception {
        return userDao.findById(id);
    }

    // 根据用户Id查找可添加角色
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Role> findOtherRoles(Integer id) throws Exception {
        return userDao.findOtherRoles(id);
    }
    // 添加角色
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addRoleToUser(Integer userId, String[] roleIds) throws Exception {
        for (String roleId : roleIds) {
            userDao.addRoleToUser(userId,roleId);
        }
    }
}
