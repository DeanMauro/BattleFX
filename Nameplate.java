
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**@author Q*/

public class Nameplate extends Pane {
    
    //This rectangle will serve as a background/boundary for the stat text
    public Rectangle box;
    
    //These labels will contain all the Dude's stats
    public Text Name;
    public Text HPText;
    public Text HPValue;     //How much hp the dude has left
    public Text MaxHPValue;  //How much he started with (This is for the "17/35" form, for example)
    public Text DamageText;
    public Text SpeedText;
    
    
    public Nameplate(String name, int MaxHP, int MinDamage, int MaxDamage, int Speed){
        setPrefSize(30,30);
        
        //Create a rectangle to hold all the stats
        box = new Rectangle(90, 90);
        box.setArcWidth(20);
        box.setArcHeight(20);                
        box.setFill(Color.DARKGOLDENROD);
        box.setStroke(Color.BLACK);
        
        //Set up labels
        Name = new Text(name.toUpperCase());
        HPText = new Text("HP: ");
        HPValue = new Text();
        MaxHPValue = new Text("/" + String.valueOf(MaxHP));
        DamageText = new Text("DMG: " + String.valueOf(MinDamage) + "-" + String.valueOf(MaxDamage));
        SpeedText = new Text("SPD: " + String.valueOf(Speed));
        
        /*You may notice the HPValue label was left out above.
          Instead of explicitly setting the HPValue label,
          we're just going to bind it to the dude's actual HP
          so that when he loses health, the label automatically updates*/
        
        //Set each component's position relative to the position of the rectangle
        Name.xProperty().bind(box.xProperty().add(new SimpleDoubleProperty(15)));
        HPText.xProperty().bind(box.xProperty().add(new SimpleDoubleProperty(15)));
        HPValue.xProperty().bind(HPText.xProperty().add(new SimpleDoubleProperty(25)));
        MaxHPValue.xProperty().bind(HPValue.xProperty().add(new SimpleDoubleProperty(15)));
        DamageText.xProperty().bind(box.xProperty().add(new SimpleDoubleProperty(15)));
        SpeedText.xProperty().bind(box.xProperty().add(new SimpleDoubleProperty(15)));
        
        Name.yProperty().bind(box.yProperty().add(new SimpleDoubleProperty(20)));
        HPText.yProperty().bind(box.yProperty().add(new SimpleDoubleProperty(40)));
        HPValue.yProperty().bind(HPText.yProperty());
        MaxHPValue.yProperty().bind(HPValue.yProperty());
        DamageText.yProperty().bind(box.yProperty().add(new SimpleDoubleProperty(60)));
        SpeedText.yProperty().bind(box.yProperty().add(new SimpleDoubleProperty(80)));
        
        //Add all components to the nameplate
        getChildren().addAll(box, Name, HPText, HPValue, MaxHPValue, DamageText, SpeedText);
    }
}
