package com.netflix.movieapi.dao;

import com.netflix.movieapi.view.Episode;
import com.netflix.movieapi.view.Season;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface SeriesRepository extends TitlesRepository {

    @Query(value = "SELECT s.season_num as SeasonNum, round(s.rating_sum / s.num_of_episodes,2) as SeasonRating " +
            "FROM season_rating s, titles t " +
            "where t.title_id = s.title_id and t.title_name = ?1", nativeQuery = true)
    List<Season> findSeasons(String name);

    @Query(value = "SELECT e.episode_num as EpisodeNum, t.title_rating as Rating " +
            "FROM title_rating t, episode e, titles s " +
            "WHERE t.title_id = e.episode_id AND s.title_id = e.title_id " +
            "AND s.title_name = ?1 AND e.season_num = ?2", nativeQuery = true)
    List<Episode> findEpisodes(String name, int sNum);

    @Query(value = "SELECT s.season_num as SeasonNum, round(s.rating_sum / s.num_of_episodes, 2) as SeasonRating " +
            "FROM season_rating s, titles t " +
            "where t.title_id = s.title_id and t.title_name = ?1 and s.season_num = ?2 LIMIT 1", nativeQuery = true)
    Season findSeasonRating(String titleName, int season);

    @Query(value = "SELECT e.episode_num as EpisodeNum, t.title_rating as Rating " +
            "FROM title_rating t, episode e, titles s " +
            "WHERE t.title_id = e.episode_id AND s.title_id = e.title_id " +
            "AND s.title_name = ?1 AND e.season_num = ?2 AND e.episode_num = ?3 LIMIT 1", nativeQuery = true)
    Episode findEpisodeRating(String titleName, int season, int episode);

    @Async
    @Query(value = "SELECT e.episode_id FROM episode e, titles s " +
            "WHERE s.title_id = e.title_id " +
            "AND s.title_name = ?1 AND e.season_num = ?2 AND e.episode_num = ?3 LIMIT 1", nativeQuery = true)
    String findEpisodeId(String titleName, int season, int episode);

}