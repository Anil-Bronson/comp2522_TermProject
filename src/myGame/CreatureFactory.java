public class CreatureFactory {
    public static Creature createCreature(String type, DNASequence<?> dna, String name) {
        return switch (type.toLowerCase()) {
            case "predator" -> new PredatorCreature((DNASequence<Double>) dna, name);
            // Add more creature types
            default -> throw new IllegalArgumentException("Unknown creature type: " + type);
        };
    }
}