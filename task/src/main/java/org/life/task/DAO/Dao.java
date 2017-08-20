package org.life.task.DAO;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Dao {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public Dao(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void executeSQLFile(String filePath) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String sql;
            while ((sql = bufferedReader.readLine()) != null) {
                jdbcTemplate.update(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

    public boolean insertEntity(String sql) {
        return updateEntity(sql);
    }

    public boolean deleteEntity(String sql) {
        return updateEntity(sql);
    }

    public boolean updateEntity(String sql) {
        if (1 != jdbcTemplate.update(sql)) {
            return false;
        }
        return true;
    }

    public <T> T queryEntity(Class<T> entityClass, String sql) {
        try {
            return jdbcTemplate.query(sql, resultSet -> {
                if (!resultSet.next()) return null;
                ResultSetMetaData data = resultSet.getMetaData();
                int count = data.getColumnCount();
                return toPOJO(entityClass, resultSet, data, count);
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T>List<T> queryEntityList(Class<T> entityClass, String sql) {
        List<T> entityList;
        entityList =(List<T>) jdbcTemplate.queryForList(sql, entityClass.getClass());
        return entityList;
    }

    private <T> T toPOJO(Class<T> cls, ResultSet resultSet, ResultSetMetaData data, int count) throws SQLException {
        T pojo;
        try {
            pojo = cls.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("get POJO InstantiationException when new " + cls.getName());
            return null;
        } catch (IllegalAccessException e) {
            LOGGER.error("get POJO IllegalAccessException when new " + cls.getName());
            return null;
        }

        for (Method method : cls.getMethods()) {
            String ms = method.getName();
            if (!ms.startsWith("set") || method.getParameterTypes().length != 1) continue;

            ms = ms.substring(3).toLowerCase();
            for (int i = 1; i <= count; i++) {
                if (!data.getColumnLabel(i).toLowerCase().equals(ms)) continue;

                Object obj = resultSet.getObject(i);
                if (obj == null) break;

                try {
                    method.invoke(pojo, obj);
                } catch (IllegalAccessException e) {
                    LOGGER.error("get POJO IllegalAccessException when invoke " + method.getName());
                    return null;
                } catch (IllegalArgumentException e) {
                    LOGGER.error("get POJO IllegalArgumentException when invoke " + method.getName());
                    return null;
                } catch (InvocationTargetException e) {
                    LOGGER.error("get POJO InvocationTargetException when invoke " + method.getName());
                }
                break;
            }
        }
        return pojo;
    }
}
