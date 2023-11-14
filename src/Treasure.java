public class Treasure {
    private int value;
    private String description;

    private String color;

    public Treasure(int value, String description){
        this.value = value;
        this.description = description;
        setColor(ConsoleColors.YELLOW);
    }

    public Treasure(){
        value = 5;
        description = "gold";
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getConsoleStr(){
        return ConsoleColors.YELLOW + String.valueOf(description.charAt(0)).toUpperCase() + ConsoleColors.RESET + " ";
    }
}
