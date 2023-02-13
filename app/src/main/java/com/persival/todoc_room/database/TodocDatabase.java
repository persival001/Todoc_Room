package com.persival.todoc_room.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.persival.todoc_room.database.dao.ProjectDao;
import com.persival.todoc_room.database.dao.TaskDao;
import com.persival.todoc_room.model.Project;
import com.persival.todoc_room.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {
    public static volatile TodocDatabase sInstance;
    private static final RoomDatabase.Callback DATABASE_CALLBACK =
        new TodocDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDataBaseAsync(sInstance).execute();
            }
        };

    /**
     * Gets database.
     *
     * @param context the context
     * @return the database
     */
    public static TodocDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (TodocDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "task_database")
                        .addCallback(DATABASE_CALLBACK)
                        .build();
                }
            }
        }
        return sInstance;
    }

    /**
     * Gets new database.
     *
     * @param context the context
     * @return the new database
     */
    //TODO : Utile pour les tests si besoin
    public static TodocDatabase getNewDatabase(final Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                TodocDatabase.class)
            .allowMainThreadQueries()
            .addCallback(DATABASE_CALLBACK)
            .build();
        return sInstance;
    }

    /**
     * Task dao task dao.
     *
     * @return the task dao
     */
    public abstract TaskDao taskDao();

    /**
     * Project dao project dao.
     *
     * @return the project dao
     */
    public abstract ProjectDao projectDao();


    private static class PopulateDataBaseAsync extends AsyncTask<Void, Void, Void> {

        private final ProjectDao projectDao;

        /**
         * Instantiates a new Populate data base async.
         *
         * @param todocDatabase the todoc database
         */
        PopulateDataBaseAsync(
            @NonNull TodocDatabase todocDatabase
        ) {
            projectDao = todocDatabase.projectDao();
        }

        /**
         * Refresh the project database
         */
        @Override
        protected Void doInBackground(final Void... params) {
            projectDao.deleteAll();
            Project[] projects = Project.getAllProjects();
            for (Project project : projects) {
                projectDao.insert(project);
            }
            return null;
        }
    }
}
