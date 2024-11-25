import java.util.ArrayList;

public class InactiveGridButton extends GameGridButton {
    private static final int DEFAULT_VALUE = 0;
    private final int buttonValue;

    public InactiveGridButton() {
        this.setText("[ ]");
        this.buttonValue = DEFAULT_VALUE;
    }

    @Override
    public boolean isButtonValid(final int position, final ArrayList<Integer> validPositions) {
        return validPositions.contains(position);
    }

    @Override
    public int getButtonValue() {
        return buttonValue;
    }
}
