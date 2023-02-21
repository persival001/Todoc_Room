package com.persival.todoc_room.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import java.util.List;

public class Repository {
    @NonNull
    private final TaskDao taskDao;
    @NonNull
    private final ProjectDao projectDao;

    public Repository(@NonNull TaskDao taskDao, @NonNull ProjectDao projectDao) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
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
        TodocDatabase.databaseWriteExecutor.execute(() ->
            taskDao.deleteTask(taskId));
    }

    public void addNewTask(Project project, String name, long creationTimestamp) {
        Task task = new Task(0, project.getId(), name, creationTimestamp);
        TodocDatabase.databaseWriteExecutor.execute(() ->
            taskDao.insertTask(task));
    }

    @NonNull
    public LiveData<List<Task>> filteredAllEntriesSortedAZ() {
        return taskDao.getAllEntriesSortedAZ();
    }

    @NonNull
    public LiveData<List<Task>> filteredAllEntriesSortedZA() {
        return taskDao.getAllEntriesSortedZA();
    }

    @NonNull
    public LiveData<List<Task>> filteredAllEntriesSortedOlderFirst() {
        return taskDao.getAllEntriesSortedByTimeASC();
    }

    @NonNull
    public LiveData<List<Task>> filteredAllEntriesSortedRecentFirst() {
        return taskDao.getAllEntriesSortedByTimeDESC();
    }
}
