package com.pf.springboot.service;

import com.pf.springboot.entity.User;

import java.util.List;

/**
 * @description:
 * @author:Peng
 * @Date:2020/12/14
 */
public interface IUserService {
    User getUserById(Integer userId);

    List<User> getUserByIdList(List userIds);
}
