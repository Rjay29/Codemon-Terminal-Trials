package Codemon;

import java.util.*;

public class MainMenu {
    private Scanner scanner;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            clearScreen();
            printTitleArt();
            printMenu();
            int choice = readInt("Enter your choice (1-4): ", 1, 4);
            switch (choice) {
                case 1 -> startBattle();
                case 2 -> PKMList.showList();
                case 3 -> showCredits();
                case 4 -> running = false;
            }
        }
        System.out.println(Colors.CYAN + "Thanks for playing Codemon!" + Colors.RESET);
        scanner.close();
    }

    private void startBattle() {
        try {
            System.out.print("Enter player Pokémon name: ");
            String playerName = scanner.nextLine();
            int playerHp = readInt("Enter HP (50-200): ", 50, 200);
            int playerAtk = readInt("Enter Attack (30-150): ", 30, 150);
            int playerDef = readInt("Enter Defense (30-150): ", 30, 150);
            String playerType = getType();
            PKM player = new PKM(playerName, playerType, playerHp, playerAtk, playerDef);
            player.setMoves(List.of(
                new Move("Tackle", "normal", 40, 100, "physical"),
                new Move("Ember", "fire", 40, 100, "special")
            ));

            System.out.print("Enter opponent Pokémon name: ");
            String oppName = scanner.nextLine();
            int oppHp = readInt("Enter HP (50-200): ", 50, 200);
            int oppAtk = readInt("Enter Attack (30-150): ", 30, 150);
            int oppDef = readInt("Enter Defense (30-150): ", 30, 150);
            String oppType = getType();
            PKM opponent = new PKM(oppName, oppType, oppHp, oppAtk, oppDef);
            opponent.setMoves(List.of(
                new Move("Tackle", "normal", 40, 100, "physical"),
                new Move("Water Gun", "water", 40, 100, "special")
            ));

            BattleGame battle = new BattleGame(player, opponent);
            battle.startBattle();
            pause();
        } catch (Exception e) {
            System.out.println("Error starting battle: " + e.getMessage());
            pause();
        }
    }

    private String getType() {
        String[] types = {"normal", "fire", "water", "grass", "electric", "ice", "fighting", "poison", "ground", "flying"};
        System.out.println("Available types: " + String.join(", ", types));
        System.out.print("Enter type: ");
        return scanner.nextLine().toLowerCase();
    }

    private void printMenu() {
        System.out.println(Colors.GREEN + "\n=== Main Menu ===" + Colors.RESET);
        System.out.println("1. Start Battle");
        System.out.println("2. View Pokémon List");
        System.out.println("3. Credits");
        System.out.println("4. Exit");
        System.out.println();
    }

    private void printTitleArt() {
        System.out.println(Colors.CYAN);
        System.out.println("  \\\\  \\\\___/ / \\_\\_  |");
        System.out.println("   \\\\  /  \\\\__  ___/  |");
        System.out.println("    \\\\ \\\\___/  \\\\___/  |");
        System.out.println("     \\\\ \\\\___/  \\\\___/ |");
        System.out.println("      \\\\\\\\___/  \\\\___/  |");
        System.out.println("    CODEMON - Battle Simulator");
        System.out.println(Colors.RESET);
    }

    private void showCredits() {
        System.out.println(Colors.BLUE + "\n=== Credits ===" + Colors.RESET);
        System.out.println("Developed by: A Coding Enthusiast");
        System.out.println("API: PokéAPI (https://pokeapi.co)");
        System.out.println("Framework: Java 21 + Maven");
        System.out.println();
        pause();
    }

    private void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.run();
    }
}
