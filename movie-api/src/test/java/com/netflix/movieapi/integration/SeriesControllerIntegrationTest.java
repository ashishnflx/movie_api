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
                createURLWithPort("/series/Regular Show/season"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"seasonNum\":8,\"seasonRating\":9.499999999999998}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasonRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/season/8/rating"), HttpMethod.GET, entity, String.class);
        String expected = "{\"seasonNum\":8,\"seasonRating\":9.499999999999998}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasonEpisodes() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/season/8/episode"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"episodeNum\":24,\"rating\":8.9},{\"episodeNum\":25,\"rating\":9.4},{\"episodeNum\":26,\"rating\":9.5}," +
                "{\"episodeNum\":27,\"rating\":9.9},{\"episodeNum\":28,\"rating\":9.7},{\"episodeNum\":29,\"rating\":9.6}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testSeasonEpisodeRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/season/8/episode/24/rating"), HttpMethod.GET, entity, String.class);
        String expected = "{\"episodeNum\":24,\"rating\":8.9}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testUpdateRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/season/8/episode/24/rating/8.9"), HttpMethod.PUT, entity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        ResponseEntity<String> responseNew = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/season/8/episode/24/rating"), HttpMethod.GET, entity, String.class);
        String expectedNew = "{\"episodeNum\":24,\"rating\":8.9}";
        JSONAssert.assertEquals(expectedNew, responseNew.getBody(), false);
        ResponseEntity<String> responseSeasonRating = restTemplate.exchange(
                createURLWithPort("/series/Regular Show/season/8/rating"), HttpMethod.GET, entity, String.class);
        String expectedSeasonRating = "{\"seasonNum\":8,\"seasonRating\":9.499999999999998}";
        JSONAssert.assertEquals(expectedSeasonRating, responseSeasonRating.getBody(), false);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
