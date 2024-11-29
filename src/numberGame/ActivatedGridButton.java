import java.util.ArrayList;

/**
 * The ActivatedGridButton class represents a grid button that has been activated
 * and is no longer valid for interaction. It is visually displayed with its value
 * and is disabled to prevent further actions.
 *
 * <p>
 * This class extends the GameGridButton class and overrides the isButtonValid and
 * getButtonValue methods. Once activated, the button's state is locked, and it
 * cannot be interacted with.
 * </p>
 */

public class ActivatedGridButton extends GameGridButton {
        private final int buttonValue;

        /**
         * Constructs an ActivatedGridButton with the given value.
         * The button's text is set to the value, and the button is disabled.
         *
         * @param value the value to be displayed on the button
         */
        public ActivatedGridButton(final int value) {
                this.setText(String.valueOf(value));
                this.buttonValue = value;
                this.setDisable(true);
        }

        /**
         * Determines if the button is valid for interaction.
         * Since the button is activated, it is always invalid for interaction.
         *
         * @param position the position of the button in the grid
         * @param validPositions the list of valid positions for interaction
         * @return false, as activated buttons are no longer valid for interaction
         */
        @Override
        public boolean isButtonValid(final int position,
                                     final ArrayList<Integer> validPositions) {
                return false; // Activated buttons are no longer valid for interaction.
        }

        /**
         * Gets the value of the activated button.
         *
         * @return the value of the button
         */
        @Override
        public int getButtonValue() {
                return buttonValue;
        }
}
