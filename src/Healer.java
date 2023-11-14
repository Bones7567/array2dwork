public class Healer extends LivingThing{
    private int healValue;

    public int getHealValue() {
        return healValue;
    }

    public Healer(String name, int health, String pieceColor, int healValue){
        super(name, health, pieceColor);
        this.healValue = healValue;
    }

    public void heal(LivingThing livingthing){
        setHealth(getHealth() + healValue);
    }
}
