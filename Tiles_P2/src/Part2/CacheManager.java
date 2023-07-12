package Part2;


import java.util.HashSet;

/*
 * The ChacheManager will hold the most frequent queries
 * This way, we can quickly return an answer if its in the CacheManager,
 * If its not, we'll move it to the next filtering factor
 */
public class CacheManager {
    int capacity;
    CacheReplacementPolicy crp;
    HashSet<String> cache;

    CacheManager(int capacity,CacheReplacementPolicy crp){
        this.capacity = capacity;
        this.crp = crp;
        this.cache = new HashSet<>();
    }

    //a query, using contains method will return true if word is in cache.
    boolean query(String word)
    {
        return  cache.contains(word);
    }

    /*
     * checks if a word in the cache,
     * If not it will use the crp interface to remove and add the word to the cache
     */
    void add(String word){
        if(!cache.contains(word)) {
            if (this.capacity == this.cache.size())
                this.cache.remove(this.crp.remove());
            this.cache.add(word);
            this.crp.add(word);
        }
    }
}
