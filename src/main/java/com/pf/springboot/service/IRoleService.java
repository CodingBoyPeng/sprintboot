package com.pf.springboot.service;

import com.pf.springboot.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();

    Integer insertRoleByThreadPool();

    String insertRole();
}
