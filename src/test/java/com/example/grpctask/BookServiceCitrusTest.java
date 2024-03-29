package com.example.grpctask;

import org.citrusframework.TestActionRunner;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.config.CitrusSpringConfig;
import static org.citrusframework.http.actions.HttpActionBuilder.http;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

@CitrusSpringSupport
@ContextConfiguration(classes = {CitrusSpringConfig.class, TestConfig.class})
public class BookServiceCitrusTest {
    @CitrusEndpoint
    private HttpClient todoClient;

    @Test
    @CitrusTest
    void testCreateBook(@CitrusResource TestActionRunner actions) {
        actions.$(http()
                .client("http://localhost:8080")
                .send()
                .post("/api/v1/books")
                .fork(true)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("""
                    {
                       "title": "Title Book",
                       "author": "Author Book",
                       "isbn": "000-0000000000",
                       "quantity":10
                    }
                    """));

        actions.$(http()
                .client("http://localhost:8080")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("""
                    {
                       "id": "@ignore@",
                       "title": "Title Book",
                       "author": "Author Book",
                       "isbn": "000-0000000000",
                       "quantity":10
                    }
                    """));
    }
}
