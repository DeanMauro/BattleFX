
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


class Dude extends ImageView{
	
///////////////////////////
//FIELDS
///////////////////////////

	private String Name;
        private boolean Enemy;
	private int MaxHP;
	private IntegerProperty HP;
        private BooleanProperty Alive;
	private int minDamage;
	private int maxDamage;
	private int Speed;
	private int Position;
	private int Readiness;
	private BooleanProperty Bleeding;
	private BooleanProperty Poisoned;
	private BooleanProperty Stunned;
        private int TurnsBleeding = 0;
        private int TurnsPoisoned = 0;
	private Attack Attack1;
	private Attack Attack2;
	private Attack Attack3;
	private Attack Attack4;
        
        public ImageView Blood;
        public ImageView Poison;
        public ImageView Stun;
        
        public ImageView Attacks;
        public Attack MoveBack;
        public Attack MoveForward;
        public ToggleGroup MoveSet;
        
        public Nameplate Stats;
        
        public DropShadow AttackingShadow;
        public DropShadow DefendingShadow;
	
///////////////////////////
//CONSTRUCTOR
///////////////////////////

	public Dude(String name, int position, boolean isEnemy){
                super();
		this.Name = name;
		name = name.toLowerCase();
                
                this.Enemy = isEnemy;
                
                this.HP = new SimpleIntegerProperty(0);
                this.Alive = new SimpleBooleanProperty(true);
                visibleProperty().bind(this.Alive);
                this.Bleeding = new SimpleBooleanProperty(false);
                this.Poisoned = new SimpleBooleanProperty(false);
                this.Stunned = new SimpleBooleanProperty(false);
                
                this.Blood = new ImageView(new Image("Images/Blood.png"));
                this.Poison = new ImageView(new Image("Images/Poison.png"));
                this.Stun = new ImageView(new Image("Images/Stun.png"));
                
                MoveSet = new ToggleGroup();
                
                this.MoveBack = new Attack("MoveBack");
                this.MoveBack.setText("Move Back 1");
                this.MoveBack.setFont(Font.font("monospace", FontWeight.EXTRA_BOLD, 17));
                this.MoveBack.setToggleGroup(MoveSet);
                this.MoveForward = new Attack("MoveForward");
                this.MoveForward.setText("Move Forward 1");
                this.MoveForward.setFont(Font.font("monospace", FontWeight.EXTRA_BOLD, 17));
                this.MoveForward.setToggleGroup(MoveSet);
                
                AttackingShadow = new DropShadow();
                AttackingShadow.setOffsetY(3.0);
                AttackingShadow.setOffsetX(3.0);
                AttackingShadow.setColor(Color.YELLOW);
                AttackingShadow.setWidth(80);

                DefendingShadow = new DropShadow();
                DefendingShadow.setOffsetY(3.0);
                DefendingShadow.setOffsetX(3.0);
                DefendingShadow.setColor(Color.FUCHSIA);
                DefendingShadow.setWidth(80);

		switch (name) {
			
		//Create Warrior
			case "warrior":
				this.MaxHP = 35;
				this.HP.set(35);    //Since HP is a Property now, we need to use methods to change its value
				this.minDamage = 7;
				this.maxDamage = 11;
				this.Speed = 6;
				this.Position = position;
				this.Readiness = 6;
				this.Bleeding.set(false);
				this.Poisoned.set(false);
				this.Stunned.set(false);
                                this.Blood.visibleProperty().bind(this.Bleeding);
                                this.Poison.visibleProperty().bind(this.Poisoned);
                                this.Stun.visibleProperty().bind(this.Stunned);
				
				Attack1 = new Attack("Crushing Blow");
				Attack2 = new Attack("Smite");
				Attack3 = new Attack("Cleave");
				Attack4 = new Attack("Shield Bash");
                                
                                //Add radio buttons to a group
                                Attack1.setToggleGroup(MoveSet);
                                Attack2.setToggleGroup(MoveSet);
                                Attack3.setToggleGroup(MoveSet);
                                Attack4.setToggleGroup(MoveSet);
                                
                                //This is an image that shows all their attacks
                                this.Attacks = new ImageView(new Image("Images/WarriorAttacks.png"));
				
                                //Positions and sprites will be different for enemies and heroes
                                if(isEnemy){
                                    setImage(new Image("Images/EnemyWarrior.png"));
                                    
                                    //Valid positions have to be reversed for enemies on the other side of the field
                                    Attack.reverse(Attack1.ValidAttackPositions);
                                    Attack.reverse(Attack2.ValidAttackPositions);
                                    Attack.reverse(Attack3.ValidAttackPositions);
                                    Attack.reverse(Attack4.ValidAttackPositions);
                                    Attack.reverse(MoveBack.ValidAttackPositions);
                                    Attack.reverse(MoveForward.ValidAttackPositions);
                                    
                                    //Valid targets too
                                    Attack.reverse(Attack1.ValidTargets);
                                    Attack.reverse(Attack2.ValidTargets);
                                    Attack.reverse(Attack3.ValidTargets);
                                    Attack.reverse(Attack4.ValidTargets);
                                    
                                    //By binding the x coordinates of the status symbols to that of the Dude,
                                    //whenever the Dude moves, the symbols will follow
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(10)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(45)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(80)));
                                    this.Stun.setY(7);
                                    
                                    //Position the attacks image on the stage
                                    this.Attacks.setX(800);
                                    this.Attacks.setY(440);
                                    
                                    //Position the radio buttons on the stage
                                    this.getAttack(1).setLayoutX(775);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(1165);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(775);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(1165);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    //Position the position changing radio buttons on the stage
                                    this.MoveBack.setLayoutX(900);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(900);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    //After all the stats have been filled in, we have to
                                    //create a nameplate to display them
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(20)));
                                    
                                } else {
                                    //Same as above, just with different positions
                                    setImage(new Image("Images/Warrior.png"));
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(20)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(55)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(90)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(130);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(105);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(495);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(105);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(495);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(230);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(230);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(40)));
                                }
                                
                            
                                break;
			
		//Create Ranger
			case "ranger":
				this.MaxHP = 25;
				this.HP.set(25);
				this.minDamage = 6;
				this.maxDamage = 9;
				this.Speed = 8;
				this.Position = position;
				this.Readiness = 8;
				this.Bleeding.set(false);
				this.Poisoned.set(false);
				this.Stunned.set(false);
                                this.Blood.visibleProperty().bind(this.Bleeding);
                                this.Poison.visibleProperty().bind(this.Poisoned);
                                this.Stun.visibleProperty().bind(this.Stunned);
				
				Attack1 = new Attack("Stab");
				Attack2 = new Attack("Ranged Shot");
				Attack3 = new Attack("Multishot");
				Attack4 = new Attack("Poison Bomb");
                                
                                Attack1.setToggleGroup(MoveSet);
                                Attack2.setToggleGroup(MoveSet);
                                Attack3.setToggleGroup(MoveSet);
                                Attack4.setToggleGroup(MoveSet);
                                
                                this.Attacks = new ImageView(new Image("Images/RangerAttacks.png"));
                                
                                if(isEnemy){
                                    setImage(new Image("Images/EnemyRanger.png"));
                                    
                                    Attack.reverse(Attack1.ValidAttackPositions);
                                    Attack.reverse(Attack2.ValidAttackPositions);
                                    Attack.reverse(Attack3.ValidAttackPositions);
                                    Attack.reverse(Attack4.ValidAttackPositions);
                                    Attack.reverse(MoveBack.ValidAttackPositions);
                                    Attack.reverse(MoveForward.ValidAttackPositions);
                                    
                                    Attack.reverse(Attack1.ValidTargets);
                                    Attack.reverse(Attack2.ValidTargets);
                                    Attack.reverse(Attack3.ValidTargets);
                                    Attack.reverse(Attack4.ValidTargets);
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(10)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(45)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(80)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(800);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(775);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(1165);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(775);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(1165);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(900);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(900);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(40)));
                                    
                                }else {
                                    setImage(new Image("Images/Ranger.png"));
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(50)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(85)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(120)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(130);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(105);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(495);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(105);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(495);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(230);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(230);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(60)));
                                }
                                
				break;
				
		//Create Mage		
			case "mage":
				this.MaxHP = 20;
				this.HP.set(20);
				this.minDamage = 6;
				this.maxDamage = 9;
				this.Speed = 7;
				this.Position = position;
				this.Readiness = 7;
				this.Bleeding.set(false);
				this.Poisoned.set(false);
				this.Stunned.set(false);
                                this.Blood.visibleProperty().bind(this.Bleeding);
                                this.Poison.visibleProperty().bind(this.Poisoned);
                                this.Stun.visibleProperty().bind(this.Stunned);
				
				Attack1 = new Attack("Shake Foundation");
				Attack2 = new Attack("Lightning Strike");
				Attack3 = new Attack("Dark Deliverance");
				Attack4 = new Attack("Phlegethon Flame");
                                
                                Attack1.setToggleGroup(MoveSet);
                                Attack2.setToggleGroup(MoveSet);
                                Attack3.setToggleGroup(MoveSet);
                                Attack4.setToggleGroup(MoveSet);
                                
                                this.Attacks = new ImageView(new Image("Images/MageAttacks.png"));
                                
                                if(isEnemy){
                                    setImage(new Image("Images/EnemyMage.png"));
                                    
                                    Attack.reverse(Attack1.ValidAttackPositions);
                                    Attack.reverse(Attack2.ValidAttackPositions);
                                    Attack.reverse(Attack3.ValidAttackPositions);
                                    Attack.reverse(Attack4.ValidAttackPositions);
                                    Attack.reverse(MoveBack.ValidAttackPositions);
                                    Attack.reverse(MoveForward.ValidAttackPositions);
                                    
                                    Attack.reverse(Attack1.ValidTargets);
                                    Attack.reverse(Attack2.ValidTargets);
                                    Attack.reverse(Attack3.ValidTargets);
                                    Attack.reverse(Attack4.ValidTargets);
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(50)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(85)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(120)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(800);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(775);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(1165);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(775);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(1165);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(900);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(900);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(60)));
                                    
                                }else {
                                    setImage(new Image("Images/Mage.png"));
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(40)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(75)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(110)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(130);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(105);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(495);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(105);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(495);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(230);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(230);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(40)));
                                    
                                }
                                
				break;
		
		//Create Priest		
			default:
			case "priest":
				this.MaxHP = 18;
				this.HP.set(18);
				this.minDamage = 5;
				this.maxDamage = 8;
				this.Speed = 5;
				this.Position = position;
				this.Readiness = 5;
				this.Bleeding.set(false);
				this.Poisoned.set(false);
				this.Stunned.set(false);
                                this.Blood.visibleProperty().bind(this.Bleeding);
                                this.Poison.visibleProperty().bind(this.Poisoned);
                                this.Stun.visibleProperty().bind(this.Stunned);
				
				Attack1 = new Attack("Judgement");
				Attack2 = new Attack("Healing Ritual");
				Attack3 = new Attack("Solomon's Ring");
				Attack4 = new Attack("Mephisto's Rage");
                                
                                Attack1.setToggleGroup(MoveSet);
                                Attack2.setToggleGroup(MoveSet);
                                Attack3.setToggleGroup(MoveSet);
                                Attack4.setToggleGroup(MoveSet);
                                
                                this.Attacks = new ImageView(new Image("Images/PriestAttacks.png"));
                                
                                if(isEnemy){
                                    setImage(new Image("Images/EnemyPriest.png"));
                                    
                                    Attack.reverse(Attack1.ValidAttackPositions);
                                    Attack.reverse(Attack2.ValidAttackPositions);
                                    Attack.reverse(Attack3.ValidAttackPositions);
                                    Attack.reverse(Attack4.ValidAttackPositions);
                                    Attack.reverse(MoveBack.ValidAttackPositions);
                                    Attack.reverse(MoveForward.ValidAttackPositions);
                                    
                                    Attack.reverse(Attack1.ValidTargets);
                                    Attack.reverse(Attack2.ValidTargets);
                                    Attack.reverse(Attack3.ValidTargets);
                                    Attack.reverse(Attack4.ValidTargets);
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(0)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(35)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(70)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(800);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(775);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(1165);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(775);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(1165);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(900);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(900);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(20)));
                                    
                                }else {
                                    setImage(new Image("Images/Priest.png"));
                                    
                                    this.Blood.xProperty().bind(xProperty().add(new SimpleDoubleProperty(30)));
                                    this.Blood.setY(5);
                                    this.Poison.xProperty().bind(xProperty().add(new SimpleDoubleProperty(65)));
                                    this.Poison.setY(5);
                                    this.Stun.xProperty().bind(xProperty().add(new SimpleDoubleProperty(100)));
                                    this.Stun.setY(7);
                                    
                                    this.Attacks.setX(130);
                                    this.Attacks.setY(440);
                                    
                                    this.getAttack(1).setLayoutX(105);
                                    this.getAttack(1).setLayoutY(465);
                                    this.getAttack(2).setLayoutX(495);
                                    this.getAttack(2).setLayoutY(465);
                                    this.getAttack(3).setLayoutX(105);
                                    this.getAttack(3).setLayoutY(535);
                                    this.getAttack(4).setLayoutX(495);
                                    this.getAttack(4).setLayoutY(535);
                                    this.getAttack(1).setSelected(true);
                                    
                                    this.MoveBack.setLayoutX(230);
                                    this.MoveBack.setLayoutY(600);
                                    this.MoveForward.setLayoutX(230);
                                    this.MoveForward.setLayoutY(630);
                                    
                                    createNameplate();
                                    this.Stats.box.xProperty().bind(xProperty().add(new SimpleDoubleProperty(40)));
                                }
                                
				break;
		}
                
                hideEverything();
		
	}
	
        
        
