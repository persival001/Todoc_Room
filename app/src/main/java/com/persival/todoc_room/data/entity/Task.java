package com.persival.todoc_room.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "tasks",
    foreignKeys = @ForeignKey(
        entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId"
    ))
public class Task {



    @PrimaryKey(autoGenerate = true)
    private long id;
    private long projectId;
    private String name;
    private long creationTimestamp;

    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, creationTimestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return id == task.id &&
            projectId == task.projectId &&
            creationTimestamp == task.creationTimestamp &&
            name.equals(task.name);
    }
}
