package com.mickmelon.carshare.database;

import android.os.AsyncTask;

public class PostAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String result = HttpClient.get(params[0]);

        return result;
    }
    //private final ProgressDialog _progressDialog = new ProgressDialog(parentActivity);
}
