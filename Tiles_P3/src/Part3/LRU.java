package Part3;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/*
 * LRU implement the CRP meaning it implements add() and remove()
 * the implementation of add() and remove() functions dictates how the cache will be handled
 * LRU meaning least recently used, keeps track of which words used and which was the least recently
 * removing the least recently used word
 */

public class LRU implements CacheReplacementPolicy {
    LinkedHashSet<String> lruSet = new LinkedHashSet<>();
    int maxSize= 400;

//using default ctor

    /*
     * impleneting add from the crp, using LRU logic, meaning the cache contains the least recently used words
     * if the word is in the cache, removing it, and adding again to show it just been "used"
       maintaining insertion order.
     */
    @Override
    public void add(String word) {
        if (lruSet.contains(word)) {
            lruSet.remove(word);
        }
        if (lruSet.size() == maxSize) {
            remove();
        }
        lruSet.add(word);


    }



    /*
     * implementing the remove() from the CRP,
     * removing the first word in the database,
     * that word is the least used word and the method returns its value
     */
    @Override
    public String remove() {

        Iterator<String> iterator = lruSet.iterator();
        String last = iterator.next();
        lruSet.remove(last);
        return last;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
