import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordGame {

    private final World earth;
    private final Scanner sc;

    public WordGame(World earth, Scanner sc) {
        this.earth = earth;
        this.sc = sc;
    }

    public void play() throws IOException {

        boolean playingWord = true;

        // Process files
        File folder = new File("data");
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Data folder missing or invalid!");
            return;
        }

        Country.processFiles(folder.toPath(), earth);

        int wordGamesPlayed = 0;
        int correctFirstAttempt = 0;
        int correctSecondAttempt = 0;
        int incorrect = 0;

        while (playingWord) {
            System.out.println("Welcome to the Word Game!");
            wordGamesPlayed++;
            Random rand = new Random();
            List<String> countryNames = new ArrayList<>(earth.getAllCountries().keySet());

            for (int i = 0; i < 10; i++) {
                int randomIndex = rand.nextInt(countryNames.size());
                String randomCountryName = countryNames.get(randomIndex);
                Country country = earth.getCountry(randomCountryName);

                int questionType = rand.nextInt(3); // 0, 1, or 2
                String userAnswer = null;

                switch (questionType) {
                    case 0 -> {
                        System.out.printf("What country has the capital city %s?%n", country.getCapitalCityName());
                        userAnswer = sc.nextLine().trim();
                    }
                    case 1 -> {
                        System.out.printf("What is the capital of %s?%n", country.getName());
                        userAnswer = sc.nextLine().trim();
                    }
                    case 2 -> {
                        String randomFact = country.getFacts()[rand.nextInt(country.getFacts().length)];
                        System.out.printf("Which country is described by this fact: %s%n", randomFact);
                        userAnswer = sc.nextLine().trim();
                    }
                    default -> System.out.println("Invalid question type!");
                }

                if (userAnswer.equalsIgnoreCase(country.getName())) {
                    System.out.println("CORRECT!");
                    correctFirstAttempt += 2;
                } else {
                    System.out.println("INCORRECT! Try again.");
                    userAnswer = sc.nextLine().trim();

                    if (userAnswer.equalsIgnoreCase(country.getName())) {
                        System.out.println("CORRECT on second attempt!");
                        correctSecondAttempt++;
                    } else {
                        System.out.printf("INCORRECT! The correct answer was: %s%n", country.getName());
                        incorrect++;
                    }
                }
            }

            displayResults(wordGamesPlayed, correctFirstAttempt, correctSecondAttempt, incorrect);

            playingWord = promptPlayAgain();
        }
    }

    private void displayResults(int wordGamesPlayed, int correctFirstAttempt, int correctSecondAttempt, int incorrect) throws IOException {
        System.out.printf(
                "Game Over!\nWord games played: %d\nCorrect on First Attempt: %d\nCorrect on Second Attempt: %d\nIncorrect: %d\n",
                wordGamesPlayed, correctFirstAttempt, correctSecondAttempt, incorrect);

        LocalDateTime time = LocalDateTime.now();
        Score score = new Score(time, wordGamesPlayed, correctFirstAttempt, correctSecondAttempt, incorrect);

        Score.appendScoreToFile(score, "score.txt");
    }

    private boolean promptPlayAgain() {
        System.out.println("Play again? (Yes/No)");
        while (true) {
            String playAgain = sc.nextLine().trim().toLowerCase();
            if (playAgain.equals("yes")) {
                return true;
            } else if (playAgain.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please type 'Yes' or 'No':");
            }
        }
    }

}
