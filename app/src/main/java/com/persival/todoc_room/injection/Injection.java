package com.persival.todoc_room.injection;

import android.app.Application;

import com.persival.todoc_room.database.TodocDatabase;
import com.persival.todoc_room.repository.Repository;

public class Injection {
    private static Repository sRepository;

    private Injection() {
        throw new IllegalStateException("Unknown Injection class");
    }

    /**
     * Gets task repository
     *
     * @param application the application
     * @return the task sRepository
     */
    public static Repository getTaskRepository(Application application) {
        if (sRepository == null) {
            TodocDatabase todocDatabase = TodocDatabase.getDatabase(application);
            sRepository = new Repository(todocDatabase.taskDao(), todocDatabase.projectDao());
        }
        return sRepository;
    }
}

