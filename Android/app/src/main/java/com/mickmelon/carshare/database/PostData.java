package com.mickmelon.carshare.database;

import java.util.AbstractMap;
import java.util.List;

public class PostData {
    private String _url;
    private List<AbstractMap.SimpleEntry> _params;

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
