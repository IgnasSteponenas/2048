public class GameLevel {

    private GameMap gameMap;

    public GameLevel(GameMap map) {
        setGameMap(map);
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private int getProbability(){
        return (int) (Math.random() * (10 - 1 + 1) + 1);
    }

    private int getRandomCoordinate() {
        return (int) (Math.random() * (gameMap.height() - 1 + 1) + 0);
    }

    private void addTile(int x, int y) {
        //20% chance of tile being a 4
        if(getProbability()<=2)
            gameMap.setValue(x,y,4);
        else
            gameMap.setValue(x,y,2);
    }

    public void addNewValue() {
        while (true) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();

            if (gameMap.isTileBlank(x,y)) {
                addTile(x,y);
                break;
            }
        }
    }
}
