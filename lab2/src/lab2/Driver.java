package lab2;

import lab2.level.Level;
import lab2.level.LevelGUI;
import lab2.level.Room;
import java.awt.Color;

public class Driver {

	public static void run() {
		Room rum1 = new Room(100, 100, Color.blue);
		Room rum2 = new Room(100, 100, Color.red);
		Room rum3 = new Room(100, 100, Color.black);
		Room rum4 = new Room(100, 100, Color.orange);
		Room rum5 = new Room(100, 100, Color.yellow);
		Room rum6 = new Room(100, 100, Color.pink);
		Room rum7 = new Room(100, 100, Color.magenta);
		// Room rum8 = new Room(100, 100, Color.blue);
		
		rum1.connectSouthTo(rum2);
		rum2.connectNorthTo(rum1);
		rum2.connectEastTo(rum3);
		rum3.connectWestTo(rum2);
		rum3.connectNorthTo(rum4);
		rum4.connectSouthTo(rum3);
		rum4.connectEastTo(rum5);
		rum5.connectWestTo(rum4);
		rum5.connectSouthTo(rum6);
		rum6.connectNorthTo(rum5);
		rum6.connectEastTo(rum7);
		rum7.connectWestTo(rum6);
		// rum7.connectNorthTo(rum8);

		Room r1 = new Room(200,100,Color.BLUE);
		Room r2 = new Room(100,60,Color.RED);
		Room r3 = new Room(70,30,Color.GREEN);
		Room r4 = new Room(170,60,Color.yellow);
		Room r5 = new Room(100,100,Color.white);
		Room r6 = new Room(40,200,Color.gray);
		Room r7 = new Room(270,160,Color.pink);
		Room r8 = new Room(120,70,Color.magenta);
		Room r9 = new Room(110,120,Color.orange);
		
		Level level = new Level();
		Level level2 = new Level();

		level2.place(r1,50,40);
		level2.place(r2,30,25);
		level2.place(r3,210,20);
		level2.place(r4,230,100);
		level2.place(r5,20,80);
		level2.place(r6,90,10);
		level2.place(r7,20,20);
		level2.place(r8,80,50);
		level2.place(r9,130,100);

		System.out.println(level.place(rum1, 0, 0));
		System.out.println(level.place(rum2, 0, 120));
		System.out.println(level.place(rum3, 120, 120));
		System.out.println(level.place(rum4, 120, 0));
		System.out.println(level.place(rum5, 240, 0));
		System.out.println(level.place(rum6, 240, 120));
		System.out.println(level.place(rum7, 360, 120));
		// System.out.println(level.place(rum8, 360, 0));
		
		level.firstLocation(rum1);
		// Room rum9 = new Room(100, 100, Color.blue);
		// System.out.println(level.place(rum9, 500, 0));
	
		LevelGUI gui = new LevelGUI(level, "name");

	}
	
}