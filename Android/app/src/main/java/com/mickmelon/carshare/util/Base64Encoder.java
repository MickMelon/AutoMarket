package com.mickmelon.carshare.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class Base64Encoder {
    public static String encode(Bitmap bitmap) {
        Bitmap imageX = bitmap;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageX.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);

        Log.e("LOOK", encoded);
        return encoded;
    }

    public static Bitmap decode(String input) {
        byte[] bytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
