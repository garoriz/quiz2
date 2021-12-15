package ru.kpfu.itis.garipov.bus_location_app;

import com.google.gson.Gson;
import ru.kpfu.itis.garipov.bus_location_app.model.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusLocationAPI {

    public String get(String busNumber) {
        StringBuilder content = new StringBuilder();
        try {
            URL getUrl = new URL("http://data.kzn.ru:8082/api/v0/dynamic_datasets/bus.json");
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            )) {
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            return "Ошибка сервера! Попробуйте позже...";
        }
        return readableInfo(content.toString(), busNumber);
    }

    private String readableInfo(String json, String busNumber) {
        Gson gson = new Gson();
        json = json.substring(1, json.length() - 1);
        String[] jsons = json.split("},");
        List<Response> responses = new ArrayList<>();
        for (int i = 0; i < jsons.length - 1; i++) {
            jsons[i] = jsons[i] + "}";
            responses.add(gson.fromJson(jsons[i], Response.class));
        }
        responses.add(gson.fromJson(jsons[jsons.length - 1], Response.class));
        StringBuilder content = new StringBuilder();
        if (busNumber.length() > 0) {
            for (Response respons : responses) {
                if (respons.getData().Marsh.equals(busNumber)) {
                    content.append("Обновлено в ").append(respons.updated_at).append("\n");
                    content.append("Маршрут: ").append(respons.getData().Marsh).append("\n");
                    content.append("Широта: ").append(respons.getData().Latitude).append("\n");
                    content.append("Долгота: ").append(respons.getData().Longitude).append("\n");
                    content.append("Скорость автобуса: ").append(respons.getData().Speed).append("\n");
                    content.append("Азимут: ").append(respons.getData().Azimuth).append("\n");
                    content.append("_____________" + "\n");
                    return content.toString();
                }
            }
            return "Автобуса с таким номером в Казани не существует!";
        } else {
            for (Response respons : responses) {
                content.append("Обновлено в ").append(respons.updated_at).append("\n");
                content.append("Маршрут: ").append(respons.getData().Marsh).append("\n");
                content.append("Широта: ").append(respons.getData().Latitude).append("\n");
                content.append("Долгота: ").append(respons.getData().Longitude).append("\n");
                content.append("Скорость автобуса: ").append(respons.getData().Speed).append("\n");
                content.append("Азимут: ").append(respons.getData().Azimuth).append("\n");
                content.append("_____________" + "\n");
            }
            return content.toString();
        }
    }
}
