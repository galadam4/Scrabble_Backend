package Part3;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/*
 * IOSearcher, is searching through the files word by word, and returns boolean value
 * has only a static method search
 */
public class IOSearcher {

    public static boolean search(String is, String... fileNames) {
        for (String f : fileNames) {
            try {
                Scanner in = new Scanner(new BufferedReader(new FileReader(f)));
                while (in.hasNext()) {
                    if (in.next().contains(is)) {
                        in.close();
                        return true;
                    }
                }
                in.close();
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
        return false;
    }
}