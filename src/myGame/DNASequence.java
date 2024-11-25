import java.util.List;
import java.util.stream.Collectors;

public class DNASequence<T extends Number> {
    private List<T> sequence;
    private final int maxLength;

    public DNASequence(List<T> sequence, int maxLength) {
        if (sequence.size() > maxLength) {
            throw new DNASequenceTooLongException("DNA sequence exceeds maximum length");
        }
        this.sequence = sequence;
        this.maxLength = maxLength;
    }

    // Functional interface for DNA mutations
    @FunctionalInterface
    interface DNAMutator<T> {
        T mutate(T value);
    }

    public void mutate(DNAMutator<T> mutator) {
        sequence = sequence.stream()
                .map(mutator::mutate)
                .collect(Collectors.toList());
    }

    // Add this method to get sequence value
    public List<T> getSequence() {
        return sequence;
    }

    // Add this method to set sequence value
    public void setSequence(List<T> sequence) {
        this.sequence = sequence;
    }
}