package com.persival.todoc_room.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class Repository {
    @NonNull
    private final TaskDao taskDao;
    @NonNull
    private final ProjectDao projectDao;
    private final Executor executor;

    public Repository(@NonNull TaskDao taskDao, @NonNull ProjectDao projectDao, Executor executor) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
        this.executor = executor;
    }

    @NonNull
    public LiveData<List<Task>> getTaskList() {
        return taskDao.getTaskList();
    }

    @NonNull
    public LiveData<List<Project>> getProjectsList() {
        return projectDao.getProjectList();
    }

    public void deleteTask(long taskId) {
        executor.execute(() ->
            taskDao.deleteTask(taskId));
    }

    public void addNewTask(Project project, String name, long creationTimestamp) {
        Task task = new Task(0, project.getId(), name, creationTimestamp);
        executor.execute(() ->
            taskDao.insertTask(task));
    }

    @NonNull
    public LiveData<List<Task>> getTasksSortedByNameASC() {
        return taskDao.getAllEntriesSortedAZ();
    }

    @NonNull
    public LiveData<List<Task>> getTasksSortedByNameDESC() {
        return taskDao.getAllEntriesSortedZA();
    }

    @NonNull
    public LiveData<List<Task>> getTasksSortedByTimeASC() {
        return taskDao.getAllEntriesSortedByTimeASC();
    }

    @NonNull
    public LiveData<List<Task>> getTasksSortedByTimeDESC() {
        return taskDao.getAllEntriesSortedByTimeDESC();
    }
}
