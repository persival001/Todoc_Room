package com.persival.todoc_room;

import static com.persival.todoc_room.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;
import com.persival.todoc_room.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    private final List<Project> projects = Arrays.asList(
        new Project(1, "Project 1", 0xFF0000),
        new Project(2, "Project 2", 0x00FF00),
        new Project(3, "Project 3", 0x0000FF)
    );
    private final List<Task> tasks = Arrays.asList(
        new Task(1, 1, "Laver", 1),
        new Task(2, 2, "Ranger", 2),
        new Task(3, 3, "Nettoyer", 3)
    );
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private Repository repository = mock(Repository.class);
    @Mock
    private TaskDao taskDao = mock(TaskDao.class);
    @Mock
    private ProjectDao projectDao = mock(ProjectDao.class);

    private MutableLiveData<List<Project>> projectLiveData;
    private MutableLiveData<List<Task>> taskLiveData;

    @Before
    public void setup() {
        repository = new Repository(taskDao, projectDao, new TestExecutor());
        projectLiveData = new MutableLiveData<>();
        taskLiveData = new MutableLiveData<>(tasks);
        projectLiveData.setValue(projects);
    }

    /**
     * Test get projects list.
     */
    @Test
    public void testGetProjectsList() {
        when(projectDao.getProjectList()).thenReturn(projectLiveData);

        List<Project> result = getValueForTesting(repository.getProjectsList());

        assertNotNull(result);
        assertEquals(projects, result);
    }

    /**
     * Test delete task.
     */
    @Test
    public void verify_deleteTask() {
        long taskID = 0L;

        repository.deleteTask(taskID);

        verify(taskDao).deleteTask(taskID);
        verifyNoMoreInteractions(taskDao);
    }


    /**
     * Test get task list.
     */
    @Test
    public void testGetTaskList() {
        when(repository.getTaskList()).thenReturn(taskLiveData);

        List<Task> result = getValueForTesting(repository.getTaskList());

        assertNotNull(result);
        assertEquals(tasks, result);
    }

    /**
     * Test add new task.
     */
    @Test
    public void testAddNewTask() {
        Task task = new Task(0, 1, "Laver", 1);
        Project project = new Project(1, "Ménage", Color.RED);

        repository.addNewTask(project, "Laver", 1);

        verify(taskDao).insertTask(task);
        verifyNoMoreInteractions(taskDao);
    }
}
