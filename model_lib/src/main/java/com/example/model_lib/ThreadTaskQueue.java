package com.example.model_lib;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by takahiro on 2015/06/29.
 */
public class ThreadTaskQueue {
    // A managed pool of threads
    private final ThreadPoolExecutor taskThreadPool;

    // Gets the number of available cores
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // A queue of Runnables for the pool
    private final BlockingQueue<Runnable> poolWorkQueue;

    private static ThreadTaskQueue instance = new ThreadTaskQueue();

    private ThreadTaskQueue() {


        poolWorkQueue = new LinkedBlockingQueue<Runnable>();

        // Creates a thread pool manager
        taskThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                poolWorkQueue);
    }

    /**
     * Returns the TaskQueue object
     *
     * @return The global TaskQueue object
     */
    public static ThreadTaskQueue getInstance() {
        return instance;
    }

    public void addTask(Runnable r) {
        taskThreadPool.execute(r);
    }
}
