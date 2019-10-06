package com.netflix.movieapi.entities;

import com.netflix.movieapi.entities.compositekey.TitleCastKey;

import javax.persistence.*;

@Entity
@IdClass(TitleCastKey.class)
@Table(name = "title_cast")
public class TitlesCast {
    @Id
    @Column(name = "title_id")
    private String titleId;

    @Id
    @Column(name = "cast_id")
    private String castId;

    public String getTitleId() {
        return titleId;
    }

    public String getCastId() {
        return castId;
    }
}

