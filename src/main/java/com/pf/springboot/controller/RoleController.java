package com.pf.springboot.controller;

import com.pf.springboot.service.IRoleService;
import com.pf.springboot.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "role")
public class RoleController {
    @Autowired
    IRoleService roleService;

    @ResponseBody
    @GetMapping("list")
    public AjaxResult getAllRoles() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("123","1223");
        return AjaxResult.success(roleService.getAllRoles());
    }

    @ResponseBody
    @GetMapping("insert")
    public AjaxResult insertRoleByThreadPool() {
        return AjaxResult.success(roleService.insertRoleByThreadPool());
    }

    @GetMapping("test")
    public AjaxResult insertRole() {
        return AjaxResult.success(roleService.insertRole());
    }
}
