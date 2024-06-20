package com.challange.LiterAlura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author diegobecerril
 */
public class ConsumoAPI {
    
    private static final String BASE_URL = "https://gutendex.com";
    
    public String getInfoAPI(String endpoint) {
        try {
            String fullUrl = BASE_URL + endpoint;
            System.out.println("Request URL: " + fullUrl);

            HttpClient client = HttpClient.newBuilder()
                                          .followRedirects(HttpClient.Redirect.ALWAYS)
                                          .build();
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(fullUrl))
                                             .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("Error: HTTP Status Code " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
