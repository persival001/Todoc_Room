package com.persival.todoc_room;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.persival.todoc_room.data.TodocDatabase;
import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;
import com.persival.todoc_room.utils.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TaskDao taskDao;
    private TodocDatabase database;

    @Before
    public void createDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        taskDao = database.taskDao();

        // Insertion of 3 projects for tasks tests
        ProjectDao projectDao = database.projectDao();
        Project project1 = new Project(0, "Projet Tartampion", 0xFFEADAD1);
        Project project2 = new Project(0, "Projet Lucidia", 0xFFB4CDBA);
        Project project3 = new Project(0, "Projet Circus", 0xFFA3CED2);
        projectDao.insert(project1);
        projectDao.insert(project2);
        projectDao.insert(project3);
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertTaskAndGetTaskList() {
        Task task = new Task(0, 1, "Task 1", System.currentTimeMillis());
        taskDao.insertTask(task);
        LiveData<List<Task>> taskListLiveData = taskDao.getTaskList();
        List<Task> taskList = TestUtil.getValueForTesting(taskListLiveData);
        assertEquals(1, taskList.size());
        assertEquals("Task 1", taskList.get(0).getName());
    }

    @Test
    public void insertTaskAndDeleteTask() {
        Task task = new Task(0, 1, "Task 2", System.currentTimeMillis());
        taskDao.insertTask(task);
        taskDao.deleteTask(1);
        LiveData<List<Task>> taskListLiveData = taskDao.getTaskList();
        List<Task> taskList = TestUtil.getValueForTesting(taskListLiveData);
        assertEquals(0, taskList.size());
    }

    @Test
    public void getAllEntriesSortedAZ() {
        Task task1 = new Task(0, 1, "A", System.currentTimeMillis());
        Task task2 = new Task(0, 1, "Z", System.currentTimeMillis());
        Task task3 = new Task(0, 1, "F", System.currentTimeMillis());
        taskDao.insertTask(task1, task2, task3);

        LiveData<List<Task>> taskListLiveData = taskDao.getAllEntriesSortedAZ();
        List<Task> taskList = TestUtil.getValueForTesting(taskListLiveData);

        assertEquals(3, taskList.size());
        assertEquals("A", taskList.get(0).getName());
        assertEquals("F", taskList.get(1).getName());
        assertEquals("Z", taskList.get(2).getName());
    }

    @Test
    public void getAllEntriesSortedZA() {
        Task task1 = new Task(0, 1, "A", System.currentTimeMillis());
        Task task2 = new Task(0, 1, "Z", System.currentTimeMillis());
        Task task3 = new Task(0, 1, "F", System.currentTimeMillis());
        taskDao.insertTask(task1, task2, task3);

        LiveData<List<Task>> taskListLiveData = taskDao.getAllEntriesSortedZA();
        List<Task> taskList = TestUtil.getValueForTesting(taskListLiveData);

        assertEquals(3, taskList.size());
        assertEquals("Z", taskList.get(0).getName());
        assertEquals("F", taskList.get(1).getName());
        assertEquals("A", taskList.get(2).getName());
    }

    @Test
    public void getAllEntriesSortedByTimeASC() {
        Task task1 = new Task(0, 1, "A", 2);
        Task task2 = new Task(0, 1, "Z", 3);
        Task task3 = new Task(0, 1, "F", 1);
        taskDao.insertTask(task1, task2, task3);

        LiveData<List<Task>> taskListLiveData = taskDao.getAllEntriesSortedByTimeASC();
        List<Task> taskList = TestUtil.getValueForTesting(taskListLiveData);

        assertEquals(3, taskList.size());
        assertEquals(1, taskList.get(0).getCreationTimestamp());
        assertEquals(2, taskList.get(1).getCreationTimestamp());
        assertEquals(3, taskList.get(2).getCreationTimestamp());
    }

    @Test
    public void getAllEntriesSortedByTimeDESC() {
        Task task1 = new Task(0, 1, "A", 1);
        Task task2 = new Task(0, 1, "Z", 2);
        Task task3 = new Task(0, 1, "F", 3);
        taskDao.insertTask(task1, task2, task3);

        LiveData<List<Task>> taskListLiveData = taskDao.getAllEntriesSortedByTimeDESC();
        List<Task> taskList = TestUtil.getValueForTesting(taskListLiveData);

        assertEquals(3, taskList.size());
        assertEquals(3, taskList.get(0).getCreationTimestamp());
        assertEquals(2, taskList.get(1).getCreationTimestamp());
        assertEquals(1, taskList.get(2).getCreationTimestamp());
    }
}