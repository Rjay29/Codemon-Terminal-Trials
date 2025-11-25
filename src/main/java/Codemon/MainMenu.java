package Codemon;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Terminal Pokémon Battle ---");
            System.out.println("1. Battle");
            System.out.println("2. Pokémon List");
            System.out.println("3. Credits");
            System.out.println("4. End Game");
            System.out.print("Choose: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.next(); // consume invalid input
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
                    System.out.println("Thanks for playing!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showCredits() {
        System.out.println("\n--- Credits ---");
        System.out.println("Apolinar, Jev Austin");
        System.out.println("Arazula, Rjay");
        System.out.println("Mendoza, Ken Frankie");
    }
}