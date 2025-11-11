package Codemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class PokeAPI {
    public static void main(String[] args) {
        for (int id = 1; id <= 151; id++) {
            try {
                String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + id;
                URL url = URI.create(apiUrl).toURL(); // ✅ fixed
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject pokemon = new JSONObject(response.toString());
                String name = pokemon.getString("name");
                JSONArray typesArray = pokemon.getJSONArray("types");
                JSONArray statsArray = pokemon.getJSONArray("stats");

                System.out.println("ID: " + id);
                System.out.println("Name: " + capitalize(name));
                System.out.print("Type(s): ");
                for (int i = 0; i < typesArray.length(); i++) {
                    String type = typesArray.getJSONObject(i).getJSONObject("type").getString("name");
                    System.out.print(capitalize(type) + (i < typesArray.length() - 1 ? ", " : ""));
                }
                System.out.println();

                for (int i = 0; i < statsArray.length(); i++) {
                    String statName = statsArray.getJSONObject(i).getJSONObject("stat").getString("name");
                    int baseStat = statsArray.getJSONObject(i).getInt("base_stat");
                    System.out.println(capitalize(statName) + ": " + baseStat);
                }

                System.out.println("--------------------------------------------------");

            } catch (Exception e) {
                System.out.println("Failed to fetch Pokémon ID " + id);
                e.printStackTrace();
            }
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}