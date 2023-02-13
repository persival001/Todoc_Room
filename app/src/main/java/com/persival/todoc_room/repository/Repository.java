package com.persival.todoc_room.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.persival.todoc_room.database.dao.ProjectDao;
import com.persival.todoc_room.database.dao.TaskDao;
import com.persival.todoc_room.model.Project;
import com.persival.todoc_room.model.Task;

import java.util.List;

public class Repository {
    @NonNull
    private final TaskDao taskDao;
    @NonNull
    private final LiveData<List<Task>> tasksList;
    @NonNull
    private final LiveData<List<Project>> projectsList;

    public Repository(
        @NonNull TaskDao taskDao,
        @NonNull ProjectDao projectDao
    ) {
        this.taskDao = taskDao;
        tasksList = taskDao.loadTaskList();
        projectsList = projectDao.loadProjectList();
    }

    @NonNull
    public LiveData<List<Task>> getTasksList() {
        return tasksList;
    }

    public void deleteTask(Task task) {
        new deleteAsyncTask(taskDao).execute(task);
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        Task task = new Task(0, projectId, name, creationTimestamp);
        new insertAsyncTask(taskDao).execute(task);
    }

    @NonNull
    public LiveData<List<Project>> getProjectsList() {
        return projectsList;
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private final TaskDao asyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            asyncTaskDao.insertTasks(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao asyncTaskDao;

        deleteAsyncTask(TaskDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            asyncTaskDao.deleteTasks(params[0]);
            return null;
        }
    }
}
