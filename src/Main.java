import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Main {

    public static void main(final String[] args) throws IOException {

        boolean stillPlaying = true;
        Scanner sc = new Scanner(System.in);
        World earth = new World();

        while (stillPlaying) {

            System.out.println("Type W to play the Word game");
            System.out.println("Type N to play the Number game");
            System.out.println("Type M to play <my game>");
            System.out.println("Type Q to quit:");

            String input = sc.nextLine();

            switch (input.toUpperCase()) {

                case "W" -> {
                    WordGame wordGame = new WordGame(earth, sc);
                    wordGame.play();
                }

                case "N" -> {
                    NumberGame.launch(NumberGame.class, args);
                }

                case "M" -> System.out.println("<My game> not implemented yet!");

                case "Q" -> stillPlaying = false;

                default -> System.out.println("Invalid input, try again.");
            }
        }
        System.out.println("Thank you for playing!");
    }
}
