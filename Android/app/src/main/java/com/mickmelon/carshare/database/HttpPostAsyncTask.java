package com.mickmelon.carshare.database;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpPostAsyncTask extends AsyncTask<String, Void, Void> {
    private JSONObject _postData;

    public HttpPostAsyncTask(Map<String, String> postData) {
        if (postData != null) {
            _postData = new JSONObject(postData);
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");

            if (_postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(_postData.toString());
                writer.flush();
            }

            int statusCode = conn.getResponseCode();

            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                String response = convertInputStreamToString(inputStream);

                // Convert the fucking response to JSON (Shit tutorial)
            } else {
                // Error
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String convertInputStreamToString(InputStream inputStream) {
        StringBuilder result = new StringBuilder();

        //while (inputstream.)
        return "";
    }

    private static String readResult(BufferedReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append('\n');
        }

        return result.toString();
    }
}
