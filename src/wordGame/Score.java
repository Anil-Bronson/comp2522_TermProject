import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's score in the word game.
 * <p>
 * Each score includes details about the number of games played, attempts,
 * and the overall performance, along with the date and time of the session.
 * Scores can be saved to or loaded from a file.
 */
public class Score {

    protected final String dateTimePlayed;
    private final int numGamesPlayed;
    private final int numCorrectFirstAttempt;
    private final int numCorrectSecondAttempt;
    private final int numIncorrectTwoAttempts;

    private final static int SPLIT_LIMIT  = 2;
    private final static int POSITION_ONE = 1;


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructs a Score object with the specified details.
     *
     * @param dateTime  the date and time of the score
     * @param games     the number of games played
     * @param first     the number of correct first attempts
     * @param second    the number of correct second attempts
     * @param incorrect the number of incorrect answers
     */
    public Score(final LocalDateTime dateTime, final int games, final int first, final int second, final int incorrect) {
        this.dateTimePlayed          = dateTime.format(FORMATTER);
        this.numGamesPlayed          = games;
        this.numCorrectFirstAttempt  = first;
        this.numCorrectSecondAttempt = second;
        this.numIncorrectTwoAttempts = incorrect;
    }

    /**
     * Appends a score to a file.
     *
     * @param score    the score to append
     * @param fileName the name of the file
     */
    public static void appendScoreToFile(final Score score, final String fileName) {

        try (final FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(score.toString());
            writer.write(System.lineSeparator());
        } catch (final IOException e) {
            System.out.println("Error handling the file: " + e.getMessage());
        }
    }

    /**
     * Reads scores from a file and returns a list of Score objects.
     *
     * @param filename the name of the file
     * @return a list of Score objects
     * @throws IOException if an error occurs while reading the file
     */
    public static List<Score> readScoresFromFile(final String filename) throws IOException {
        final List<Score> scores;
        scores = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String dateTime = null;
            String line;

            //must be initialized for the score
            int gamesPlayed             = 0;
            int correctFirstAttempts    = 0;
            int correctSecondAttempts   = 0;
            int incorrectAttempts       = 0;


            while ((line = reader.readLine()) != null) {

                if (line.startsWith("Date and Time:")) {
                    dateTime = line.split(": ", SPLIT_LIMIT)[POSITION_ONE];
                }

                else if (line.startsWith("Games Played:")) {
                    gamesPlayed = Integer.parseInt(line.split(": ")[POSITION_ONE]);
                }

                else if (line.startsWith("Correct First Attempts:")) {
                    correctFirstAttempts = Integer.parseInt(line.split(": ")[POSITION_ONE]);
                }

                else if (line.startsWith("Correct Second Attempts:")) {
                    correctSecondAttempts = Integer.parseInt(line.split(": ")[POSITION_ONE]);
                }

                else if (line.startsWith("Incorrect Attempts:")) {
                    incorrectAttempts = Integer.parseInt(line.split(": ")[POSITION_ONE]);
                }

                else if (line.startsWith("Score:")) {
                    assert dateTime != null;
                    final LocalDateTime dateTimeParsed = LocalDateTime.parse(dateTime, FORMATTER);

                    final Score score;
                    score = new Score(dateTimeParsed, gamesPlayed, correctFirstAttempts, correctSecondAttempts, incorrectAttempts);

                    scores.add(score);
                }
            }
        }
        return scores;
    }

    /**
     * Returns a string representation of the score details.
     *
     * @return a formatted string containing the score details
     */
    @Override
    public String toString() {
        return String.format(
                """
                        Date and Time: %s
                        Games Played: %d
                        Correct First Attempts: %d
                        Correct Second Attempts: %d
                        Incorrect Attempts: %d
                        Score: %d points
                        """,
                dateTimePlayed,
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts,
                getScore() // Dynamically calculate score when calling toString
        );
    }

    /**
     * Checks if the user has set a new high score and displays the result.
     *
     * @param scores    a list of previous scores
     * @param userScore the user's current score
     */
    public static void checkHighScoreAndNotify(List<Score> scores, Score userScore) {

        final Score highScore;
        highScore = scores.stream()
                .max((s1, s2) -> Integer.compare(s1.getScore(), s2.getScore()))
                .orElse(null);

        if (highScore == null || userScore.getScore() > highScore.getScore()) {
            System.out.println("Congratulations! You set a new high score!");
        }

        else {
            System.out.printf(
                    "You did not beat the high score of %d points per game from %s.%n",
                    highScore.getScore(),
                    highScore.dateTimePlayed
            );
        }
    }

    /**
     * Calculates the score based on the number of correct attempts.
     *
     * @return the calculated score
     */
    public int getScore() {
        return numCorrectFirstAttempt * 2 + numCorrectSecondAttempt;
    }
}
