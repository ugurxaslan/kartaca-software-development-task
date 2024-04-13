package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class HelloRestController {
    private List<Marker> markers = new ArrayList<>();


    @GetMapping("/markers")

    public String hello() {

        // URL'den JSON verisi almak için RestTemplate kullanalım
        RestTemplate restTemplate = new RestTemplate();
        LocalDate today = LocalDate.now();
        today = today.minusYears(1).minusMonths(1).minusDays(5);
        LocalDate twoDaysAgo =today.minusDays(2);
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" +
                twoDaysAgo.toString()+ "&endtime=" + today.toString();
        System.out.println(url);

        // JSON verisini String olarak alalım
        String jsonData = restTemplate.getForObject(url, String.class);

        // ObjectMapper ile JSON verisini parse edelim
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder result = new StringBuilder();
        String jsonResult=null;
        try {
            // JSON verisini JsonNode'a dönüştürelim
            JsonNode root = objectMapper.readTree(jsonData);

            // Features dizisini alalım
            JsonNode features = root.path("features");

            // Her bir feature için işlem yapalım
            for (JsonNode feature : features) {
                // Latitude, longitude ve location değerlerini alalım
                JsonNode geometry = feature.path("geometry");
                JsonNode coordinates = geometry.path("coordinates");
                double lon = coordinates.get(0).asDouble();
                double lat = coordinates.get(1).asDouble();
                String location = feature.path("properties").path("place").asText();

                markers.add(new Marker(new double[]{lat-lat%0.25, lon-lon%0.50}, "latitude:"+(lat-lat%0.25)+" longitude:"+(lon-lon%0.50)+" "+location));
            }

            // Aynı koordinatlara sahip olanları tutmak için bir Set oluştur
            Set<Marker> uniqueMarkers = new HashSet<>();
            List<Marker> duplicates = new ArrayList<>();
            for (Marker marker : markers) {
                if (!uniqueMarkers.add(marker)) {
                    duplicates.add(marker);
                }
            }
            markers.removeAll(duplicates);

            // JSON dönüşümü için ObjectMapper oluşturalım
            ObjectMapper mapper = new ObjectMapper();
            jsonResult = "";
            try {
                // markers listesini JSON formatına dönüştürelim
                jsonResult = mapper.writeValueAsString(markers);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}