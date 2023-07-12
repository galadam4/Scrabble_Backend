package Part1;


import java.util.ArrayList;

public class Board {

    //Declaring variables
    int boardWidth = 15;
    int boardLength = 15;
    private static Board game;
    private final Tile[][] liveBoard;
    private boolean firstWord;
    private final String[][] scoreBoard;
    private final ArrayList<Word> usedWords;



    /*
     * Initialize liveBoard, scoreboard
     * Initialize live_words
     * set firstWord to true
     * and create the scoreBoard
     */
    private Board() {
        firstWord = true;
        usedWords = new ArrayList<>();
        liveBoard = new Tile[boardLength][boardWidth];
        scoreBoard = new String[][] {
                {"TW", "NO", "NO", "DL", "NO", "NO", "NO", "TW", "NO", "NO", "NO", "DL", "NO", "NO", "TW"},
                {"NO", "DW", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "DW", "NO"},
                {"NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO"},
                {"DL", "NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO", "DL"},
                {"NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO"},
                {"NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO"},
                {"NO", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "NO"},
                {"TW", "NO", "NO", "DL", "NO", "NO", "NO", "DW1", "NO", "NO", "NO", "DL", "NO", "NO", "TW"},
                {"NO", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "NO"},
                {"NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO"},
                {"NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO"},
                {"DL", "NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO", "DL"},
                {"NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO"},
                {"NO", "DW", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "DW", "NO"},
                {"TW", "NO", "NO", "DL", "NO", "NO", "NO", "TW", "NO", "NO", "NO", "DL", "NO", "NO", "TW"}
        };
    }
    /*
     * By doing the following method, we implement the singleton idea
     * meaning we create 1 instance of the game
     */
    public static Board getGame() {
        if (game == null)
            game = new Board();
        return game;
    }

    /*
     * isNext checks if new words have been created on the board AFTER the placing of a new word..
     * isNext, looks for nearby tiles and returns its index + boolean if found, and scans the nearby tiles
     * return the BooleanPoint object which holds an index to 2d array and boolean.
     * isNext splits the check into 2 routes, either the word is vertical or horizontal with boolean vertical

     */
    private BooleanPoint isNext(Tile letter, int row, int col, boolean firstIndex, boolean lastIndex, boolean vertical){

        BooleanPoint p = new BooleanPoint(false, row, col);
        if (vertical) {
            if (firstIndex && row > 0)
                if (game.liveBoard[row - 1][col] != null) {
                    p.flag = true;
                    p.row -= 1;
                    return p;
                }

            if (col < 14)
                if (game.liveBoard[row][col + 1] != null) {
                    p.flag = true;
                    p.col += 1;
                    return p;
                }

            if (col > 0)
                if (game.liveBoard[row][col - 1] != null) {
                    p.flag = true;
                    p.col -= 1;
                    return p;
                }

            if (row < 14 && lastIndex)
                if (game.liveBoard[row + 1][col] != null) {
                    p.flag = true;
                    p.row += 1;
                    return p;
                }

            return p;
        } else {
            if (row > 0)
                if (game.liveBoard[row - 1][col] != null) {
                    p.flag = true;
                    p.row -= 1;
                    return p;
                }

            if (row < 14)
                if (game.liveBoard[row + 1][col] != null) {
                    p.flag = true;
                    p.row += 1;
                    return p;
                }

            if (col > 0 && firstIndex)
                if (game.liveBoard[row][col - 1] != null) {
                    p.flag = true;
                    p.col -= 1;
                    return p;
                }

            if (col < 14 && lastIndex)
                if (game.liveBoard[row][col + 1] != null) {
                    p.flag = true;
                    p.col += 1;
                    return p;
                }

            return p;
        }
    }




    /*
     * this method checks if the board remains legal, if we were to place the word.
     */
    public boolean boardLegal(Word word) {
        int col, row;
        int i =0;
        col = word.getCol();
        row = word.getRow();
        boolean legalPlace = false;
        for (Tile tile : word.getWord()) {
            i++;
            if (col >= 15 || row >= 15 || row < 0 || col < 0)
                return false;

            //center of the board, first word must be place here.
            if (row == 7 && col == 7)
                legalPlace = true;

            /*
             * if we receive a word with missing letter - meaning we're using an existing tile on the board
             * So we should assign the tile to t before check
             * Or we will receive null error
             */

            if (game.liveBoard[row][col] != null && tile != game.liveBoard[row][col])
                return false;

            if (!word.isVertical())
                col++;
            else
                row++;

        }
        if (legalPlace)
            return true;

        for (i = 0; i < word.getWord().length; i++) {
            //do notice, isNext returns an object with a boolean variable!!
            if (word.isVertical())
                legalPlace = game.isNext(word.getWord()[i], word.getRow() + i, word.getCol(), i == 0,
                        i + 1 == word.getWord().length, word.isVertical()).flag;
            else
                legalPlace = game.isNext(word.getWord()[i], word.getRow(), word.getCol() + i, i == 0,
                        i + 1 == word.getWord().length, word.isVertical()).flag;
            if (legalPlace)
                return true;
        }
        return legalPlace;

    }


    //currently not using a dictionary so this will return true for now.
    public boolean dicitonaryLegal(Word word) {
        return true;
    }



