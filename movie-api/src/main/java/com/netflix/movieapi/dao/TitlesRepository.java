package com.netflix.movieapi.dao;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.netflix.movieapi.entities.Titles;
import com.netflix.movieapi.view.Cast;
import com.netflix.movieapi.view.TitleRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

public interface TitlesRepository extends CrudRepository<Titles, String> {

    @Query(value = "SELECT * FROM titles WHERE title_type = ?1", nativeQuery = true)
    List<Titles> findAll(String title_type);

    @Query(value = "SELECT * FROM titles WHERE title_name = ?1 AND title_type = ?2", nativeQuery = true)
    List<Titles> findTitleByName(String name, String title_type);

    @Async("threadPoolTaskExecutor")
    @Query(value = "SELECT title_id FROM titles WHERE title_name = ?1 AND title_type = ?2 LIMIT 1", nativeQuery = true)
    CompletableFuture<String> findTitleId(String name, String title_type);


    @Query(value = "SELECT c.cast_id as CastId,c.cast_name as CastName FROM title_cast tc, titles t, casts c " +
            "WHERE t.title_id = tc.title_id AND c.cast_id = tc.cast_id AND t.title_name = ?1" , nativeQuery = true)
    List<Cast> findTitleCasts(String titleId);

    @Query(value = "SELECT t.title_name as TitleName, r.title_rating as TitleRating FROM titles t, title_rating r " +
            "WHERE t.title_id = r.title_id AND t.title_name = ?1 LIMIT 1", nativeQuery = true)
    TitleRating findTitleRating(String titleId);



}