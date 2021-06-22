package com.am.testerpointone;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

public class MyWorker extends Worker {
    private static final String TAG = "123ab21abcmywer";

    public MyWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Performing long running task in scheduled job");
        return null;
    }
}
