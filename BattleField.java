import java.util.LinkedList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**@author Q*/
public class BattleField extends Application {
    Pane root;
    ObservableList<Node> Field;
    FilteredList<Dude> Characters;
    SortedList<Dude> ReadyDudes;
    
    //Lists to store the characters in the correct order
    //and keep trask of positions
    public LinkedList<Dude> Heroes = new LinkedList<>();
    public LinkedList<Dude> Enemies = new LinkedList<>();
    
    //Readiness variables
    private final int readinessThreshold = 20;
    public Dude CurrentAttacker;
    
    //Heroes
    public Dude Warrior;
    public Dude Ranger;
    public Dude Mage;
    public Dude Priest;
    
    //Enemies
    public Dude EnemyWarrior;
    public Dude EnemyRanger;
    public Dude EnemyMage;
    public Dude EnemyPriest;
    
    //Create background image and two buttons:
    //A red button to be shown if the selected attack
    //can't be used and a green one if it can.
    public ImageView ValidAttack;
    public ImageView InvalidAttack;
    public ImageView Background;
    
    //This is the Y coordinate for all the characters' sprites.
    //They will be 50 units down from the top of the GUI.
    public double AllY = 50;
    
    //These are the X coordinates of the sprites, which vary depending 
    //on which position the character is in.
    public double Hero1X = 500;
    public double Hero2X = 300;
    public double Hero3X = 150;
    public double Hero4X = 0;
    
    public double Enemy1X = 650;
    public double Enemy2X = 800;
    public double Enemy3X = 950;
    public double Enemy4X = 1150;
    
    //These are markers to indicate valid attack positions
    public ImageView Valid1;
    public ImageView Valid2;
    public ImageView Valid3;
    public ImageView Valid4;
    public ImageView Valid5;
    public ImageView Valid6;
    public ImageView Valid7;
    public ImageView Valid8;
    
    public ImageView[] ValidPositions;
    
    
    
    
/*//////////////////////////////////////////////////////
START
 Start, not main, is called when the program starts up
//////////////////////////////////////////////////////*/
    