    //a method to find a verticaly placed word on the board
    private Word FindverticalWord(int row, int col) {
        int tempRow = row, i;
        Tile[] t = new Tile[15];
        Tile[] s;
        //vertical first;
        while (game.liveBoard[tempRow][col] != null && tempRow > 0)
            tempRow--;
        tempRow += 1;
        for (i = 0; i < 15; i++)
        {
            if (game.liveBoard[tempRow + i][col] == null)
                break;
            else
                t[i] = game.liveBoard[tempRow + i][col];
        }
        s = new Tile[i];
        System.arraycopy(t, 0, s, 0, i);
        return new Word(s, tempRow, col, true);
    }

    //a method to find horizontally placed word on the board.
    private Word horizontalWord(int row, int col) {
        int tempCol = col, i;
        Tile[] t = new Tile[15];
        Tile[] s;
        //horizontal
        while (tempCol >= 0 && game.liveBoard[row][tempCol] != null) {
            tempCol--;
        }
        tempCol += 1;
        for (i = 0; i < 15 && tempCol + i < 15; i++) {
            if (game.liveBoard[row][tempCol + i] == null)
                break;
            else
                t[i] = game.liveBoard[row][tempCol + i];

        }
        s = new Tile[i];
        System.arraycopy(t, 0, s, 0, i);
        return new Word(s, row, tempCol, false);
    }

    private ArrayList<Word> getWord(Word word) {
        BooleanPoint flagIndex;
        Word tempWord;
        int col, row, wordLength = word.getWord().length;
        ArrayList<Word> valid_words = new ArrayList<>();
        valid_words.add(word);

        for (int i = 0; i < wordLength; i++) {
            if (word.isVertical())
                flagIndex = game.isNext(word.getWord()[i], word.getRow() + i, word.getCol(), i == 0,
                        i + 1 == word.getWord().length, word.isVertical());
            else
                flagIndex = game.isNext(word.getWord()[i], word.getRow(), word.getCol() + i, i == 0,
                        i + 1 == word.getWord().length, word.isVertical());

            //if flagIndex == true, it means isNext found nearby tile.
            if (flagIndex.flag) {
                //find vertical new words on horizontal insert.
                if (!word.isVertical()) {

                    tempWord = game.FindverticalWord(flagIndex.row, flagIndex.col);
                }
                //find horizontal new words on vertical insert.
                else {
                    tempWord = game.horizontalWord(flagIndex.row, flagIndex.col);
                }
                //check if there's already same word in valid_words array.
                if (!valid_words.contains(tempWord))
                    valid_words.add(tempWord);

            }
        }
        return valid_words;
    }

    private int getScore(Word word)
    {
        int i;
        int letterScore = 0;
        int multiply = 1;
        String temp;

        for (i = 0; i < word.getWord().length; i++)
        {


            if (word.isVertical())
                temp = game.scoreBoard[word.getRow() + i][word.getCol()];
            else
                temp = game.scoreBoard[word.getRow()][word.getCol() + i];

            switch (temp)
            {
                case "DL":
                    letterScore += word.getWord()[i].score * 2;
                    break;

                case "TL":
                    letterScore += word.getWord()[i].score * 3;
                    break;

                case "DW":
                    letterScore += word.getWord()[i].score;
                    multiply *= 2;
                    break;

                case "TW":
                    letterScore += word.getWord()[i].score;
                    multiply *= 3;
                    break;

                case "DW1":
                    letterScore += word.getWord()[i].score;
                    if(game.firstWord)
                        multiply *= 2;
                    break;

                default:
                    letterScore += word.getWord()[i].score;

            }


        }
        return letterScore*multiply;
    }
    private void putWord(Word word)
    {
        for(int i =0; i < word.getWord().length; i++) {
            if (word.isVertical()) {
                game.liveBoard[word.getRow() + i][word.getCol()] = word.getWord()[i];
            }
            else
            {
                game.liveBoard[word.getRow()][word.getCol()+i] = word.getWord()[i];
            }
        }
    }
    public int tryPlaceWord(Word word)
    {
        int sumScore =0;
        boolean flagRepairWord = false;
        ArrayList<Word> t;

            for (int i = 0; i < word.getWord().length; i++)
                if (word.getWord()[i] == null)
                    if(word.isVertical())
                        word.getWord()[i] = game.liveBoard[word.getRow()+i][word.getCol()];
                    else
                        word.getWord()[i] = game.liveBoard[word.getRow()][word.getCol()+i];


        if(!game.dicitonaryLegal(word))
            return 0;
        if(!game.boardLegal(word))
            return 0;

        if(game.firstWord)
        {
            game.putWord(word);
            game.usedWords.add(word);
            sumScore = game.getScore(word);
            game.firstWord = false;
            return sumScore;
        }
        game.putWord(word);
        t = game.getWord(word);
        if(!game.usedWords.isEmpty())
            for (Word w : game.usedWords)
                t.remove(w);
        for(Word s: t)
        {
            sumScore += game.getScore(s);
            game.usedWords.add(s);
        }

         return sumScore;
    }
    //Class point, allowing to better represent a specific are of the board;
    private class Point {
        int col, row;
        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    /*
     * BooleanPoint is class to help deal with the issue of finding a nearby letter on the board
     */
    private class BooleanPoint {
        boolean flag;
        int col, row;


        /*
         * the ctor initializes flag, row and col.
         */
        BooleanPoint(boolean flag, int row, int col) {
            this.flag = flag;
            this.row = row;
            this.col = col;
        }
    }
}

