package ro.alexandru.wallet;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class App {

    public static void main(String[] args) {
        URI baseUri = URI.create("http://localhost:8080/wallet/");
        ResourceConfig config = new ResourceConfig(new ResourceConfig()
                .packages("ro.alexandru.wallet"));
        JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("HTTP Server started. WADL location: " + baseUri + "application.wadl");
    }
}