    @Override
    public void start(Stage primaryStage) {
        
        //Panes store all the GUI elements
        root = new Pane();
        Field = root.getChildren(); //This gets used a lot. Better just
                                    //shorten it to something like "Field"
        
        //Initialize everything
        SetStage();
        EnterHeroes();
        EnterEnemies();
        FillHelperLists();
        AddListeners();
        
        
        
        //Create a scene with all the elements
        Scene scene = new Scene(root, 1300, 700);
        
        //Display the scene on our stage
        primaryStage.setTitle("Death BATTTLLLLEEEE!!!!!!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //BEGIN THE BATTLE
        PickReadiestDude();
        ShowHisAttacks();
        DisplayValidPositions();
        TurnAttackButtonOnOff();
    }

 
    
    
/*//////////////////////////////////////////////////////
SET STAGE
 Initialize the background, valid attack icons, & attack buttons 
//////////////////////////////////////////////////////*/
 
    public void SetStage(){
        
        //Initialize background, valid position icons, attack button images
        Background = new ImageView(new Image("Images/MeadowBackground.png"));
        
        ValidAttack = new ImageView(new Image("Images/AttackGreen.png"));
        InvalidAttack = new ImageView(new Image("Images/AttackRed.png"));
        
        Valid1 = new ImageView(new Image("Images/Check.png"));
        Valid2 = new ImageView(new Image("Images/Check.png"));
        Valid3 = new ImageView(new Image("Images/Check.png"));
        Valid4 = new ImageView(new Image("Images/Check.png"));
        Valid5 = new ImageView(new Image("Images/Check.png"));
        Valid6 = new ImageView(new Image("Images/Check.png"));
        Valid7 = new ImageView(new Image("Images/Check.png"));
        Valid8 = new ImageView(new Image("Images/Check.png"));
        
        //Store Valid positions in an array
        ValidPositions = new ImageView[] {Valid1, Valid2, Valid3, Valid4,
                                          Valid5, Valid6, Valid7, Valid8};
        
        //Tell attack button where to go on the stage
        ValidAttack.setX(600);
        ValidAttack.setY(550);
        
        InvalidAttack.setX(600);
        InvalidAttack.setY(550);
        
        //Tell valid position icons where to go
        Valid1.setX(70);
        Valid2.setX(220);
        Valid3.setX(380);
        Valid4.setX(570);
        Valid5.setX(700);
        Valid6.setX(870);
        Valid7.setX(1040);
        Valid8.setX(1200);
        
        Valid1.setY(260);
        Valid2.setY(260);
        Valid3.setY(260);
        Valid4.setY(260);
        Valid5.setY(260);
        Valid6.setY(260);
        Valid7.setY(260);
        Valid8.setY(260);
        
        //Set icons invisible for now
        Valid1.setVisible(false);
        Valid2.setVisible(false);
        Valid3.setVisible(false);
        Valid4.setVisible(false);
        Valid5.setVisible(false);
        Valid6.setVisible(false);
        Valid7.setVisible(false);
        Valid8.setVisible(false);
        
        //Add all 3 to the Pane
        Field.addAll(Background, 
                     ValidAttack, InvalidAttack,
                     Valid1, Valid2, Valid3, Valid4, Valid5, Valid6, Valid7, Valid8);
    }    
    
    
    
    
/*//////////////////////////////////////////////////////
ENTER HEROES
 Initialize all heroes
//////////////////////////////////////////////////////*/
    public void EnterHeroes(){
        
        //Create Heroes
        Warrior = new Dude("warrior", 3, false);
        Ranger = new Dude("ranger", 2, false);
        Mage = new Dude("mage", 1, false);
        Priest = new Dude("priest", 0, false);
        
        //Tell them were to go on the stage
        Warrior.setX(Hero1X);
        Warrior.setY(AllY);
        
        Ranger.setX(Hero2X);
        Ranger.setY(AllY + 30); //Set ranger lower because he is crouching
        
        Mage.setX(Hero3X);
        Mage.setY(AllY);
        
        Priest.setX(Hero4X);
        Priest.setY(AllY);
        
        //Add their sprites (Dude now extends ImageView) to the Pane
        Field.addAll(Warrior, Ranger, Mage, Priest);
        
        //Add their status symbols to the Pane
        Field.addAll(Warrior.Blood, Warrior.Poison, Warrior.Stun,
                                  Ranger.Blood, Ranger.Poison, Ranger.Stun,
                                  Mage.Blood, Mage.Poison, Mage.Stun,
                                  Priest.Blood, Priest.Poison, Priest.Stun);
        
        //Add their attacks and corresponding radio buttons to the Pane
        Field.addAll(Warrior.Attacks, Ranger.Attacks, Mage.Attacks, Priest.Attacks);
        Field.addAll(Warrior.getAttack(1), Warrior.getAttack(2), Warrior.getAttack(3), Warrior.getAttack(4),
                                  Ranger.getAttack(1), Ranger.getAttack(2), Ranger.getAttack(3), Ranger.getAttack(4),
                                  Mage.getAttack(1), Mage.getAttack(2), Mage.getAttack(3), Mage.getAttack(4),
                                  Priest.getAttack(1), Priest.getAttack(2), Priest.getAttack(3), Priest.getAttack(4));
        
        //Add the position changing radio buttons to the Pane
        Field.addAll(Warrior.MoveBack, Warrior.MoveForward,
                                  Ranger.MoveBack, Ranger.MoveForward,
                                  Mage.MoveBack, Mage.MoveForward,
                                  Priest.MoveBack, Priest.MoveForward);
        
        //Add their nameplates to the Pane
        Field.addAll(Warrior.Stats, Ranger.Stats, Mage.Stats, Priest.Stats);
    }
    
    
    
    
/*//////////////////////////////////////////////////////
ENTER ENEMIES
 Initialize all enemies
//////////////////////////////////////////////////////*/
    
    public void EnterEnemies(){
        
        //Create enemies
        EnemyWarrior = new Dude("warrior", 4, true);
        EnemyRanger = new Dude("ranger", 5, true);
        EnemyMage = new Dude("mage", 6, true);
        EnemyPriest = new Dude("priest", 7, true);
        
        //Tell them where to go on the stage
        EnemyWarrior.setX(Enemy1X);
        EnemyWarrior.setY(AllY);
        
        EnemyRanger.setX(Enemy2X);
        EnemyRanger.setY(AllY + 30); //Set ranger lower because he is crouching
        
        EnemyMage.setX(Enemy3X);
        EnemyMage.setY(AllY);
        
        EnemyPriest.setX(Enemy4X);
        EnemyPriest.setY(AllY);
        
        //Add their sprites to the Pane
        Field.addAll(EnemyWarrior, EnemyRanger, EnemyMage, EnemyPriest);
        
        //Add their status symbols to the Pane
        Field.addAll(EnemyWarrior.Blood, EnemyWarrior.Poison, EnemyWarrior.Stun,
                                  EnemyRanger.Blood, EnemyRanger.Poison, EnemyRanger.Stun,
                                  EnemyMage.Blood, EnemyMage.Poison, EnemyMage.Stun,
                                  EnemyPriest.Blood, EnemyPriest.Poison, EnemyPriest.Stun);
        
        //Add their attacks and the corresponding radiobuttons to the Pane
        Field.addAll(EnemyWarrior.Attacks, EnemyRanger.Attacks, EnemyMage.Attacks, EnemyPriest.Attacks);
        Field.addAll(EnemyWarrior.getAttack(1), EnemyWarrior.getAttack(2), EnemyWarrior.getAttack(3), EnemyWarrior.getAttack(4),
                                  EnemyRanger.getAttack(1), EnemyRanger.getAttack(2), EnemyRanger.getAttack(3), EnemyRanger.getAttack(4),
                                  EnemyMage.getAttack(1), EnemyMage.getAttack(2), EnemyMage.getAttack(3), EnemyMage.getAttack(4),
                                  EnemyPriest.getAttack(1), EnemyPriest.getAttack(2), EnemyPriest.getAttack(3), EnemyPriest.getAttack(4));
        
        //Add the position changing radio buttons to the Pane
        Field.addAll(EnemyWarrior.MoveBack, EnemyWarrior.MoveForward,
                                  EnemyRanger.MoveBack, EnemyRanger.MoveForward,
                                  EnemyMage.MoveBack, EnemyMage.MoveForward,
                                  EnemyPriest.MoveBack, EnemyPriest.MoveForward);
        
        //Add their nameplates to the Pane
        Field.addAll(EnemyWarrior.Stats, EnemyRanger.Stats, EnemyMage.Stats, EnemyPriest.Stats);
    }
    
    
    
    
/*//////////////////////////////////////////////////////
ENTER ENEMIES
 Initialize all enemies
//////////////////////////////////////////////////////*/
    public void FillHelperLists(){
        
        //Populate a list of characters so we can iterate through them
        //without having to use indices. Characters and Field share
        //the Dudes now, so any change made to one is updated in the other.
        Characters = new FilteredList(Field, e -> (e instanceof Dude));
        
        //Dudes are sorted by readiness in this list. Any changes made
        //automatically updates their positions.
        ReadyDudes = new SortedList(Characters, new ReadinessComparator());
    }
    
    
    
    
    public void AddListeners(){
        
        //Listen when user selects a new attack
        for(Dude guy : Characters){
            guy.MoveSet.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){ 
                public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
                    DisplayValidPositions(); 
                    TurnAttackButtonOnOff();
                } });
        }
        
        
    }
    
    
    
    
