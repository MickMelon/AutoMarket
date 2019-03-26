package com.mickmelon.carshare.database;

import java.util.AbstractMap;
import java.util.List;

/**
 * This class is used to pass post data to the http client.
 */
public class PostData {
    /**
     * The URL to make a request to.
     */
    private String _url;

    /**
     * The POST parameters.
     */
    private List<AbstractMap.SimpleEntry> _params;

    /**
     * Creates a new instance of the PostData class.
     * @param url The URL to make a request to.
     * @param params The POST parameters.
     */
    public PostData(String url, List<AbstractMap.SimpleEntry> params) {
        _url = url;
        _params = params;
    }

    /**
     * Gets the URL string.
     */
    public String getUrl() { return _url; }

    /**
     * Gets the POST parameters to be sent with the request.
     */
    public List<AbstractMap.SimpleEntry> getParams() { return _params; }
}
