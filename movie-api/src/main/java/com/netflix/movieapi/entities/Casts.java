package com.netflix.movieapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "casts")
public class Casts {

    public Casts(String castId, String castName) {
        this.castId = castId;
        this.castName = castName;
    }

    @Id
    @Column(name = "cast_id")
    private String castId;

    @Column(name = "cast_name")
    private String castName;

    public void setCastId(String castId) {
        this.castId = castId;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCastId() {
        return castId;
    }

    public String getCastName() {
        return castName;
    }

    @Override
    public String toString() {
        return "Cast[" + castId + "," + castName + "]";
    }
}
