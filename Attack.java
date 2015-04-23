
import javafx.scene.control.RadioButton;

class Attack extends RadioButton{
	
///////////////////////////
//FIELDS
///////////////////////////

	private String Name;
        private int ID;
	private int Damage;
	private int Accuracy;
	private boolean CausesBleeding;
	private boolean Poisons;
	private boolean Stuns;
	private boolean KnocksBack;
	private int KnockBackSpaces;
	private boolean Heals;
	public boolean[] ValidAttackPositions;
	public boolean[] ValidTargets;
	private boolean TargetAll;	// True if the attack targets multiple enemies, false for single-target
	private boolean TargetTeam;	// True for healing spells, enchantment spells (though we don't have any of those, w/e)

	
///////////////////////////
//CONSTRUCTOR
///////////////////////////
	
	public Attack(String name){
		
		switch (name) {
			
		/*Warrior's Attacks*/
			case "Crushing Blow":
				this.Name = name;
                                this.ID = 1;
				this.Damage = 125; //125%, that is
				this.Accuracy = 75;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, false, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, false, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;
				
			case "Smite":
				this.Name = name;
                                this.ID = 2;
				this.Damage = 100; 
				this.Accuracy = 90;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, false, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, false, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;				

			case "Cleave":
				this.Name = name;
                                this.ID = 3;
				this.Damage = 50; 
				this.Accuracy = 100;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, false, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, false, false};
				this.TargetAll = true;
				this.TargetTeam = false;
				break;	
					
			case "Shield Bash":
				this.Name = name;
                                this.ID = 4;
				this.Damage = 50; 
				this.Accuracy = 85;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = true;
				this.KnockBackSpaces = 2;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, false, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, false, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	
				
				
		/*Ranger's Attacks*/
			case "Stab":
				this.Name = name;
                                this.ID = 1;
				this.Damage = 75; 
				this.Accuracy = 95;
				this.CausesBleeding = true;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, false, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	 
				
			case "Ranged Shot":
				this.Name = name;
                                this.ID = 2;
				this.Damage = 100; 
				this.Accuracy = 80;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, true, true, false, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, true};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	
				
			case "Multishot":
				this.Name = name;
                                this.ID = 3;
				this.Damage = 60; 
				this.Accuracy = 100;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, true, true, false, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, false};
				this.TargetAll = true;
				this.TargetTeam = false;
				break;	 
					
			case "Poison Bomb":
				this.Name = name;
                                this.ID = 4;
				this.Damage = 40; 
				this.Accuracy = 80;
				this.CausesBleeding = false;
				this.Poisons = true;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, true, true, false, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, false, false, true, true};
				this.TargetAll = true;
				this.TargetTeam = false;
				break;	  				


		/*Mage's Attacks*/
			case "Shake Foundation":
				this.Name = name;
                                this.ID = 1;
				this.Damage = 20; 
				this.Accuracy = 100;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = true;
				this.KnockBackSpaces = 3; //Reverses all enemy positions
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, true, true, true, false, false, false, false}; 
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, false, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;
				
			case "Lightning Strike":
				this.Name = name;
                                this.ID = 2;
				this.Damage = 100; 
				this.Accuracy = 65;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = true;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, true, true, false, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;				

			case "Dark Deliverance":
				this.Name = name;
                                this.ID = 3;
				this.Damage = 150;
				this.Accuracy = 80;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, true, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, false, false, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	
					
			case "Phlegethon Flame":
				this.Name = name;
                                this.ID = 4;
				this.Damage = 50; 
				this.Accuracy = 95;
				this.CausesBleeding = true;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, true, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	
				
				
		/*Priest's Attacks*/
			case "Judgement":
				this.Name = name;
                                this.ID = 1;
				this.Damage = 0; 
				this.Accuracy = 95;
				this.CausesBleeding = true;
				this.Poisons = false;
				this.Stuns = true;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {false, false, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, false, false, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	 
				
			case "Healing Ritual":
				this.Name = name;
                                this.ID = 2;
				this.Damage = 125;	// For healing, "Damage" will convert to amount healed. This move will heal for 7-12
				this.Accuracy = 100;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = true;
				this.ValidAttackPositions = new boolean[] {true, true, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {true, true, true, true, false, false, false, false};
				this.TargetAll = false;
				this.TargetTeam = true;
				break;	
				
			case "Solomon's Ring":
				this.Name = name;
                                this.ID = 3;
				this.Damage = 50; 
				this.Accuracy = 100;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, false, false, false, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, true};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	 
					
			case "Mephisto's Rage":
				this.Name = name;
                                this.ID = 4;
				this.Damage = 30; 
				this.Accuracy = 100;
				this.CausesBleeding = false;
				this.Poisons = true;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, true, true, true, false, false, false, false};
				this.ValidTargets = new boolean[] {false, false, false, false, true, true, true, false};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;
                            
                        case "MoveBack":
				this.Name = name;
                                this.ID = 5;
				this.Damage = 0; 
				this.Accuracy = 0;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, true, true, true, true, true, true, true};
				this.ValidTargets = new boolean[] {true, true, true, true, true, true, true, true};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;
                            
                        case "MoveForward":
				this.Name = name;
                                this.ID = 6;
				this.Damage = 0; 
				this.Accuracy = 0;
				this.CausesBleeding = false;
				this.Poisons = false;
				this.Stuns = false;
				this.KnocksBack = false;
				this.KnockBackSpaces = 0;
				this.Heals = false;
				this.ValidAttackPositions = new boolean[] {true, true, true, true, true, true, true, true};
				this.ValidTargets = new boolean[] {true, true, true, true, true, true, true, true};
				this.TargetAll = false;
				this.TargetTeam = false;
				break;	
	
							
			default:
				System.out.println("Invalid attack requested. Ending game...");
				System.exit(-1);
		}
	}
	

	
///////////////////////////
//CHECKERS
///////////////////////////

	public boolean isValidTarget(int position){
		return ValidTargets[position];
	}
	
	public boolean isValidAttackPosition(int position){
		return ValidAttackPositions[position];
	}
	

///////////////////////////
//GETTERS
///////////////////////////
	
	public String getName(){
		return this.Name;
	}
        
        public int getID(){
                return this.ID;
        }
	
	public int getDamage(){
		return this.Damage;
	}
	
	public int getAccuracy(){
		return this.Accuracy;
	}
	
	public boolean causesBleeding(){
		return this.CausesBleeding;
	}
	
	public boolean Poisons(){
		return this.Poisons;
	}
	
	public boolean Stuns(){
		return this.Stuns;
	}
	
	public boolean Heals() {
		return this.Heals;
	}
	
	public boolean knocksBack(){
		return this.KnocksBack;
	}
	
	public int getKnockBackSpaces(){
		return this.KnockBackSpaces;
	}
	
	public boolean targetsAll() {
		return this.TargetAll;
	}	
	
	public boolean targetsTeam() {
		return this.TargetTeam;
	}
	
        public static void reverse(boolean[] array) {
            if (array == null) {
                return;
            }
            int i = 0;
            int j = array.length - 1;
            boolean tmp;
            while (j > i) {
                tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j--;
                i++;
            }
        }
	
///////////////////////////
//MAIN
///////////////////////////
	
	public static void main(String[] args) {}

}