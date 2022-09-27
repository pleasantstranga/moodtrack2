package com.moodtrack.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRetrieveUsers() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users"), HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":0,\"createTS\":\"2018-01-01T06:00:00.000+00:00\",\"lastUpdateTS\":\"2018-01-01T06:00:00.000+00:00\",\"fname\":\"Aaron\",\"lname\":\"Bernstein\"},{\"id\":1,\"createTS\":\"2018-01-02T06:00:00.000+00:00\",\"lastUpdateTS\":\"2018-01-02T06:00:00.000+00:00\",\"fname\":\"Carrie\",\"lname\":\"Bernstein\"},{\"id\":2,\"createTS\":\"2018-01-02T06:00:00.000+00:00\",\"lastUpdateTS\":\"2018-01-02T06:00:00.000+00:00\",\"fname\":\"Jen\",\"lname\":\"Bernstein\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
