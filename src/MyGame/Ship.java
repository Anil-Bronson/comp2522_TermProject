final class Ship extends GameObject {
    private boolean revealed;

    public Ship() {
        this.type = "Ship";
        this.revealed = false;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    @Override
    public void interact(Player player) {
        if (revealed) {
            System.out.println("You've already hit this part of the ship!");
        } else {
            System.out.println("HIT! You've hit a part of the ship.");
            reveal(); // Mark the ship as revealed
        }
    }
}
