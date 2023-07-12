package Part1;


import java.util.Arrays;
import java.util.Objects;

public class Tile {

    final public char letter;
    final public int score;

    /*
     * Tile ctor initializes letter and score
     */
    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    /*
     * class bag, Holds all the tiles in the game
     * as a finite amount, and this class also helps keep count of the tiles used
     */
    public static class Bag
    {
        private static Bag bag;
        int[] countLetters;
        final int[] final_countLetters;
        Tile[] lettersArray;

        /*
         * Bag ctor
         * initializes the countLetters array, keep count of the "live game" tiles there are in the game
         * initializes final_countLetters which acts a static array, will be used to make sure everything is in check.
         * initializes lettersArray, which stores all the tiles and their score acts as the "tile bag"
         */
        private Bag()
        {

            countLetters = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 4, 2, 6,
                      8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

            final_countLetters = new int[26];
            System.arraycopy(countLetters,0, final_countLetters,0,26);


                lettersArray = new Tile[]{
                        new Tile('A', 1), new Tile('B', 3),
                        new Tile('C', 3), new Tile('D', 2),
                        new Tile('E', 1), new Tile('F', 4),
                        new Tile('G', 2), new Tile('H', 4),
                        new Tile('I', 1), new Tile('J', 8),
                        new Tile('K', 5), new Tile('L', 1),
                        new Tile('M', 3), new Tile('N', 1),
                        new Tile('O', 1), new Tile('P', 3),
                        new Tile('Q', 10), new Tile('R', 1),
                        new Tile('S', 1), new Tile('T', 1),
                        new Tile('U', 1), new Tile('V', 4),
                        new Tile('W', 4), new Tile('X', 8),
                        new Tile('Y', 4), new Tile('Z', 10)};


        }

        //Generates a random int, and takes a Tile from the bag,
        // returns null if no tiles available
        public Tile getRand()
        {
            int min=0, max =25, random_int;
            // checks if numOfLetters is an empty array, if empty return null;
            if(Arrays.stream(bag.countLetters).sum() == 0)
            {
                System.out.println("Bag is empty, cannot take new tile");
                return null;
            }
            //if not, generate random_int, and continue to do so if
            //the random_int isn't in the array (meaning we cant take the tile that random_int "took" from the bag)
            else
            {
                do
                    //generate a random number between min && max values (0-25)
                    random_int = (int) Math.floor(Math.random() * (max - min + 1 ) + min);
                while
                (bag.countLetters[random_int] == 0);

                bag.countLetters[random_int]--;
                return bag.lettersArray[random_int];
            }
        }


        //findCharIndex, find the index of a char in the array of chars
        //a "helper method" for getTile
        private int findCharIndex(char letter)
        {
            for(int i = 0; i < 26; i++)
            {
                if(bag.lettersArray[i].letter == letter)
                    return i;
            }
            return -1;

        }


        /*
         * acts the same as getRand, but the user is giving us the letter he wants to take out of the bag.
         * method will return -1 if tile not found / tile count is at 0
         */
        public Tile getTile(char letter)
        {
            //find the index on the char in the array
            int index = findCharIndex(letter);

            if(index == -1 || bag.countLetters[index] == 0)
                return null;

            //update count of tiles in numOfLetters array
            bag.countLetters[index]--;
            return bag.lettersArray[index];
        }

        /*
         * insert a tile into the bag
         * essentially just updates the countLetters array.
         */
        public void put(Tile t)
        {
            int index = findCharIndex(t.letter);
            if(index != -1)
            {
                //compares vs the final array of letters, so we're not adding to many tiles(!)
                if(bag.countLetters[index] < bag.final_countLetters[index])
                    bag.countLetters[index] = bag.countLetters[index]+1;
            }
        }

        //size return how many letters remain in bag
        public int size()
        {
               return Arrays.stream(bag.countLetters).sum();
        }

        //GetQuantities returns a clone of countLetters array
        public int[] getQuantities()
        {
            return bag.countLetters.clone();
        }


        /*
         * implementation of the singleton idea,
         * checks if there's already a live game going on, and if not it creates one.
         */
        public static Bag getBag()
        {
            if(bag == null)
                bag = new Bag();

            return bag;
        }
    }
}
