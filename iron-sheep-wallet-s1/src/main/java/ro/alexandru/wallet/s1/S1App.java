package ro.alexandru.wallet.s1;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class S1App {

    private static final Logger LOG = LoggerFactory.getLogger(S1App.class);

    public static void main(String[] args) {
        createAndStartHttpServer();
    }

    private static void createAndStartHttpServer() {
        URI baseUri = URI.create("http://localhost:8080/");
        ResourceConfig config = new ResourceConfig().packages("ro.alexandru.wallet.s1.http");
        JdkHttpServerFactory.createHttpServer(baseUri, config);
        LOG.info("S1 HTTP Server started. WADL location: `{}application.wadl`", baseUri);
    }
}
