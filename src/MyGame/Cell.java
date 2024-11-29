final class Cell<T> {
    private T value;
    private boolean revealed;

    public Cell() {
        this.value = null;
        this.revealed = false;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    @Override
    public String toString() {
        if (revealed) {

            if (value == null) {
                return "O"; // 'O' for miss
            } else {
                return value.toString().charAt(0) + ""; // First character of the GameObject type
            }
        }
        return "."; // Return '.' if not revealed
    }

}
