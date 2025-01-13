/*
 * Class Name: Configurations
 * Purpose: Implements all the methods needed by class Play
 * Author: Willie Leung, 251374052
 */

public class Configurations {
    private int boardSize; // Declare board size, board 2d array, length to win the game and max levels for the game tree
    private char board[][];
    private int lengthToWin;
    private int maxLevels;

    /*
     * Constructor Configurations(boardSize, lengthToWin, maxLevels)
     * Input: The size of the board, the length of the sequence needed to win, and tthe maximum level of the game tree that will be explored by the program
     * Output: None
     */
    public Configurations (int boardSize, int lengthToWin, int maxLevels){
        this.boardSize = boardSize; // Initalize board size, 2d board, length to win and max levels
        this.lengthToWin = lengthToWin;
        this.maxLevels = maxLevels;
        board = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) { // Initalize board with spaces
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /*
     * Algorithm createDictionary()
     * Input: None
     * Output: A empty hash dictionary with size 7001
     */
    public HashDictionary createDictionary(){
        return new HashDictionary(9973); // Return empty hash dictionary with size 7001
    }

    /*
     * Algorithm repeatedConfiguration(hashTable)
     * Input: Check if the string representation of the current board is in the given hash dictionary
     * Output: Integer representing the score of the board if it is in the given hash dictionary, else -1
     */
    public int repeatedConfiguration(HashDictionary hashTable){
        if (hashTable.get(getConfiguration()) == -1){return -1;} // Return -1 of the string representation of the board is not in the given hash dictionary
        else {return hashTable.get(getConfiguration());} // Else get the associated score of the board
    }

    /*
     * Algorithm addConfiguration(hashDictionary, score)
     * Input: Score of the current board to insert into the given hash dictionary
     * Output: None
     */
    public void addConfiguration(HashDictionary hashDictionary, int score){
        hashDictionary.put(new Data(getConfiguration(), score)); // Add score with the configuration of the board to the given hash dictionary
    }

    /*
     * Algorithm savePlay(row, column, symbol)
     * Input: Symbol to store in the given row and column of the board
     * Output: None
     */
    public void savePlay(int row, int col, char symbol){
        board[row][col] = symbol; // Store symbol in given row and column
    }

    /*
     * Algorithm squareIsEmpty(row, column)
     * Input: Row and column of square to check if it's empty
     * Output: Boolean representing whether the square is empty or not
     */
    public boolean squareIsEmpty (int row, int col){
        return board[row][col] == ' '; // Return square empty or not
    }

    /*
     * Algorithm wins(symbol)
     * Input: Symbol to check if it has a sequence the length needed to win
     * Output: Boolean representing if the symbol has a sequence long enough to win
     */
    public boolean wins (char symbol){
        int length; // Integer to store length of diagonal
        char[] line = new char[boardSize]; // Array to store a row, column or diagonal

        for (int i = 0; i < boardSize; i++) { // Nested loops to go through board
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == symbol){ // When the loops reach the symbol needs to be checked for a win
                    for (int k = 0; k < boardSize; k++){line[k] = board[i][k];} // Get row
                    if (getLongestSeq(line, symbol) == lengthToWin){return true;} // Check if the row has a winning line
                    line = new char[boardSize]; // Clear array

                    for (int k = 0; k < boardSize; k++) {line[k] = board[k][j];} // Get column
                    if (getLongestSeq(line, symbol) == lengthToWin){return true;} // Check if the column has a winning line
                    line = new char[boardSize]; // Clear array

                    length = 1; // Calculate length of top left to bottom right diagonal
                    if (i < j){length = length + i;}
                    else if (i > j){length = length + j;}
                    else {length = length + i;}

                    for (int k = 0; k < length; k++) { // Loop to get top left to bottom right diagonal
                        if (i < j){line[k] = board[k][j - i + k];}
                        else if (i > j){line[k] = board[i - j + k][k];}
                        else {line[k] = board[k][k];}
                    }

                    if (getLongestSeq(line, symbol) == lengthToWin){return true;} // Check if the diagonal has a winning line
                    line = new char[boardSize]; // Clear array

                    length = 1; // Calculate length of top right to bottom left diagonal
                    if (i < (boardSize - 1 - j)){length = length + i + j;}
                    else if (i > (boardSize - 1 - j)){length = length + (boardSize - 1 - i) + (boardSize - 1 - j);}
                    else if (i == (boardSize - 1 - j)){length = length + i + j;}

                    for (int k = 0; k < length; k++) { // Loop to get top right to bottom left diagonal
                        if (i < (boardSize - 1 - j)){line[k] = board[i + j - k][k];}
                        else if (i > (boardSize - 1 - j)){line[k] = board[i + (boardSize - 1 - i) - k][j - (boardSize - 1 - i) + k];}
                        else if (i == (boardSize - 1 - j)){line[k] = board[boardSize - 1 - k][k];}
                    }

                    if (getLongestSeq(line, symbol) == lengthToWin){return true;} // Check if the diagonal has a winning line
                    line = new char[boardSize]; // Clear array
                }
            }
        }
        return false; // Return false if no wins
    }

    /*
     * Algorithm getLongestSeq(array, symbol)
     * Input: An array and symbol to find the longest sequence of
     * Output: Integer representing the longest sequence of the given symbol in the array
     */
    private int getLongestSeq(char array[], char symbol){
        int length = 0, longestLength = 0; // Initalize return variable and counter variable

        for (int i = 0; i < array.length; i++) { // Loop to go through array and find longest sequence of the given symbol
            if (array[i] == symbol){length++;}
            else {
                if (length > longestLength){longestLength = length;}
                length = 0;
            }
        }
        if (length > longestLength){longestLength = length;}

        return longestLength; // Return longest sequence of the given variable
    }

    /*
     * Algorithm isDraw()
     * Input: None
     * Output: Boolean representing if the board is a draw or not
     */
    public boolean isDraw(){
        boolean fullBoard = true; // Boolean to see if board is full
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' '){
                    fullBoard = false;
                    break;
                }
            }
        }

        if(!wins('O') && !wins('X') && fullBoard){return true;} // If the board is full and no one won, it is a draw
        return false; // Else it's not a draw
    }

    /*
     * Algorithm evalBoard()
     * Input: None
     * Output: Integer representing that status of the board, 3 for computer win, 0 for human win, 2 for draw and 1 for undecided
     */
    public int evalBoard(){ // Return int based on status of the board
        if (wins('O')){return 3;}
        else if (wins('X')){return 0;}
        else if (isDraw()){return 2;}
        else {return 1;}
    }

    /*
     * Algorithm getConfiguration()
     * Input: None
     * Output: String representing the configuration of the board
     */
    private String getConfiguration(){
        String configuration = ""; // Initialize output string
        for (int i = 0; i < boardSize; i++) { // Nested loop to go through board
            for (int j = 0; j < boardSize; j++) {
                configuration = configuration + board[i][j]; // Add each symbol to the string
            }
        }
        return configuration; // Return string
    }
}
