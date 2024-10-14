package api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class ExchangeRateClient {

    private static final String API_KEY = "c887bdc178e048b15761cd89";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private JsonObject conversionRates; // Almacena las tasas de conversión

    public JsonObject obtenerTasasDeCambio() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = BASE_URL + API_KEY + "/latest/USD";  // Solicita las tasas desde USD

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                System.out.println("Respuesta: " + responseBody);
                conversionRates = procesarRespuesta(responseBody);
            }
            else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
        }
        catch (IOException | InterruptedException e) {
        }
        return conversionRates;
    }

    // Método para procesar la respuesta JSON
    public JsonObject procesarRespuesta(String respuestaJson) {
        return JsonParser.parseString(respuestaJson).getAsJsonObject().getAsJsonObject("conversion_rates");
    }

    public JsonObject getConversionRates() {
        return conversionRates;
    }
}
