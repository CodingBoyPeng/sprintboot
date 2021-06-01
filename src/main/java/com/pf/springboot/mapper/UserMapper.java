package com.pf.springboot.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

/***
 * @author pengfeng
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User randomFindUser();
}