/*//////////////////////////////////////////////////////
START BATTLE
//////////////////////////////////////////////////////*/
    public void PickReadiestDude(){
        CurrentAttacker = null;
        
        //Go through the list of Dudes, sorted by readiness.
        for(Dude guy : ReadyDudes){

            //First guy whose readiness exceeds the threshold gets to attack.
            if((CurrentAttacker == null) && (guy.getReadiness() >= readinessThreshold)){
                CurrentAttacker = guy;
                break;
            }
            
            //For all other Dudes, increase readiness by speed
            guy.setReadiness(guy.getReadiness() + guy.getSpeed());
        }
        
        //Retry or reset current attacker's readines and go on.
        if(CurrentAttacker == null)
            PickReadiestDude();
        else
            CurrentAttacker.setReadiness(0);
    }
        
    
    
    public void ShowHisAttacks(){
        CurrentAttacker.setUpAttacker();
    }
    
    
    
    public void DisplayValidPositions(){
        
        //Set all valid position icons invisible
        for(ImageView valid : ValidPositions){
            valid.setVisible(false);
        }
            
        //Get currently selected attack (Moving is accounted for)
        Attack selectedAttack = (Attack) CurrentAttacker.MoveSet.getSelectedToggle();

        //Set appropriate valid position icons visible
        for(int i=0; i < 8; i++){
            if(selectedAttack.isValidAttackPosition(i))
                ValidPositions[i].setVisible(true);
        }
        
    }
    
    
    
    public void TurnAttackButtonOnOff(){
        //Get currently selected attack (Moving is accounted for)
        Attack selectedAttack = (Attack) CurrentAttacker.MoveSet.getSelectedToggle();
        
        if(selectedAttack.getName().equals("MoveBack") && CurrentAttacker.getPosition() != 0 && CurrentAttacker.getPosition() != 8){
            InvalidAttack.setVisible(false);
        }
        
        else if(selectedAttack.getName().equals("MoveForward") && CurrentAttacker.getPosition() != 3 && CurrentAttacker.getPosition() != 4){
            InvalidAttack.setVisible(false);
        }
        
        else if(selectedAttack.isValidAttackPosition(CurrentAttacker.getPosition())){
            InvalidAttack.setVisible(false);
        }
        else{
            InvalidAttack.setVisible(true);
        }
    }
    
    
/*//////////////////////////////////////////////////////
MAIN
 The main method is not used in JavaFX
//////////////////////////////////////////////////////*/
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

    
    
    
   
    
    
    
