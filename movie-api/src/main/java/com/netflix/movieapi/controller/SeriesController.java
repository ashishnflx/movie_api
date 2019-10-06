package com.netflix.movieapi.controller;

import com.netflix.movieapi.dao.SeasonRatingRepository;
import com.netflix.movieapi.dao.SeriesRepository;
import com.netflix.movieapi.entities.Titles;
import com.netflix.movieapi.response.ResponseHandler;
import com.netflix.movieapi.view.Cast;
import com.netflix.movieapi.enums.TitleType;
import com.netflix.movieapi.response.codes.RatingUpdateStatus;
import com.netflix.movieapi.exceptions.RatingUpdateException;
import com.netflix.movieapi.view.Episode;
import com.netflix.movieapi.view.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeriesController {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRatingRepository seasonRatingRepository;

    @RequestMapping("/series")
    public ResponseEntity<List<Titles>> getSeries() {
        return ResponseHandler.getResponse(seriesRepository.findAll(TitleType.SERIES.getType()));
    }

    @GetMapping("/series/{name}")
    public ResponseEntity<List<Titles>> getSeries(@PathVariable String name) {
        return ResponseHandler.getResponse(seriesRepository.findTitleByName(name, TitleType.SERIES.getType()));
    }

    @GetMapping("/series/{seriesName}/cast")
    public ResponseEntity<List<Cast>> getSeriesCast(@PathVariable String seriesName) {
        return ResponseHandler.getResponse(seriesRepository.findTitleCasts(seriesName));
    }

    @GetMapping("/series/{seriesName}/season")
    public ResponseEntity<List<Season>> getSeasons(@PathVariable String seriesName) {
        return ResponseHandler.getResponse(seriesRepository.findSeasons(seriesName));
    }

    @GetMapping("/series/{seriesName}/season/{season}")
    public ResponseEntity<List<Episode>> getSeason(@PathVariable String seriesName, @PathVariable Integer season) {
        return ResponseHandler.getResponse(seriesRepository.findEpisodes(seriesName, season));
    }

    @GetMapping("/series/{seriesName}/season/{season}/rating")
    public ResponseEntity<Season> getSeasonRating(@PathVariable String seriesName, @PathVariable Integer season) {
        return ResponseHandler.getResponse(seriesRepository.findSeasonRating(seriesName, season));
    }

    @GetMapping("/series/{seriesName}/season/{season}/episode")
    public ResponseEntity<List<Episode>> getEpisodes(@PathVariable String seriesName, @PathVariable Integer season){
        return ResponseHandler.getResponse(seriesRepository.findEpisodes(seriesName, season));

    }


    @GetMapping("/series/{seriesName}/season/{season}/episode/{episode}/rating")
    public ResponseEntity<Episode> getEpisodeRating(
            @PathVariable String seriesName,
            @PathVariable Integer season,
            @PathVariable Integer episode) {
        return ResponseHandler.getResponse(seriesRepository.findEpisodeRating(seriesName,season,episode));
    }

    @PutMapping("/series/{seriesName}/season/{season}/episode/{episode}/rating/{rating}")
    public ResponseEntity updateEpisodeRating(@PathVariable String seriesName,
                                      @PathVariable Integer season,
                                      @PathVariable Integer episode,
                                      @PathVariable double rating
                                      ) {

        //episodeId and titleId calls are Async calls as they are not dependent
        String episodeId = seriesRepository.findEpisodeId(seriesName, season, episode);
        String titleId = seriesRepository.findTitleId(seriesName, TitleType.SERIES.getType());
        String errors = checkUpdateErrors(titleId,episodeId, rating);
        if(errors != null) return ResponseHandler.getErrorResponse(errors);
        try {
            seasonRatingRepository.updateRating(titleId, episodeId, season, rating);
        } catch (RatingUpdateException e) {
            return ResponseHandler.getServerErrorResponse(RatingUpdateStatus.RATING_UPDATE_FA);
        }
        return ResponseHandler.getResponse();
    }

    private String checkUpdateErrors(String titleId, String episodeId, double rating) {
        if(titleId == null)
            return RatingUpdateStatus.MISSING_TITLE;
        if(episodeId == null)
            return RatingUpdateStatus.MISSING_EPISODE;
        if(rating < 0)
            return RatingUpdateStatus.NEGATIVE_RATING;
        return null;
    }

}