///////////////////////////
//CREATE NAMEPLATE
///////////////////////////  
        private void createNameplate(){
            
            this.Stats = new Nameplate(this.getName(),
                                       this.getMaxHP(), 
                                       this.getMinDamage(), this.getMaxDamage(), 
                                       this.getSpeed());
            
            //Now let's bind the nameplate's position to that of the Dude's sprite
            //so it follows him when he moves
            this.Stats.box.yProperty().set(290);
            
            //Let's bind its visibility to that of the sprite. When the Dude dies,
            //everything will be invisible.
            this.Stats.visibleProperty().bind(this.Alive);
            
            //Lastly, let's bind the HPValue label to the Dude's actual HP
            //so the display will update every time his HP changes
            this.Stats.HPValue.textProperty().bind(Bindings.convert(this.HP));
        }
        
        
        
///////////////////////////
//HIDE
///////////////////////////        
        public void hideEverything(){
            //We only want the currently attacking character's attacks to show
            //so to begin, let's just hide everything
            
            this.Attacks.setVisible(false);
            this.Attack1.setVisible(false);
            this.Attack2.setVisible(false);
            this.Attack3.setVisible(false);
            this.Attack4.setVisible(false);
            
            this.MoveBack.setVisible(false);
            this.MoveForward.setVisible(false);
            
            setEffect(null);
            
        }
        
        
        
