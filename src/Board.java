public class Board {
    /*
     * Hold the board
     * check if win or tie
     */
    //fields
    //the two dimentional array that holds the board
    private char[][] board;

    //creates an empty double array where both arrays are 3 items long
    public Board(){
        this.board = new char[6][7];
    }

    public void initBoard() {
        // fills up the board with blanks
        for (int r = 0; r < 6; r++)
            for (int c = 0; c < 7; c++)
                this.board[r][c] = ' ';
    }
    //method to display the board
     public void displayBoard() {
        System.out.println("1  " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]+ " | " + board[0][3]+ " | " + board[0][4]+ " | " + board[0][5]+ " | " + board[0][6]);
        System.out.println("  ---+---+---+---+---+---+---");
        System.out.println("2  " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]+ " | " + board[1][3]+ " | " + board[1][4]+ " | " + board[1][5]+ " | " + board[1][6]);
        System.out.println("  ---+---+---+---+---+---+---");
        System.out.println("3  " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]+ " | " + board[2][3]+ " | " + board[2][4]+ " | " + board[2][5]+ " | " + board[2][6]);
        System.out.println("  ---+---+---+---+---+---+---");
        System.out.println("4  " + board[3][0] + " | " + board[3][1] + " | " + board[3][2]+ " | " + board[3][3]+ " | " + board[3][4]+ " | " + board[3][5]+ " | " + board[3][6]);
        System.out.println("  ---+---+---+---+---+---+---");
        System.out.println("5  " + board[4][0] + " | " + board[4][1] + " | " + board[4][2]+ " | " + board[4][3]+ " | " + board[4][4]+ " | " + board[4][5]+ " | " + board[4][6]);
        System.out.println("  ---+---+---+---+---+---+---");
        System.out.println("6  " + board[5][0] + " | " + board[5][1] + " | " + board[5][2]+ " | " + board[5][3]+ " | " + board[5][4]+ " | " + board[5][5]+ " | " + board[5][6]);
        System.out.println("   1   2   3   4   5   6   7");
    }

      //method make a move by editing the board double array
      public void Move(char BorR, int r, int c){
        r = r-1;
        c = c-1;
        board[r][c] = BorR;
    }

    //method to check if move has already been played
    public boolean legalMove(int r, int c){
        char row = (char) (r-1);
        char column = (char)(c-1);
        return board[row][column] == ' ';
    }

    //method to return true check if game is won
    public char winnerCheck(){
        //looping over rows and columns to look for wins
        //horizontal win check
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                if(board[r][c] == board[r][c+1] && board[r][c+1] == board[r][c+2] && board[r][c+2] == board[r][c+3] && board[r][c] != ' ') {
                    return board[r][c];
                }
            }
        }
        //vertical check
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 7; c++) {
                if(board[r][c] == board[r+1][c] && board[r+1][c] == board[r+2][c] && board[r+2][c] == board[r+3][c] && board[r][c] != ' ') {
                    return board[r][c];
                }
            }
        }
        //checking for diagonal win diagonal botom left to top right
        for (int r = 3; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == board[r-1][c+1] && board[r-1][c+1] == board[r-2][c+2] && board[r-2][c+2] == board[r-3][c+3] && board[r][c] != ' ') {
                    return board[r][c];
                }
            }
        }
        //checking for diagonal win diagonal top left to bottom right
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == board[r+1][c-1] && board[r+1][c+1] == board[r+2][c+2] && board[r+2][c+2] == board[r+3][c+3] && board[r][c] != ' ') {
                    return board[r][c];
                }
            }
        }
        return ' ';
    }


    public static void main(String[] args) {
        Board board = new Board();
        board.initBoard();
        board.displayBoard();
        board.Move('B', 1, 4);
        board.Move('B', 2, 3);
        board.Move('B', 3, 2);
        // board.Move('B', 4, 1);

        board.Move('R', 1, 7);//6
        board.Move('R', 2, 6);//5
        board.Move('R', 3, 5);
        // board.Move('R', 4, 4);

        board.Move('A', 3, 7);//2,6
        board.Move('A', 4, 6);//3,5
        board.Move('A', 5, 5);//4,4
        board.Move('A', 6, 4);//5,3


        // board.Move('B', 3, 5);
        // board.Move('B', 2, 6);
        // board.Move('B', 1, 7);
        board.displayBoard();
        System.out.println(board.winnerCheck());

        // for (int i = 3; i <7; i++) {
        //     board.Move('R', i, 1);
        //     board.displayBoard();
        //     System.out.println(board.winnerCheck());
        // };
        
    }
}
