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

    public String getAction() { return _action; }

    public List<AbstractMap.SimpleEntry> getParams() { return _params; }
}
