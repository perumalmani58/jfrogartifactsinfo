package org.jfrog.model;

import java.util.List;

public class RepoItem {
    private String path;
    private String name;
    private List<Stat> stats;

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Stat> getStats() { return stats; }

    public void setStats(List<Stat> stats) { this.stats = stats; }

    public Integer getDownloads() {
        if (stats == null || stats.isEmpty()) return 0;
        return stats.iterator().next().getDownloads();
    }

    static class Stat {
        private Integer downloads;

        public Integer getDownloads() { return downloads; }

        public void setDownloads(Integer downloads) { this.downloads = downloads; }
    }

    @Override
    public String toString() {
        return "{" + "path='" + path + '\'' + ", name='" + name + '\'' + ", downloads=" + getDownloads() + '}';
    }
}
