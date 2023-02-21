package com.persival.todoc_room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;
import com.persival.todoc_room.databinding.ActivityMainBinding;
import com.persival.todoc_room.ui.DeleteTaskListener;
import com.persival.todoc_room.ui.add.AddTaskDialogFragment;
import com.persival.todoc_room.ui.main.MainViewModel;
import com.persival.todoc_room.ui.main.TasksAdapter;
import com.persival.todoc_room.utils.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeleteTaskListener {
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    @NonNull
    private List<Task> tasksList = new ArrayList<>();
    @NonNull
    private List<Project> projectList = new ArrayList<>();
    private TasksAdapter tasksAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainViewModel = new ViewModelProvider(
            this, ViewModelFactory.getInstance(
            getApplication())).get(MainViewModel.class);

        tasksAdapter = new TasksAdapter(tasksList, this, projectList);

        initBinding();
        initObserver();
    }

    public void initBinding() {
        binding.listTasks.setLayoutManager(new LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false)
        );
        binding.listTasks.setAdapter(tasksAdapter);
        binding.fabAddTask.setOnClickListener(view -> showAddTaskDialog());
    }

    public void initObserver() {
        mainViewModel.getTasksList().observe(
            this, observedTaskList -> {
                tasksList = observedTaskList;
                updateTasks();
            });

        mainViewModel.getProjectsList().observe(
            this, observedProjectsList -> {
                projectList = observedProjectsList;
                tasksAdapter.updateProjects(projectList);
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter_alphabetical) {
            // mainViewModel.filteredListOfTask("AtoZ");
            return true;
        }
        if (id == R.id.filter_alphabetical_inverted) {
            // mainViewModel.filteredListOfTask("ZtoA");
            return true;
        }
        if (id == R.id.filter_oldest_first) {
            // mainViewModel.filteredListOfTask("OlderFirst");
            return true;
        }
        if (id == R.id.filter_recent_first) {
            // mainViewModel.filteredListOfTask("RecentFirst");
            return true;
        }
        updateTasks();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(long taskId) {
        mainViewModel.deleteTask(taskId);
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        AddTaskDialogFragment addDialogFragment = new AddTaskDialogFragment();
        addDialogFragment.show(getSupportFragmentManager(), "add_task_dialog");
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks() {

        if (tasksList.isEmpty()) {
            binding.lblNoTask.setVisibility(View.VISIBLE);
            binding.listTasks.setVisibility(View.GONE);
        } else {
            binding.lblNoTask.setVisibility(View.GONE);
            binding.listTasks.setVisibility(View.VISIBLE);
            // mainViewModel.filteredListOfTask("ZtoA");
            tasksAdapter.updateTasks(tasksList);
        }
    }
}
