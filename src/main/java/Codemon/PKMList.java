package Codemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public final class PKMList {
    private static class Colors {
        static final String RESET = "\u001B[0m";
        static final String CYAN = "\u001B[36m";
    }

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
            System.out.println("\n" + Colors.CYAN + "=== First 151 Codémon ===" + Colors.RESET);
            for (int i = 0; i < results.length(); i++) {
                System.out.println((i + 1) + ". " + results.getJSONObject(i).getString("name"));
            }
            System.out.print("\u001B[34m" + "\nPress Enter to continue..." + "\u001B[0m");
            System.in.read();
        } catch (Exception e) {
            System.out.println("Error fetching Codémon list: " + e.getMessage());
        }
    }
}
