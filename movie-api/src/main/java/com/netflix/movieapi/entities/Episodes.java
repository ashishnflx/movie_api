package com.netflix.movieapi.entities;

import com.netflix.movieapi.entities.compositekey.EpisodeKey;

import javax.persistence.*;

@Entity
@IdClass(EpisodeKey.class)
@Table(name = "episode")
public class Episodes {

    @Id
    @Column(name = "title_id")
    private String titleId;

    @Id
    @Column(name = "episode_id")
    private String episodeId;

    @Column(name = "season_num")
    private int seasonNum;

    @Column(name = "episode_num")
    private int episodeNum;

    public String getTitleId() {
        return titleId;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public int getSeasonNum() {
        return seasonNum;
    }

    public int getEpisodeNum() {
        return episodeNum;
    }
}

