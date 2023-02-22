package com.persival.todoc_room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class FiltersUnitTest {

    @Mock
    private TaskDao taskDao;

    @Test
    public void testGetAllEntriesSortedAZ() {
        List<Task> tasks = Arrays.asList(
            new Task(0, 1, "A - Tache1", 902231408),
            new Task(0, 2, "Z - Tache2", 902231409),
            new Task(0, 3, "X - Tache3", 902231410)
        );

        MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        liveData.setValue(tasks);
        when(taskDao.getAllEntriesSortedAZ()).thenReturn(liveData);

        LiveData<List<Task>> result = taskDao.getAllEntriesSortedAZ();

        verify(taskDao).getAllEntriesSortedAZ();

        List<Task> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, Comparator.comparing(Task::getName));
        assertThat(result.getValue()).containsExactly(sortedTasks.toArray(new Task[0]));
    }

    @Test
    public void testGetAllEntriesSortedZA() {
        List<Task> tasks = Arrays.asList(
            new Task(0, 1, "A - Tache1", 902231408),
            new Task(0, 2, "Z - Tache2", 902231409),
            new Task(0, 3, "X - Tache3", 902231410)
        );

        MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        liveData.setValue(tasks);
        when(taskDao.getAllEntriesSortedZA()).thenReturn(liveData);

        LiveData<List<Task>> result = taskDao.getAllEntriesSortedZA();

        verify(taskDao).getAllEntriesSortedZA();

        List<Task> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, Comparator.comparing(Task::getName).reversed());
        assertThat(result.getValue()).containsExactly(sortedTasks.toArray(new Task[0]));
    }

    @Test
    public void testGetAllEntriesSortedByTimeASC() {
        List<Task> tasks = Arrays.asList(
            new Task(0, 1, "Tache1", 902231408),
            new Task(0, 2, "Tache2", 902231409),
            new Task(0, 3, "Tache3", 902231410)
        );

        MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        liveData.setValue(tasks);
        when(taskDao.getAllEntriesSortedByTimeASC()).thenReturn(liveData);

        LiveData<List<Task>> result = taskDao.getAllEntriesSortedByTimeASC();

        verify(taskDao).getAllEntriesSortedByTimeASC();

        List<Task> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, Comparator.comparing(Task::getCreationTimestamp));
        assertThat(result.getValue()).containsExactly(sortedTasks.toArray(new Task[0]));
    }

    @Test
    public void testGetAllEntriesSortedByTimeDESC() {
        List<Task> tasks = Arrays.asList(
            new Task(0, 1, "Tache1", 902231408),
            new Task(0, 2, "Tache2", 902231409),
            new Task(0, 3, "Tache3", 902231410)
        );

        MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        liveData.setValue(tasks);
        when(taskDao.getAllEntriesSortedByTimeDESC()).thenReturn(liveData);

        LiveData<List<Task>> result = taskDao.getAllEntriesSortedByTimeDESC();

        verify(taskDao).getAllEntriesSortedByTimeDESC();

        List<Task> sortedTasks = new ArrayList<>(tasks);

        Collections.sort(sortedTasks, Comparator.comparing(Task::getCreationTimestamp).reversed());
        assertThat(result.getValue()).containsExactly(sortedTasks.toArray(new Task[0]));
    }
}
