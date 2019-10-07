package com.netflix.movieapi.integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIntegrationTest {
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testMovie() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/movie/Samantaral"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"titleId\":\"tt7746986\",\"titleName\":\"Samantaral\",\"titleType\":\"movie\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testMovieCast() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/movie/Pilgrimage/cast"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"castId\":\"nm0000068\",\"castName\":\"Randolph Scott\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testMovieRating() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/movie/Samantaral/rating"), HttpMethod.GET, entity, String.class);
        String expected = "{\"titleRating\":\"7.6\",\"titleName\":\"Samantaral\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
