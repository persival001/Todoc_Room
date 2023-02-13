package com.persival.todoc_room.ui.add;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.persival.todoc_room.R;
import com.persival.todoc_room.databinding.DialogAddTaskBinding;
import com.persival.todoc_room.model.Project;

import java.util.ArrayList;
import java.util.List;

public class AddTaskDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    private DialogAddTaskBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        binding = DialogAddTaskBinding.inflate(getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setView(R.layout.dialog_add_task);
        builder.setTitle(R.string.add_task);
        builder.setMessage("This is a message displayed in the Dialog Fragment");

        initAutoCompletionSpinner();

        builder.setPositiveButton(R.string.add, (dialog, which) -> onPositiveButtonClick(dialog));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    /**
     * Auto completion for spinner (list of tasks)
     */
    private void initAutoCompletionSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
            R.array.projects_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.projectSpinner.setAdapter(adapter);
        binding.projectSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String projectSelected = adapterView.getItemAtPosition(position).toString();
        //String project = meetingViewModel.getRoomString(projectSelected);
        //onClickOkButton(project);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
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
                //addTask(taskProject.getId(), taskName, new Date().getTime());
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

