package lab2.level;

import java.util.Observable;
import java.util.ArrayList;


public class Level extends Observable {
	ArrayList<Room> savedRooms = new ArrayList<Room>();

	protected Room firstperson = null;
	
	public boolean place(Room r, int x, int y)  {
        if(firstperson != null){
            System.out.println("Rummet kan inte användas!");
            return false;
        }
        r.x = x;
        r.y = y;
        if(savedRooms.isEmpty()){
            savedRooms.add(r);
            return true;
        }
        for (Room room : savedRooms) {
            if(!(r.x + r.dx <= room.x || room.x + room.dx <= r.x  
            || r.y + r.dy <= room.y || r.y >= room.y + room.dy))
            {
                return false;
            }
        

        }
        savedRooms.add(r);
        return true;

    }
	
	public void firstLocation(Room r) {
		firstperson = r;
        
			
	}
    public void movePlayer(Room rum){
        if(rum != null){
            this.firstperson = rum;
            this.setChanged();
            this.notifyObservers();
        }
    }
	
}
