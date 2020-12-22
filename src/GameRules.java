import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameRules {

    private GameLevel level;
    private boolean gameOver = false;
    private int moveCount = 0;
    private boolean checkForWin = true;

    public GameRules(GameLevel level) {
        this.level = level;
    }

    public int getMoveCount() {
        return moveCount;
    }

    private void addMove(){
        moveCount++;
    }

    public boolean isCheckForWinOn(){
        return checkForWin;
    }

    private int[][] copyMap(int[][] arrayToCopyFrom) {
        int[][] copiedArray = new int[arrayToCopyFrom.length][arrayToCopyFrom[0].length];
        for (int i = 0; i < arrayToCopyFrom.length; i++) {
            System.arraycopy(arrayToCopyFrom[i], 0, copiedArray[i], 0, arrayToCopyFrom[0].length);
        }
        return copiedArray;
    }

    private void rotateMap() {
        int [][] oldMap = copyMap(level.getGameMap().model);
        for (int i = 0; i < level.getGameMap().height(); i++) {
            for (int j = 0; j < level.getGameMap().width(); j++) {
                level.getGameMap().setValue(i,j,oldMap[j][i]);
            }
        }
    }

    private ArrayList<Integer> slideRow(ArrayList<Integer> arr) {
        Integer[] arrayWithoutZeroes = arr.stream().filter(num -> num != 0).toArray(Integer[]::new);
        Integer[] zeros = new Integer[arr.size() - arrayWithoutZeroes.length];
        Arrays.fill(zeros, 0);
        return Stream.of(arrayWithoutZeroes, zeros).flatMap(Stream::of).collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Integer> combineRow(ArrayList<Integer> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            int firstPosition = arr.get(i);
            int secondPosition = arr.get(i + 1);
            if (firstPosition == secondPosition && firstPosition != 0) {
                arr.set(i, firstPosition + secondPosition);
                arr.set(i + 1, 0);
            }
        }
        return arr;
    }

    private void resetMap(){
        rotateMap();
        rotateMap();
        rotateMap();
    }

    public void changeMap(String direction) {

        boolean reversed = false;
        boolean mapRotated = false;

        if (direction.equals("up") || direction.equals("down")) {
            rotateMap();
            mapRotated = true;
        }

        for (int column = 0; column < level.getGameMap().height(); column++) {
            ArrayList<Integer> row = level.getGameMap().getRow(column);

            if (direction.equals("right") || direction.equals("down")) {
                reversed = true;
                Collections.reverse(row);
            }

            row = combineRow(slideRow(row));
            row = slideRow(row);

            if (reversed) {
                Collections.reverse(row);
                reversed = false;
            }
            for (int i = 0; i < level.getGameMap().height(); i++) {
                level.getGameMap().setValue(column,i, row.get(i));
            }
        }

        if (mapRotated) {
            resetMap();
        }
    }

    private boolean canMove(String direction) {
        int[][] tempMAP = copyMap(level.getGameMap().model);
        changeMap(direction);
        if (!Arrays.deepEquals(tempMAP, level.getGameMap().model)) {
            level.getGameMap().model = copyMap(tempMAP);
            return true;
        }
        return false;
    }

    public boolean noMoreMovesLeft() {
        return !canMove("up") && !canMove("down") && !canMove("right") && !canMove("left");
    }

    public boolean reached2048() {
        for (int[] ints : level.getGameMap().model) {
            for (int row = 0; row <= ints.length - 1; row++) {
                if (ints[row] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public void continuePlayingAfterWin(int key){
            if (key == 'n') {
                setGameOver(true);
            } else checkForWin = false;
    }

    public void processUserInput(int key) {
        switch (key) {
            case 'a':
                if(canMove("left")) {
                    changeMap("left");
                    level.addNewValue();
                    addMove();
                }
                break;
            case 'd':
                if(canMove("right")) {
                    changeMap("right");
                    level.addNewValue();
                    addMove();
                }
                break;
            case 'w':
                if(canMove("up")) {
                    changeMap("up");
                    level.addNewValue();
                    addMove();
                }
                break;
            case 's':
                if(canMove("down")) {
                    changeMap("down");
                    level.addNewValue();
                    addMove();
                }
                break;
            case 'q':
                setGameOver(true);
                break;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
