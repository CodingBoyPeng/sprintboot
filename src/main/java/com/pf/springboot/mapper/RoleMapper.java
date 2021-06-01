package com.pf.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.springboot.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role>  {
    List<Role> getAllRoles();
}
