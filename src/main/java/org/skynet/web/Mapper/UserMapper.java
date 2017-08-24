package org.skynet.web.Mapper;

import org.apache.ibatis.annotations.*;
import org.skynet.web.Model.User;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(username, password) VALUES (#{username}, #{password})")
    int insertUserByAccount(@Param("username") String username, @Param("password") String password);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(@Param("id") long id);

    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{id}")
    int updateUser(@Param("username") String username, @Param("password") String password, @Param("id") long id);

    @Select("SELECT * FROM user WHERE username = #{username} && password = #{password}")
    User findUserByAccount(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") long id);
}
