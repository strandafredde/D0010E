
package lab2.level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class LevelGUI implements Observer {

	private Level lv;
	private Display d;
	
	public LevelGUI(Level level, String name) {
		
		this.lv = level;
		
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: You should change 200 to a value 
		// depending on the size of the level
		d = new Display(lv,1500,1500);

		this.lv.addObserver(this);	
		
		frame.getContentPane().add(d);
		frame.pack();
		frame.setLocation(0,0);
		frame.setVisible(true);
	}
	
	
	public void update(Observable arg0, Object arg1) {
		d.repaint();
	}
	
	private class Display extends JPanel {
		
		public Display(Level fp, int x, int y) {
		
			
			addKeyListener(new Listener());
			
			setBackground(Color.gray);
			setPreferredSize(new Dimension(x+20,y+20));
			setFocusable(true);
		}
	
		
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (Room r : lv.savedRooms) {
				g.drawRect(r.x, r.y, r.dx, r.dy);
				g.setColor(r.color);
				g.fillRect(r.x, r.y, r.dx, r.dy);
				
				if(r.east != null){
					g.setColor(r.color);
					g.drawRect(r.x+110, r.y + r.dy/2-10, 10, 10);
					g.fillRect(r.x+110, r.y + r.dy/2-10, 10, 10);
					
				}
				if(r.south != null){
					g.setColor(r.color);
					g.drawRect(r.x+40, r.y+10 + r.dy, 10, 10);
					g.fillRect(r.x+40, r.y+10 + r.dy, 10, 10);
				}
				if(r.north != null){
					g.setColor(r.color);
					g.drawRect(r.x+40, r.y-60 + r.dy/2-10, 10, 10);
					g.fillRect(r.x+40, r.y-60 + r.dy/2-10, 10, 10);
				}
				if(r.west != null){
					g.setColor(r.color);
					g.drawRect(r.x-20, r.y + r.dy/2-10, 10, 10);
					g.fillRect(r.x-20, r.y + r.dy/2-10, 10, 10);
				}
				if(lv.firstperson != null){
					g.setColor(Color.CYAN);
					g.drawRect(lv.firstperson.x + lv.firstperson.dx / 2 -15, lv.firstperson.y + lv.firstperson.dx / 2 -15, 20, 20);
					g.fillRect(lv.firstperson.x + lv.firstperson.dx / 2 -15, lv.firstperson.y + lv.firstperson.dx / 2 -15, 20, 20);

				}
				
			}
		
		}

	 	private class Listener implements KeyListener {

	 		
	 		public void keyPressed(KeyEvent arg0) {
			}
			
			public void keyReleased(KeyEvent arg0) {
				
				switch(arg0.getKeyCode()) {
				   case 39:
						lv.movePlayer(lv.firstperson.east);
					   	break;
				   case 38:
						lv.movePlayer(lv.firstperson.north);
					   	break;
				   case 37:
						lv.movePlayer(lv.firstperson.west);
						break;
				   case 40:
						lv.movePlayer(lv.firstperson.south);
						break;		
				}

			}
			
			public void keyTyped(KeyEvent event) {
			}
		}
		
	}
	
}
