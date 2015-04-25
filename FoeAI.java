import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
//import wekafolder.wekaML;


/**
 * Created by Drew on 4/19/2015.
 * There are three AI Versions:
 *   Dumber: Select the strongest attack and target whichever hero is in front
 *   Dumb: Use some battle logic to determine a smart attack (if statements)
 *   Smart: Use a decision tree to choose the best attack
 *
 * In BattleField.DoFoesTurn, SelectFoesAttack() is called. This method will set the selectedAttack and CurrentDefender
 * fields in the battle.
 */
public class FoeAI {
    BattleField Battle;
    String AIVersion;
    
    public wekaML mlw;
    public J48 warriorTree;
    public wekaML mlm;
    public J48 mageTree;
    public wekaML mlp;
    public J48 priestTree;
    public wekaML mlr;
    public J48 rangerTree;

    public FoeAI(BattleField Battle) {
        this.Battle = Battle;
        AIVersion = "Smart";
        
        mlw = new wekaML();
        try {
        warriorTree = mlw.makeTree("WarriorLog.txt");
        }
        catch (Exception e) {
        	System.out.println(e);
        }
        mlm = new wekaML();
        try {
            mageTree = mlm.makeTree("MageLog.txt");
            }
            catch (Exception e) {
            	System.out.println(e);
            }
        mlp = new wekaML();
        try {
            priestTree = mlp.makeTree("PriestLog.txt");
            }
            catch (Exception e) {
            	System.out.println(e);
            }
        mlr = new wekaML();
        try {
            rangerTree = mlr.makeTree("RangerLog.txt");
            }
            catch (Exception e) {
            	System.out.println(e);
            }
    }

    public void SetAIVersion(String AIVersion) {
        this.AIVersion = AIVersion;
    }

    public void SelectFoesAttack() {
        switch(AIVersion) {
            case "Dumber":
                SelectDumberMove();
                break;
            case "Dumb":
                SelectDumbMove();
                break;
            case "Smart":
                SelectSmartMove();
                break;
            default:
                System.out.println("You are dumb. Set AIVersion to either \"Dumber\", \"Dumb\", or \"Smart\". Idiot.");
                break;
        }
    }

///=====================================
/// MACHINE LEARNING AI
///=====================================

    public void SelectSmartMove() {
    	Instance myInstance = Battle.thisTurn;
    	double[] moveDist = null;
    	
    	switch(Battle.CurrentAttacker.getName()) {
        case "Warrior":
        	myInstance.setDataset(mlw.myInstances);
        	try {
        		moveDist = warriorTree.distributionForInstance(myInstance);
        	}
        	catch (Exception e) {
        		System.out.println(e);
        	}
            break;
        case "Ranger":
        	myInstance.setDataset(mlr.myInstances);
        	try {
            	moveDist = rangerTree.distributionForInstance(myInstance);
        	}
        	catch (Exception e) {
        		System.out.println(e);
        	}
        	
            break;
        case "Mage":
        	myInstance.setDataset(mlm.myInstances);
        	try {
            	moveDist = mageTree.distributionForInstance(myInstance);
        	}
        	catch (Exception e) {
        		System.out.println(e);
        	}
        	
            break;
        case "Priest":
        	myInstance.setDataset(mlp.myInstances);
        	try {
            	moveDist = priestTree.distributionForInstance(myInstance);
        	}
        	catch (Exception e) {
        		System.out.println(e);
        	}
        	
            break;
        default:
            System.out.println("Wut");
    	}
    	
    	//for (int i=0; i < moveDist.length; i++) {
    	//	System.out.println(moveDist[i]);
    	//}
    	int move = maxprob(moveDist);
    	
    	if (move == 16) {
            Battle.selectedAttack = Battle.CurrentAttacker.MoveBack;
            Battle.CurrentDefender = Battle.Ranger;
            
            if (Battle.selectedAttack.isValidAttackPosition(Battle.CurrentAttacker.getPosition()) || Battle.selectedAttack.isValidTarget(Battle.CurrentDefender.getPosition())) {
            	return;
            }
    	}
    	if (move == 17) {
            Battle.selectedAttack = Battle.CurrentAttacker.MoveForward;
            Battle.CurrentDefender = Battle.Ranger;
            
            if (Battle.selectedAttack.isValidAttackPosition(Battle.CurrentAttacker.getPosition()) || Battle.selectedAttack.isValidTarget(Battle.CurrentDefender.getPosition())) {
            	return;
            }
    	}
    	 
    	int moveNum = move / 4 + 1;
    	int target = (move % 4) + 1;
    	System.out.println("Calling getmove for: " + Battle.CurrentAttackersName);
    	getMove(moveNum, target, move, moveDist);
    }
    
