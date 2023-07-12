package Part1;

import java.util.Arrays;
import java.util.Objects;

public class Word
{
    private final Tile[] word;
    private final int row;
    private final int col;
    private final boolean vertical;


    /*
     * word ctor
     * initializes word, row col and a boolean to keep track of which way the word is heading.
     */
    public Word(Tile[] word, int row, int col, boolean vertical)
    {
        this.word = word.clone();
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    /* getters for word class */

    public Tile[] getWord() {
        return word;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return row == word1.row && col == word1.col && vertical == word1.vertical && Arrays.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col, vertical);
        result = 31 * result + Arrays.hashCode(word);
        return result;
    }
}
