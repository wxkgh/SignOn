package org.skynet.web.DataBase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.web.Model.Task;
import org.skynet.web.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)

public class TaskDataBaseTest {
    @Autowired
    TaskService taskService;

    @Before
    public void init() {
        taskService.executeSQLFile("sql/task_init.sql");
    }

    @Test
    public void addTaskTest() {
        Task sleepTask = new Task(12001, "sleep early", 30, 1);
        Assert.assertTrue(taskService.addTask(sleepTask));
        Assert.assertNotNull(taskService.findTask(sleepTask));
    }

    @Test
    public void deleteTaskTest() {
        Task oldTask = new Task(12001, "get up at 6:30", 30, 1);
        oldTask.setId(50002);
        Assert.assertTrue(taskService.deleteTask(oldTask));
        Assert.assertNull(taskService.findTask(oldTask));
    }

    @Test
    public void updateTaskTest() {
        Task currentTask = new Task(12001, "get up at 7:30", 30, 1);
        currentTask.setId(50002);
        Assert.assertTrue(taskService.updateTask(currentTask));
        Assert.assertNotNull(taskService.findTask(currentTask));
    }

}
