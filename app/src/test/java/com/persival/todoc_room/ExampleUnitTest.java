package com.persival.todoc_room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ExampleUnitTest {
    private final TaskDao taskDao = mock(TaskDao.class);
    private final ProjectDao projectDao = mock(ProjectDao.class);
    private final List<Task> expectedTasks = Arrays.asList(
        new Task(0, 0, "name", 902231408),
        new Task(1, 1, "name", 902231409)
    );
    LiveData<List<Task>> liveDataTaskList = new MutableLiveData<>(
        expectedTasks
    );
    private Repository repository;
    private final List<Project> expectedProjects = Arrays.asList(repository.getProjectsList().getValue().toArray(new Project[0]));
    LiveData<List<Project>> liveDataProjectList = new MutableLiveData<>(
        expectedProjects
    );

    @Before
    public void setup() {
        when(taskDao.loadTaskList()).thenReturn(liveDataTaskList);
        when(projectDao.loadProjectList()).thenReturn(liveDataProjectList);

        repository = new Repository(taskDao, projectDao);
    }

    @Test
    public void getAllTasksShouldReturnList() {
        assertEquals(liveDataTaskList, repository.getTaskList());
    }

    @Test
    public void getAllProjectsShouldReturnList() {
        assertEquals(liveDataProjectList, repository.getProjectsList());
    }

    @Test
    public void test_projects() {
        final Task task1 = new Task(1, 1, "task 1", 902231408);
        final Task task2 = new Task(2, 2, "task 2", 902231409);
        final Task task3 = new Task(3, 3, "task 3", 902231410);

        //assertEquals("Projet Tartampion", Objects.requireNonNull(task1.getProject()).getName());
        //assertEquals("Projet Lucidia", Objects.requireNonNull(task2.getProject()).getName());
        //assertEquals("Projet Circus", Objects.requireNonNull(task3.getProject()).getName());
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1, 1, "Laver la vaisselle", 902231408);
        final Task task2 = new Task(2, 2, "Ranger le linge", 902231409);
        final Task task3 = new Task(3, 3, "Balayer le sol", 902231410);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task1);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1, 1, "Laver la vaisselle", 902231408);
        final Task task2 = new Task(2, 2, "Ranger le linge", 902231409);
        final Task task3 = new Task(3, 3, "Balayer le sol", 902231410);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task1);
        assertSame(tasks.get(2), task3);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, 1, "Laver la vaisselle", 902231408);
        final Task task2 = new Task(2, 2, "Ranger le linge", 902231409);
        final Task task3 = new Task(3, 3, "Balayer le sol", 902231410);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, 1, "Laver la vaisselle", 902231408);
        final Task task2 = new Task(2, 2, "Ranger le linge", 902231409);
        final Task task3 = new Task(3, 3, "Balayer le sol", 902231410);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}