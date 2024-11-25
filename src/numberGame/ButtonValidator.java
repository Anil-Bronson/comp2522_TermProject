import java.util.ArrayList;

public interface ButtonValidator {
    boolean isButtonValid(final int position, final ArrayList<Integer> validPositions);

    int getButtonValue();
}
