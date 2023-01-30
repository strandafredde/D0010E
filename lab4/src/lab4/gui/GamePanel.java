package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import lab4.data.GameGrid;
/**
 * A panel providing a graphical view of the game board
 * @author-Fredrik Justin 
 */
public class GamePanel extends JPanel implements Observer{
    private final int UNIT_SIZE = 22;
    private GameGrid grid;
    /**
     * The constructor
     *
     * @param grid The grid that is to be displayed
     */
    public GamePanel(GameGrid grid){
        this.grid = grid;
        grid.addObserver(this);
        Dimension dim = new Dimension(grid.getSize()*UNIT_SIZE+45, grid.getSize()*UNIT_SIZE+100);
        this.setPreferredSize(dim);
        this.setPreferredSize(dim);
        this.setBackground(Color.WHITE);

    }
    /**
     * Returns a grid position given pixel coordinates
     * of the panel
     *
     * @param x the x coordinates
     * @param y the y coordinates
     * @return an integer array containing the [x, y] grid position
     */
    public int[] getGridPosition(int x, int y){
        int X = x/UNIT_SIZE;
        int Y = y/UNIT_SIZE;
        int[] pos = {X,Y};
        return pos;
    }

    public void update(Observable arg0, Object arg1) {
        this.repaint();
    }

    //	tv� funktioner som anv�nds i gomokuGUI
//    int GridSize() {
//        return this.grid.getSize();
//    }

    int gridSize() {
        return this.grid.getSize()*UNIT_SIZE;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        super.paintComponent(g);
        int xMax = grid.getSize()*UNIT_SIZE;
        int yMax = grid.getSize()*UNIT_SIZE;
//      ritar sidor runt br�dan(board)
        g.drawLine(xMax, 0, xMax, yMax);
        g.drawLine(0, yMax, xMax, yMax);
        g.drawLine(0, 0, xMax, 0);
        g.drawLine(0, 0, 0, yMax);
//      ritar ut de horisontella och vertikala linjerna
        for(int i = 1; i <= grid.getSize(); i += 1) {
            g.drawLine(0, i*UNIT_SIZE, xMax, i*UNIT_SIZE);
            g.drawLine(i*UNIT_SIZE, 0,i*UNIT_SIZE, yMax);
        }
//      ritar ut spelarens r�relse d�r green �r sin spelare och r�d �r en annans
        for(int x = 0; x < grid.getSize(); x += 1) {
            for(int y = 0; y < grid.getSize(); y += 1) {
                if(grid.getLocation(x, y) == 1) {
                    g.setColor(Color.green);
                    g.fillRect((x*UNIT_SIZE), (y*UNIT_SIZE), UNIT_SIZE, UNIT_SIZE);
                }
                if(grid.getLocation(x, y) == 2) {
                    g.setColor(Color.red);
                    g.fillRect((x*UNIT_SIZE), (y*UNIT_SIZE), UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
    }


}