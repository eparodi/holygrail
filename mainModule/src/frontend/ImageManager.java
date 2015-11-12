package frontend;

import backend.building.Building;
import backend.building.Castle;
import backend.building.Mine;
import backend.exceptions.NoSuchPlayerException;
import backend.terrain.*;
import backend.units.Archer;
import backend.units.Lancer;
import backend.units.Rider;
import backend.units.Unit;

import java.util.HashMap;

/**
 * Represents a class that returns the Image path for the object to be drawn.
 */
public class ImageManager {
    private HashMap<Class, String> images;

    /**
     * Constructs a ImageManager, with a HashMap which contains the class of each Object needed to be drawn in the game,
     * and its Image Path.
     */
    public ImageManager() {
        this.images = new HashMap<Class, String>();
        images.put(Archer.class, "file:mainModule/resources/archer.png");
        images.put(Lancer.class, "file:mainModule/resources/lancer.png");
        images.put(Rider.class, "file:mainModule/resources/rider.png");
        images.put(Castle.class, "file:mainModule/resources/Castle.png");
        images.put(Mine.class, "file:mainModule/resources/mine.png");
        images.put(Grass.class, "file:mainModule/resources/cellGrass.png");
        images.put(Forest.class, "file:mainModule/resources/cellForest.png");
        images.put(Hill.class, "file:mainModule/resources/cellHill.png");
        images.put(Mountain.class, "file:mainModule/resources/cellMountain.png");
        images.put(Water.class, "file:mainModule/resources/cellWater.png");
    }

    /**
     * Returns the Image path of the Terrain, depending on the type.
     *
     * @param terrain Terrain which image path will be returned.
     * @return Image path of the Terrain.
     */
    public String getTerrainImage(Terrain terrain) {
        return images.get(terrain.getClass());
    }

    /**
     * Returns the Image path of the Unit, depending on the Class (Archer/Lancer/Rider).
     * @param unit Unit which image path will be returned.
     * @return Image path of the Unit.
     */
    public String getUnitImage(Unit unit) {
        return images.get(unit.getClass());
    }

    /**
     * Returns the Image path of the Building, depending on its type.
     * @param building Building which image path will be returned.
     * @return Image path of the Building.
     */
    public String getBuildingImage(Building building) {
        return images.get(building.getClass());
    }

    /**
     * Returns the Flag image path of the Building Owner.
     * @param building Building to add the Flag image to.
     * @return Flag image path depending on the Player who owns the building.
     */
    public String getFlagImage(Building building) {
        if (building.getOwner() != null) {
            if (building.getOwner().getId() != null) {
                switch (building.getOwner().getId()) {
                    case 1:
                        return "file:mainModule/resources/blueFlag.png";
                    case 2:
                        return "file:mainModule/resources/redFlag.png";
                    default:
                        throw new NoSuchPlayerException("No player with id " + building.getOwner().getId());
                }
            }

        }

        return "file:mainModule/resources/empty.png"; //empty image
    }

    /**
     * Returns the Marker image path of the Owner of the Unit.
     * @param unit Unit to add the Maker image to.
     * @return Marker image path depending on the Player who owns the unit.
     */
    public String getMarkerImage(Unit unit) {
        if (unit.getOwner().getId() != null) {
            switch (unit.getOwner().getId()) {
                case 1:
                    return "file:mainModule/resources/blueMarker.png";
                case 2:
                    return "file:mainModule/resources/redMarker.png";
                default:
                    throw new NoSuchPlayerException("No player with id " + unit.getOwner().getId());
            }
        }
        throw new NoSuchPlayerException("Unit hasn't a player assigned");
    }

    /**
     * Returns the corresponding Health Bar Image depending on the Health of the Unit.
     *
     * @param unit Unit which health want to be drawn.
     * @return Image path of the corresponding life bar.
     */
    public String getLifeImage(Unit unit) {
        Double lifeRatio = (double) unit.getHealth() / unit.getMaxHealth();
        if (lifeRatio == 1d) {
            return "file:mainModule/resources/life100.png";
        } else if (lifeRatio >= 0.8d) {
            return "file:mainModule/resources/life80.png";
        } else if (lifeRatio >= 0.6d) {
            return "file:mainModule/resources/life60.png";
        } else if (lifeRatio >= 0.4d) {
            return "file:mainModule/resources/life40.png";
        } else if (lifeRatio >= 0.1d) {
            return "file:mainModule/resources/life20.png";
        } else {
            return "file:mainModule/resources/lifeMin.png";
        }
    }

    /**
     * Returns the corresponding AP Bar Image depending on the AP left of the Unit.
     *
     * @param unit Unit which AP bar want to be drawn.
     * @return Image path of the corresponding AP bar.
     */
    public String getAPImage(Unit unit) {
        Double APRatio = (double) unit.getActionPoints() / unit.getMaxActionPoints();
        if (APRatio == 1d) {
            return "file:mainModule/resources/mana100.png";
        } else if (APRatio >= 0.8d) {
            return "file:mainModule/resources/mana80.png";
        } else if (APRatio >= 0.6d) {
            return "file:mainModule/resources/mana60.png";
        } else if (APRatio >= 0.4d) {
            return "file:mainModule/resources/mana40.png";
        } else if (APRatio >= 0.1d) {
            return "file:mainModule/resources/mana20.png";
        } else {
            return "file:mainModule/resources/manaMin.png";
        }
    }
}
