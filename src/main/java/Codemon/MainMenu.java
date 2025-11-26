package Codemon;

import java.util.Scanner;

public class MainMenu {

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            clearScreen();
            printTitleArt();
            System.out.println(Colors.YELLOW + "Welcome to Codémon: Terminal Trials" + Colors.RESET);
            System.out.println(Colors.BLUE + "------------------------------------" + Colors.RESET);
            System.out.println(Colors.CYAN + "What would you like to do?" + Colors.RESET);
            System.out.println();
            System.out.println(Colors.GREEN + "  [1] Enter Battle Arena" + Colors.RESET);
            System.out.println(Colors.GREEN + "  [2] View Codéx" + Colors.RESET);
            System.out.println(Colors.GREEN + "  [3] Credits" + Colors.RESET);
            System.out.println(Colors.GREEN + "  [4] Exit Game" + Colors.RESET);
            System.out.print(Colors.YELLOW + "\nSelect an option (1-4): " + Colors.RESET);

            if (!scanner.hasNextInt()) {
                System.out.println(Colors.RED + "\nInvalid input. Please enter a number between 1 and 4." + Colors.RESET);
                scanner.next(); // consume invalid input
                pause();
                continue;
            }

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    BattleGame.startBattle(scanner);
                    break;
                case 2:
                    PKMList.showList();
                    break;
                case 3:
                    showCredits();
                    break;
                case 4:
                    System.out.println(Colors.YELLOW + "\nSaving progress..." + Colors.RESET);
                    System.out.println(Colors.YELLOW + "Closing Codémon..." + Colors.RESET);
                    System.out.println(Colors.CYAN + "Thanks for playing Codémon!" + Colors.RESET);
                    scanner.close();
                    return;
                default:
                    System.out.println(Colors.RED + "\nInvalid choice. Please select a valid option." + Colors.RESET);
                    pause();
            }
        }
    }

    private static void showCredits() {
        clearScreen();
        System.out.println(Colors.BLUE + "=== Codémon Development Team ===" + Colors.RESET);
        System.out.println(Colors.GREEN + "Jev Austin Apolinar" + Colors.RESET);
        System.out.println(Colors.GREEN + "Rjay Arazula" + Colors.RESET);
        System.out.println(Colors.GREEN + "Ken Frankie Mendoza" + Colors.RESET);
        System.out.println(Colors.PURPLE + "\nSpecial thanks to PokéAPI and the Java community and Vibe Coding." + Colors.RESET);
        pause();
    }

    private static void printTitleArt() {
    System.out.println(Colors.RED +
        "   ____ ___  ____  __//_ __  __  ___  _   _ \n" +
        "  / ___/ _ \\|  _ \\| ____|  \\/  |/ _ \\| \\ | |\n" +
        " | |  | | | | | | | |_  | |  | | | | |  \\| |\n" +
        " | |  | | | | | | |  _| | |\\/| | | | |   \\ |\n" +
        " | |__| |_| | |_| | |___| |  | | |_| | |\\  |\n" +
        "  \\____\\___/|____/|_____|_|  |_|\\___/|_| \\_|\n" +
        "           Terminal Trials Edition                 \n" +


        Colors.RESET);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pause() {
        System.out.println(Colors.PURPLE + "\nPress Enter to continue..." + Colors.RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
}