import java.util.ArrayList;

/**
 * Represents a grid button that has been activated. It displays its value and is disabled.
 */
public class ActivatedGridButton extends GameGridButton {
        private final int buttonValue;

        public ActivatedGridButton(final int value) {
                this.setText(String.valueOf(value));
                this.buttonValue = value;
                this.setDisable(true);
        }

        @Override
        public boolean isButtonValid(final int position, final ArrayList<Integer> validPositions) {
                return false; // Activated buttons are no longer valid for interaction.
        }

        @Override
        public int getButtonValue() {
                return buttonValue;
        }
}
