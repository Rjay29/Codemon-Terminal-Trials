package Codemon;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
import java.net.*;
import java.util.*;

public class Factory {
    public static Species createFromAPI(int id) {
        try {
            URL url = URI.create("https://pokeapi.co/api/v2/pokemon/" + id).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject obj = new JSONObject(response.toString());
            String name = capitalize(obj.getString("name"));
            String type = obj.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name");

            JSONArray stats = obj.getJSONArray("stats");
            int hp = stats.getJSONObject(0).getInt("base_stat");
            int attack = stats.getJSONObject(1).getInt("base_stat");
            int defense = stats.getJSONObject(2).getInt("base_stat");

            JSONArray moveArray = obj.getJSONArray("moves");
            List<Move> moves = new ArrayList<>();
            
            // Get up to 4 moves that have power > 0
            for (int i = 0; i < moveArray.length() && moves.size() < 4; i++) {
                JSONObject moveObj = moveArray.getJSONObject(i);
                String moveName = moveObj.getJSONObject("move").getString("name");
                Move move = fetchMove(moveName);
                if (move != null && move.getPower() > 0) {
                    moves.add(move);
                }
            }

            // Add "Struggle" if no moves were found
            if (moves.isEmpty()) {
                moves.add(new Move("Struggle", "normal", 50, 100, "physical"));
            }

            return new Species(name, type, 5, hp, attack, defense, moves);
        } catch (Exception e) {
            System.out.println("Error fetching Pokémon: " + e.getMessage());
            return new Species("MissingNo", "normal", 1, 1, 1, 1, 
                List.of(new Move("Struggle", "normal", 50, 100, "physical")));
        }
    }

    private static Move fetchMove(String moveName) {
        try {
            URL url = URI.create("https://pokeapi.co/api/v2/move/" + moveName).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject obj = new JSONObject(response.toString());
            String type = obj.getJSONObject("type").getString("name");
            int power = obj.optInt("power", 0);
            int accuracy = obj.optInt("accuracy", 100);
            String damageClass = obj.getJSONObject("damage_class").getString("name");

            return new Move(capitalize(moveName), type, power, accuracy, damageClass);
        } catch (Exception e) {
            return null;
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}