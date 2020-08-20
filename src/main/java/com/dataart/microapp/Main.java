package com.dataart.microapp;

import io.helidon.config.Config;
import io.helidon.microprofile.server.Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import static io.helidon.config.ConfigSources.classpath;

/**
 * The application main class.
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        setupLogging();

        Server server = startServer();

        System.out.println("http://localhost:" + server.port() + "/greet");
    }

    /**
     * Start the server.
     *
     * @return the created {@link Server} instance
     */
    static Server startServer() {
        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        return Server.builder()
                .config(buildConfig())
                .build()
                .start();
    }

    private static Config buildConfig() {
        return Config.builder()
                .sources(
                        classpath("application.yaml"),
                        classpath("META-INF/microprofile-config.properties").optional())
                .build();
    }


    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }
}
