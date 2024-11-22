import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Score {
    private final String dateTimePlayed;
    private final int numGamesPlayed;
    private final int numCorrectFirstAttempt;
    private final int numCorrectSecondAttempt;
    private final int numIncorrectTwoAttempts;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int score;

    // Constructor with 5 parameters
    public Score(LocalDateTime dateTime, int games, int first, int second, int incorrect) {
        this.dateTimePlayed = dateTime.format(FORMATTER);
        this.numGamesPlayed = games;
        this.numCorrectFirstAttempt = first;
        this.numCorrectSecondAttempt = second;
        this.numIncorrectTwoAttempts = incorrect;
    }

    // Method to append score to file
    public static void appendScoreToFile(Score score, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(score.toString());
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error handling the file: " + e.getMessage());
        }
    }

    // Method to read scores from a file and return a list of Score objects
    public static List<Score> readScoresFromFile(String filename) throws IOException {
        List<Score> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Score.parseScore(line));
            }
        }
        return scores;
    }

    // Method to parse a serialized score string
    private static Score parseScore(String line) {
        String[] parts = line.split("\n");
        if (parts.length < 6) return null;

        LocalDateTime dateTime = LocalDateTime.parse(parts[0].split(":")[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int gamesPlayed = Integer.parseInt(parts[1]);
        int correctFirstAttempts = Integer.parseInt(parts[2]);
        int correctSecondAttempts = Integer.parseInt(parts[3]);
        int incorrectAttempts = Integer.parseInt(parts[4]);
        int score = Integer.parseInt(parts[5].split(" ")[1]);

        return new Score(dateTime, gamesPlayed, correctFirstAttempts, correctSecondAttempts, incorrectAttempts);
    }

    private void setScore(int score) {
        this.score = score;
    }

    // Override the toString method to return a formatted string
    @Override
    public String toString() {
        return String.format(
                "Date and Time: %s\n" +
                        "Games Played: %d\n" +
                        "Correct First Attempts: %d\n" +
                        "Correct Second Attempts: %d\n" +
                        "Incorrect Attempts: %d\n" +
                        "Score: %d points\n",
                dateTimePlayed,
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts,
                getScore() // Dynamically calculate score when calling toString
        );
    }

    // Calculate score based on correct attempts
    public int getScore() {
        return numCorrectFirstAttempt * 2 + numCorrectSecondAttempt;
    }
}
