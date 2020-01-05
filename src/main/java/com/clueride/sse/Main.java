package com.clueride.sse;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    static final String BASE_URI = "http://0.0.0.0:6543/rest/";
    static boolean serverRunning = true;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in the listed packages
        final ResourceConfig rc = new ResourceConfig()
                .packages(
                        "com.clueride",
                        "com.clueride.sse.common",
                        "com.clueride.sse.event"
                )
                .register(SseFeature.class)
                .register(JacksonFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args array of strings passed on command line.
     * @throws IOException if there's a problem reading console.
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("SSE app started listening at "
                + "%s", BASE_URI));

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("SSE beginning shutdown");
                server.shutdown();
                serverRunning = false;
                System.exit(0);
            }
        });

        while(serverRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }

        System.out.println("SSE now down");
    }

}

