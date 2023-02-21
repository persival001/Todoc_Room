package com.persival.todoc_room.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.persival.todoc_room.data.entity.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Query("DELETE FROM projects")
    void deleteAll();

    @Query("SELECT * from projects")
    LiveData<List<Project>> getProjectList();
}
