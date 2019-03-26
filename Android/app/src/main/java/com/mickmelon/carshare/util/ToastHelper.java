package com.mickmelon.carshare.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Helpful functions related to Toasts.
 */
public class ToastHelper {
    /**
     * Shows a toast.
     * @param context The application context.
     * @param text The text to display.
     */
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a toast.
     * @param context The application context.
     * @param text The text to display.
     * @param duration How long to display the toast for.
     */
    public static void showToast(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
