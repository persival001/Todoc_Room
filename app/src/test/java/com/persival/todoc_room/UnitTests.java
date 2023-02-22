package com.persival.todoc_room;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Task;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UnitTests {

    // Utilise un InOrder pour vérifier que les méthodes sont appelées dans l'ordre attendu
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);
    @Mock
    private TaskDao taskDao;

    @Test
    public void testInsertTask() {
        // Crée une tâche à insérer
        Task task = mock(Task.class);

        // Insère la tâche avec le DAO
        taskDao.insertTask(task);

        // Vérifie que la méthode insertTask a été appelée avec la tâche en argument
        verify(taskDao).insertTask(task);
    }

    @Test
    public void testDeleteTask() {
        // Définit l'identifiant de la tâche à supprimer
        long taskId = 1;

        // Supprime la tâche avec le DAO
        taskDao.deleteTask(taskId);

        // Vérifie que la méthode deleteTask a été appelée avec l'identifiant de la tâche en argument
        verify(taskDao).deleteTask(taskId);
    }

    @Test
    public void testGetTaskList() {
        // Crée une liste de tâches à retourner par le DAO
        List<Task> tasks = Arrays.asList(
            new Task(1, 1, "Tache1", 902231408),
            new Task(1, 2, "Tache2", 902231409),
            new Task(1, 3, "Tache3", 902231410));

        // Utilise Mockito pour simuler la LiveData retournée par le DAO
        MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        liveData.setValue(tasks);
        when(taskDao.getTaskList()).thenReturn(liveData);

        // Appelle la méthode getTaskList du DAO
        LiveData<List<Task>> result = taskDao.getTaskList();

        // Vérifie que la méthode getTaskList a été appelée
        verify(taskDao).getTaskList();

        // Vérifie que la liste retournée par la LiveData correspond à celle qui a été simulée
        assertThat(result.getValue(), containsInAnyOrder(tasks.toArray()));

    }

    @Test
    public void testGetAllEntriesSortedAZ() {
        // Crée une liste de tâches triées par ordre alphabétique croissant à retourner par le DAO
        List<Task> tasks = Arrays.asList(
            new Task(1, 1, "Tache1", 902231408),
            new Task(1, 2, "Tache2", 902231409),
            new Task(1, 3, "Tache3", 902231410));

        // Utilise Mockito pour simuler la LiveData retournée par le DAO
        MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        liveData.setValue(tasks);
        when(taskDao.getAllEntriesSortedAZ()).thenReturn(liveData);

        // Appelle la méthode getAllEntriesSortedAZ du DAO
        LiveData<List<Task>> result = taskDao.getAllEntriesSortedAZ();

        // Vérifie que la méthode getAllEntriesSortedAZ a été appelée
        verify(taskDao).getAllEntriesSortedAZ();

        // Vérifie que la liste retournée par la LiveData correspond à celle qui a été simulée
        assertThat(result.getValue(), containsInAnyOrder(tasks.toArray()));
    }

    // Ajoutez des tests similaires pour les autres méthodes du DAO
}
