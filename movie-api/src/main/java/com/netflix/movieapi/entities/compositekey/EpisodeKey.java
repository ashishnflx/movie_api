package com.netflix.movieapi.entities.compositekey;

import java.io.Serializable;

public class EpisodeKey implements Serializable {
    private String titleId;
    private String episodeId;

    public EpisodeKey(){}

    public EpisodeKey(String titleId, String episodeId) {
        this.titleId = titleId;
        this.episodeId = episodeId;
    }
}
