import java.nio.file.*;
import java.util.*;

class Ecosystem {
    private static Ecosystem instance;
    private final Map<String, List<Creature>> habitats;

    private Ecosystem() {
        habitats = new HashMap<>();
    }

    public static Ecosystem getInstance() {
        if (instance == null) {
            instance = new Ecosystem();
        }
        return instance;
    }

    public void addCreature(String habitat, Creature creature) {
        habitats.computeIfAbsent(habitat, k -> new ArrayList<>()).add(creature);
    }

    // Save ecosystem state
    public void saveState(String filename) throws Exception {
        Path path = Paths.get(filename);
        // Implementation for saving state
    }
}