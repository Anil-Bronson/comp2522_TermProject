import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DNAGameTest {
    @Test
    void testDNASequenceCreation() {
        List<Double> sequence = Arrays.asList(1.0, 2.0, 3.0);
        DNASequence<Double> dna = new DNASequence<>(sequence, 5);
        Assertions.assertNotNull(dna);
    }

    @Test
    void testDNASequenceTooLongException() {
        List<Double> sequence = Arrays.asList(1.0, 2.0, 3.0);
        Assertions.assertThrows(DNASequenceTooLongException.class, () -> {
            new DNASequence<>(sequence, 2);
        });
    }

    @Test
    void testCreatureFactory() {
        List<Double> sequence = Arrays.asList(1.0, 2.0, 3.0);
        DNASequence<Double> dna = new DNASequence<>(sequence, 5);
        Creature creature = CreatureFactory.createCreature("predator", dna, "Test Predator");
        Assertions.assertTrue(creature instanceof PredatorCreature);
    }
}