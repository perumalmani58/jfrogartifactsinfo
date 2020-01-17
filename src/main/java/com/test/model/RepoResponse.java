package com.test.model;

import java.util.HashMap;
import java.util.List;

public class RepoResponse {
    private List<RepoItem> results;
    private HashMap<String, String> range;

    public List<RepoItem> getResults() { return results; }

    public void setResults(List<RepoItem> results) { this.results = results; }

    public HashMap<String, String> getRange() { return range; }

    public void setRange(HashMap<String, String> range) { this.range = range; }
}

