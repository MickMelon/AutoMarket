package com.mickmelon.carshare.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mickmelon.carshare.StartupActivity;
import com.mickmelon.carshare.presentation.MainActivity;

/**
 * Helpful functions related to showing activities.
 */
public class ActivityHelper {
    /**
     * Shows an activity.
     * @param context The application context.
     * @param activityClass The activity class e.g. MainActivity.class
     * @param closePreviousActivity Specifies whether to close the previous activity.
     *                              (adds FLAG_ACTIVITY_CLEAR_TASK and FLAG_ACTIVITY_NEW_TASK flags)
     */
    public static void showActivity(Context context, Class activityClass, boolean closePreviousActivity) {
        Intent intent = new Intent(context, activityClass);

        if (closePreviousActivity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    /**
     * Shows an activity with arguments.
     * @param context The application context.
     * @param activityClass The activity class e.g. MainActivity.class
     * @param closePreviousActivity Specifies whether to close the previous Activity.
     *                              (adds FLAG_ACTIVITY_CLEAR_TASK and FLAG_ACTIVITY_NEW_TASK flags)
     * @param args The arguments to be passed to the newly created activity.
     */
    public static void showActivity(Context context, Class activityClass, boolean closePreviousActivity, Bundle args) {
        Intent intent = new Intent(context, activityClass);

        if (closePreviousActivity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.putExtras(args);
        context.startActivity(intent);
    }

    /**
     * Shows the MainActivity
     * @param context The application context.
     */
    public static void showMainActivity(Context context) {
        showActivity(context, MainActivity.class, true);
    }
}
