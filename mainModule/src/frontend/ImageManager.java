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
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Created by Familia Balaguer on 30/10/2015.
 */
public class ImageManager {
    HashMap<Class, String> images;

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

    public String getTerrainImage(Terrain terrain) {
        return images.get(terrain.getClass());
    }

    public String getUnitImage(Unit unit) {
        return images.get(unit.getClass());
    }

    public String getBuildingImage(Building building) {
        return images.get(building.getClass());
    }

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
        } else if (lifeRatio >= 0.2d) {
            return "file:mainModule/resources/life20.png";
        } else {
            return "file:mainModule/resources/lifeMin.png";
        }
    }
}
