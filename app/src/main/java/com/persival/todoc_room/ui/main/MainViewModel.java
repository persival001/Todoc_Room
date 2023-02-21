package com.persival.todoc_room.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<Task>> tasksList;
    private final LiveData<List<Project>> projectsList;

    public MainViewModel(Repository repository) {
        this.repository = repository;
        tasksList = this.repository.getTaskList();
        projectsList = this.repository.getProjectsList();
    }

    public LiveData<List<Task>> getTasksList() {
        return tasksList;
    }

    public LiveData<List<Project>> getProjectsList() {
        return projectsList;
    }

    public void deleteTask(long taskId) {
        repository.deleteTask(taskId);
    }

    /*public LiveData<List<ProjectWithTasks>> filteredListOfTask(String filterType) {
        if (Objects.equals(filterType, "AtoZ")) {
            return repository.filteredAllEntriesSortedAZ();
        }
        if (Objects.equals(filterType, "ZtoA")) {
            return repository.filteredAllEntriesSortedZA();
        }
        if (Objects.equals(filterType, "OlderFirst")) {
            return repository.filteredAllEntriesSortedOlderFirst();
        }
        if (Objects.equals(filterType, "RecentFirst")) {
            return repository.filteredAllEntriesSortedRecentFirst();
        }
        return projectWithTasksList;
    }*/
}

