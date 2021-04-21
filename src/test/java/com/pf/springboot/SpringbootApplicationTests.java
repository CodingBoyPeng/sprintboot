package com.pf.springboot;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.pf.springboot.entity.User;
import com.pf.springboot.mapper.UserMapper;
import com.pf.springboot.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testResultMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "lue.lu@adinall.com");

        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }


    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("username");
        user.setNickname("nickname");
        user.setCreateTime("2021-03-09 00:00:00");
        userMapper.insert(user);
    }



    @Test
    public void testQueryWarpper() {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(true, User::getId, 1);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
}
