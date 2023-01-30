/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.gui.GamePanel;

/**
 * Represents the state of a game
 * @author-Fredrik Justin 
 */

public class GomokuGameState extends Observable implements Observer {

    // Game variables
    private final int DEFAULT_SIZE = 15;
    private final GameGrid gameGrid;


    //Possible game states
    private final int NOT_STARTED = 0;
    private final int MY_TURN = 1;
    private final int OTHER_TURN = 2;
    private final int FINISHED = 3;
    private int currentState;

    private final GomokuClient client;
    //GameGrid gameGrid = new GameGrid(19);


    private String message;

    /**
     * The constructor
     *
     * @param gc The client used to communicate with the other player
     */
    public GomokuGameState(GomokuClient gc) {
        client = gc;
        client.addObserver(this);
        gc.setGameState(this);
        currentState = NOT_STARTED;
        gameGrid = new GameGrid(DEFAULT_SIZE);
    }


    /**
     * Returns the message string
     *
     * @return the message string
     */
    public String getMessageString() {
        return message;
    }

    /**
     * Returns the game grid
     *
     * @return the game grid
     */
    public GameGrid getGameGrid() {
        return gameGrid;
    }

    /**
     * This player makes a move at a specified location
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void move(int x, int y) {
        if (currentState == MY_TURN) {
            if (gameGrid.move(x, y, MY_TURN)) {
                client.sendMoveMessage(x, y);
                if (gameGrid.isWinner(MY_TURN)) {
                    currentState = FINISHED;
                    this.message = "Player 'me' has won the game!";
                    getMessageString();
                }else{
                    currentState = OTHER_TURN;
                }

            }else{
                this.message = "Unreachable destination, location occupied!";

            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Starts a new game with the current client
     */
    public void newGame() {
        if (currentState != NOT_STARTED){
            gameGrid.clearGrid();
            currentState = OTHER_TURN;
            this.message = "A new game has begun, player 'other' turn.";
            getMessageString();
            client.sendNewGameMessage();
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Other player has requested a new game, so the
     * game state is changed accordingly
     */
    public void receivedNewGame() {
        gameGrid.clearGrid();
        currentState = MY_TURN;
        this.message = "A new game has begun, player 'me' turn.";
        getMessageString();
        setChanged();
        notifyObservers();
    }

    /**
     * The connection to the other player is lost,
     * so the game is interrupted
     */
    public void otherGuyLeft() {
        gameGrid.clearGrid();
        this.currentState = FINISHED;
        this.message = "Player 'other' has left the game.";
        getMessageString();
        setChanged();
        notifyObservers();
    }

    /**
     * The player disconnects from the client
     */
    public void disconnect() {
        gameGrid.clearGrid();
        this.currentState = FINISHED;
        this.message = "Player 'me' has left the game.";
        client.sendNewGameMessage();
        setChanged();
        notifyObservers();
    }

    /**
     * The player receives a move from the other player
     *
     * @param x The x coordinate of the move
     * @param y The y coordinate of the move
     */
    public void receivedMove(int x, int y) {
        if (gameGrid.move(x, y, OTHER_TURN)) {
            if (gameGrid.isWinner(OTHER_TURN)){
                currentState = FINISHED;
                this.message = "Player 'other' has won the game!";
                getMessageString();
            }else{
                currentState = MY_TURN;
            }

        }else{
            this.message = "Unreachable destination, location occupied!";
            getMessageString();
        }


        setChanged();
        notifyObservers();
    }

    public void update(Observable o, Object arg) {

        switch (client.getConnectionStatus()) {
            case GomokuClient.CLIENT:
                message = "Game started, it is your turn!";
                currentState = MY_TURN;
                break;
            case GomokuClient.SERVER:
                message = "Game started, waiting for other player...";
                currentState = OTHER_TURN;
                break;
        }
        setChanged();
        notifyObservers();


    }

}