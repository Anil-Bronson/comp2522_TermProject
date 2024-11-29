import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The Country class represents a country with a name, capital city, and associated facts.
 * It provides methods to retrieve these details and supports parsing country data
 * from text files to populate a World object.
 *
 * <p>
 * Example of country data format in files:
 * <pre>
 * CountryName: CapitalCity
 * Fact1
 * Fact2
 * ...
 * </pre>
 * Each country entry begins with the country's name and its capital, followed by facts.
 * Multiple countries can be defined in a single file, separated by a colon (:).
 * </p>
 */
public class Country {

    private final String name;
    private final String capitalCityName;
    private final String[] facts;

    //constants
    private final static int SPLIT_LIMIT        = 2;
    private final static int COUNTRY_NAME_SPLIT = 0;
    private final static int CAPITAL_NAME_SPLIT = 1;


    /**
     * Processes all `.txt` files in the given directory to extract country data
     * and adds them to the provided World object.
     *
     * Each file is expected to contain information about countries in the format:
     * <pre>
     * CountryName: CapitalCity
     * Fact1
     * Fact2
     * ...
     * </pre>
     * Multiple countries can be defined within the same file, separated by lines containing a colon (:).
     *
     * @param dirPath the path of the directory containing the files
     * @param world   the World object to which the countries will be added
     * @throws RuntimeException if an error occurs while reading or processing the files
     */
    public static void processFiles(final Path dirPath, final World world) {
        try {
            Files.walk(dirPath)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> {
                        try {
                            final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

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
                                        world.addCountry(new Country(countryName, capitalName, facts.toArray(new String[COUNTRY_NAME_SPLIT])));

                                        facts.clear(); // Reset facts for the new country
                                    }

                                    // Parse country and capital
                                    String[] parts = line.split(":", 2);
                                    countryName = parts[COUNTRY_NAME_SPLIT].trim();
                                    capitalName = parts[CAPITAL_NAME_SPLIT].trim();
                                } else {
                                    // Add facts to the current country
                                    facts.add(line);
                                }
                            }

                            // Add the last country after looping
                            if (countryName != null) {
                                final Country country;
                                country = new Country(countryName, capitalName, facts.toArray(new String[COUNTRY_NAME_SPLIT]));

                                world.addCountry(country);
                            }

                        } catch (final IOException e) {
                            throw new RuntimeException("Error reading file: " + path, e);
                        }
                    });
        } catch (final IOException e) {
            throw new RuntimeException("Error processing files in directory: " + dirPath, e);
        }
    }

    /**
     * Constructs a Country object with the specified name, capital city, and facts.
     *
     * @param name            the name of the country
     * @param capitalCityName the name of the capital city
     * @param facts           an array of facts about the country
     */
    public Country(final String name, final String capitalCityName, final String[] facts) {
        this.name            = name;
        this.capitalCityName = capitalCityName;
        this.facts           = facts;
    }

    /**
     * Retrieves the name of the country.
     *
     * @return the country's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the name of the capital city.
     *
     * @return the capital city's name
     */
    public String getCapitalCityName() {
        return capitalCityName;
    }

    /**
     * Retrieves an array of facts about the country.
     *
     * @return an array of facts
     */
    public String[] getFacts() {
        return facts;
    }

    /**
     * Returns a string representation of the Country object, including its name,
     * capital city, and a list of its facts.
     *
     * @return a formatted string with the country's details
     */
    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder();

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
