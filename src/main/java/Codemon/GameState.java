package Codemon;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GameState {
    public static void saveNamed(Scanner scanner, Species player) {
        System.out.print("Enter save name: ");
        String name = scanner.next();
        try (PrintWriter out = new PrintWriter(new FileWriter(name + ".sav"))) {
            out.println(player.getName());
            out.println(player.getType());
            out.println(player.getLevel());
            out.println(player.getHp());
            out.println(player.getAttack());
            out.println(player.getDefense());
            for (Move move : player.getMoves()) {
                out.println(move.getName() + "," + move.getType() + "," + move.getPower() + "," + move.getAccuracy() + "," + move.getDamageClass());
            }
            System.out.println("Game saved as " + name + ".sav");
        } catch (IOException e) {
            System.out.println("Failed to save game.");
        }
    }

    public static Species loadNamed(Scanner scanner) {
        System.out.print("Enter save name to load: ");
        String name = scanner.next();
        try (BufferedReader in = new BufferedReader(new FileReader(name + ".sav"))) {
            String pname = in.readLine();
            String type = in.readLine();
            int level = Integer.parseInt(in.readLine());
            int hp = Integer.parseInt(in.readLine());
            int atk = Integer.parseInt(in.readLine());
            int def = Integer.parseInt(in.readLine());

            List<Move> moves = new java.util.ArrayList<>();
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                moves.add(new Move(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), parts[4]));
            }

            return new Species(pname, type, level, hp, atk, def, moves);
        } catch (IOException e) {
            System.out.println("Save file not found.");
            return null;
        }
    }
}