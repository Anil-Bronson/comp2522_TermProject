import java.util.HashMap;
import java.util.Map;

/**
 * The {@code World} class represents a collection of countries,
 * allowing storage, retrieval, and management of {@code Country} objects.
 * <p>
 * Each country is stored in a {@code Map}, where the key is the country's name,
 * and the value is the corresponding {@code Country} object.
 * </p>
 */
public class World {

    /**
     * A map to store countries with their names as keys and {@code Country} objects as values.
     */
    private final Map<String, Country> countries;

    /**
     * Constructs a new {@code World} instance with an empty collection of countries.
     */
    public World() {
        countries = new HashMap<>();
    }

    /**
     * Adds a {@code Country} to the world.
     * If a country with the same name already exists, it will be replaced.
     *
     * @param country the {@code Country} object to be added
     */
    public void addCountry(Country country) {
        countries.put(country.getName(), country);
    }

    /**
     * Retrieves a {@code Country} from the world by its name.
     *
     * @param name the name of the country to retrieve
     * @return the {@code Country} object if found; {@code null} otherwise
     */
    public Country getCountry(String name) {
        return countries.get(name);
    }

    /**
     * Prints all countries in the world to the standard output.
     * Each country's information is printed using its {@code toString()} method.
     */
    public void printCountries() {
        for (Country country : countries.values()) {
            System.out.println(country);
        }
    }

    /**
     * Retrieves the entire collection of countries as a map.
     *
     * @return a map of all countries, where the key is the country name,
     *         and the value is the {@code Country} object
     */
    public Map<String, Country> getAllCountries() {
        return countries;
    }
}
