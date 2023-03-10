package com.persival.todoc_room.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import java.util.List;

public class MainViewModel extends ViewModel {
    public final MutableLiveData<String> filterLiveData = new MutableLiveData<>("OlderFirst");
    private final Repository repository;

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    /**
     * Gets projects list.
     *
     * @return the projects list
     */
    public LiveData<List<Project>> getProjectsList() {
        return repository.getProjectsList();
    }

    /**
     * Delete task.
     *
     * @param taskId the task id
     */
    public void deleteTask(long taskId) {
        repository.deleteTask(taskId);
    }

    /**
     * Gets tasks list.
     *
     * @return the tasks list
     */
    public LiveData<List<Task>> getTasksList() {
        return Transformations.switchMap(filterLiveData, filter -> {
            if (filter == null) {
                return repository.getTaskList();
            } else if (filter.equals("AtoZ")) {
                return repository.getTasksSortedByNameASC();
            } else if (filter.equals("ZtoA")) {
                return repository.getTasksSortedByNameDESC();
            } else if (filter.equals("OlderFirst")) {
                return repository.getTasksSortedByTimeASC();
            } else if (filter.equals("RecentFirst")) {
                return repository.getTasksSortedByTimeDESC();
            } else {
                return repository.getTaskList();
            }
        });
    }

    /**
     * Set a filter type for sort the list
     *
     */
    public void setFilter(String filter) {
        filterLiveData.setValue(filter);
    }
}

