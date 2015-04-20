import com.sun.org.apache.bcel.internal.generic.Select;

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

    public FoeAI(BattleField Battle) {
        this.Battle = Battle;
        AIVersion = "Dumb";
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
            Dude frontDude = Battle.Characters.get(3);
            if(frontDude.getName().equals("Warrior")) {
                Battle.selectedAttack = Battle.CurrentAttacker.getAttack(3);
                Battle.CurrentDefender = Battle.Characters.get(3);
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
        } else if(!Battle.Characters.get(2).isAlive() && !Battle.DudesInOrder.get(3).isAlive()) {
            Battle.selectedAttack = new Attack("Shake Foundation");
            if (!Battle.Characters.get(1).isAlive()) {
                Battle.CurrentDefender = Battle.DudesInOrder.get(0);
            } else {
                Battle.CurrentDefender = Battle.DudesInOrder.get(1);
            }
        } else if(Battle.Characters.get(3).isAlive()) {
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
            if(Battle.EnemyPriest.getPosition() == 4 && Battle.Characters.get(3).isAlive()) {
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
}
