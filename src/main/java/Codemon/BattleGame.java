package Codemon;

import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class BattleGame {

    // ANSI Colors
    public static class Colors {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
    }

    public static void startBattle(Scanner scanner) {
        System.out.println(Colors.CYAN + "\nChoose difficulty:" + Colors.RESET);
        System.out.println(Colors.GREEN + "1. Easy" + Colors.RESET);
        System.out.println(Colors.RED + "2. Hard" + Colors.RESET);
        System.out.print(Colors.YELLOW + "1 or 2?: " + Colors.RESET);
        int difficulty = scanner.nextInt();

        Species opponent = Factory.createFromAPI(new Random().nextInt(151) + 1);
        Species player;

        if (difficulty == 1) {
            System.out.println(Colors.RED + "Opponent: " + opponent.getName() + " (Type: " + opponent.getType() + ")" + Colors.RESET);
            System.out.print("Choose your Pokémon ID (1-151): ");
            player = Factory.createFromAPI(scanner.nextInt());
        } else {
            System.out.print("Choose your Pokémon ID (1-151): ");
            player = Factory.createFromAPI(scanner.nextInt());
            System.out.println(Colors.RED + "Opponent: " + opponent.getName() + " (Type: " + opponent.getType() + ")" + Colors.RESET);
            opponent = boostOpponent(opponent);
        }

        System.out.println(Colors.CYAN + "\n~~ Battle Start! ~~" + Colors.RESET);
        System.out.println(Colors.GREEN + "Go! " + player.getName() + "!" + Colors.RESET);
        battleLoop(scanner, player, opponent);
    }

    private static void battleLoop(Scanner scanner, Species player, Species opponent) {
        while (player.getHp() > 0 && opponent.getHp() > 0) {
            System.out.println("\n=== Battle Menu ===");
            System.out.println(
                Colors.GREEN + player.getName() + " HP: " + hpBar(player.getHp(), player.getMaxHp()) + Colors.RESET +
                "   " +
                Colors.RED + opponent.getName() + " HP: " + hpBar(opponent.getHp(), opponent.getMaxHp()) + Colors.RESET
            );
            System.out.println("1. Fight");
            System.out.println("2. Run");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();

            if (choice == 2) {
                System.out.println(Colors.YELLOW + "You ran away safely..." + Colors.RESET);
                pause(scanner);
                return;
            } else if (choice != 1) {
                System.out.println(Colors.PURPLE + "Invalid choice! Try again." + Colors.RESET);
                continue;
            }

            // Turn order
            boolean playerFirst = player.getLevel() >= opponent.getLevel();

            if (playerFirst) {
                boolean opponentFainted = playerTurn(scanner, player, opponent);
                if (opponentFainted) break;
                boolean playerFainted = opponentTurn(opponent, player);
                if (playerFainted) break;
            } else {
                boolean playerFainted = opponentTurn(opponent, player);
                if (playerFainted) break;
                boolean opponentFainted = playerTurn(scanner, player, opponent);
                if (opponentFainted) break;
            }
        }

        if (player.getHp() > 0) {
            System.out.println(Colors.GREEN + "\n*** Victory! ***" + Colors.RESET);
            int xpGain = 10 + new Random().nextInt(20);
            int newLevel = player.getLevel() + xpGain / 10;
            System.out.println(player.getName() + " gained " + xpGain + " XP!");
            if (newLevel > player.getLevel()) {
                System.out.println(Colors.CYAN + player.getName() + " leveled up to Lv " + newLevel + "!" + Colors.RESET);
                player.setLevel(newLevel);
            }
        } else {
            System.out.println(Colors.RED + "\n*** You blacked out... ***" + Colors.RESET);
        }
        pause(scanner);
    }

    private static boolean playerTurn(Scanner scanner, Species player, Species opponent) {
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
            boolean crit = new Random().nextInt(16) == 0;
            double critMultiplier = crit ? 1.5 : 1.0;
            double stab = move.getType().equalsIgnoreCase(player.getType()) ? 1.5 : 1.0;
            double typeMultiplier = TypeEffectiveness.getMultiplier(move.getType(), opponent.getType());

            int damage = calculatePokemonDamage(player.getLevel(), player.getAttack(), opponent.getDefense(), move.getPower(), typeMultiplier, stab, critMultiplier);
            opponent.setHp(Math.max(0, opponent.getHp() - damage));

            System.out.print(Colors.CYAN + player.getName() + " used " + move.getName() + "! " + Colors.RESET);
            if (crit) System.out.print(Colors.YELLOW + "A critical hit! " + Colors.RESET);
            System.out.println(effectivenessText(typeMultiplier) + " Dealt " + damage + " damage.");
        } else {
            System.out.println(Colors.PURPLE + player.getName() + " missed!" + Colors.RESET);
        }

        return opponent.getHp() <= 0;
    }

    private static boolean opponentTurn(Species opponent, Species player) {
        Move move = opponent.getMoves().get(new Random().nextInt(opponent.getMoves().size()));
        if (new Random().nextInt(100) < move.getAccuracy()) {
            boolean crit = new Random().nextInt(16) == 0;
            double critMultiplier = crit ? 1.5 : 1.0;
            double stab = move.getType().equalsIgnoreCase(opponent.getType()) ? 1.5 : 1.0;
            double typeMultiplier = TypeEffectiveness.getMultiplier(move.getType(), player.getType());

            int damage = calculatePokemonDamage(opponent.getLevel(), opponent.getAttack(), player.getDefense(), move.getPower(), typeMultiplier, stab, critMultiplier);
            player.setHp(Math.max(0, player.getHp() - damage));

            System.out.print(Colors.RED + opponent.getName() + " used " + move.getName() + "! " + Colors.RESET);
            if (crit) System.out.print(Colors.YELLOW + "A critical hit! " + Colors.RESET);
            System.out.println(effectivenessText(typeMultiplier) + " Dealt " + damage + " damage.");
        } else {
            System.out.println(Colors.PURPLE + opponent.getName() + " missed!" + Colors.RESET);
        }

        return player.getHp() <= 0;
    }

    private static int calculatePokemonDamage(int level, int atk, int def, int power, double typeMultiplier, double stab, double crit) {
        double base = (((2.0 * level) / 5.0 + 2.0) * power * ((double) atk / Math.max(1, def)) / 50.0) + 2.0;
        double variance = 0.85 + (new Random().nextDouble() * 0.15);
        double modifier = typeMultiplier * stab * crit * variance;
        return (int) Math.max(1, Math.floor(base * modifier));
    }

    private static String effectivenessText(double multiplier) {
        if (multiplier == 2.0) return Colors.GREEN + "It's super effective!" + Colors.RESET;
        if (multiplier == 0.5) return Colors.YELLOW + "Not very effective..." + Colors.RESET;
        if (multiplier == 0.0) return Colors.PURPLE + "It had no effect!" + Colors.RESET;
        return "Effective.";
    }

    private static String hpBar(int hp, int maxHp) {
        int totalBars = 20;
        int filled = (int) ((double) hp / maxHp * totalBars);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < totalBars; i++) {
            bar.append(i < filled ? "█" : "-");
        }
        bar.append("]");
        return bar.toString();
    }

    private static void pause(Scanner scanner) {
        System.out.print(Colors.BLUE + "\nPress Enter to continue..." + Colors.RESET);
        scanner.nextLine();
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