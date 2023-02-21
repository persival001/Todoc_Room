package com.persival.todoc_room.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.persival.todoc_room.data.Repository;
import com.persival.todoc_room.data.TodocDatabase;
import com.persival.todoc_room.ui.add.AddTaskViewModel;
import com.persival.todoc_room.ui.main.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final Repository repository;

    public ViewModelFactory(Context context) {
        TodocDatabase db = TodocDatabase.getDatabase(context);
        this.repository = new Repository(db.taskDao(), db.projectDao());
    }

    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            factory = new ViewModelFactory(context);
        }


        return factory;
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

