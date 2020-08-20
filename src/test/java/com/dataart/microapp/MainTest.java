
package com.dataart.microapp;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.microprofile.server.Server;
import io.helidon.webclient.WebClient;
import io.helidon.webserver.WebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainTest {

    private static Server webServer;
    private static WebClient webClient;
    private static final JsonBuilderFactory JSON_BUILDER = Json.createBuilderFactory(Collections.emptyMap());


    @BeforeAll
    public static void startTheServer() throws Exception {
        webServer = Main.startServer();

        webClient = WebClient.builder()
                .baseUri("http://localhost:" + webServer.port())
                .addMediaSupport(JsonpSupport.create())
                .build();
    }

    @AfterAll
    public static void stopServer() throws Exception {
        if (webServer != null) {
            webServer.stop();
        }
    }

    @Test
    public void testHelloWorld() throws Exception {
        webClient.get()
                .path("/service")
                .request(String.class)
                .thenAccept(response -> Assertions.assertTrue(response.contains("hello @")))
                .toCompletableFuture()
                .get();
    }

}
