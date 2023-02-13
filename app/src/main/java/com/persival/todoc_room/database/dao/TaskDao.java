package com.persival.todoc_room.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.persival.todoc_room.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTasks(Task... tasks);

    @Delete
    void deleteTasks(Task... tasks);

    @Query("SELECT * from task_table")
    LiveData<List<Task>> loadTaskList();

    @Query("SELECT * FROM task_table WHERE id = :id LIMIT 1")
    LiveData<Task> loadTaskById(long id);
}
