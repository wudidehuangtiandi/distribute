package com.dao;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

//@Repository
public interface UserSearchDao extends Mapper<User>{

    @Select("select *from users where username=#{username}")
    public List<User> findUser(@Param("username")String username);

}
