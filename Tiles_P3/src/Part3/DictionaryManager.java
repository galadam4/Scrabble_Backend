package Part3;

import java.util.HashMap;
import java.util.Map;

/*
 * DictionaryManager class provides functionality to query and challenge dictionaries stored in a map
 * The class also follows the Singleton pattern, allowing only one instance of DictionaryManager to exist
 */
public class DictionaryManager {
    private static DictionaryManager instance;
    private Map<String, Dictionary> map;

    public DictionaryManager() {
        map = new HashMap<>();
    }

    /*
     * implementing the singleton principle, making sure there's only one instance
     * dictionaryManager
     */
    public static DictionaryManager get() {
        if (instance == null)
            instance = new DictionaryManager();
        return instance;
    }

    /*
     * similarly to query is implemented in Dictionary class
     * this method, receives a string arg, where the first word represent the dictionary
     * and the last word represent the actual word we're querying about
     * this method, checks if the dictionary is in the database, if not it adds it
     */
    public boolean query(String... args) {
        //if the last args is in map
        boolean contain = false;
        String searchWord = args[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            if (!map.containsKey(args[i])) {
                map.put(args[i], new Dictionary(args[i]));
            }
            if (map.get(args[i]).query(searchWord)) {
                contain = true;
            }
        }
        return contain;
    }

    /*
     * this method is similar to the query method. It takes variable arguments, where the last argument represents the test string.
     * it checks if dictionaries are already in our map, if not it added it the map with a new dictionary object
     * this method searches inside the dictionaries for the word by invoking the challenge method in Dictionary.
     *
     */
    public boolean challenge(String...args) {
        String testString = args[args.length - 1];
        String[] argsList = new String[args.length-1];
        for (int i = 0; i < args.length - 1; i++) {
            argsList[i] = new String(args[i]);
        }
        for (int i = 0; i < args.length - 1; i++) {
            if (!map.containsKey(args[i])) {
                map.put(args[i], new Dictionary(args[i]));
            }
            if(map.get(args[i]).challenge(testString))return true;
        }
        return false;
    }


    
    public int getSize() {
        return map.size();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
