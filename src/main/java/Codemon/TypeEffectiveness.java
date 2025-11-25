package Codemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public final class TypeEffectiveness {
    private static final Map<String, Map<String, Double>> cache = new HashMap<>();

    public static double getMultiplier(String attackType, String defenderType) {
        try {
            if (!cache.containsKey(attackType)) {
                URL url = URI.create("https://pokeapi.co/api/v2/type/" + attackType).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                JSONObject obj = new JSONObject(response.toString());
                JSONObject damage = obj.getJSONObject("damage_relations");
                Map<String, Double> typeMap = new HashMap<>();
                for (String key : List.of("double_damage_to", "half_damage_to", "no_damage_to")) {
                    JSONArray arr = damage.getJSONArray(key);
                    for (int i = 0; i < arr.length(); i++) {
                        String target = arr.getJSONObject(i).getString("name");
                        double mult = switch (key) {
                            case "double_damage_to" -> 2.0;
                            case "half_damage_to" -> 0.5;
                            case "no_damage_to" -> 0.0;
                            default -> 1.0;
                        };
                        typeMap.put(target, mult);
                    }
                }
                cache.put(attackType, typeMap);
            }
            return cache.get(attackType).getOrDefault(defenderType, 1.0);
        } catch (Exception e) {
            return 1.0;
        }
    }
}
