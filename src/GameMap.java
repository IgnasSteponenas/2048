import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GameMap {
    public int[][] model = new int[][]{
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    public GameMap() {
    }

    public int width() {
        return model.length;
    }

    public int height() {
        return model[0].length;
    }

    public boolean isTileBlank(int x, int y) {
        return model[x][y] == 0;
    }

    public int getValue(int x, int y) {
        return model[x][y];
    }

    public void setValue(int x, int y, int value) {
         this.model[x][y] = value;
    }

    public ArrayList<Integer> getRow(int column){
        return Arrays.stream(model[column])
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
