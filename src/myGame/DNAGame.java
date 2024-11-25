import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DNAGame {
    private final Scanner scanner;
    private final Ecosystem ecosystem;

    public DNAGame(Scanner scanner) {
        this.scanner = scanner;
        this.ecosystem = Ecosystem.getInstance();
    }

    public void play() {
        boolean playing = true;

        while (playing) {
            System.out.println("\n=== DNA Evolution Game ===");
            System.out.println("1. Create new creature");
            System.out.println("2. View existing creatures");
            System.out.println("3. Evolve a creature");
            System.out.println("4. Save ecosystem");
            System.out.println("5. Load ecosystem");
            System.out.println("6. Return to main menu");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> createNewCreature();
                    case 2 -> viewCreatures();
                    case 3 -> evolveCreature();
                    case 4 -> saveGame();
                    case 5 -> loadGame();
                    case 6 -> playing = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void createNewCreature() {
        System.out.println("Enter creature name:");
        String name = scanner.nextLine();

        System.out.println("Choose creature type (predator/prey):");
        String type = scanner.nextLine().toLowerCase();

        try {
            // Create a simple DNA sequence for the creature
            List<Double> sequence = new ArrayList<>();
            System.out.println("Enter 3 DNA values (0-100):");
            for (int i = 0; i < 3; i++) {
                double value = Double.parseDouble(scanner.nextLine());
                sequence.add(value);
            }

            DNASequence<Double> dna = new DNASequence<>(sequence, 5);
            Creature creature = CreatureFactory.createCreature(type, dna, name);
            ecosystem.addCreature("default", creature);
            System.out.println("Creature created successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers for DNA sequence.");
        } catch (DNASequenceTooLongException e) {
            System.out.println("DNA sequence is too long!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid creature type!");
        }
    }

    private void viewCreatures() {
        // Implementation for viewing creatures
        System.out.println("Viewing creatures... (To be implemented)");
    }

    private void evolveCreature() {
        // Implementation for evolving a creature
        System.out.println("Evolving creature... (To be implemented)");
    }

    private void saveGame() {
        System.out.println("Enter filename to save:");
        String filename = scanner.nextLine();
        try {
            ecosystem.saveState(filename);
            System.out.println("Game saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    private void loadGame() {
        // Implementation for loading game
        System.out.println("Loading game... (To be implemented)");
    }
}