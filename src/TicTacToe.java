import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game(sc);
        game.newGame();
        sc.close();
    }
}
