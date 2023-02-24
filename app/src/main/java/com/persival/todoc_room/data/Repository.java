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

    /**
     * Gets task list.
     *
     * @return the task list
     */
    @NonNull
    public LiveData<List<Task>> getTaskList() {
        return taskDao.getTaskList();
    }

    /**
     * Gets projects list.
     *
     * @return the projects list
     */
    @NonNull
    public LiveData<List<Project>> getProjectsList() {
        return projectDao.getProjectList();
    }

    /**
     * Delete task.
     *
     * @param taskId the task id
     */
    public void deleteTask(long taskId) {
        executor.execute(() ->
            taskDao.deleteTask(taskId));
    }

    /**
     * Add new task.
     *
     * @param project           the project
     * @param name              the name
     * @param creationTimestamp the creation timestamp
     */
    public void addNewTask(Project project, String name, long creationTimestamp) {
        Task task = new Task(0, project.getId(), name, creationTimestamp);
        executor.execute(() ->
            taskDao.insertTask(task));
    }

    /**
     * Gets tasks sorted by name asc.
     *
     * @return the tasks sorted by name asc
     */
    @NonNull
    public LiveData<List<Task>> getTasksSortedByNameASC() {
        return taskDao.getAllEntriesSortedAZ();
    }

    /**
     * Gets tasks sorted by name desc.
     *
     * @return the tasks sorted by name desc
     */
    @NonNull
    public LiveData<List<Task>> getTasksSortedByNameDESC() {
        return taskDao.getAllEntriesSortedZA();
    }

    /**
     * Gets tasks sorted by time asc.
     *
     * @return the tasks sorted by time asc
     */
    @NonNull
    public LiveData<List<Task>> getTasksSortedByTimeASC() {
        return taskDao.getAllEntriesSortedByTimeASC();
    }

    /**
     * Gets tasks sorted by time desc.
     *
     * @return the tasks sorted by time desc
     */
    @NonNull
    public LiveData<List<Task>> getTasksSortedByTimeDESC() {
        return taskDao.getAllEntriesSortedByTimeDESC();
    }
}
