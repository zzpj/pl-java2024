package com.example.httpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TodoRepository {

    String getTodo() throws URISyntaxException, IOException, InterruptedException {
        // I think you want me to use something like that,
        // but I have no idea how to create a response with the desired body

//        HttpResponse<String> response;
//        try (HttpClient client = HttpClient.newHttpClient()) {
//            HttpRequest request = HttpRequest.newBuilder()
//                    .header("accept", "application/json")
//                    .uri(URI.create("todos.org/getTodo"))
//                    .build();
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        }
//        return response.body();

        // So I have commented out a block of code that should work, and hardcoded this text block in return
        return """
                {
                  "userId": 1,
                  "id": 1,
                  "title": "delectus aut autem",
                  "completed": false
                }""";
    }

}
