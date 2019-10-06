package com.netflix.movieapi.unit;

import com.netflix.movieapi.controller.MovieController;
import com.netflix.movieapi.dao.SeasonRatingRepository;
import com.netflix.movieapi.dao.TitlesRepository;
import com.netflix.movieapi.entities.SeasonRatings;
import com.netflix.movieapi.entities.TitleRatings;
import com.netflix.movieapi.entities.Titles;
import com.netflix.movieapi.entities.compositekey.SeasonRatingKey;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SeasonRatingRepositoryTest {
    @InjectMocks
    private SeasonRatingRepository seasonRatingRepository;

    @Mock
    private EntityManager entityManager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testfindEpisodeRatings() {
        TitleRatings t = new TitleRatings("epId1", 8);
        when(entityManager.find(TitleRatings.class, "epId1")).thenReturn(t);
        TitleRatings title = seasonRatingRepository.findEpisodeRatings("epId1");
        assertEquals(8.0, title.getTitleRating(), 0);
    }

}
