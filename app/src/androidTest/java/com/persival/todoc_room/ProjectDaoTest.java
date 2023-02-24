package com.persival.todoc_room;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.persival.todoc_room.data.TodocDatabase;
import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.utils.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private ProjectDao projectDao;
    private TodocDatabase database;

    @Before
    public void createDataBase() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        projectDao = database.projectDao();
    }

    @After
    public void closeDataBase() {
        database.close();
    }

    @Test
    public void addAllProjects() {
        Project project1 = new Project(0, "Projet Tartampion", 0xFFEADAD1);
        Project project2 = new Project(0, "Projet Lucidia", 0xFFB4CDBA);
        Project project3 = new Project(0, "Projet Circus", 0xFFA3CED2);

        projectDao.insert(project1);
        projectDao.insert(project2);
        projectDao.insert(project3);

        List<Project> result = TestUtil.getValueForTesting(projectDao.getProjectList());
        assertEquals(3, result.size());
    }
}
