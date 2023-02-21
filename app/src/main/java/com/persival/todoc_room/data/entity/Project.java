package com.persival.todoc_room.data.entity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "projects")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private final long id;
    @NonNull
    private final String name;
    @ColorInt
    private final int color;

    public Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Project project = (Project) o;
        return id == project.id &&
            color == project.color &&
            name.equals(project.name);
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
