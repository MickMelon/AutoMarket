package com.mickmelon.carshare.database;

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
    static URL url;
    static HttpURLConnection conn = null;

    public static boolean connect() {

        try {
            url = new URL("http://localhost/~michael/cartrader/carshare/Web/index.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (Exception ex) {
            return false;
        } finally {
            if (conn != null) conn.disconnect();
        }

        return true;
    }

    public static String post(List<AbstractMap.SimpleEntry> params) {
        if (params == null || params.size() < 1) {
            return "ERROR: No parameters";
        }

        String response = "ERROR: No response"; // Value gets assigned to if there is response

        String request = generateRequest(params);
        conn.setFixedLengthStreamingMode(request.getBytes().length);

        try {
            OutputStream stream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
            writer.write(request);
            writer.close();
            stream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = readResult(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String get(String request) {
        String result = "ERROR: Could not get a result.";

        try {
            URL url = new URL("http://192.168.1.8/~michael/cartrader/carshare/Web/index.php?" + request);
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
}