///////////////////////////
//SET UP ATTACKER
///////////////////////////        
        public void setUpAttacker(){
            //If the Dude is attacking, reveal his attack image
            //and all associated radiobuttons

            this.Attacks.setVisible(true);
            this.Attack1.setVisible(true);
            this.Attack2.setVisible(true);
            this.Attack3.setVisible(true);
            this.Attack4.setVisible(true);
            
            this.MoveBack.setVisible(true);
            this.MoveForward.setVisible(true);
            
            setEffect(AttackingShadow);
            
        }  
        
///////////////////////////
//SET UP DEFENDER
///////////////////////////        
        public void setUpDefender(){
            //Add shadow to the selected attack target
            setEffect(DefendingShadow);
        }  
        
        
        
        
///////////////////////////
//GETTERS
///////////////////////////

	public String getName(){
		return this.Name;
	}
	
	public int getMaxHP() {
		return this.MaxHP;
	}
	
	public int getHP(){
		return this.HP.get();
	}

	public int getMinDamage(){
		return this.minDamage;
	}
	
	public int getMaxDamage(){
		return this.maxDamage;
	}
	
	public int getSpeed(){
		return this.Speed;
	}
	
	public int getPosition(){
		return this.Position;
	}
	
	public int getReadiness() {
		return this.Readiness;
	}
	
	public boolean isBleeding(){
		return this.Bleeding.get();
	}
	
	public boolean isPoisoned(){
		return this.Poisoned.get();
	}
	
	public boolean isStunned(){
		return this.Stunned.get();
	}
        
        public boolean isHero(){
            return !this.Enemy;
        }
        
        public boolean isEnemy(){
            return this.Enemy;
        }
        
        public boolean isAlive(){
            return this.Alive.get();
        }
	
	public Attack getAttack(int num){
		
		switch (num) {
			
			default:
			case 1:
				return Attack1;
			case 2:
				return Attack2;
			case 3:
				return Attack3;
			case 4:
				return Attack4;
				
		}
	}

