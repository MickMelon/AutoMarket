package com.mickmelon.carshare.database;

import java.util.AbstractMap;
import java.util.List;

public class PostData {
    private String _action;
    private List<AbstractMap.SimpleEntry> _params;

    public PostData(String action, List<AbstractMap.SimpleEntry> params) {
        _action = action;
        _params = params;
    }

    /**
     * Gets the action string.
     */
    public String getAction() { return _action; }

    /**
     * Gets the POST parameters to be sent with the request.
     */
    public List<AbstractMap.SimpleEntry> getParams() { return _params; }
}
