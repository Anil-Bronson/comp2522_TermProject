
import java.util.HashMap;
import java.util.Map;

public class World {
    private final Map<String, Country> countries;

    public World() {
        countries = new HashMap<>();
    }

    public void addCountry(Country country) {
        countries.put(country.getName(), country);
    }

    public Country getCountry(String name) {
        return countries.get(name);
    }

    // Method to print all countries
    public void printCountries() {
        for (Country country : countries.values()) {
            System.out.println(country);
        }
    }

    public Map<String, Country> getAllCountries() {
        return countries;
    }
}




