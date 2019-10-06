package com.netflix.movieapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "title_rating")
public class TitleRatings {

    @Id
    @Column(name = "title_id")
    private String titleId;

    public void setTitleRating(double titleRating) {
        this.titleRating = titleRating;
    }

    public TitleRatings() {
    }

    public TitleRatings(String titleId, double titleRating) {
        this.titleId = titleId;
        this.titleRating = titleRating;
    }

    @Column(name = "title_rating")
    private double titleRating;

    public String getTitleId() {
        return titleId;
    }

    public double getTitleRating() {
        return titleRating;
    }
}
