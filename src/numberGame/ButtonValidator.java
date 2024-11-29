import java.util.ArrayList;

/**
 * The ButtonValidator interface defines the contract for validating and retrieving
 * values from buttons in a game grid. Any class implementing this interface must
 * provide the logic for determining the validity of a button and returning its value.
 * <p>
 * This interface is typically used by button classes to enforce consistency and
 * ensure that only valid buttons can be interacted with while also providing
 * access to the button's value.
 * </p>
 */

public interface ButtonValidator {
    /**
     * Validates whether the button at the specified position is valid for interaction.
     *
     * @param position the position of the button in the grid
     * @param validPositions a list of valid positions for interaction
     * @return true if the button is valid for interaction, false otherwise
     */
    boolean isButtonValid(final int position,
                          final ArrayList<Integer> validPositions);

    /**
     * Gets the value of the button.
     *
     * @return the value of the button
     */
    int getButtonValue();

}
