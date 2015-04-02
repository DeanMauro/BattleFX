
import java.util.Comparator;

/**@author Q*/

public class PositionComparator implements Comparator<Dude>{
    @Override
    public int compare(Dude o1, Dude o2) {
        if(o1.getPosition() < o2.getPosition())           //Less than
            return -1;
        
        else if(o1.getPosition() > o2.getPosition())      //Greater than
            return 1;
        
        else {                                              //Equals
            return 0;
        }
    }
}
