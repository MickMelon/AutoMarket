package com.mickmelon.carshare.database;

public class HttpResult {
    private String _result;
    private int _responseCode;

    public HttpResult(String result, int responseCode) {
        _result = result;
        _responseCode = responseCode;
    }

    public String getResult() { return _result; }
    public int getResponseCode() { return _responseCode; }
}
