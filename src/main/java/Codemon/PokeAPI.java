package Codemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public final class PokeAPI {
    public static void main(String[] args) {
        System.out.println("Fetching first 151 Pokémon from PokéAPI...\n");
        for (int i = 1; i <= 151; i++) {
            try {
                URL url = URI.create("https://pokeapi.co/api/v2/pokemon/" + i).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                JSONObject obj = new JSONObject(response.toString());
                String name = obj.getString("name");
                JSONArray stats = obj.getJSONArray("stats");
                int hp = stats.getJSONObject(0).getInt("base_stat");
                int atk = stats.getJSONObject(1).getInt("base_stat");
                int def = stats.getJSONObject(2).getInt("base_stat");

                System.out.println(i + ". " + name + " - HP: " + hp + " ATK: " + atk + " DEF: " + def);
            } catch (Exception e) {
                System.out.println(i + ". Error fetching data");
            }
        }
    }
}
