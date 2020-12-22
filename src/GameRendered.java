public class GameRendered {
    public void renderMap(GameLevel gameLevel) {
        for (int i = 0; i < gameLevel.getGameMap().width(); i++) {
            for (int j = 0; j < gameLevel.getGameMap().height(); j++) {
                System.out.print(gameLevel.getGameMap().getValue(i,j) + "\t");
            }
            System.out.println();
        }
    }

    public void youWonMessage(){
        System.out.println("You won!!! Would you like to continue? (y/n)");
    }

    public void printUI(GameRules gameRules){
        System.out.println("Move count: " + gameRules.getMoveCount());
        System.out.println("Input a direction u want to go to");
        System.out.println("(w - up, a - left, s - down, d - right, q - quit)");
    }

    public void youLostMessage(){
        System.out.println("You lost :(");
    }

}
