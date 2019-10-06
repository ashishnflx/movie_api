package com.netflix.movieapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "titles")
public class Titles {

    public Titles() {}

    public Titles(String titleId, String titleName, String titleType) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.titleType = titleType;
    }

    @Id
    @Column(name = "title_id")
    private String titleId;

    @Column(name = "title_name")
    private String titleName;

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    @Column(name = "title_type")
    private String titleType;

    public String getTitleId() {
        return titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getTitleType() {
        return titleType;
    }

    @Override
    public String toString() {
        return "Title[" + titleId + "," + titleName + "," + titleType + "]";
    }
}
