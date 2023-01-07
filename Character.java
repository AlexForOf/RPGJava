import java.util.ArrayList;
import java.util.Scanner;

public class Character {
    public Scanner scan = new Scanner(System.in);

    public int maxHP;
    public int maxStamina;
    public int maxMana;
    public int EXP = 0;

    public int HP;
    public int Stamina;
    public int Mana;

    public int team = 0;

    public ArrayList<Item> inventory = new ArrayList<Item>();
    public ArrayList<Character> allies = new ArrayList<Character>();

    public Character engagedOn = null;
    public Character characterHelps = null;

    public boolean isHero;
    public boolean isMonster;

    public int expToEarn;

    

    public Character(int chosenHP, int chosenStamina, int chosenMana, boolean isHero){
        this.isHero = isHero;

        this.maxHP = chosenHP;
        this.maxStamina = chosenStamina;
        this.maxMana = chosenMana;

        this.HP = maxHP;
        this.Stamina = maxStamina;
        this.Mana = maxMana;
            
        this.team = rollDice(3, 1);
        
    }

    public Character(){
        this.isHero = false;
        this.isMonster = false;

        this.maxHP = rollDice(6, 3);
        this.maxStamina = rollDice(6, 2);
        this.maxMana = rollDice(6, 1);

        this.HP = maxHP;
        this.Stamina = maxStamina;
        this.Mana = maxMana;

        this.team = rollDice(3, 1);
    }

    void attackEnity(int attackType, boolean playerAttack){
            if(Stamina < 1){
                System.out.println("No more stamina");
            }else{
            Stamina -= 1;
            int damage;
            if(attackType == 1){
                System.out.println("Physical attack!");
                if(rollDice(2, 1) == 1){
                    damage = rollDice(5, 2);
                    engagedOn.HP -= damage; 
                    System.out.println("Attack has hit for " + damage + " hitpoints");
                }else{
                    System.out.println("Miss!");
                }
            }else{
                System.out.println("Magic attack!");
                Stamina -= 1;
                Mana -= 1;
                if(rollDice(2, 1) == 1){
                    damage = rollDice(8, 2);
                    engagedOn.HP -= damage;
                    System.out.println("Attack has hit for " + damage + " hitpoints");
                }else{
                    System.out.println("Miss!");
                }
            }
        }
    }

    void attackEnity(int attackType){
        Stamina -= 1;
        int damage;
        int missOrNot = rollDice(4, 1);
        if(attackType == 1){
            System.out.println("Physical attack!");
            if(missOrNot < 2){
                damage = rollDice(2, 1);
                engagedOn.HP -= damage; 
                System.out.println("Attack has hit for " + damage + " hitpoints");
            }else{
                System.out.println("Miss!");
            }
        }else{
            System.out.println("Magic attack!");
            Stamina -= 1;
            Mana -= 1;
            if(missOrNot < 2){
                damage = rollDice(4, 2);
                engagedOn.HP -= damage;
                System.out.println("Attack has hit for " + damage + " hitpoints");
            }else{
                System.out.println("Miss!");
            }
        }
    }

    void skipTurn(){
        Stamina += 2;
        if(Stamina > maxStamina){
            Stamina = maxStamina;
        }
        HP++;
        if(HP > maxHP){
            HP = maxHP;
        }
        System.out.println("Turn skipped, stats have slightly restored");
    }

    boolean isAlly(){
        System.out.println(engagedOn.team + " engaged");
        if(engagedOn.team == this.team) return true;
        else return false;
    }

    Character callAlly(){
        if(allies.size() == 0){
            System.out.println("You don't have allies");
        }else{
            int respond;
            System.out.println("Choose ally to call for help(to leave this screen, print not valid number): ");
            for (int i = 0; i < allies.size(); i++) {
                System.out.println((i + 1) + ": " + allies.get(i));
            }
            respond = scan.nextInt();
            if(respond > 0 && respond < allies.size() + 1){
                return allies.get(respond - 1);
            }          
        }
        return null;
    }

    boolean nextEncounter(){
        boolean next = false;

        if(HP < 1 && isHero) next = false;
        if(engagedOn.HP < 1) next = true;
        // if(checkedAlly) next = true;
        return next;
    }

    void seeStats(){
        System.out.println("Your stats are: " +
        "\nHP: " + HP + "/" + maxHP +
        "\nStamina: " + Stamina + "/" + maxStamina +
        "\nMana: " + Mana + "/" + maxMana +
        "\nEXP to next level: " + EXP + "/5");
    }

    void inventory(){
        seeStats();

        if(inventory.size() > 0){
            System.out.println("These are your items(type number of item to use it): ");
        
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println(i+1 + ". " + inventory.get(i).name + " with an effect: " + inventory.get(i).value);
            }
    
            int respond = scan.nextInt();
    
            if(respond <= inventory.size() && respond >= 0){
                useItem(inventory.get(respond - 1));
            }else{
                System.out.println("No item with such a number!");
            }
        }else{
            System.out.println("Your inventory is empty!");
        }
        

    }

    void useItem(Item item){
        System.out.println("You've used " + item.name);
        switch(item.use){
            case 1:
                HP += item.value;
                if(HP > maxHP) HP = maxHP;
                break;
            case 2:
                Stamina += item.value;
                if(Stamina > maxStamina) Stamina = maxStamina;
                break;
            case 3:
                Mana += item.value;
                if(Mana > maxMana) Mana = maxMana;
                break;
        }
        inventory.remove(item);
    }

    void levelUp(){
        int statsToIncrease = 5;
        while(statsToIncrease > 0){
            System.out.println("You have " + statsToIncrease + " stats to increase, choose prefered stat: " + 
            "\n1.HP" + 
            "\n2.Stamina" + 
            "\n3.Mana");
            int respond = scan.nextInt();
            if(respond == 1){
                maxHP++;
                HP++;
            }
            if(respond == 2){
                maxStamina++;
                Stamina++;
            }
            if(respond == 3){
                maxMana++;
                Mana++;
            }
        }
    }
    
    int rollDice(int max, int min) { return (int) (Math.random() * (max + 1 - min) + min); }
}
