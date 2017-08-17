package org.life.task.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class userDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean finduser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? && password = ?";
        if (jdbcTemplate.queryForRowSet(sql, username, password) != null) {
            return true;
        }
        return false;
    }
}
