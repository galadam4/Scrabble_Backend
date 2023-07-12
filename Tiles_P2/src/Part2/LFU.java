package Part2;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * LFU implements the CRP interface, meaning it implement add() and remove()
 * same as LRU, but this time it will manage the cache abit different
 * LRU will keep track of how many times each word was used
 * and delete the least used word
 */

public class LFU implements CacheReplacementPolicy {

    int maxSize = 100;
    Map<String, Integer> lfuMap = new LinkedHashMap<>();

    //using default ctor

    /*
     * adding to cache, checking if its already in the cache, if it is update the value
     * checking if cache is full, if it is, call lru.remove()
     */
    @Override
    public void add(String word) {

        if (lfuMap.containsKey(word)) {
            lfuMap.put(word, lfuMap.get(word) + 1);
        } else {
            if (lfuMap.size() == maxSize) {
                remove();
            }
            lfuMap.put(word, 1);
        }


    }
    /*
     * implements the remove method from crp
     * removing the least frequentely queried word in the cache
     */
    @Override
    public String remove() {
        String minKey = null;
        int minFreq = Integer.MAX_VALUE;

        for (String key : lfuMap.keySet()) {
            if (lfuMap.get(key) < minFreq) {
                minKey = key;
                minFreq = lfuMap.get(key);
            }
        }
        lfuMap.remove(minKey);
        return minKey;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
