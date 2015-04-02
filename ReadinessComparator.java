
import java.util.Comparator;

/**@author Q*/
public class ReadinessComparator implements Comparator<Dude>{

    @Override
    public int compare(Dude o1, Dude o2) {
        if(o1.getReadiness() > o2.getReadiness())           //Less than
            return -1;
        
        else if(o1.getReadiness() < o2.getReadiness())      //Greater than
            return 1;
        
        else {                                              //Equals
            if(Math.random() <= 0.5)                        //If equal,
                return 1;                                   //flip a coin
            else
                return -1;
        }
    }
    
}
