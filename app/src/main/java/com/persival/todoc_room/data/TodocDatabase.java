package com.persival.todoc_room.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.persival.todoc_room.data.dao.ProjectDao;
import com.persival.todoc_room.data.dao.TaskDao;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
        Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile TodocDatabase sInstance;
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ProjectDao dao = sInstance.projectDao();
                dao.deleteAll();
                dao.insert(new Project(0, "Projet Tartampion", 0xFFEADAD1));
                dao.insert(new Project(0, "Projet Lucidia", 0xFFB4CDBA));
                dao.insert(new Project(0, "Projet Circus", 0xFFA3CED2));
            });
        }
    };

    public static TodocDatabase getDatabase(final Application application, final Executor executor) {
        if (sInstance == null) {
            synchronized (TodocDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(application,
                            TodocDatabase.class, "task_database").addCallback(sRoomDatabaseCallback)
                        .build();
                }
            }
        }
        return sInstance;
    }

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();
}