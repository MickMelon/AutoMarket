package com.mickmelon.carshare.database;

/**
 * The result from a Http request.
 */
public class HttpResult {
    /**
     * The result from the Http request. Usually in JSON format.
     */
    private String _result;

    /**
     * The Http response code.
     */
    private int _responseCode;

    /**
     * Creates a new instance of the HttpResult class.
     * @param result The result string.
     * @param responseCode The response code.
     */
    public HttpResult(String result, int responseCode) {
        _result = result;
        _responseCode = responseCode;
    }

    /**
     * Gets the result string.
     */
    public String getResult() { return _result; }

    /**
     * Gets the response code.
     */
    public int getResponseCode() { return _responseCode; }
}
