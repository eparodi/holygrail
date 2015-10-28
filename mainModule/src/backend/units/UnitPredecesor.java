package backend.units;


import backend.Entity;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;

public abstract class UnitPredecesor extends Entity{
    static Integer nextId = 0;
    private Player owner;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer id;
    private Integer health;
    private Integer maxHealth;


    public Player getOwner() {
        return owner;
    }

    public UnitPredecesor(Player inOwner, Location inLocation, Integer inmaxActionPoints,Integer inmaxHealth){
        super(inLocation);
        this.owner=inOwner;
        id=getNextId();
        this.actionPoints=inmaxActionPoints;
        this.maxActionPoints=inmaxActionPoints;
        this.health=inmaxHealth;
        this.maxHealth=inmaxHealth;
    }

    public void refillAP() {
        this.actionPoints = this.maxActionPoints;
    }

    public void spendAP(Integer actionPointsSpent) {
        actionPoints -= actionPointsSpent;
        if (actionPoints < 0) throw new IllegalStateException(this + " is using more AP than it has");
    }

    public Integer getActionPoints() {
        return actionPoints;
    }

    public Integer getMaxActionPoints() {
        return maxActionPoints;
    }

    public void setActionPoints(Integer toChangeAP){
        this.actionPoints=toChangeAP;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }


    public Integer getHealth() {
        return health < 0 ? 0 : health;
    }

    public void  reduceHealth(Integer toReduce) {
        health = getHealth() - toReduce;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns true if the unit has died.
     * @return true if the unit is dead, false if it's alive.
     */
    public boolean isDed() {
        return getHealth() == 0;
    }


}