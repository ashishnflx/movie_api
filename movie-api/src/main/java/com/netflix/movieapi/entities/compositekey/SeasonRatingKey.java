package com.netflix.movieapi.entities.compositekey;

import java.io.Serializable;

public class SeasonRatingKey implements Serializable {
    private int seasonNum;
    private String titleId;

    public SeasonRatingKey(){}

    public SeasonRatingKey(int seasonNum, String titleId) {
        this.seasonNum = seasonNum;
        this.titleId = titleId;
    }
}
