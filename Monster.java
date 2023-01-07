public class Monster extends Character{
    public int expToEarn;

    public Monster(){
        this.maxHP = rollDice(8, 5); 
        this.maxStamina = rollDice(8, 5);
        this.maxMana = rollDice(10, 6);

        this.HP = maxHP;
        this.Stamina = maxStamina;
        this.Mana = maxMana;

        this.expToEarn = rollDice(3, 1);

        this.inventory = null;
        this.allies = null;

        this.isMonster = true;
    }
}
