package org.skynet.web.DAO;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class Dao {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void executeSQLFile(String filePath) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String sql;
            while ((sql = bufferedReader.readLine()) != null) {
                jdbcTemplate.execute(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

    public <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (fieldMap.isEmpty()) {
            LOGGER.error("Can not insert entity : fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + entityClass.getSimpleName();
        StringBuffer columns = new StringBuffer("(");
        StringBuffer values = new StringBuffer("(");
        for (String filedName : fieldMap.keySet()) {
            columns.append(filedName).append(",");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + "VALUES" + values;
        Object[] params = fieldMap.values().toArray();
        if (jdbcTemplate.update(sql, params) == 1) {
            return true;
        }
        return false;
    }

    public <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + entityClass.getSimpleName() + " WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 1) {
            return true;
        }
        return false;
    }

    public <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (fieldMap.isEmpty()) {
            LOGGER.error("Can not update entity : fieldMap is empty");
            return false;
        }
        String sql = "UPDATE " + entityClass.getSimpleName() + " SET ";
        StringBuffer columns = new StringBuffer();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        if (jdbcTemplate.update(sql, params) == 1) {
            return true;
        }
        return false;
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
