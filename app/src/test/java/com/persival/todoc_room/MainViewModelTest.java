package com.persival.todoc_room;

import static com.persival.todoc_room.utils.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;
import com.persival.todoc_room.ui.add.AddTaskViewModel;
import com.persival.todoc_room.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

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

    private MainViewModel viewModel;
    private AddTaskViewModel addTaskViewModel;
    private MutableLiveData<List<Project>> projectLiveData;
    private MutableLiveData<List<Task>> taskLiveData;

    @Before
    public void setup() {
        viewModel = new MainViewModel(repository);
        addTaskViewModel = new AddTaskViewModel(repository);
        projectLiveData = new MutableLiveData<>();
        taskLiveData = new MutableLiveData<>(tasks);
        projectLiveData.setValue(projects);
    }

    /**
     * Test get projects list.
     */
    @Test
    public void testGetProjectsList() {
        when(repository.getProjectsList()).thenReturn(projectLiveData);

        List<Project> result = getValueForTesting(viewModel.getProjectsList());

        assertNotNull(result);
        assertEquals(projects, result);
    }

    /**
     * Test delete task.
     */
    @Test
    public void testDeleteTask() {
        long taskId = 1L;

        viewModel.deleteTask(taskId);

        verify(repository).deleteTask(taskId);
    }

    /**
     * Test get task list.
     */
    @Test
    public void testGetTaskList() {
        when(repository.getTaskList()).thenReturn(taskLiveData);

        viewModel.setFilter("");
        List<Task> result = getValueForTesting(viewModel.getTasksList());

        assertNotNull(result);
        assertEquals(tasks, result);
    }

    /**
     * Test add new task.
     */
    @Test
    public void testAddNewTask() {
        Project project = new Project(1, "Project 1", 0xFF0000);
        String name = "Task 1";
        long timestamp = System.currentTimeMillis();

        addTaskViewModel.addNewTask(project, name, timestamp);

        verify(repository).addNewTask(project, name, timestamp);
    }
}



