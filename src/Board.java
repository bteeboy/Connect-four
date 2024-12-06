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
        this.board = new char[3][3];
    }

    public void initBoard() {
        // fills up the board with blanks
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                this.board[r][c] = ' ';
    }
    //method to display the board
     public void displayBoard() {
        System.out.println("1  " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("  ---+---+---");
        System.out.println("2  " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("  ---+---+---");
        System.out.println("3  " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
        System.out.println("   1   2   3 ");
    }

      //method make a move by editing the board double array
      public void Move(char XorO, int r, int c){
        r = r-1;
        c = c-1;
        board[r][c] = XorO;
    }

    //method to check if move has already been played
    public boolean legalMove(int r, int c){
        char row = (char) (r-1);
        char column = (char)(c-1);
        return board[row][column] == ' ';
    }

    //method to return true check if game is over
    public char winnerCheck(){
        //looping over rows and columns to look for a win
        for (int i = 0; i <= 2; i++) {
            //horizontal win check
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                return board[i][0];
            }
            //vertical check
            else if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                return board[0][i];
            }
        }
        //checking for diagonal win from top left
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            return board[0][0];
        }
        //checking for diagonal win from bottom left
        else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            return board[0][2];
        }
        //if no winner return blank
        return ' ';
    }
}
