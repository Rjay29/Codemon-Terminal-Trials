package Codemon;

import java.util.*;

public class BattleGame {
    private PKM player;
    private PKM opponent;
    private Scanner scanner;

    public BattleGame(PKM player, PKM opponent) {
        this.player = player;
        this.opponent = opponent;
        this.scanner = new Scanner(System.in);
    }

    public void startBattle() {
        System.out.println("\n" + Colors.YELLOW + "=== Battle Start ===" + Colors.RESET);
        System.out.println("Player: " + player.getName() + " (HP: " + player.getHp() + ")");
        System.out.println("Opponent: " + opponent.getName() + " (HP: " + opponent.getHp() + ")\n");
        battleLoop();
    }

    private void battleLoop() {
        while (player.getHp() > 0 && opponent.getHp() > 0) {
            System.out.println(Colors.CYAN + "\n--- Turn ---" + Colors.RESET);
            System.out.println(hpBar("Player", player) + " | " + hpBar("Opponent", opponent));
            playerTurn();
            if (opponent.getHp() <= 0) break;
            opponentTurn();
        }
        endBattle();
    }

    private void playerTurn() {
        List<Move> moves = player.getMoves();
        if (moves == null || moves.isEmpty()) {
            System.out.println("Player has no moves!");
            return;
        }
        System.out.println("\n" + Colors.GREEN + "Player's turn:" + Colors.RESET);
        for (int i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i).getName());
        }
        int choice = readInt("Choose move (1-" + moves.size() + "): ", 1, moves.size());
        Move move = moves.get(choice - 1);
        int damage = calculateDamage(player.getAttack(), opponent.getDefense(), move.getPower(),
            TypeEffectiveness.getMultiplier(move.getType(), opponent.getType()),
            move.getType().equalsIgnoreCase(player.getType()) ? 1.5 : 1.0, Math.random() > 0.8 ? 1.5 : 1.0);
        opponent.setHp(opponent.getHp() - damage);
        System.out.println(Colors.GREEN + player.getName() + " used " + move.getName() + "!" + Colors.RESET);
        System.out.println(effectivenessText(TypeEffectiveness.getMultiplier(move.getType(), opponent.getType())));
        System.out.println(Colors.RED + "Damage dealt: " + damage + Colors.RESET);
    }

    private void opponentTurn() {
        List<Move> moves = opponent.getMoves();
        if (moves == null || moves.isEmpty()) {
            System.out.println("Opponent has no moves!");
            return;
        }
        Move move = moves.get(new Random().nextInt(moves.size()));
        int damage = calculateDamage(opponent.getAttack(), player.getDefense(), move.getPower(),
            TypeEffectiveness.getMultiplier(move.getType(), player.getType()),
            move.getType().equalsIgnoreCase(opponent.getType()) ? 1.5 : 1.0, Math.random() > 0.8 ? 1.5 : 1.0);
        player.setHp(player.getHp() - damage);
        System.out.println(Colors.RED + "\n" + opponent.getName() + " used " + move.getName() + "!" + Colors.RESET);
        System.out.println(effectivenessText(TypeEffectiveness.getMultiplier(move.getType(), player.getType())));
        System.out.println(Colors.BLUE + "Damage taken: " + damage + Colors.RESET);
    }

    private int calculateDamage(int atk, int def, int power, double typeMultiplier, double stab, double crit) {
        double base = ((power * ((double) atk / Math.max(1, def))) / 50.0) + 2.0;
        double variance = 0.85 + (Math.random() * 0.15);
        double damage = base * typeMultiplier * stab * crit * variance;
        return (int) Math.max(1, Math.floor(damage));
    }

    private String hpBar(String name, PKM pkm) {
        int bars = Math.max(0, (pkm.getHp() / 10));
        return name + ": [" + "█".repeat(bars) + "░".repeat(10 - bars) + "] " + pkm.getHp();
    }

    private String effectivenessText(double multiplier) {
        if (multiplier > 1.0) return Colors.GREEN + "Super effective!" + Colors.RESET;
        if (multiplier < 1.0 && multiplier > 0.0) return Colors.BLUE + "Not very effective..." + Colors.RESET;
        if (multiplier == 0.0) return Colors.YELLOW + "No effect..." + Colors.RESET;
        return "";
    }

    private void endBattle() {
        if (player.getHp() > 0) {
            System.out.println("\n" + Colors.GREEN + "=== You won! ===" + Colors.RESET);
        } else {
            System.out.println("\n" + Colors.RED + "=== You lost! ===" + Colors.RESET);
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) return val;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
