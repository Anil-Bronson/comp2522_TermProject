import java.util.ArrayList;

/**
 * The InactiveGridButton class represents a button on the game grid that is initially inactive.
 * This button has a default value of 0 and is visually represented with an empty bracket "[ ]".
 * It can be activated later based on game conditions. The button validates whether its position is
 * part of the valid positions in the current game state.
 * <p>
 * This class inherits from the GameGridButton class and implements the isButtonValid method to
 * check if the button's position is valid for interaction, and the getButtonValue method to
 * return the button's value.
 * </p>
 */

public class InactiveGridButton extends GameGridButton {

    private static final int DEFAULT_VALUE = 0;
    private final int buttonValue;

    /**
     * Constructs an InactiveGridButton with the default value of 0 and an empty text representation "[ ]".
     */
    public InactiveGridButton() {
        this.setText("[ ]");
        this.buttonValue = DEFAULT_VALUE;
    }

    /**
     * Determines if the button at the specified position is valid based on the provided list of valid positions.
     *
     * @param position the position of the button to validate
     * @param validPositions the list of valid positions
     * @return true if the position is valid, false otherwise
     */
    @Override
    public boolean isButtonValid(final int position, final ArrayList<Integer> validPositions) {
        return validPositions.contains(position);
    }

    /**
     * Returns the value of the button. Since this is an inactive button, it always returns the default value 0.
     *
     * @return the value of the button
     */
    @Override
    public int getButtonValue() {
        return buttonValue;
    }
}
