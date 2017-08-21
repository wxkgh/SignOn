package org.life.task.Service;

import org.life.task.DAO.Dao;
import org.life.task.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class TaskService {
    @Autowired
    Dao taskDao;

    public boolean addTask(Task task) {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("userid", task.getUserid());
        fieldMap.put("title", task.getTitle());
        fieldMap.put("reword", task.getReword());
        fieldMap.put("number", task.getNumber());
//        fieldMap.put("lable", task.getLabel());
//        fieldMap.put("species", task.getSpecies());
        return taskDao.insertEntity(Task.class, fieldMap);
    }

    public boolean deleteTask(Task task) {
        return true;
    }

    public boolean updateTask(Task task) {
        return true;
    }

    public boolean findTask(Task task) {
        return true;
    }
}