    public void setAttackTarget(int target, int moveNum) {
    	if (Battle.CurrentAttacker == Battle.EnemyPriest && moveNum == 2) {
	   		switch (target){
    		case 1:
    			Battle.CurrentDefender = Battle.EnemyWarrior;
    			break;
    		case 2:
    			Battle.CurrentDefender = Battle.EnemyRanger;
    			break;
    		case 3:
    			Battle.CurrentDefender = Battle.EnemyMage;
    			break;
    		case 4:
    			Battle.CurrentDefender = Battle.EnemyPriest;
    			break;
    		default:
    			Battle.CurrentDefender = Battle.Ranger;
    		}
		}
		else {
	   		switch (target){
    		case 1:
    			Battle.CurrentDefender = Battle.Warrior;
    			break;
    		case 2:
    			Battle.CurrentDefender = Battle.Ranger;
    			break;
    		case 3:
    			Battle.CurrentDefender = Battle.Mage;
    			break;
    		case 4:
    			Battle.CurrentDefender = Battle.Priest;
    			break;
    		default:
    			Battle.CurrentDefender = Battle.Ranger;
    		}
		}
    }
    
    public void getMove(int moveNum, int target, int move, double[] moveDist) {
    	//System.out.println("Current Attacker Is: " + Battle.CurrentAttacker.getName());
    	//System.out.println("Current move is: " + moveNum + "against target: " + target);
    	Battle.selectedAttack = Battle.CurrentAttacker.getAttack(moveNum);
    	setAttackTarget(target, moveNum);
		
		//if the attack cannot be completed, get a different attack
		while (!Battle.selectedAttack.isValidAttackPosition(Battle.CurrentAttacker.getPosition()) || !Battle.selectedAttack.isValidTarget(Battle.CurrentDefender.getPosition())) {
			//System.out.println("Inalid position for: "+Battle.selectedAttack.getName());
			System.out.println("Invalid position or target for: " + Battle.selectedAttack.getName());

			if(move == 14) {
				System.out.println(moveDist[move]);
			}
			else if (move==15) {
				System.out.println(moveDist[move]);
			}
			
			moveDist[move] = -1;
			move = maxprob(moveDist);
			
			
			if (move == 16) {
	            Battle.selectedAttack = Battle.CurrentAttacker.MoveBack;
	            Battle.CurrentDefender = Battle.Ranger;
	            //return;
	    	}
	    	else if (move == 17) {
	            Battle.selectedAttack = Battle.CurrentAttacker.MoveForward;
	            Battle.CurrentDefender = Battle.Ranger;
	            //return;
	    	}
	    	else {
	    		moveNum = move / 4 + 1;
	    		target = (move % 4) + 1;
	    		getMove(moveNum, target, move, moveDist);
	    	}
		}		
		
    	//System.out.println(Battle.selectedAttack.getName());
    	//System.out.println(Battle.CurrentDefender.getName());
    }

///=====================================
/// DUMB AI 2.0
///=====================================

    public void SelectDumbMove() {
        switch(Battle.CurrentAttacker.getName()) {
            case "Warrior":
                SelectWarriorMove();
                break;
            case "Ranger":
                SelectRangerMove();
                break;
            case "Mage":
                SelectMageMove();
                break;
            case "Priest":
                SelectPriestMove();
                break;
            default:
                System.out.println("Wut");
        }
    }

    /// WARRIOR BATTLE LOGIC
    /// If in position 7 or 8, move forward
    /// If someone in spot 1 or 2 has 7 or less HP, use Smite on them
    /// If the warrior is in spot 1, use Shield Bash
    /// Otherwise use Crushing Blow on whoever has less HP
    public void SelectWarriorMove() {
        int position = Battle.CurrentAttacker.getPosition();
        if(position >= 6) {
            Battle.selectedAttack = Battle.CurrentAttacker.MoveForward;
            SelectFirstDude();
        }
        else {
            Dude frontDude = Battle.DudesInOrder.get(3);
            if(frontDude.getName().equals("Warrior")) {
                Battle.selectedAttack = Battle.CurrentAttacker.getAttack(3);
                Battle.CurrentDefender = Battle.DudesInOrder.get(3);
            }
            else {
                Battle.selectedAttack = Battle.CurrentAttacker.getAttack(0);
                SelectWeakestDude();
            }
        }
    }

    /// RANGER BATTLE LOGIC
    /// Ranger doesn't have any special moves, really. He can target anybody with ranged shot, though
    /// Ranged shot whoever has the least HP
    public void SelectRangerMove() {
        if(Battle.CurrentAttacker.getPosition() == 4) { // Move back to use ranged shot
            Battle.selectedAttack = Battle.CurrentAttacker.MoveBack;
            SelectFirstDude();
        }
        else {
            Battle.selectedAttack = Battle.CurrentAttacker.getAttack(1);
            SelectWeakestDude();
        }
    }

