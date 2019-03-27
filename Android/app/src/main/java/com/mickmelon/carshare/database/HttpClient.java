package com.mickmelon.carshare.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.List;

/**
 * The HttpClient used to make Http requests.
 */
public class HttpClient {
    private HttpClient() {}

    /**
     * Used to make a HttpGet request.
     */
    public static class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            String result = HttpClient.get(params[0]);

            return result;
        }
    }

    /**
     * Used to make a HttpPost request.
     */
    public static class HttpPostAsyncTask extends AsyncTask<PostData, Void, HttpResult> {
        @Override
        protected HttpResult doInBackground(PostData... params) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            PostData postData = params[0];
            HttpResult httpResult = HttpClient.post(postData.getUrl(), postData.getParams());
            return httpResult;
        }
    }

    public static class HttpGetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];

            Bitmap bitmap = getImage(url);

            return bitmap;
        }
    }

    public static class HttpPostImageAsyncTask extends AsyncTask<PostData, Void, HttpResult> {
        @Override
        protected HttpResult doInBackground(PostData... params) {
            PostData postData = params[0];
            Bitmap bitmap = (Bitmap) postData.getParams().get(0).getValue();
            HttpResult httpResult = HttpClient.postImage(postData.getUrl(), bitmap);
            return httpResult;
        }
    }

    private static Bitmap getImage(String imageUrl) {
        Bitmap bitmap = null;

        try {
            InputStream inputStream = new java.net.URL(imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static HttpResult postImage(String textUrl, Bitmap bitmap) {
        HttpResult httpResult = null;
        String attachmentName = "bitmap";
        String attachmentFileName = "bitmap.bmp";
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream outputStream = new DataOutputStream(httpConn.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + crlf);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + attachmentFileName + "\"" + crlf);
            outputStream.writeBytes(crlf);

            byte[] pixels = convertBitmapToBytes(bitmap);
            outputStream.write(pixels);

            outputStream.writeBytes(crlf);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

            outputStream.flush();
            outputStream.close();

            InputStream inputStream = new BufferedInputStream(httpConn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int responseCode = httpConn.getResponseCode();
            String result = readResult(bufferedReader);
            httpResult = new HttpResult(result, responseCode);

            inputStream.close();
            httpConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpResult;
    }

    private static byte[] convertBitmapToBytes(Bitmap bitmap) {
        byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        for (int i = 0; i < bitmap.getWidth(); ++i) {
            for (int j = 0; j < bitmap.getHeight(); ++j) {
                //we're interested only in the MSB of the first byte,
                //since the other 3 bytes are identical for B&W images
                pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
            }
        }

        return pixels;
    }

    /**
     * Makes a Post request.
     * @param textUrl The action (i.e. c=seller&a=create)
     * @param params The POST parameters to be sent with the request.
     * @return The request response.
     */
    private static HttpResult post(String textUrl, List<AbstractMap.SimpleEntry> params) {
        if (params == null || params.size() < 1) {
            return new HttpResult("ERROR: No parameters", 400);
        }

        //String result = null;
        HttpResult httpResult = null;

        try {
            URL url = new URL(textUrl);
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
            String result = readResult(bufferedReader);
            int responseCode = httpConn.getResponseCode();
            httpResult = new HttpResult(result, responseCode);

            httpConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpResult;
    }

    /**
     * Makes a GET request.
     * @param textUrl The action (i.e. c=seller&a=read&id=1)
     * @return The request response.
     */
    private static String get(String textUrl) {
        String result = null;

        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            InputStream inputStream = httpConn.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            result = readResult(bufferedReader);

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Generates a request for the given params.
     * @param params the POST params.
     * @return The request string ready to send in POST.
     */
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
                request.append(URLEncoder.encode(param.getKey().toString(), "UTF-8"));
                request.append("=");
                request.append(URLEncoder.encode(param.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return request.toString();
    }

    /**
     * Reads a request result.
     * @param reader The BufferedReader from the request.
     * @return The request result.
     */
    private static String readResult(BufferedReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append('\n');
        }

        return result.toString();
    }
}
