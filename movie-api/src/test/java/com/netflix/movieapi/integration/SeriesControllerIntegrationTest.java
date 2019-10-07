package com.netflix.movieapi.integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeriesControllerIntegrationTest {
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testSeries() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Regular Show"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"titleId\":\"tt1710308\",\"titleName\":\"Regular Show\",\"titleType\":\"tvSeries\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeriesCast() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/cast"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"castId\":\"nm0000434\",\"castName\":\"Mark Hamill\"},{\"castId\":\"nm0089710\"," +
                "\"castName\":\"Steve Blum\"},{\"castId\":\"nm0759525\",\"castName\":\"William Salyers\"},{\"castId\"" +
                ":\"nm0851317\",\"castName\":\"Fred Tatasciore\"},{\"castId\":\"nm1886746\",\"castName\":\"Roger Craig Smith\"}," +
                "{\"castId\":\"nm2936993\",\"castName\":\"Sam Marin\"},{\"castId\":\"nm3096205\",\"castName\":\"J.G. Quintel\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasons() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Stella/season"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"seasonNum\":6,\"seasonRating\":7.62}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasonRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/rating"), HttpMethod.GET, entity, String.class);
        String expected = "{\"seasonNum\":6,\"seasonRating\":7.62}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasonEpisodes() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/episode"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"rating\":7.2,\"episodeNum\":1},{\"rating\":7.6,\"episodeNum\":2},{\"rating\":7.2,\"episodeNum\":3},{\"rating\":7.6,\"episodeNum\":4},{\"rating\":8.0,\"episodeNum\":5},{\"rating\":8.1,\"episodeNum\":6}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasonEpisodeRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/episode/2/rating"), HttpMethod.GET, entity, String.class);
        String expected = "{\"episodeNum\":2,\"rating\":7.6}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testUpdateRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseOld = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/episode/2/rating"), HttpMethod.GET, entity, String.class);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/episode/2/rating/9.7"), HttpMethod.PUT, entity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        ResponseEntity<String> responseNew = restTemplate.exchange(
                createURLWithPort("//series/Stella/season/6/episode/2/rating"), HttpMethod.GET, entity, String.class);
        String expectedNew = "{\"episodeNum\":2,\"rating\":9.7}";
        JSONAssert.assertEquals(expectedNew, responseNew.getBody(), false);
        ResponseEntity<String> responseSeasonRating = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/rating"), HttpMethod.GET, entity, String.class);
        String expectedSeasonRating = "{\"seasonNum\":6,\"seasonRating\":7.62}";

        JSONAssert.assertNotEquals(expectedSeasonRating, responseSeasonRating.getBody(), false);
        ResponseEntity<String> responseUpd = restTemplate.exchange(
                createURLWithPort("/series/Stella/season/6/episode/2/rating/7.6"), HttpMethod.PUT, entity, String.class);
        assertEquals(200, responseUpd.getStatusCodeValue());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