    /// MAGE BATTLE LOGIC
    /// If the warrior is in the front, Shake Foundation
    /// If nobody is in the front two spots, Shake Foundation
    /// If somebody has <5 hp, Phlegethon Flame
    /// Otherwise, Dark Deliverance
    public void SelectMageMove() {
        Dude frontDude = Battle.DudesInOrder.get(3);
        if(frontDude.getName().equals("Warrior")) {
            Battle.selectedAttack = new Attack("Shake Foundation");
            Battle.CurrentDefender = Battle.DudesInOrder.get(3);
        } else if(!Battle.DudesInOrder.get(2).isAlive() && !Battle.DudesInOrder.get(3).isAlive()) {
            Battle.selectedAttack = new Attack("Shake Foundation");
            if (!Battle.DudesInOrder.get(1).isAlive()) {
                Battle.CurrentDefender = Battle.DudesInOrder.get(0);
            } else {
                Battle.CurrentDefender = Battle.DudesInOrder.get(1);
            }
        } else if(Battle.DudesInOrder.get(3).isAlive()) {
            Battle.selectedAttack = new Attack("Dark Deliverance");
            Battle.CurrentDefender = Battle.DudesInOrder.get(3);
        } else {
            Battle.selectedAttack = new Attack("Phlegethon Flame");
            SelectWeakestDude();
        }
    }

    /// PRIEST BATTLE LOGIC
    /// If the priest has been swapped to the front, it will prefer to use Judgement
    /// If a character has <10 HP, it will heal (Priority: Self, Mage, Warrior, Ranger)
    /// Otherwise, it will select the strongest move and target whoever is weakest
    public void SelectPriestMove() {
        if(Battle.EnemyPriest.getHP() < 10) {               // Heal Self
            Battle.CurrentDefender = Battle.EnemyPriest;
            Battle.selectedAttack = new Attack("Healing Ritual");
        } else if(Battle.EnemyMage.isAlive() && Battle.EnemyMage.getHP() < 10) {          // Heal Mage
            Battle.CurrentDefender = Battle.EnemyMage;
            Battle.selectedAttack = new Attack("Healing Ritual");
        } else if(Battle.EnemyWarrior.isAlive() && Battle.EnemyWarrior.getHP() < 10) {       // Heal Warrior
            Battle.CurrentDefender = Battle.EnemyWarrior;
            Battle.selectedAttack = new Attack("Healing Ritual");
        } else if(Battle.EnemyRanger.isAlive() && Battle.EnemyRanger.getHP() < 10) {        // Heal Ranger
            Battle.CurrentDefender = Battle.EnemyRanger;
            Battle.selectedAttack = new Attack("Healing Ritual");
        } else {
            if(Battle.EnemyPriest.getPosition() == 4 && Battle.DudesInOrder.get(3).isAlive()) {
                Battle.CurrentDefender = Battle.DudesInOrder.get(3);
                Battle.selectedAttack = new Attack("Judgement");
            } else {
                SelectStrongestAttack();
                SelectWeakestDude();
            }
        }
    }

///=====================================
/// REALLY DUMB AI 1.0
///=====================================

    public void SelectDumberMove() {
        SelectStrongestAttack();
        SelectFirstDude();
    }

///=====================================
/// HELPER METHODS
///=====================================

    /// Select the attack which does the most damage, and is valid
    /// If none are valid, the character will move forward (i.e. Warrior after Shake Foundation)
    public void SelectStrongestAttack() {
        int strongestAttackIndex = -1;
        int strongestAttack = 0;
        for(int i = 0; i < 4; i++) {
            Attack attack = Battle.CurrentAttacker.getAttack(i);
            if(attack.getDamage() > strongestAttack && !attack.getName().equals("Healing Ritual")) {
                if(attack.isValidAttackPosition(Battle.CurrentAttacker.getPosition())) {
                    strongestAttack = attack.getDamage();
                    strongestAttackIndex = i;
                }
            }
        }
        if(strongestAttackIndex > -1) {
            Battle.selectedAttack = Battle.CurrentAttacker.getAttack(strongestAttackIndex);
        } else {
            Battle.selectedAttack = Battle.CurrentAttacker.MoveForward;
        }
    }

    public void SelectWeakestDude() {
        int leastHP = 100;
        int weakestDudeIndex = 0;
        for(int i = 0; i < 4; i++) {
            Dude dude = Battle.DudesInOrder.get(i);
            if(Battle.selectedAttack.isValidTarget(i) && dude.isAlive() && dude.getHP() < leastHP) {
                leastHP = dude.getHP();
                weakestDudeIndex = i;
            }
        }
        Battle.CurrentDefender = Battle.DudesInOrder.get(weakestDudeIndex);
    }

    /// Iterates through the hero player's characters and picks the one who is alive and in front.
    public void SelectFirstDude() {
        for(int i = 3; i >= 0; i--) {
            if(Battle.DudesInOrder.get(i).isAlive()) {
                Battle.CurrentDefender = Battle.DudesInOrder.get(i);
                return;
            }
        }
    }
    
    public int maxprob(double[] probs) {
    	int max=0;
    	double maxval=0;
    	
    	for (int i=0; i < probs.length; i++) {
    		System.out.println(probs[i]);
    	}
    	
    	for (int i = 0; i < probs.length; i++) {
    		if ((i == probs.length-1 || i == probs.length-2) && probs[i]==0) {
    			probs[i] = 0.00001;
    		}
    		if (probs[i] > maxval) {
    			max = i;
    			maxval = probs[i];
    		}
    	}
    	
    	return max;
    }
    
    public Instances getDataSet() {
    	return mlw.myInstances;
    }
}
