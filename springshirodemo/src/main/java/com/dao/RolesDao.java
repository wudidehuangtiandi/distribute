package com.dao;

import com.pojo.UserRoles;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RolesDao extends Mapper<UserRoles> {

    @Select("select * from user_roles where username=#{username}")
    public List<UserRoles> findRoles(@Param("username")String username);
}
