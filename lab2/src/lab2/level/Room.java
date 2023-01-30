
package lab2.level;

import java.awt.Color;


public class Room { 

	protected Room east = null;
	protected Room west = null;
	protected Room north = null;
	protected Room south = null;
	protected int dx = 0;
	protected int dy = 0;
	protected int x = 0;
	protected int y = 0;

	Color color = null;
	
	public Room(int dx, int dy, Color color) {
		this.color = color;
		this.dx = dx;
		this.dy = dy;
		
	}

	public void connectNorthTo(Room r) {
		this.north = r;
	}
	public void connectEastTo(Room r) {
		this.east = r;
	}
	public void connectSouthTo(Room r) {
		this.south = r;
	}
	public void connectWestTo(Room r) {
		this.west = r;
	}
}
