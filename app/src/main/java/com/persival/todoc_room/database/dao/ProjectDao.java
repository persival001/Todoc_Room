package com.persival.todoc_room.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.persival.todoc_room.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Query("SELECT * from project_table")
    LiveData<List<Project>> loadProjectList();
}
