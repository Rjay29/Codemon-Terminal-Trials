package Codemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class PokemonList {
    public static void showList() {
        try {
            URL url = URI.create("https://pokeapi.co/api/v2/pokemon?limit=151").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();

            JSONObject obj = new JSONObject(response.toString());
            JSONArray results = obj.getJSONArray("results");

            System.out.println("\n--- Pokémon List ---");
            for (int i = 0; i < results.length(); i++) {
                String name = results.getJSONObject(i).getString("name");
                System.out.println((i + 1) + ". " + capitalize(name));
            }
        } catch (Exception e) {
            System.out.println("Failed to load Pokémon list.");
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}