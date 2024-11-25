import java.util.*;

abstract class Creature {
    protected DNASequence dna;
    protected int health;
    protected String name;

    public Creature(DNASequence dna, String name) {
        this.dna = dna;
        this.name = name;
        this.health = 100;
    }

    public abstract void evolve();
    public abstract void interact(Creature other);

    // Add these methods to the base class
    public abstract String getName();
    public abstract int getHealth();
    public abstract void takeDamage(int amount);
    public abstract void heal(int amount);

    // Inner class for creature statistics
    public class Statistics {
        private int generations;
        private List<String> mutations;
        private int totalDamageDealt;
        private int totalHealthGained;

        public Statistics() {
            this.generations = 1;
            this.mutations = new ArrayList<>();
            this.totalDamageDealt = 0;
            this.totalHealthGained = 0;
        }

        public void addMutation(String mutation) {
            mutations.add(mutation);
        }

        public void recordDamageDealt(int damage) {
            totalDamageDealt += damage;
        }

        public void recordHealthGained(int health) {
            totalHealthGained += health;
        }

        public int getTotalDamageDealt() {
            return totalDamageDealt;
        }

        public int getTotalHealthGained() {
            return totalHealthGained;
        }
    }
}