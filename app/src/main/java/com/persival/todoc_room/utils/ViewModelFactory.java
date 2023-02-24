package com.persival.todoc_room.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.persival.todoc_room.MainApplication;
import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.TodocDatabase;
import com.persival.todoc_room.ui.add.AddTaskViewModel;
import com.persival.todoc_room.ui.main.MainViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(THREADS);
    private static final ViewModelFactory FACTORY = new ViewModelFactory();
    private final Repository repository;

    public ViewModelFactory() {
        TodocDatabase db = TodocDatabase.getDatabase(MainApplication.getApplication(), EXECUTOR);
        this.repository = new Repository(db.taskDao(), db.projectDao(), EXECUTOR);
    }

    /**
     * Gets new instance of ViewModelFactory.
     *
     * @return the instance
     */
    public static ViewModelFactory getInstance() {
        return FACTORY;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel");
    }
}

