package com.mickmelon.carshare.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mickmelon.carshare.StartupActivity;
import com.mickmelon.carshare.presentation.MainActivity;

public class ActivityHelper {
    public static void showActivity(Context context, Class activityClass) {
        context.startActivity(new Intent(context, activityClass)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void showActivity(Context context, Class activityClass, Bundle args) {
        Intent intent = new Intent(context, activityClass)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtras(args);
        context.startActivity(intent);
    }

    public static void showMainActivity(Context context) {
        showActivity(context, MainActivity.class);
    }

    public static void showStartupActivity(Context context) {
        showActivity(context, StartupActivity.class);
    }
}
