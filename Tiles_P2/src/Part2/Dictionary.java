package Part2;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
 * Dictionary class implements all we've worked on so far, the cache managing, bloom filter
 * and  IOSearcher.
 */

public class Dictionary {
    BloomFilter bf;
    CacheManager cm_NOT_Exist;
    CacheManager cm_Exist;
    List<String> filesList = new ArrayList<>();

    /*
     * Dictionary ctor
     * initializes CacheManager with LRU and LFU for words that exists and don't exist
     * initialize bloomFilter and adding the words found in all the files in fileNames
     * to the bloomFilter
     */
    Dictionary(String... fileNames){
        cm_Exist = new CacheManager(400,new LRU());
        cm_NOT_Exist = new CacheManager(100,new LFU());
        bf = new BloomFilter(256,"SHA1","MD5");

        for(String fileName : fileNames)
        {
            Scanner myScanner = null;
            try{
                myScanner = new Scanner(new BufferedReader(new FileReader(fileName)));
                while(myScanner.hasNext())
                    bf.add(myScanner.next());
                filesList.add(fileName);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            assert myScanner != null;
            myScanner.close();
        }
    }

    /*
     * query, searches in the caches of LRU and LFU
     * returns true/false if word is found and editing the correct cache
     */
    public boolean query(String word){
        if(cm_Exist.query(word))
            return true;
        if(cm_NOT_Exist.query(word))
            return false;
        if(bf.contains(word)) {
            cm_Exist.add(word);
            return true;
        }
        else{
            cm_NOT_Exist.add(word);
            return false;
        }
    }

    /*
     * if words not in the LRU & LFU cache,
     * the challenge method will call IOSearcher to search through the files
     */
    public boolean challenge(String word){
        String[] array = new String[this.filesList.size()];
        for(int i = 0; i < this.filesList.size(); i++)
            array[i] = this.filesList.get(i);
        if(IOSearcher.search(word,array)){
            this.cm_Exist.add(word);
            this.bf.add(word);
            return true;
        }
        else {
            this.cm_NOT_Exist.add(word);
            return false;
        }



    }
}

