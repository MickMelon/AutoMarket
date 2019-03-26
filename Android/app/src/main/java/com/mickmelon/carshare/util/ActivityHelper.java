package com.mickmelon.carshare.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mickmelon.carshare.StartupActivity;
import com.mickmelon.carshare.presentation.MainActivity;

public class ActivityHelper {
    public static void showActivity(Context context, Class activityClass, boolean closePreviousActivity) {
        Intent intent = new Intent(context, activityClass);

        if (closePreviousActivity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    public static void showActivity(Context context, Class activityClass, boolean closePreviousActivity, Bundle args) {
        Intent intent = new Intent(context, activityClass);

        if (closePreviousActivity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.putExtras(args);
        context.startActivity(intent);
    }

    public static void showMainActivity(Context context) {
        showActivity(context, MainActivity.class, true);
    }

    public static void showStartupActivity(Context context) {
        showActivity(context, StartupActivity.class, true);
    }
}
