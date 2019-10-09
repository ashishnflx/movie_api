package com.netflix.movieapi.dao;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import com.netflix.movieapi.entities.*;
import com.netflix.movieapi.entities.compositekey.SeasonRatingKey;
import com.netflix.movieapi.exceptions.RatingUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SeasonRatingRepository {

    @Autowired
    private EntityManager entityManager;

    public SeasonRatingRepository() {}

    public TitleRatings findEpisodeRatings(String episodeId) {
        return this.entityManager.find(TitleRatings.class, episodeId);
    }

    public SeasonRatings findSeasonRating(int seasonNumber, String titleId) {
        SeasonRatingKey sk = new SeasonRatingKey(seasonNumber, titleId);
        return this.entityManager.find(SeasonRatings.class, sk, LockModeType.PESSIMISTIC_WRITE);
    }

    @Transactional(propagation = Propagation.MANDATORY )
    public double updateEpisodeRating(String episodeId, double rating) throws RatingUpdateException {
        TitleRatings episode = this.findEpisodeRatings(episodeId);
        if (episode == null) {
            throw new RatingUpdateException("Episode not found " + episodeId);
        }
        double oldRating = episode.getTitleRating();
        episode.setTitleRating(rating);
        return oldRating;
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public void updateSeasonRating(SeasonRatings season, double ratingDiff) {
        double seasonRating = season.getRatingSum();
        if(seasonRating + ratingDiff < 0)
            season.setRatingSum(0);
        else
            season.setRatingSum(seasonRating + ratingDiff);
    }

    /*
        Season ratings are maintained in SeasonRating table which contains total number of episodes of that title season
        and it also contains the total sum of all episode ratings.
        During update operation we want to update 2 entities
            1. TitleRatings entity with the new rating
            2. SeasonRatings entity with the new sum of episode rating
        Inorder for rating update to be successful both of the updates should happen together or should'nt happen it all.
        Due to this we are taking care of it under one transaction which throws exception if its not able to update
        both entities.
        In the first step we update episode rating and get old episode rating
        In second step we calculate difference of new episode rating and old episode rating and add it to season rating
     */


    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = RatingUpdateException.class)
    public void updateRating(String titleId, String episodeId, int seasonNumber, double rating) throws RatingUpdateException {
        SeasonRatings season = this.findSeasonRating(seasonNumber, titleId);
        if (season == null) {
            throw new RatingUpdateException("Season not found " + seasonNumber);
        }
        double oldRating = updateEpisodeRating(episodeId, rating);
        updateSeasonRating(season, rating - oldRating);
    }

}