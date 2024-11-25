import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Country {

    private final String name;
    private final String capitalCityName;
    private String facts[];

    public static void processFiles(final Path dirPath, World world) {
        try {
            Files.walk(dirPath)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> {
                        try {
                            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

                            String countryName = null;
                            String capitalName = null;

                            final List<String> facts;
                            facts = new ArrayList<>();

                            for (String line : lines) {
                                line = line.trim(); // Remove leading/trailing spaces

                                // Skip empty lines
                                if (line.isEmpty()) {
                                    continue;
                                }

                                if (line.contains(":")) {
                                    // New country starts here
                                    if (countryName != null) {
                                        // Save the previous country
                                        world.addCountry(new Country(countryName, capitalName, facts.toArray(new String[0])));

                                        facts.clear(); // Reset facts for the new country
                                    }

                                    // Parse country and capital
                                    String[] parts = line.split(":", 2);
                                    countryName = parts[0].trim();
                                    capitalName = parts[1].trim();
                                } else {
                                    // Add facts to the current country
                                    facts.add(line);
                                }
                            }

                            // Add the last country after looping
                            if (countryName != null) {
                                world.addCountry(new Country(countryName, capitalName, facts.toArray(new String[0])));
                            }

                        } catch (IOException e) {
                            throw new RuntimeException("Error reading file: " + path, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error processing files in directory: " + dirPath, e);
        }
    }

    public Country(String name, String capitalCityName, String[] facts) {
        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    public String getName() {
        return name;
    }

    public String getCapitalCityName() {
        return capitalCityName;
    }

    public String[] getFacts() {
        return facts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country: ").append(name)
                .append(" Capital:  \n").append(capitalCityName)
                .append(" Facts: ");
        for (String fact : facts) {
            sb.append("\n  - ").append(fact);  // Print each fact on a new line
        }
        System.out.println("\n");
        return sb.toString();
    }
}
