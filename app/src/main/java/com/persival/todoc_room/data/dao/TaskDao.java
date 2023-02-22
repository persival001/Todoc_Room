package com.persival.todoc_room.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.persival.todoc_room.data.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task... tasks);

    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTask(long taskId);

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTaskList();

    @Query("SELECT * FROM tasks ORDER BY name ASC")
    LiveData<List<Task>> getAllEntriesSortedAZ();

    @Query("SELECT * FROM tasks ORDER BY name DESC")
    LiveData<List<Task>> getAllEntriesSortedZA();

    @Query("SELECT * FROM tasks ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getAllEntriesSortedByTimeASC();

    @Query("SELECT * FROM tasks ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getAllEntriesSortedByTimeDESC();
}
