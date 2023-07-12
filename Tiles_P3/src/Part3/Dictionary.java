package Part3;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    BloomFilter bf;
    CacheManager cm_NOT_Exist;
    CacheManager cm_Exist;
    public List<String> filesList = new ArrayList<>();



    /*
     * Dictionary ctor
     * initializes CacheManager with LRU and LFU for words that exists and don't exist
     * initialize bloomFilter and adding the words found in all the files in fileNames
     * to the bloomFilter
     */

    public Dictionary(String...args) {
        cm_Exist = new CacheManager(400, new LRU());
        cm_NOT_Exist = new CacheManager(100, new LFU());
        bf = new BloomFilter(256, "MD5", "SHA1");

        for (String f : args) {
            filesList.add(f);
            try {
                Scanner in = new Scanner(new BufferedReader(new FileReader(f)));
                while (in.hasNext()) {
                    bf.add(in.next());
                }
                in.close();
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }

    /*
     * query, searches in the caches of LRU and LFU
     * returns true/false if word is found and editing the correct cache
     */

    public boolean query(String is) {
        if (cm_Exist.query(is))
            return true;
        if (cm_NOT_Exist.query(is))
            return false;
        if (bf.contains(is)) {
            cm_Exist.add(is);
        } else
            cm_NOT_Exist.add(is);
        return (bf.contains(is));
    }

    /*
     * if words not in the LRU & LFU cache,
     * the challenge method will call IOSearcher to search through the files
     */
    public boolean challenge(String lazy) {
        boolean contain = false;
        //for (String f : filesList) {
        for(int i = 0; i < filesList.size(); i++) {
            if (IOSearcher.search(lazy, filesList.get(i))) {
                cm_Exist.add(lazy);
                contain = true;
            }
            else
                cm_NOT_Exist.add(lazy);
        }
        return contain;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}


