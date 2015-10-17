package backend.items;

public class ItemFactory {

    //Constructor: name, maxHealthBonus, maxAPBonus
    public static Extra buildExtra(String name){
       if(name == null){
           return null;
       }
        if (name.equalsIgnoreCase("Potion")){
            return new Extra(name, 20, 1);
        }
        return null;
    }

    //Constructor: name, slashBonus, piercingBonus, bluntBonus
    public static Rune buildRune(String name){
        if(name == null){
            return null;
        }
        if (name.equalsIgnoreCase("Fire Rune")){
            return new Rune(name, 3, 3, 1);
        }
        return null;
    }
}



