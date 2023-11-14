import java.util.ArrayList;

public class Explorer extends LivingThing{
    private ArrayList<Treasure> treasures;
    public Explorer(String name, int health, String pieceColor) {
        super(name, health, pieceColor);
        setTreasures(new ArrayList<Treasure>());
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }
    
    public void addTreasureValue(Treasure treasure){
        treasures.add(treasure);
    }
    
    public int getTreasureValue(){
        int value = 0;
        for (Treasure treasure: treasures ) {
            value += treasure.getValue();
        }
        return value;
    }
}
