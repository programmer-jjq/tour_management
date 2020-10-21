package com.ssm.web;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    // 查询所有角色(未分页)
    /*@RequestMapping("/findAll")
    public ModelAndView findALl() throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Role> roleList = roleService.findAll();
        mv.addObject("roleList",roleList);
        mv.setViewName("role-list");
        return mv;
    }*/
    // 查询所有角色(分页)
    @RequestMapping("/findAll")
    public ModelAndView findALl(
            @RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
            @RequestParam(name = "size",required = true,defaultValue = "4") Integer size
            ) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Role> roleList = roleService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(roleList);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("role-list");
        return mv;
    }

    // 添加角色
    @RequestMapping("/save")
    public String save(Role role) throws Exception{
        roleService.save(role);
        return "redirect:findAll";
    }

    // 删除角色
    @RequestMapping("/deleteRole")
    public String deleteRole(Integer id) throws Exception{
        roleService.deleteRoleById(id);
        return "redirect:findAll";
    }

    // 根据Id查询角色信息
    @RequestMapping("/findById")
    public ModelAndView findById(Integer id) throws Exception{
        ModelAndView mv = new ModelAndView();
        Role role = roleService.findById(id);
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    // 给角色添加权限===1.先查询角色以及角色可以添加的权限
    @RequestMapping("/addPermission")
    public ModelAndView addPermission(Integer id) throws Exception{
        ModelAndView mv = new ModelAndView();
        //1.根据角色Id查询角色
        Role role = roleService.findById(id);
        //2.根据角色Id查询可以添加的权限
        List<Permission> otherPermissions = roleService.findOtherPermissions(id);
        mv.addObject("role",role);
        mv.addObject("permissionList",otherPermissions);
        mv.setViewName("role-permission-add");
        return mv;
    }
    // 给角色添加权限===2.选择可添加权限进行添加
    @RequestMapping("/addPermissionToRole")
    public String addPermissionToRole(
            @RequestParam(name = "roleId",required = true) Integer roleId,
            @RequestParam(name = "ids",required = true) String[] permissionIds) throws Exception{
        roleService.addPermissionToRole(roleId,permissionIds);
        return "redirect:findAll";
    }
}
