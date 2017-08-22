package org.skynet.web.Service;

import org.skynet.web.DAO.Dao;
import org.skynet.web.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaskService {
    @Autowired
    Dao taskDao;

    public void executeSQLFile(String filePath) {
        taskDao.executeSQLFile(filePath);
    }

    public boolean addTask(Task newTask) {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("userid", newTask.getUserid());
        fieldMap.put("title", newTask.getTitle());
        fieldMap.put("reword", newTask.getReword());
        fieldMap.put("number", newTask.getNumber());
//        fieldMap.put("lable", task.getLabel());
//        fieldMap.put("species", task.getSpecies());
        return taskDao.insertEntity(Task.class, fieldMap);
    }

    public boolean deleteTask(Task oldTask) {
        return taskDao.deleteEntity(Task.class, oldTask.getId());
    }

    public boolean updateTask(Task currentTask) {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("title", currentTask.getTitle());
        fieldMap.put("reword", currentTask.getReword());
        return taskDao.updateEntity(Task.class, currentTask.getId(), fieldMap);
    }

    public Task findTask(Task task) {
        String sql = "SELECT * FROM task WHERE userid = \""+ task.getUserid() +"\" && title = \"" + task.getTitle() + "\"";
        return taskDao.queryEntity(Task.class, sql);
    }
}
