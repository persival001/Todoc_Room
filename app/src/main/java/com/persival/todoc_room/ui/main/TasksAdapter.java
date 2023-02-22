package com.persival.todoc_room.ui.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.persival.todoc_room.R;
import com.persival.todoc_room.data.entity.Project;
import com.persival.todoc_room.data.entity.Task;
import com.persival.todoc_room.ui.DeleteTaskListener;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    @NonNull
    private final DeleteTaskListener deleteTaskListener;
    @NonNull
    private List<Task> tasksList;
    @NonNull
    private List<Project> projectList;

    public TasksAdapter(@NonNull final List<Task> tasksList,
                        @NonNull final DeleteTaskListener deleteTaskListener,
                        @NonNull final List<Project> projectList
    ) {
        this.tasksList = tasksList;
        this.deleteTaskListener = deleteTaskListener;
        this.projectList = projectList;
    }

    public void updateProjects(@NonNull final List<Project> projectList) {
        this.projectList = projectList;
        notifyDataSetChanged();
    }

    public void updateTasks(@NonNull final List<Task> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        Task task = tasksList.get(position);
        taskViewHolder.bind(task);
        taskViewHolder.imgDelete.setOnClickListener(v -> deleteTaskListener.onDeleteTask(task.getId()));
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imgProject;
        private final TextView lblTaskName;
        private final TextView lblProjectName;
        private final AppCompatImageView imgDelete;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }

        @SuppressLint("RestrictedApi")
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            for (Project project : projectList) {
                if (project.getId() == task.getProjectId()) {
                    imgProject.setSupportImageTintList(ColorStateList.valueOf(project.getColor()));
                    lblProjectName.setText(project.getName());
                }
            }

        }
    }
}