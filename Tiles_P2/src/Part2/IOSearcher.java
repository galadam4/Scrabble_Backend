package Part2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/*
 * IOSearcher, is searching through the files word by word, and returns boolean value
 * has only a static method search
 */
public class IOSearcher {
     public static boolean search(String word, String... fileNames)
     {
        for (String s : fileNames) {
            Scanner myScanner = null;
            try {
                myScanner = new Scanner(new BufferedReader(
                        new FileReader(s)));
                while (myScanner.hasNext())
                    if (myScanner.next().equals(word)) {
                        myScanner.close();
                        return true;
                    }
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            myScanner.close();
        }

        return false;

     }

}
