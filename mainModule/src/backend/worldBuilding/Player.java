package backend.worldBuilding;

import backend.building.ProductionBuilding;
import backend.exceptions.CantPayException;

import java.io.Serializable;

/**
 * Represents a player of the game.
 * A player has a name, and certain amount of gold.
 */
public class Player implements Serializable {
    private static Integer nextId = 1;

    private String name;
    private Integer id;
    private Integer gold = 20;
    private ProductionBuilding productionBuilding;

    /**
     * Creates a new player with certain name, and a unique player ID.
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.id = getNextId();
    }

    public void addProductionBuilding(ProductionBuilding productionBuilding){
        this.productionBuilding = productionBuilding;
    }

    public ProductionBuilding getProductionBuilding(){
        return productionBuilding;
    }

    /**
     * Returns the next id, to avoid having two players with same ID's.
     * @return Integer value of the next available ID.
     */
    private Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    /**
     * Returns the name of the player.
     * @return name of the player.
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * Returns the amount of gold of the player.
     * @return Integer value of the player gold amount.
     */
    public Integer getGold() {
        return gold;
    }

    /**
     * Returns true if a Player has enough gold to pay certain amount.
     *
     * @param amount gold to pay.
     * @return true if the Player has more gold than amount, false if he hasn't.
     */
    public boolean canPay(Integer amount){
        return amount <= gold;
    }

    /**
     * Adds certain amount of gold to a Player.
     *
     * @param amount gold to add.
     */
    public void addGold(Integer amount){
        gold+=amount;
    }

    public void pay(Integer amount){
        if (!canPay(amount)){
            throw new CantPayException("Amount to pay is bigger than actual gold.");
        }
        gold -= amount;
    }

    /**
     * Returns the player's ID.
     * @return player ID.
     */
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Player)obj).id.equals(id);
    }

}
