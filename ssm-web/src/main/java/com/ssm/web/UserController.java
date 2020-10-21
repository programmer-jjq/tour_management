package com.ssm.web;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    // 查询所有用户(未分页)
    /*@RequestMapping("/findAll")
    public ModelAndView findAll() throws Exception{
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userInfoList = userService.findAll();
        mv.addObject("userList",userInfoList);
        mv.setViewName("user-list");
        return mv;
    }*/

    // 查询所有用户(分页)
    @RequestMapping("/findAll")
    public ModelAndView findAll(
            @RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
            @RequestParam(name = "size",required = true,defaultValue = "4") Integer size
        ) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userInfoList = userService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(userInfoList);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("user-list");
        return mv;
    }

    // 添加用户
    @RequestMapping("/save")
    public String save(UserInfo userInfo) throws Exception{
        userService.save(userInfo);
        return "redirect:findAll";
    }

    // 根据Id查询用户详情
    @RequestMapping("/findById")
    public ModelAndView findById(Integer id) throws Exception{
        ModelAndView mv = new ModelAndView();
        // 根据用户Id查询：用户信息+角色信息+权限信息
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show");
        return mv;
    }

    // 给用户添加角色===1.先查询用户以及用户可以添加的角色）
    @RequestMapping("/addRole")
    public ModelAndView addRole(Integer id) throws Exception{
        ModelAndView mv = new ModelAndView();
        //1.根据用户Id查询用户
        UserInfo userInfo = userService.findById(id);
        //2.根据用户Id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(id);
        mv.addObject("user",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }
    // 给用户添加角色===2.选择可添加角色进行添加
    @RequestMapping("/addRoleToUser")
    public String addRoleToUser(
            @RequestParam(name = "userId",required = true) Integer userId,
            @RequestParam(name = "ids",required = true) String[] roleIds) throws Exception{
        userService.addRoleToUser(userId,roleIds);
        return "redirect:findAll";
    }
}