///////////////////////////
//SETTERS
///////////////////////////	

	public void setPosition(int pos){
		this.Position = pos;
	}
	
	public void setHP(int hp) {
		this.HP.set(hp);
	}
	
	public void setReadiness(int readiness) {
		this.Readiness = readiness;
	}
	
	public void setBleeding(boolean HemophiliacInGymClass){
		this.Bleeding.set(HemophiliacInGymClass); //wow
	}
	
	public void setPoisoned(boolean ArbokUsePoisonSting){
		this.Poisoned.set(ArbokUsePoisonSting); //lol
	}
	
	public void setStunned(boolean StevenHawking){
		this.Stunned.set(StevenHawking); //lmao
	}
        
        public void UpdateLivingOrDead(){
            if(this.HP.get() == 0){
                this.Readiness = -5000;
                
                this.Bleeding.set(false);
                this.Poisoned.set(false);
                this.Stunned.set(false);
                
                this.Alive.set(false);
            }
        }
        

              
///////////////////////////
//INFLICT BLEEDING DAMAGE
///////////////////////////      
        public void BleedBabyBleed(){
            this.TurnsBleeding += 1;
            
            this.HP.set(this.HP.get() - 2);  //Bleeding will take 2 HP
            
            if(this.TurnsBleeding == 3){     //Bleeding will last 3 turns
                this.TurnsBleeding = 0;
                this.Bleeding.set(false);
            }
        }
        
	
        
///////////////////////////
//INFLICT POISON DAMAGE
///////////////////////////      
        public void FeelingVenomenal(){
            this.TurnsPoisoned += 1;
            
            this.HP.set(this.HP.get() - this.TurnsPoisoned);   //Poison damage will increase each turn
            
            if(this.TurnsPoisoned == 3){     //Poison will last 3 turns
                this.TurnsPoisoned = 0;
                this.Poisoned.set(false);
            }
        }
       
        
        
///////////////////////////
//INFLICT STUN
//A man got into a car accident and was rushed to the hospital.
//His life was saved but the left side of his body was completely paralyzed.
//"He's going to be all right now," said the doctor.
///////////////////////////  
        
        public void WheelchairBound(){
            this.Stunned.set(false);  //Stun only lasts 1 turn
        }
        

      
        
///////////////////////////
//MAIN
///////////////////////////		
	public static void main(String[] args) {}

}