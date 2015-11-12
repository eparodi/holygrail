package backend.items;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents an Item Factory which creates new Items.
 */
public abstract class ItemFactory implements Serializable {
    private static Random random = new Random();

    private final static int MAX_ITEM_TYPES = ItemType.values().length;
    private final static ItemType[] ITEM_TYPES = ItemType.values();
    private final static int NUMBER_OF_CHANCES = 100;
    private final static int LEVEL1_CHANCES = 50;
    private final static int LEVEL2_CHANCES = 75;
    private final static int LEVEL3_CHANCES = 90;
    private final static int LEVEL4_CHANCES = 97;

    /**
     * Creates a new item with the given stats.
     *
     * @param name           Name of the item.
     * @param maxAPBonus     Action points added to the maximum.
     * @param maxHealthBonus Health added to the maximum.
     * @param slashBonus     Slash Bonus added.
     * @param piercingBonus  Piercing Bonus added.
     * @param bluntBonus     Blunt Bonus added.
     * @return An Item with the given stats.
     */
    public static Item buildItem(String name, Integer maxAPBonus, Integer maxHealthBonus,
                                 Integer slashBonus, Integer piercingBonus, Integer bluntBonus) {
        if (name == null) {
            return null;
        }

        return new Item(name, maxAPBonus, maxHealthBonus, slashBonus, piercingBonus, bluntBonus);
    }

    /**
     * Creates a random item. The more powerful the item is, there are less chances that it will appear.
     *
     * @return Random Item.
     */
    public static Item buildRandomItem() {

        ItemType type;
        type = ITEM_TYPES[random.nextInt(MAX_ITEM_TYPES)];

        // Now we generate a random level for the item, it will be used only to generate the level. The level is the
        // addition of all the stats points.
        Integer levelChance = random.nextInt(NUMBER_OF_CHANCES) + 1;
        Integer level;

        if (levelChance < LEVEL1_CHANCES) {
            level = 1;
        } else if (levelChance < LEVEL2_CHANCES) {
            level = 2;
        } else if (levelChance < LEVEL3_CHANCES) {
            level = 3;
        } else if (levelChance < LEVEL4_CHANCES) {
            level = 4;
        } else {
            level = 5;
        }

        Integer maxAPBonus, maxHealthBonus, slashBonus, piercingBonus, bluntBonus;
        maxAPBonus = maxHealthBonus = slashBonus = piercingBonus = bluntBonus = 0;

        // Giving away the level to the stats.
        switch (type) {
            case EXTRA:
                maxAPBonus = random.nextInt(level + 1);
                level -= maxAPBonus;
                maxHealthBonus = level;
                break;
            case RUNE:
                slashBonus = random.nextInt(level + 1);
                level -= slashBonus;
                piercingBonus = random.nextInt(level + 1);
                level -= piercingBonus;
                bluntBonus = level;
        }

        String name = getName(maxAPBonus, maxHealthBonus, slashBonus, piercingBonus, bluntBonus, type);

        return buildItem(name, maxAPBonus, maxHealthBonus, slashBonus, piercingBonus, bluntBonus);
    }

    /**
     * Generates a names from the stats given.
     *
     * @param maxAPBonus     Action Point Bonus of the item.
     * @param maxHealthBonus Health Bonus of the item.
     * @param slashBonus     Slash Bonus of the item.
     * @param piercingBonus  Piercing Bonus of the item.
     * @param bluntBonus     Blunt Bonus of the item.
     * @return String with the name of the item.
     */
    private static String getName(Integer maxAPBonus, Integer maxHealthBonus, Integer slashBonus,
                                  Integer piercingBonus, Integer bluntBonus, ItemType type) {
        String name = "";
        if (maxAPBonus != 0) {
            if (maxAPBonus == 1) {
                name += "Miser ";
            } else if (maxAPBonus == 2) {
                name += "Antiquus ";
            } else if (maxAPBonus == 3) {
                name += "Virorum ";
            } else if (maxAPBonus == 4) {
                name += "Magnus ";
            } else {
                name += "Optimus " + maxAPBonus + " ";
            }
        }

        if (maxHealthBonus != 0) {
            if (maxHealthBonus == 1) {
                name += "Calceus ";
            } else if (maxHealthBonus == 2) {
                name += "Gladius ";
            } else if (maxHealthBonus == 3) {
                name += "Liber ";
            } else if (maxHealthBonus == 4) {
                name += "Scutum ";
            } else {
                name += "Potio " + maxHealthBonus + " ";
            }
        }

        if (slashBonus != 0) {
            if (slashBonus == 1) {
                name += "Aquae ";
            } else if (slashBonus == 2) {
                name += "Focii ";
            } else if (slashBonus == 3) {
                name += "Terrae ";
            } else if (slashBonus == 4) {
                name += "Lucis ";
            } else {
                name += "Umbrae " + slashBonus + " ";
            }
        }

        if (piercingBonus != 0) {
            if (piercingBonus == 1) {
                name += "Pauperrimus ";
            } else if (piercingBonus == 2) {
                name += "Regularis ";
            } else if (piercingBonus == 3) {
                name += "Ubiqui ";
            } else if (piercingBonus == 4) {
                name += "Magnificus ";
            } else {
                name += "Omnipotens " + piercingBonus + " ";
            }
        }

        if (bluntBonus != 0) {
            if (bluntBonus == 1) {
                name += "I ";
            } else if (bluntBonus == 2) {
                name += "II ";
            } else if (bluntBonus == 3) {
                name += "III ";
            } else if (bluntBonus == 4) {
                name += "IV ";
            } else {
                name += "V " + bluntBonus + " ";
            }
        }

        if (type == ItemType.RUNE) {
            name += "Rune";
        }
        if (type == ItemType.EXTRA) {
            name += "Accessory";
        }

        return name;
    }
}



