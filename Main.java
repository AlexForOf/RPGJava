import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Character[] encounters = new Character[(int) (Math.random() * (13 - 7) + 7)];
    static Character hero; 
    static int currentEncounter = 0;

    public static void main(String[] args) {
        createHero();
        initializeEncounters();

        movement();
        gameOver();
    }

    static void gameOver(){
        if(hero.HP <= 0){
            System.out.println("Game over, you failed your journey");
        }else{
            System.out.println("Congratulations, you've successfully completed your journey!");
        }
    }

    static void movement(){
        while(currentEncounter < encounters.length && hero.HP > 0){
            System.out.println("You are moving forward to " + currentEncounter);
            System.out.println("Encounters number = " + encounters.length);

            hero.engagedOn = encounters[currentEncounter];
            encounters[currentEncounter].engagedOn = hero;

            boolean finishedEncounter = false;



            while(!finishedEncounter && hero.HP > 0 && hero.engagedOn.HP > 0){
                System.out.println("You see " + (hero.engagedOn.isMonster ? "Monster" : "Character"));

                System.out.println("What do you want to do?" + 
                "\n1. Attack 'em" + 
                "\n2. Interact with 'em" +
                "\n3. Use item(opens inventory)" +
                "\n4. See your stats");

                int respond = scan.nextInt();

                if(respond == 1){
                    combat();
                }
                if(respond == 2){
                    System.out.println("Your team: " + hero.engagedOn.isMonster);
                    if(hero.engagedOn.isMonster) {
                        System.out.println("This is monster, so it attacked you");
                        combat();
                    }
                    else{
                        if(hero.isAlly()){
                            System.out.println("This is your ally, he will join you in your journey");
                            hero.allies.add(hero.engagedOn);
                            finishedEncounter = true;
                        }else{
                            System.out.println("This is non-friendly character, so he attacked you");
                            combat();
                        }
                    }
                    
                }
                if(respond == 3){
                    hero.inventory();
                }
                if(respond == 4){
                    hero.seeStats();
                }
                if(respond == 10){
                    finishedEncounter = true;
                    currentEncounter++;
                }
                }
                currentEncounter++;
            }  
        }
    
    static void combat(){
        int turn = 0;
        System.out.println("You are in the combat with: " + (hero.engagedOn.isMonster ? "Monster" : "Character"));
        System.out.println("Exp to earn: " + hero.engagedOn.expToEarn);
        while(hero.engagedOn.HP > 0 && hero.HP > 0){

            if(turn == 0){
                System.out.println("Enemy's hp is: " + hero.engagedOn.HP);
                System.out.println("Your turn!");
                System.out.println("What do you want to do?" +
                "\n1. Attack with a sword" +
                "\n2. Attack with magic" + 
                "\n3. See inventory and stats" +
                "\n4. Call ally for help" +
                "\n5. Skip turn(restore some Stamina and HP)");
                
                int response = 0;

                while(response > 5 || response < 1){
                    response = scan.nextInt();

                    if(response == 1 || response == 2){
                        hero.attackEnity(response, true);
                    }
                    if(response == 3){
                        hero.inventory();
                    }
                    if(response == 4){
                        hero.callAlly();
                    }
                    if(response == 5){
                        hero.skipTurn();
                    }
                }

                if(hero.engagedOn.HP < 1) break;
                if(hero.characterHelps != null) turn = 1;
                else turn = 2;
            }
            if(turn == 1){
                System.out.println("Your friend's turn!");

                if(hero.characterHelps.Stamina < 2 || hero.characterHelps.Mana < 1){
                    hero.characterHelps.attackEnity(1);
                }else if(hero.characterHelps.Stamina < 1){
                    hero.characterHelps.skipTurn();
                }else{
                    hero.characterHelps.attackEnity((int) (Math.random() * (3 - 1) + 1));
                }

                turn = 2;
            }
            if(turn == 2){
                System.out.println("Enemy's turn!");
                System.out.println("Enemy stats: " + hero.engagedOn.Stamina + " Stamina " + hero.engagedOn.Mana + " Mana ");

                if(hero.engagedOn.Stamina < 2){
                    hero.engagedOn.attackEnity(1);
                }else if(hero.engagedOn.Mana < 1 && hero.engagedOn.Stamina > 0){
                    hero.engagedOn.attackEnity(1);
                }
                else if(hero.engagedOn.Stamina < 1){
                    hero.engagedOn.skipTurn();
                }else{
                    hero.engagedOn.attackEnity((int) (Math.random() * (3 - 1) + 1));
                }
                turn = 0;
            }
        }
        if(hero.HP > 0){
            System.out.println("You've successfully defeated " + (hero.engagedOn.isMonster ? "Monster" : "Character"));
            hero.Stamina = hero.maxStamina;
            hero.Mana = hero.maxMana;
            if(hero.engagedOn.inventory != null){
                for (int i = 0; i < hero.engagedOn.inventory.size(); i++) {
                    System.out.println("You've taken the " + hero.engagedOn.inventory.get(i).name + " from defeated enemy's inventory");
                    hero.inventory.add(hero.engagedOn.inventory.get(i));
                }
            }
            if(hero.engagedOn.isMonster){
                hero.EXP += hero.engagedOn.expToEarn;
                if(hero.EXP >= 5){
                    hero.EXP -= 5;
                    hero.levelUp();
                }
            }
        } 
    }

    // static void inventory(){
        

    //     System.out.println("These are your items(type number of item to use it): ");
        
    //     for (int i = 0; i < hero.inventory.size(); i++) {
    //         System.out.println(i+1 + ". " + hero.inventory.get(i).name + " with an effect: " + hero.inventory.get(i).value);
    //     }

    //     int respond = scan.nextInt();

    //     if(respond < hero.inventory.size() && respond >= 0){
    //         hero.useItem(hero.inventory.get(respond - 1));
    //     }else{
    //         System.out.println("No item with such a number!");
    //     }

    // }

    static void createHero(){
        int remainingPoints = 1;
        int HP = 200;
        int Stamina = 200;
        int Mana = 200;


        while(remainingPoints != 0){
            
            System.out.println("You have " + remainingPoints + " points to use!");
            System.out.println("What stat do you want to increase?" +
            "\n1: HP" + 
            "\n2: Stamina" +
            "\n3: Mana");

            int respond = scan.nextInt();

            switch(respond){
                case 1:
                    HP++;
                    remainingPoints--;
                    break;
                case 2:
                    Stamina++;
                    remainingPoints--;
                    break;
                case 3:
                    Mana++;
                    remainingPoints--;
                    break;
            }
        }

        hero = new Character(HP, Stamina, Mana, true);

        hero.inventory.add(new Item());

        System.out.println(hero.HP + " HP " + hero.Stamina + " Stamina " + hero.Mana + " Mana");
        System.out.println("Item name: " + hero.inventory.get(0).name);
    }

    static Character createCharacter(){
        Character newChar = new Character();
        newChar.inventory.add(new Item());

        return newChar;
    }

    static void initializeEncounters(){
        
        for (int i = 0; i < encounters.length; i++) {
            int random = (int) Math.round(Math.random());
            
            if(random == 0){
                encounters[i] = createCharacter();
            }else{
                encounters[i] = new Monster();
            }
            
        }
    }
}
