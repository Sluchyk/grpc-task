package com.example.grpctask;

import java.util.Collections;
import org.citrusframework.dsl.endpoint.CitrusEndpoints;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.validation.json.JsonTextMessageValidator;
import org.citrusframework.xml.namespace.NamespaceContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public HttpClient bookClient() {
        return CitrusEndpoints
                .http()
                .client()
                .requestUrl("http://localhost/api/v1/books")
                .build();
    }

    @Bean
    public NamespaceContextBuilder namespaceContextBuilder() {
        NamespaceContextBuilder namespaceContextBuilder = new NamespaceContextBuilder();
        namespaceContextBuilder.setNamespaceMappings(Collections.singletonMap("xh", "http://www.w3.org/1999/xhtml"));
        return namespaceContextBuilder;
    }
    @Bean
    public JsonTextMessageValidator defaultJsonMessageValidator() {
        return new JsonTextMessageValidator();
    }
}
