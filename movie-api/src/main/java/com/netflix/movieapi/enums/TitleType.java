package com.netflix.movieapi.enums;

public enum TitleType {

    MOVIE("movie"), SERIES("tvSeries"), EPISODE("tvEpisode");
    private String type;

    private TitleType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
