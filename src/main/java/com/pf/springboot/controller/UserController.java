package com.pf.springboot.controller;


import com.pf.springboot.entity.User;
import com.pf.springboot.service.IUserService;
import com.pf.springboot.util.AjaxResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * @description:
 * @author:Peng
 * @Date:2020/12/14
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    IUserService userService;
    
    @ResponseBody
    @GetMapping("/{id}")
    public AjaxResult getUserById(@PathVariable(value = "id") Integer userId) {
        User user = userService.getUserById(userId);
        return AjaxResult.success(user);
    }

    /**
     * mybatis-plus批量查询
     * @param ids
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public AjaxResult getUserList(@Param(value = "ids") String ids) {
        List<String> userIds = Arrays.asList(ids.split(","));
        return AjaxResult.success(userService.getUserByIdList(userIds));
    }
}
