package com.pf.springboot.service.impl;

import com.pf.springboot.entity.User;
import com.pf.springboot.mapper.UserMapper;
import com.pf.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author:Peng
 * @Date:2020/12/14
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<User> getUserByIdList(List<String> userIds) {
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public User randomFindUser() {
        return userMapper.randomFindUser();
    }
}
