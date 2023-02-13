package com.persival.todoc_room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.persival.todoc_room.databinding.ActivityMainBinding;
import com.persival.todoc_room.model.Project;
import com.persival.todoc_room.model.Task;
import com.persival.todoc_room.ui.add.AddTaskDialogFragment;
import com.persival.todoc_room.ui.main.MainViewModel;
import com.persival.todoc_room.ui.main.TasksAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    @Nullable
    public AlertDialog dialog = null;
    private ActivityMainBinding binding;
    @NonNull
    private List<Project> projectList = new ArrayList<>();
    @NonNull
    private List<Task> taskList = new ArrayList<>();
    private final TasksAdapter tasksAdapter = new TasksAdapter(taskList, this);
    @Nullable
    private EditText dialogEditText = null;
    @Nullable
    private Spinner dialogSpinner = null;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        mainViewModel = new ViewModelProvider(
            this).get(MainViewModel.class
        );
        mainViewModel.getProjectList().observe(
            this, observedProjectList ->
                this.projectList = observedProjectList
        );
        mainViewModel.getTasksList().observe(
            this, observedTaskList -> {
                taskList = observedTaskList;
                updateTasks();
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
        mainViewModel.filterItemSelected(id);
        updateTasks();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        mainViewModel.deleteTask(task);
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                addTask(taskProject.getId(), taskName, new Date().getTime());
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        //AddTaskDialogFragment addDialogFragment = new AddTaskDialogFragment();
        //addDialogFragment.show(getSupportFragmentManager(), "add_task_dialog");
        final AlertDialog alertDialog = getAddTaskDialog();

        assert dialog != null;
        dialog.show();

        dialogEditText = alertDialog.findViewById(R.id.txt_task_name);
        dialogSpinner = alertDialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Adds the given task to the list of created tasks.
     *
     * @param projectId         the id of the associated project
     * @param name              the name of the task
     * @param creationTimestamp the timestamp of the creation of the task
     */
    private void addTask(long projectId, @NonNull String name, long creationTimestamp) {
        mainViewModel.addNewTask(projectId, name, creationTimestamp);
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks() {

        if (taskList.isEmpty()) {
            binding.lblNoTask.setVisibility(View.VISIBLE);
            binding.listTasks.setVisibility(View.GONE);
        } else {
            binding.lblNoTask.setVisibility(View.GONE);
            binding.listTasks.setVisibility(View.VISIBLE);
            mainViewModel.filterTheList();
            tasksAdapter.updateTasks(taskList);
        }
    }

    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
       final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projectList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(arrayAdapter);
        }
    }
}
