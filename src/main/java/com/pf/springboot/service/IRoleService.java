package com.pf.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pf.springboot.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();

    Integer insertRoleByThreadPool();

    String insertRole();

    IPage<Role> rolePage();
}
