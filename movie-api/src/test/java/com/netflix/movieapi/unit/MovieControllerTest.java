package com.netflix.movieapi.unit;

import com.netflix.movieapi.controller.MovieController;
import com.netflix.movieapi.dao.TitlesRepository;
import com.netflix.movieapi.entities.Casts;
import com.netflix.movieapi.entities.Titles;
import com.netflix.movieapi.view.Cast;
import com.netflix.movieapi.view.TitleRating;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MovieControllerTest {
    @InjectMocks
    private MovieController movieController;

    @Mock
    private TitlesRepository titlesRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMovie() {
        List<Titles> t= new ArrayList<Titles>();
        t.add(new Titles("t123","testMovie","movie"));
        when(movieController.getMovie("testMovie").getBody()).thenReturn(t);

        List<Titles> titles = movieController.getMovie("testMovie").getBody();
        assertEquals("t123", titles.get(0).getTitleId());
    }

    @Test
    public void testGetMovieCast() {
        Cast c = new Cast () {
            @Override
            public String getCastId() {
                return "id1";
            }
            @Override
            public String getCastName() {
                return "cast1";
            }
        };
        List<Cast> t= new ArrayList<>();
        t.add(c);

        when(movieController.getMovieCast("testMovie").getBody()).thenReturn(t);
        List<Cast> cast = movieController.getMovieCast("testMovie").getBody();
        assertEquals("cast1", cast.get(0).getCastName());
    }

    @Test
    public void testGetMovieRating() {
        TitleRating c = new TitleRating () {
            @Override
            public String getTitleName() {
                return "title1";
            }

            @Override
            public String getTitleRating() {
                return "8.0";
            }
        };
        when(movieController.getMovieRating("title1").getBody()).thenReturn(c);
        TitleRating rating = movieController.getMovieRating("title1").getBody();
        assertEquals("8.0", rating.getTitleRating());
    }

    @Test
    public void testGetMovies() {
        List<Titles> t = new ArrayList<Titles>();
        t.add(new Titles("t123","testMovie","movie"));
        t.add(new Titles("t124","testMovie2","movie"));
        when(movieController.getMovies().getBody()).thenReturn(t);
        List<Titles> titles = movieController.getMovies().getBody();
        assertEquals(t.get(0).getTitleId(), titles.get(0).getTitleId());
        assertEquals(t.get(1).getTitleId(), titles.get(1).getTitleId());
    }



}
