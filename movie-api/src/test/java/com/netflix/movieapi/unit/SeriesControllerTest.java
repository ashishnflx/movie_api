package com.netflix.movieapi.unit;

import com.netflix.movieapi.controller.SeriesController;
import com.netflix.movieapi.dao.SeasonRatingRepository;
import com.netflix.movieapi.dao.SeriesRepository;
import com.netflix.movieapi.entities.Titles;
import com.netflix.movieapi.response.codes.RatingUpdateStatus;
import com.netflix.movieapi.view.Cast;
import com.netflix.movieapi.view.Episode;
import com.netflix.movieapi.view.Season;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SeriesControllerTest {
    @InjectMocks
    private SeriesController seriesController;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private SeasonRatingRepository seasonRatingRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSeries() {
        List<Titles> t= new ArrayList<Titles>();
        t.add(new Titles("t123","testSeries","tvSeries"));
        when(seriesController.getSeries("testSeries").getBody()).thenReturn(t);

        List<Titles> titles = seriesController.getSeries("testSeries").getBody();
        assertEquals("t123", titles.get(0).getTitleId());
    }


    @Test
    public void testGetSeriesCast() {
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

        when(seriesController.getSeriesCast("testSeries").getBody()).thenReturn(t);
        List<Cast> cast = seriesController.getSeriesCast("testSeries").getBody();
        assertEquals("cast1", cast.get(0).getCastName());
    }

    @Test
    public void testGetSeriesSeasonRatings() {
        Season s1 = new Season () {
            @Override
            public Integer getSeasonNum() {
                return 1;
            }

            @Override
            public Double getSeasonRating() {
                return 8.0;
            }
        };
        Season s2 = new Season () {
            @Override
            public Integer getSeasonNum() {
                return 2;
            }

            @Override
            public Double getSeasonRating() {
                return 9.0;
            }
        };
        List<Season> t= new ArrayList<>();
        t.add(s1);
        t.add(s2);
        when(seriesController.getSeasons("testSeries").getBody()).thenReturn(t);
        List<Season> seasons = seriesController.getSeasons("testSeries").getBody();
        assertEquals(seasons.get(0).getSeasonRating().toString(), "8.0");
        assertEquals(seasons.get(1).getSeasonRating().toString(), "9.0");
    }

    @Test
    public void testGetSeriesSeasonEpisodeRatings() {
        Episode s1 = new Episode () {
            @Override
            public Integer getEpisodeNum() {
                return 1;
            }

            @Override
            public Double getRating() {
                return 8.9;
            }
        };
        Episode s2 = new Episode () {
            @Override
            public Integer getEpisodeNum() {
                return 1;
            }

            @Override
            public Double getRating() {
                return 9.9;
            }
        };
        List<Episode> t= new ArrayList<>();
        t.add(s1);
        t.add(s2);
        when(seriesController.getEpisodes("testSeries",1).getBody()).thenReturn(t);
        List<Episode> episodes = seriesController.getEpisodes("testSeries", 1).getBody();
        assertEquals(episodes.get(0).getRating().toString(), "8.9");
        assertEquals(episodes.get(1).getRating().toString(), "9.9");
    }

    @Test
    public void testGetSeasonRating() {
        Season s1 = new Season () {
            @Override
            public Integer getSeasonNum() {
                return 1;
            }

            @Override
            public Double getSeasonRating() {
                return 8.0;
            }
        };
        when(seriesController.getSeasonRating("testSeries", 1).getBody()).thenReturn(s1);
        Season season = seriesController.getSeasonRating("testSeries", 1).getBody();
        assertEquals(season.getSeasonRating().toString(), "8.0");
    }

    @Test
    public void testGetEpisodeRating() {
        Episode s1 = new Episode () {
            @Override
            public Integer getEpisodeNum() {
                return 1;
            }

            @Override
            public Double getRating() {
                return 8.9;
            }
        };
        when(seriesController.getEpisodeRating("testSeries",1, 1).getBody()).thenReturn(s1);
        Episode episode = seriesController.getEpisodeRating("testSeries", 1, 1).getBody();
        assertEquals(episode.getRating().toString(), "8.9");
    }

    @Test
    public void testGetAllSeries() {
        List<Titles> t = new ArrayList<Titles>();
        t.add(new Titles("t123","testSeries","tvSeries"));
        t.add(new Titles("t124","testSeries2","tvSeries"));
        when(seriesController.getSeries().getBody()).thenReturn(t);
        List<Titles> titles = seriesController.getSeries().getBody();
        assertEquals(t.get(0).getTitleId(), titles.get(0).getTitleId());
        assertEquals(t.get(1).getTitleId(), titles.get(1).getTitleId());
    }

    @Test
    public void testRatingUpdateNegRate() {
        when(seriesRepository.findEpisodeId("testSeries",1, 5)).thenReturn("e1");
        when(seriesRepository.findTitleId("testSeries", "tvSeries")).thenReturn("t1");
        String result = (String)seriesController.updateEpisodeRating("testSeries",1, 5, -5).getBody();
        assertEquals(RatingUpdateStatus.NEGATIVE_RATING, result);
    }

    @Test
    public void testRatingUpdateMissingEpisode() {
        when(seriesRepository.findTitleId("testSeries", "tvSeries")).thenReturn("t1");
        String result = (String)seriesController.updateEpisodeRating("testSeries",1, 5, -5).getBody();
        assertEquals(RatingUpdateStatus.MISSING_EPISODE, result);
    }

    @Test
    public void testRatingUpdateMissingTitle() {
        when(seriesRepository.findEpisodeId("testSeries",1, 5)).thenReturn("e1");
        String result = (String)seriesController.updateEpisodeRating("testSeries",1, 5, -5).getBody();
        assertEquals(RatingUpdateStatus.MISSING_TITLE, result);
    }

}


