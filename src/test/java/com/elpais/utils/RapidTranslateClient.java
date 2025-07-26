package com.elpais.utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
public class RapidTranslateClient {
    private static final String API_URL = "https://rapid-translate-multi-traduction.p.rapidapi.com/t";
    private static final String API_KEY = "9c34c8bba5mshe03d6043c3f0027p186d91jsnda536bd481ce";
    private static final String API_HOST = "rapid-translate-multi-traduction.p.rapidapi.com";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String translate(String text, String fromLang, String toLang) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("from", fromLang);
        requestBody.put("to", toLang);
        requestBody.put("e", "");
        requestBody.put("q", new JSONArray().put(text));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .POST(BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed: " + response.body());
        }

        JSONArray translatedArray = new JSONArray(response.body());
        return translatedArray.getString(0);
    }
}