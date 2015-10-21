package backend.items;

import java.util.Random;

public class ItemFactory {

    private final static int MAX_ITEM_TYPES = ItemType.values().length;
    private final static ItemType[] ITEM_TYPES = ItemType.values();
    private final static int NUMBER_OF_CHANCES = 100;
    private final static int LEVEL1_CHANCES = 50;
    private final static int LEVEL2_CHANCES = 75;
    private final static int LEVEL3_CHANCES = 90;
    private final static int LEVEL4_CHANCES = 97;

    /**
     * Creates a new item with the given stats.
     * @param name Name of the item.
     * @param type Type of the item.
     * @param maxAPBonus Action points added to the maximum.
     * @param maxHealthBonus Health added to the maximum.
     * @param slashBonus Slash Bonus added.
     * @param piercingBonus Piercing Bonus added.
     * @param bluntBonus Blunt Bonus added.
     * @return An Item with the given stats.
     */
    public static Item buildItem(String name, ItemType type ,Integer maxAPBonus, Integer maxHealthBonus,
                                 Integer slashBonus, Integer piercingBonus, Integer bluntBonus){
        if (name == null){
            return null;
        }

        Item item = new Item(name,type,maxAPBonus,maxHealthBonus,slashBonus,piercingBonus,bluntBonus);
        return item;
    }

    /**
     * Creates a random item. The more powerful the item is, there are less chances that it will appear.
     * @return Random Item.
     */
    public static Item buildRandomItem(){
        //TODO Get a random name and use it.
        Random random = new Random();
        ItemType type;
        type = ITEM_TYPES[random.nextInt(MAX_ITEM_TYPES)];

        // Now we generate a random level for the item, it will be used only to generate the level. The level is the
        // addition of all the stats points.
        Integer levelChance = random.nextInt(NUMBER_OF_CHANCES) + 1;
        Integer level ;

        if ( levelChance < LEVEL1_CHANCES ){
            level = 1;
        }
        else if ( levelChance < LEVEL2_CHANCES ){
            level = 2;
        }
        else if ( levelChance < LEVEL3_CHANCES ){
            level = 3;
        }
        else if ( levelChance < LEVEL4_CHANCES ){
            level = 4;
        }
        else{
            level = 5;
        }

        Integer maxAPBonus, maxHealthBonus, slashBonus, piercingBonus, bluntBonus;
        maxAPBonus = maxHealthBonus = slashBonus = piercingBonus = bluntBonus = 0 ;

        // Giving away the level to the stats.
        switch ( type ){
            case EXTRA: maxAPBonus = random.nextInt( level + 1 );
                level -= maxAPBonus;
                maxHealthBonus = level;
                break;
            case RUNE: slashBonus = random.nextInt( level + 1 );
                level -= slashBonus;
                piercingBonus = random.nextInt( level + 1 );
                level -= piercingBonus;
                bluntBonus = level;
        }

        String name = getName(maxAPBonus,maxHealthBonus,slashBonus,piercingBonus,bluntBonus);

        return buildItem( name , type, maxAPBonus, maxHealthBonus, slashBonus, piercingBonus, bluntBonus);
    }

    /**
     * Generates a names from the stats given.
     * @param maxAPBonus Action Point Bonus of the item.
     * @param maxHealthBonus Health Bonus of the item.
     * @param slashBonus Slash Bonus of the item.
     * @param piercingBonus Piercing Bonus of the item.
     * @param bluntBonus Blunt Bonus of the item.
     * @return String with the name of the item.
     */
    private static String getName( Integer maxAPBonus, Integer maxHealthBonus, Integer slashBonus,
                                  Integer piercingBonus, Integer bluntBonus){
        String name = "";
        if ( maxAPBonus != 0 ){
            if ( maxAPBonus == 1 ){
                name += "";
            }else if ( maxAPBonus == 2 ){
                name += "";
            }else if ( maxAPBonus == 3 ){
                name += "";
            }else if ( maxAPBonus == 4 ){
                name += "";
            }else if ( maxAPBonus == 5 ){
                name += "";
            }
        }

        if ( maxHealthBonus != 0 ) {
            if (maxHealthBonus == 1) {
                name += "";
            } else if (maxHealthBonus == 2) {
                name += "";
            } else if (maxHealthBonus == 3) {
                name += "";
            } else if (maxHealthBonus == 4) {
                name += "";
            } else if (maxHealthBonus == 5) {
                name += "";
            }
        }

        if ( slashBonus != 0 ) {
            if (slashBonus == 1) {
                name += "";
            } else if (slashBonus == 2) {
                name += "";
            } else if (slashBonus == 3) {
                name += "";
            } else if (slashBonus == 4) {
                name += "";
            } else if (slashBonus == 5) {
                name += "";
            }
        }

        if ( piercingBonus != 0 ) {
            if (piercingBonus == 1) {
                name += "";
            } else if (piercingBonus == 2) {
                name += "";
            } else if (piercingBonus == 3) {
                name += "";
            } else if (piercingBonus == 4) {
                name += "";
            } else if (piercingBonus == 5) {
                name += "";
            }
        }

        if ( bluntBonus != 0 ) {
            if (bluntBonus == 1) {
                name += "";
            } else if (bluntBonus == 2) {
                name += "";
            } else if (bluntBonus == 3) {
                name += "";
            } else if (bluntBonus == 4) {
                name += "";
            } else if (bluntBonus == 5) {
                name += "";
            }
        }

        //TODO create names.
        return name;
    }
}



