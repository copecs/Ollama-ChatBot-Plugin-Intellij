import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OllamaApiClient{
    private static final String API_URL = "http://localhost:11434/api/generate"; // generic one

    private final HttpClient client = HttpClient.newHttpClient();

    public String queryOllamaModel(String model, String prompt, String suffix) throws IOException, InterruptedException {
        // Create JSON
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", model);
        jsonObject.addProperty("prompt", prompt);
        jsonObject.addProperty("suffix", suffix);

        // Advanced options may be in use in future
        JsonObject options = new JsonObject();
        jsonObject.add("options", options);
        jsonObject.addProperty("stream", false);

        // Build HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString(), StandardCharsets.UTF_8))
                .build();

        // Send request and handle response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonObject responseBody = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseBody.get("response").getAsString();
        } else {
            throw new IOException("Unexpected response code: " + response.statusCode());
        }
    }
}
