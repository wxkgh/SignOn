package org.life.task.DAO;

import org.apache.commons.dbcp.BasicDataSource;
import org.life.task.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
//    private static final String TABLE_NAME = "User";
//    private static final String COLUMN_NAMES = "id, username, password";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(BasicDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User findUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? && password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username, password},
                (resultSet, i) -> new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
                )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
