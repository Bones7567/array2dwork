import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private ArrayList<ArrayList<Space>> board;

    private ArrayList<Treasure> remainingTreasures;

    private ArrayList<Monster> monsters;

    public static Random rand = new Random();

    int rows = 0;
    int cols = 0;
    int gazooRow = 0;
    int gazooCol = 0;

    Explorer Gazoo;

    public Board(){
        board = new ArrayList<ArrayList<Space>>();
        cols = 4;
        rows = 4;
        BuildBoard();
        createMonster();
        createTreasures();
        placeHealer(new Healer("Healer", 3, ConsoleColors.BLUE, 5));
    }

    public Board(int rows, int cols){
        board = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
        BuildBoard();
        createMonster();
        createTreasures();
        placeHealer(new Healer("Healer", 3, ConsoleColors.BLUE, 5));
    }

    public void createTreasures(){
        remainingTreasures = new ArrayList<Treasure>();
        for (int i = 0; i <= 4; i++) {
            remainingTreasures.add(new Treasure());
        }
        placeEntities(remainingTreasures);
    }

    public void createMonster(){
        monsters = new ArrayList<Monster>();
        monsters.add(new Monster("Razorclaw", 3, ConsoleColors.RED, 9));
        monsters.add(new Monster("Gloomfury", 3, ConsoleColors.RED, 8));
        monsters.add( new Monster("Fangsnarl", 3, ConsoleColors.RED, 7));
        monsters.add( new Monster("Vilespike", 3, ConsoleColors.RED, 6));
        monsters.add( new Monster("Grimscowl", 3, ConsoleColors.RED, 5));
        placeEntities(monsters);
    }

    public void placeHealer(Healer healer){
        boolean isPlaced = false;
        while(!isPlaced) {
            int randomCols = rand.nextInt(cols);
            int randomRows = rand.nextInt(rows);
            Space space = board.get(randomRows).get(randomCols);
            if (space.getOccupant() == null) {
                space.setOccupant(healer);
                isPlaced = true;
            }
        }
    }

    public void placeEntity(Object entity){
        if (entity instanceof LivingThing){
            LivingThing l = (LivingThing) entity;
            boolean placed = false;
            while (!placed){
                int randRow = ThreadLocalRandom.current().nextInt(rows);
                int randCol = ThreadLocalRandom.current().nextInt(cols);
                Space space = board.get(randRow).get(randCol);
                if (emptySpace(space)){
                    space.setOccupant(l);
                    placed = true;
                }
            }
        }else if(entity instanceof Treasure){
            Treasure t = (Treasure) entity;
            boolean placed = false;
            while (!placed){
                int randRow = ThreadLocalRandom.current().nextInt(rows);
                int randCol = ThreadLocalRandom.current().nextInt(cols);
                Space space = board.get(randRow).get(randCol);
                if (emptySpace(space)){
                    space.setCache(t);
                    placed = true;
                }
            }
        }
    }

    public void placeEntities(ArrayList<?> entityList){
        for (Object entity : entityList) {
            placeEntity(entity);
        }
    }

    public boolean emptySpace(Space space){
        return space.getCache() == null && space.getOccupant() == null;
    }

    public void BuildBoard(){
        board.clear();
        for(int i = 0; i < rows; i++){
            ArrayList<Space> row = new ArrayList();
            for(int j = 0; j < cols; j++){
                row.add(new Space());
            }
            board.add(row);
        }

        Gazoo = new Explorer("Gazoo", 20, ConsoleColors.GREEN);
        board.get(gazooRow).get(gazooCol).setOccupant(Gazoo);
    }

    public void printBoard(boolean showContents){
            for (ArrayList<Space> rows : board) {
                for (Space space: rows) {
                    System.out.print(space.getConsoleStr(showContents));
                }
                System.out.println();
        }
    }

    public boolean move(char movement){
        if (movement == 'w'){
            return moveByInput(0, -1);
        }else if(movement == 'a'){
            return moveByInput(-1, 0);
        } else if (movement == 's') {
            return moveByInput(0, 1);
        } else if (movement == 'd') {
            return moveByInput(1, 0);
        } else if (movement == 'r') {
            printBoard(true);
            return true;
        } else {
            System.out.println("Illegal Input");
            return false;
        }
    }

    public boolean validSpace(int x, int y){
        return x >= 0 && x < board.get(0).size() && y >= 0 && y < board.size();
    }

    public boolean moveByInput(int x, int y){
        int newX = gazooCol + x;
        int newY = gazooRow + y;

        if(validSpace(newX, newY)){
            Space space = board.get(gazooRow).get(gazooCol);
            LivingThing occupant = space.getOccupant();

            Space newSpace = board.get(newY).get(newX);
            LivingThing newOccupant = newSpace.getOccupant();
            Treasure newCache = newSpace.getCache();

            if(newOccupant instanceof Monster){
                Monster m = (Monster) newOccupant;
                m.hurt(Gazoo);
                System.out.println(ConsoleColors.RED + Gazoo.getName() + " was attacked by " + m.getName() + " for " + m.getDamage() + " damage. " + Gazoo.getName() + " now has " + Gazoo.getHealth() + " remaining. " + ConsoleColors.RESET);
            }
            if(newOccupant instanceof Healer){
                Healer h = (Healer) newOccupant;
                h.heal(Gazoo);
                System.out.println(ConsoleColors.BLUE + Gazoo.getName() + " was healed by " + h.getName() + " for " + h.getHealValue() + " health. " + Gazoo.getName() + " now has " + Gazoo.getHealth() + " remaining. " + ConsoleColors.RESET);
            }
            if(newCache != null){
                Gazoo.addTreasureValue(newCache);
                remainingTreasures.remove(newCache);
                System.out.println(ConsoleColors.YELLOW + Gazoo.getName() + " found " + newCache.getDescription().toLowerCase() + " for " + newCache.getValue() + ". There are " + remainingTreasures.size() + " treasure left." + ConsoleColors.RESET);
                newSpace.setCache(null);
            }

            newSpace.setOccupant(occupant);
            space.setOccupant(null);
            gazooCol = newX;
            gazooRow = newY;
            return true;
        }
        return false;
    }

    public boolean isGameOver(){
        if (checkForWin()){
            return true;
        }
        return checkForDeath();
    }

    public boolean checkForDeath(){
        if(Gazoo.isDead()){
            System.out.println(ConsoleColors.RED + "Gazoo died!!! Game over!!!" + ConsoleColors.RESET);
            return true;
        }
        return false;
    }

    public boolean checkForWin(){
        if (remainingTreasures.isEmpty()){
            System.out.println(ConsoleColors.YELLOW + "You win!");
            return true;
        }
        return false;
    }

    private int collectedTreasureCount() {
        return Gazoo.getTreasures().size();
    }
    private int remainingTreasureCount()
    {
        return remainingTreasures.size();
    }
}
