package com.netflix.movieapi.entities;

import com.netflix.movieapi.entities.compositekey.SeasonRatingKey;

import javax.persistence.*;

@Entity
@IdClass(SeasonRatingKey.class)
@Table(name = "season_rating")
public class SeasonRatings {

    @Id
    @Column(name = "season_num")
    private int seasonNum;

    public SeasonRatings(int seasonNum, String titleId, int totalEpisodes, double ratingSum) {
        this.seasonNum = seasonNum;
        this.titleId = titleId;
        this.totalEpisodes = totalEpisodes;
        this.ratingSum = ratingSum;
    }

    public SeasonRatings() {
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public double getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(double ratingSum) {
        this.ratingSum = ratingSum;
    }

    @Id
    @Column(name = "title_id")
    private String titleId;

    @Column(name = "num_of_episodes")
    private int totalEpisodes;

    @Column(name = "rating_sum")
    private double ratingSum;
}

