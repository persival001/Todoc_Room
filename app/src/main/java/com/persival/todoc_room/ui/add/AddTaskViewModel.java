package com.persival.todoc_room.ui.add;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import java.util.List;

public class AddTaskViewModel extends ViewModel {
    private Repository repository;
    private final LiveData<List<Project>> projectList;

    public AddTaskViewModel(Repository repository) {
        this.repository = repository;
        projectList = this.repository.getProjectsList();
    }

    public LiveData<List<Project>> getProjectList() {
        return projectList;
    }

    public void addNewTask(Project project, String name, long creationTimestamp) {
        repository.addNewTask(project, name, creationTimestamp);
    }
}


