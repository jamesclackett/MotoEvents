package com.jimboidin.motoevents;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class for accessing the Sportrader MotoGP v2 API.
 * <br>Contains default Sport IDs for both MotoGP and WSBK
 */
public class API {
    private static final String apiKey = System.getenv("sportradar_motogp_api");
    public static final String MOTOGP_ID = "1030979";
    public static final String WSBK_ID = "1055199";

    private API(){}

    /**
     * Makes a HTTPRequest with the specified Sport ID and returns a HTTPResponse
     * @param stage The stage (sport) to be requested.
     * @return a HTTPResponse from the API
     */
    public static HttpResponse<String> getStages(String stage) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.sportradar.us/motogp/trial/v2/en/sport_events/" +
                        "sr:stage:"+stage+"/schedule.json?api_key="+apiKey))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }


}
