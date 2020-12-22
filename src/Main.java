import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameMap map = new GameMap();
        GameLevel level = new GameLevel(map);
        GameRendered renderer = new GameRendered();
        GameRules gameRules = new GameRules(level);
        ConsoleInput consoleInput = new ConsoleInput();

        level.addNewValue();

        while (!gameRules.isGameOver()) {
            renderer.renderMap(level);
            renderer.printUI(gameRules);

            int key = consoleInput.readConsoleInput();
            gameRules.processUserInput(key);

            if (gameRules.reached2048() && gameRules.isCheckForWinOn()) {
                renderer.renderMap(level);
                renderer.youWonMessage();
                gameRules.continuePlayingAfterWin(consoleInput.readConsoleInput());
            }

            if (gameRules.noMoreMovesLeft()) {
                renderer.renderMap(level);
                renderer.youLostMessage();
                gameRules.setGameOver(true);
            }
        }
    }
}
