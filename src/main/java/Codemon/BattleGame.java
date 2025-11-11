package Codemon;

import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class BattleGame {
    public static void startBattle(Scanner scanner) {
        System.out.println("\nChoose difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Hard");
        System.out.println("1 or 2?: ");
        int difficulty = scanner.nextInt();

        Species opponent = Factory.createFromAPI(new Random().nextInt(151) + 1);
        Species player;

        if (difficulty == 1) {
            System.out.println("Opponent: " + opponent.getName() + " (Type: " + opponent.getType() + ")");
            System.out.print("Choose your Pokémon ID (1-151): ");
            player = Factory.createFromAPI(scanner.nextInt());
        } else {
            System.out.print("Choose your Pokémon ID (1-151): ");
            player = Factory.createFromAPI(scanner.nextInt());
            System.out.println("Opponent: " + opponent.getName() + " (Type: " + opponent.getType() + ")");
            opponent = boostOpponent(opponent);
        }

        System.out.println("\nBattle Start!");
        battleLoop(scanner, player, opponent);
    }

    private static void battleLoop(Scanner scanner, Species player, Species opponent) {
        while (player.getHp() > 0 && opponent.getHp() > 0) {
            System.out.println("\n--- Battle Menu ---");
            System.out.println("1. Fight");
            System.out.println("2. Run");
            System.out.println("3. Save State");
            System.out.println("4. Load State");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    playerTurn(scanner, player, opponent);
                    if (opponent.getHp() <= 0) break;
                    opponentTurn(opponent, player);
                }
                case 2 -> {
                    System.out.println("You ran away!");
                    return;
                }
                case 3 -> GameState.saveNamed(scanner, player);
                case 4 -> {
                    Species loaded = GameState.loadNamed(scanner);
                    if (loaded != null) player = loaded;
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        if (player.getHp() > 0) {
            System.out.println("\nYou won!");
            int xpGain = 10 + new Random().nextInt(20);
            int newLevel = player.getLevel() + xpGain / 10;
            if (newLevel > player.getLevel()) {
                System.out.println(player.getName() + " leveled up to Lv " + newLevel + "!");
                player.setLevel(newLevel);
            }
        } else {
            System.out.println("\nYou lost...");
        }
    }

    private static void playerTurn(Scanner scanner, Species player, Species opponent) {
        List<Move> moves = player.getMoves();
        System.out.println("\nYour Moves:");
        for (int i = 0; i < moves.size(); i++) {
            Move m = moves.get(i);
            System.out.println((i + 1) + ". " + m.getName() + " (" + m.getType() + ", " + m.getPower() + ")");
        }

        System.out.print("Choose a move: ");
        int choice = scanner.nextInt() - 1;
        Move move = moves.get(choice);

        if (new Random().nextInt(100) < move.getAccuracy()) {
            double multiplier = TypeEffectiveness.getMultiplier(move.getType(), opponent.getType());
            int damage = calculateDamage(player.getAttack(), opponent.getDefense(), move.getPower(), multiplier);
            opponent.setHp(opponent.getHp() - damage);
            System.out.println(player.getName() + " used " + move.getName() + "! " + effectivenessText(multiplier) + " Deals " + damage + " damage.");
        } else {
            System.out.println(player.getName() + " missed!");
        }
    }

    private static void opponentTurn(Species opponent, Species player) {
        Move move = opponent.getMoves().get(new Random().nextInt(opponent.getMoves().size()));
        if (new Random().nextInt(100) < move.getAccuracy()) {
            double multiplier = TypeEffectiveness.getMultiplier(move.getType(), player.getType());
            int damage = calculateDamage(opponent.getAttack(), player.getDefense(), move.getPower(), multiplier);
            player.setHp(player.getHp() - damage);
            System.out.println(opponent.getName() + " used " + move.getName() + "! " + effectivenessText(multiplier) + " Deals " + damage + " damage.");
        } else {
            System.out.println(opponent.getName() + " missed!");
        }
    }

    private static int calculateDamage(int atk, int def, int power, double multiplier) {
        int base = (atk * power / 50) - (def / 4);
        return (int) Math.max(1, base * multiplier);
    }

    private static String effectivenessText(double multiplier) {
        if (multiplier == 2.0) return "It's super effective!";
        if (multiplier == 0.5) return "Not very effective...";
        if (multiplier == 0.0) return "It had no effect!";
        return "Effective.";
    }

    private static Species boostOpponent(Species original) {
        return new Species(
            original.getName(),
            original.getType(),
            original.getLevel() + 5,
            original.getHp() * 2,
            original.getAttack() * 2,
            original.getDefense() * 2,
            original.getMoves()
        );
    }
}