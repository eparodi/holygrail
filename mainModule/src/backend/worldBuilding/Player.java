package backend.worldBuilding;

import backend.exceptions.CantPayException;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.Objects;

public class Player {
    static Integer nextId = 1;

    private String name;
    private Integer id;
    private Integer gold = 100;

    public Player(String name) {
        this.name = name;
        this.id = getNextId();
    }

    private Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public String toString(){
        return name;
    }

    public Integer getGold() {
        return gold;
    }

    public boolean canPay(Integer amount){
        return amount <= gold;
    }

    public void addGold(Integer amount){
        gold+=amount;
    }
    public void pay(Integer amount){
        if (!canPay(amount)){
            throw new CantPayException("Amount to pay is bigger than actual gold.");
        }
        gold -= amount;
    }

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

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
