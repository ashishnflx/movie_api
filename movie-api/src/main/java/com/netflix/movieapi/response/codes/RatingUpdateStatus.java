package com.netflix.movieapi.response.codes;

public class RatingUpdateStatus {
    public static final String RATING_UPDATE_SU = "Ratings update succeeded";
    public static final String RATING_UPDATE_FA = "Ratings update failed. Please try again";
    public static final String MISSING_TITLE =  "Ratings update failed because title not found";
    public static final String MISSING_EPISODE = "Ratings update failed because episode not found";
    public static final String NEGATIVE_RATING = "Ratings update failed because rating can't be less than 0";
}
