package com.persival.todoc_room.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.persival.todoc_room.R;
import com.persival.todoc_room.injection.Injection;
import com.persival.todoc_room.model.Project;
import com.persival.todoc_room.model.Task;
import com.persival.todoc_room.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<Task>> tasksList;
    private final LiveData<List<Project>> projectList;
    @NonNull
    private List<Task> taskList = new ArrayList<>();
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    public MainViewModel(Application application) {
        super(application);
        repository = Injection.getTaskRepository(application);
        tasksList = repository.getTasksList();
        projectList = repository.getProjectsList();
    }

    public LiveData<List<Task>> getTasksList() {
        return tasksList;
    }

    public LiveData<List<Project>> getProjectList() {
        return projectList;
    }

    public void deleteTask(Task task) {
        repository.deleteTask(task);
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        repository.addNewTask(projectId, name, creationTimestamp);
    }

    public void filterItemSelected(int id) {
        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }
    }

    public void filterTheList() {
        List<Task> listToCompare = getTasksList().getValue();
        switch (sortMethod) {
            case ALPHABETICAL:
                assert listToCompare != null;
                Collections.sort(listToCompare, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                assert listToCompare != null;
                Collections.sort(listToCompare, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                assert listToCompare != null;
                Collections.sort(listToCompare, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                assert listToCompare != null;
                Collections.sort(listToCompare, new Task.TaskOldComparator());
                break;
            default:
                assert listToCompare != null;
                Collections.addAll(listToCompare);
                break;
        }
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

}
