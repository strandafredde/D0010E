package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

    private GomokuClient client;
    private GomokuGameState gamestate;
    private GamePanel gameGridPanel;
    private JLabel messageLabel;
    private JButton connectButton;
    private JButton newGameButton;
    private JButton disconnectButton;

    /**
     * The constructor
     * The GUI class
     * @param g   The game state that the GUI will visualize
     * @param c   The client that is responsible for the communication
     * @author-Fredrik Justin 
     */
    public GomokuGUI(GomokuGameState g, GomokuClient c){
        this.client = c;
        this.gamestate = g;
        client.addObserver(this);
        gamestate.addObserver(this);
        JFrame frame = new JFrame("Gomoku");
        gameGridPanel = new GamePanel(g.getGameGrid());
        frame.setSize(350,430);

        gameGridPanel.setVisible(true);
        frame.add(gameGridPanel);

//		skapar knapparna
        messageLabel = new JLabel("                      Welcome to Gomoku");
        connectButton = new JButton("Connect");
        newGameButton = new JButton("New Game");
        disconnectButton = new JButton("Disconnect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		lägger till knapparna till gamePanelen
        frame.add(connectButton);
        frame.add(newGameButton);
        frame.add(disconnectButton);
        frame.add(messageLabel);
        newGameButton.setEnabled(false);
        disconnectButton.setEnabled(false);

//		skapar horisontella boxar f�r knappar som kommer placeras v�nster till h�ger
        Box horBox = new Box(BoxLayout.X_AXIS);
        horBox.setAlignmentX(horBox.LEFT_ALIGNMENT);
        horBox.add(connectButton);
        horBox.add(horBox.createHorizontalStrut(10));
        horBox.add(newGameButton);
        horBox.add(horBox.createHorizontalStrut(10));
        horBox.add(disconnectButton);
        frame.add(horBox);



//		skapar vertikala l�dor f�r titeln och som placerar fr�n toppen till botten
        Box verBox = Box.createVerticalBox();
        verBox.add(verBox.createVerticalStrut(gameGridPanel.gridSize()+15));

        verBox.add(verBox.createVerticalStrut(8));
        verBox.add(messageLabel);
        frame.add(verBox);
        verBox.add(horBox);

        frame.setVisible(true);
        frame.add(gameGridPanel);


        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectionWindow conWindow = new ConnectionWindow(c);
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                g.newGame();
            }
        });

        disconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                g.disconnect();
            }
        });
        gameGridPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int moveX = gameGridPanel.getGridPosition(x, y)[0];
                int moveY = gameGridPanel.getGridPosition(x, y)[1];
                g.move(moveX, moveY);
                frame.repaint();
            }
        });

        
    }




    public void update(Observable arg0, Object arg1) {

        // Update the buttons if the connection status has changed
        if(arg0 == client){
            if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
                connectButton.setEnabled(true);
                newGameButton.setEnabled(false);
                disconnectButton.setEnabled(false);
            }else{
                connectButton.setEnabled(false);
                newGameButton.setEnabled(true);
                disconnectButton.setEnabled(true);
            }
        }

        // Update the status text if the gamestate has changed
        if(arg0 == gamestate){
            messageLabel.setText(gamestate.getMessageString());
        }

    }


}