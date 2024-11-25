import java.util.List;
import java.util.stream.Collectors;

public final class PredatorCreature extends Creature {
    private static final double EVOLUTION_RATE = 0.1;
    private static final int DAMAGE_AMOUNT = 20;
    private static final double HEALTH_STEAL_RATE = 0.5; // 50% of damage dealt is converted to health

    public PredatorCreature(DNASequence<Double> dna, String name) {
        super(dna, name);
    }

    @Override
    public void evolve() {
        DNASequence<Double> dnaCast = (DNASequence<Double>) dna;
        List<Double> oldSequence = dnaCast.getSequence();
        List<Double> newSequence = oldSequence.stream()
                .map(val -> val * (1 + EVOLUTION_RATE))
                .collect(Collectors.toList());
        dnaCast.setSequence(newSequence);
    }

    @Override
    public void interact(Creature other) {
        // Get average DNA value to determine strength
        DNASequence<Double> dnaCast = (DNASequence<Double>) dna;
        double avgDNA = dnaCast.getSequence().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        // Calculate damage based on DNA strength
        int actualDamage = (int)(DAMAGE_AMOUNT * (avgDNA / 100.0));

        // Apply damage to other creature
        other.takeDamage(actualDamage);

        // Heal predator based on damage dealt
        int healthGained = (int)(actualDamage * HEALTH_STEAL_RATE);
        this.heal(healthGained);

        System.out.printf("%s attacks %s for %d damage and gains %d health!%n",
                this.getName(), other.getName(), actualDamage, healthGained);
    }

    // Getters and additional methods
    @Override
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    public void heal(int amount) {
        this.health = Math.min(100, this.health + amount);
    }
}