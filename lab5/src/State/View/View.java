package State.View;

import java.util.Observable;
import java.util.Observer;

/**
 * Denna klass används för att skapa en vy för programmet.
 * Observerar ett observerbart objekt och uppdaterar sig själv
 * när det observerbara objektet har uppdaterats.
 * @Author Fredrik Larsson
 */
public abstract class View implements Observer {
    
    public void update(Observable obs, Object obj) {

    }
}
