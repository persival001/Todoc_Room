package com.persival.todoc_room.utils;

import java.util.concurrent.Executor;

public class TestExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}