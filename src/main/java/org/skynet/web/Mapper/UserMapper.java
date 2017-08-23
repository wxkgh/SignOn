package org.skynet.web.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.skynet.web.Model.User;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username} && password = #{password}")
    User findUser(@Param("username") String username, @Param("password") String password);
}
