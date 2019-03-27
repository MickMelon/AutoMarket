package com.mickmelon.carshare.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Contains helpful functions for executing intents.
 */
public class IntentHelper {
    public static final int SELECT_PHOTO = 1;

    /**
     * Composes an email and opens the email client ready to be sent.
     * @param activity The calling activity.
     * @param address The email address to send to.
     * @param subject The subject of the email.
     */
    public static void composeEmail(Activity activity, String address, String subject, String message) {
        String[] addresses = new String[] {address};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    /**
     * Makes a phone call to the given number.
     * @param activity The calling activity.
     * @param phoneNumber The number to call.
     */
    public static void makePhoneCall(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    /**
     * Opens a web page with the given URL.
     * @param activity The calling activity.
     * @param url The URL of the web page to be loaded.
     */
    public static void openWebPage(Activity activity, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {

        }
        Uri webPage = Uri.parse("http://" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    public static void pickPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, SELECT_PHOTO);
    }
}
