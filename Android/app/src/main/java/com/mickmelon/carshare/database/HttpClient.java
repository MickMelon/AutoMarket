package com.mickmelon.carshare.database;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.List;

public class HttpClient {
    protected HttpClient() {}

    private static String post(String action, List<AbstractMap.SimpleEntry> params) {
        if (params == null || params.size() < 1) {
            return "ERROR: No parameters";
        }

        String result = null;

        try {
            URL url = new URL("http://192.168.1.8/~michael/cartrader/carshare/Web/index.php?" + action);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            String request = generateRequest(params);
            httpConn.setFixedLengthStreamingMode(request.getBytes().length);

            // Send to API
            OutputStream outputStream = httpConn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(request);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            // Get response
            InputStreamReader inputStreamReader = new InputStreamReader(httpConn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            result = readResult(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String get(String action) {
        String result = null;

        try {
            URL url = new URL("http://192.168.1.8/~michael/cartrader/carshare/Web/index.php?" + action);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            InputStream inputStream = httpConn.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            result = readResult(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String generateRequest(List<AbstractMap.SimpleEntry> params) {
        StringBuilder request = new StringBuilder();

        boolean isFirstParam = true;
        for (AbstractMap.SimpleEntry param : params) {
            if (isFirstParam) {
                isFirstParam = false;
            } else {
                request.append("&");
            }

            try {
                request.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
                request.append("=");
                request.append(URLEncoder.encode((String)param.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return request.toString();
    }

    private static String readResult(BufferedReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append('\n');
        }

        return result.toString();
    }

    public static class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = HttpClient.get(params[0]);

            return result;
        }
    }

    public static class HttpPostAsyncTask extends AsyncTask<PostData, Void, String> {
        @Override
        protected String doInBackground(PostData... params) {
            PostData postData = params[0];
            String result = HttpClient.post(postData.getAction(), postData.getParams());
            return result;
        }
    }
}
