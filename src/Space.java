public class Space {
    private LivingThing occupant;

    private Treasure cache;

    public Space(){
        setOccupant(null);
        setCache(null);
    }

    public Space(LivingThing occupant){
        this.setOccupant(occupant);
    }

    public Space(Treasure cache){
        this.cache = cache;
    }

    public LivingThing getOccupant() {
        return occupant;
    }

    public void setOccupant(LivingThing occupant) {
        this.occupant = occupant;
    }

    public String getConsoleStr(){
        if(occupant == null){
            return "- ";
        } else {
            return occupant.getConsoleStr();
        }
    }

    public Treasure getCache() {
        return cache;
    }

    public void setCache(Treasure cache) {
        this.cache = cache;
    }

    public String getConsoleStr(Boolean reveal) {
        return occupant == null  ? (reveal ? (getCache() == null ? "- " : getCache().getConsoleStr()) : "- ") : getOccupant().getConsoleStr();
    }
}
