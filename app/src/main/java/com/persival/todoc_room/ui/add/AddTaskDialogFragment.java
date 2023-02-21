package com.persival.todoc_room.ui.add;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.persival.todoc_room.R;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.databinding.DialogAddTaskBinding;
import com.persival.todoc_room.utils.ViewModelFactory;

import java.util.Date;
import java.util.List;

public class AddTaskDialogFragment extends DialogFragment {
    private DialogAddTaskBinding binding;
    private AddTaskViewModel addTaskViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        binding = DialogAddTaskBinding.inflate(requireActivity().getLayoutInflater());
        builder.setView(binding.getRoot());

        addTaskViewModel = new ViewModelProvider(
            this, ViewModelFactory.getInstance(
            getContext())).get(AddTaskViewModel.class);

        initAutoCompletionSpinner();

        builder.setTitle(R.string.add_task);
        builder.setPositiveButton(R.string.add, (dialog, which) -> onPositiveButtonClick(dialog));
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    /**
     * Auto completion for spinner (list of tasks)
     */
    private void initAutoCompletionSpinner() {
        addTaskViewModel.getProjectList().observe(
            this, observedProjectList -> {
                ArrayAdapter<Project> adapter = new ArrayAdapter<>(
                    requireContext(), android.R.layout.simple_spinner_item, observedProjectList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.projectSpinner.setAdapter(adapter);
            });
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (binding.txtTaskName != null && binding.projectSpinner != null) {
            // Get the name of the task
            String taskName = binding.txtTaskName.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (binding.projectSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) binding.projectSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                binding.txtTaskName.setError(getString(R.string.empty_task_name));
            }

            // If both project and name of the task have been set
            else if (taskProject != null) {
                addTaskViewModel.addNewTask((Project) binding.projectSpinner.getSelectedItem(), taskName, new Date().getTime());
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
}

