package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 * @author-Fredrik Justin 
 */

public class GameGrid extends Observable {


    // Board[row][column]
    int size = 19;
    int[][] board = new int[size][size];

    // Statuses
    public static final int EMPTY = 0;
    public static final int ME = 1;
    public static final int OTHER = 2;


    // Winning amount
    int INROW = 5;

    /**
     * Constructor
     *
     * @param size The width/height of the game grid
     */
    public GameGrid(int size) {

        // Sets and clears grid
        this.size = size;
        clearGrid();

    }

    /**
     * Reads a location of the grid
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return the value of the specified location
     */
    public int getLocation(int x, int y) {
        // Board[row][column]
        return this.board[x][y];
    }

    /**
     * Returns the size of the grid
     *
     * @return the grid size
     */
    public int getSize() {

        // Returns grid size
        return this.size;
    }

    /**
     * Enters a move in the game grid
     *
     * @param x      the x position
     * @param y      the y position
     * @param player the player
     * @return true if the insertion worked, false otherwise
     */
    public boolean move(int x, int y, int player) {

        if (getLocation(x, y) == EMPTY) {
            if (player == ME) {
                // Board[row][column]
                this.board[x][y] = ME;
                setChanged();
                notifyObservers();
                return true;
            }
            if (player == OTHER) {
                // Board[row][column]
                this.board[x][y] = OTHER;
                setChanged();
                notifyObservers();
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the grid of pieces
     */
    public void clearGrid() {
        //Status empty = Status.EMPTY;
        for (int column = 0; column < this.size; column++) {
            for (int row = 0; row < this.size; row++) {
                this.board[row][column] = EMPTY;
                setChanged();
                notifyObservers();
            }
        }

    }

    /**
     * Check if a player has 5 in row
     *
     * @param player the player to check for
     * @return true if player has 5 in row, false otherwise
     */
    public boolean isWinner(int player) {
        // Board[row][column]
        int inRow = 0;
        int limit = (this.INROW-1);
        // Vertical win
        for(int row = 0; row <this.size; row += 1) {
        	for(int column= 0; column <this.size; column += 1) {
        		if (this.board[row][column] == player) {
        			inRow += 1;
        			if (inRow == INROW) {
        				return true;
        			}
        		}
        		if (!(this.board[row][column] == player)) {
        			inRow= 0;
            	}
        	}
        }

        // Horizontal win
        for(int column = 0; column <this.size; column += 1) {
        	for(int row = 0; row <this.size; row += 1) {
        		if (this.board[row][column] == player) {
        			inRow += 1;
        			if (inRow == INROW) {
        				return true;
        			}
        		}
        		if (!(this.board[row][column] == player)) {
        			inRow = 0;
            	}
        	}
        }

     // Diagonal win right
        for(int row = 0; row < this.size; row++) {
            for (int column = 0; column < (this.size-limit); column++) {
                int r = row;
                int c = column;
                for(int i = 0; i <= limit; i++){
                    if(this.board[r][c] == player){
                        inRow++;
                    }else{
                        inRow = 0;
                    }
                    if(r < this.size && r >= 0){
                        r++;
                    }
                    if(c < this.size && c >= 0){
                        c--;
                }
                if(inRow == 5){return true;}

            }

        }
        }


        // Diagonal win left
        for(int row = 0; row < this.size; row++) {
            for (int column = limit; column < this.size; column++) {
                        int r = row;
                        int c = column;
                        for(int i = 0; i <= limit; i++){
                            if(this.board[r][c] == player){
                                inRow++;
                            }else{
                            	inRow = 0;
                            }
                            if(r < this.size && r >= 0){
                                r++;
                            }
                            if(c < this.size && c >= 0){
                                c++;
                            }
                        }
                        if(inRow == INROW){return true;}

            }

        }
        return false;

    }


}
