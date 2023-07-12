package Part3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/*
 * BloomFilter, can say with certainty if a word is found in the vocabulary or not
 *
 */

public class BloomFilter {
    BitSet bitSet;
    int size;
    List<MessageDigest> MD_List;
    BigInteger num;

    /*
     * BloomFilter ctor
     * initializes size, and MD_List with encryption methods such as MD5,SHA1,SHA3
     */
    BloomFilter(int size, String... args) {

        bitSet = new BitSet(size);
        this.size = size;
        MD_List = new ArrayList<>();
        for (String string : args) {
            try {
                MD_List.add(MessageDigest.getInstance(string));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * this method create a hashcode and stores it in bts
     * from BTS we extract an int value that will be turned "on" in bitSet array
     */
    public void add(String string) {
        byte[] bts;
        for (MessageDigest m : this.MD_List) {
            bts = m.digest(string.getBytes());
            this.num = new BigInteger(bts);
            this.bitSet.set(Math.abs((num.abs().intValue()) % size), true);
        }
    }


    /*
     * similar to add function, this method created the hashcode and generated a bigInteger from bts byte array
     * with this num, we can go to the bitSet array and compare to know if string is stored in the BloomFilter or not.
     */
    public boolean contains(String string) {
        byte[] bts;

        for (MessageDigest m : this.MD_List) {
            bts = m.digest(string.getBytes());

            this.num = new BigInteger(bts);
            if (!this.bitSet.get(Math.abs(num.abs().intValue() % size)))
                return false;

        }
        return true;
    }
    /*
     * this method builds the string with StringBuilder depending on the bitSet
     * if its set or not, it then converts the byteString into a string.
     */
    @Override
    public String toString(){
        StringBuilder byteString = new StringBuilder();
        for(int i = 0; i < bitSet.length(); i++) {
            if (this.bitSet.get(i))
                byteString.append("1");
            else
                byteString.append("0");
        }
        return byteString.toString();
    }
}