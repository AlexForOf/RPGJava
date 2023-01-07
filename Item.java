public class Item{
    public int use;
    public int value;
    public String name = "";

    public Item(){
        StringBuilder forName = new StringBuilder(name);

        this.use = (int) (Math.random() * (4 - 1) + 1);
        this.value = (int) (Math.random() * (10 - 1) + 1);
        switch(use){
            case 1:
                forName.append("Healing potion");
                break;
            case 2:
                forName.append("Stamina potion");
                break;
            case 3:
                forName.append("Mana potion");
                break;
        }


        if(value < 4){
            forName.insert(0, "Small ");
        }
        if(value >= 4 && value <= 7){
            forName.insert(0, "Medium ");
        }
        if(value > 7){
            forName.insert(0, "Large ");
        }
        name = forName.toString();
    }
}