/**
 * Created by Drew on 4/19/2015.
 * There are three AI Versions:
 *  Dumb: Select the strongest attack and target whichever hero is in front
 *  Smart: Use some battle logic to determine a smart attack (if statements)
 *  Smartest: Use a decision tree to choose the best attack
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

    public void setAIVersion(String AIVersion) {
        this.AIVersion = AIVersion;
    }

    public void SelectFoesAttack() {
        switch("AIVersion") {
            case "Dumb":
                SelectDumbMove();
                break;
            case "Smart":
                SelectSmartMove();
                break;
            case "Smartest":
                SelectSmartestMove();
                break;
            default:
                System.out.println("You are dumb. Set AIVersion to either \"Dumb\", \"Smart\", or \"Smartest\". Idiot.");
                break;
        }
    }

    public void SelectSmartestMove() {

    }

    public void SelectSmartMove() {

    }

    public void SelectDumbMove() {
        SelectStrongestAttack();
        SelectFirstDude();
    }

    /// Select the attack which does the most damage, and is valid
    /// If none are valid, the character will move forward (i.e. Warrior after Shake Foundation)
    public void SelectStrongestAttack() {
        int strongestAttackIndex = -1;
        int strongestAttack = 0;
        for(int i = 0; i < 4; i++) {
            Attack attack = Battle.CurrentAttacker.getAttack(i);
            if(attack.getDamage() > strongestAttack) {
                if(attack.isValidAttackPosition(Battle.CurrentAttacker.getPosition())) {
                    strongestAttack = attack.getDamage();
                    strongestAttackIndex = i;
                }
            }
        }
        if(strongestAttackIndex > -1) {
            Battle.selectedAttack = Battle.CurrentAttacker.getAttack(strongestAttackIndex);
        }
        else {
            Battle.selectedAttack = Battle.CurrentAttacker.MoveForward;
        }
    }

    /// Iterates through the hero player's characters and picks the one who is alive and in front.
    public void SelectFirstDude() {
        for(int i = 3; i >= 0; i--) {
            if(Battle.Characters.get(i).isAlive()) {
                Battle.CurrentDefender = Battle.Characters.get(i);
                return;
            }
        }
    }
}
