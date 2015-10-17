package backend.items;

public class Item {
    String name="Default";


    public Item(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public boolean isHolyGrail(){
        return name.equals("Holy Grail");
    }
}
