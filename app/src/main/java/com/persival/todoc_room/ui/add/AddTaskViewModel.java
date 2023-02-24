package com.persival.todoc_room.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.entity.Project;

import java.util.List;

public class AddTaskViewModel extends ViewModel {
    private final LiveData<List<Project>> projectList;
    private final Repository repository;

    public AddTaskViewModel(Repository repository) {
        this.repository = repository;
        projectList = this.repository.getProjectsList();
    }

    /**
     * Gets project list.
     *
     * @return the project list
     */
    public LiveData<List<Project>> getProjectList() {
        return projectList;
    }

    /**
     * Add new task.
     *
     * @param project           the project
     * @param name              the name
     * @param creationTimestamp the creation timestamp
     */
    public void addNewTask(Project project, String name, long creationTimestamp) {
        repository.addNewTask(project, name, creationTimestamp);
    }
}


