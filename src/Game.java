import java.util.Random;
import java.util.Scanner;

public class Game {
    /*
     *     central hub 
     *  makes the players and the board, decides who's turn it is 
     * gathers moves, get's user input 
     *  
     */
    //fields
    private Scanner sc;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player xPlayer;
    private Player oPlayer;
    public Board ttcBoard;
    private int numberOfTurns;

    //methods
    //Game constructor passing in the scanner and creating players with the player constructor
    public Game (Scanner sc){
        this.sc = sc;
        this.firstPlayer = new Player(sc);
        this.secondPlayer = new Player(sc);
    }
    
    //method to make the board, set up the board, do the coin toss to figure out who goes first, and start the game
    public void newGame(){
        this.ttcBoard = new Board();
        ttcBoard.initBoard();
        this.coinToss();
        this.startGame();
    }
    //method to hand out the Xs and Os
    public void setTokens(){
        xPlayer.setToken('X');
        oPlayer.setToken('O');
    }
    //method to set the turns to 1 and start the loop that organizes turns
    public void startGame(){
        this.numberOfTurns = 1;
        this.gameLoop();
    }
    //method to refresh the game after starting a new round
    public void startNewGame(){
        this.numberOfTurns = 1;
        ttcBoard.initBoard();
        this.gameLoop();
    }
    //method to randomly select the starting player and assign them to the x and y player positions
    public void coinToss(){
        Random rand = new Random();
        int randomInt = rand.nextInt(2);
        //if first player wins they become x player and go first
          if (randomInt == 1) {
            this.xPlayer = firstPlayer;
            this.oPlayer = secondPlayer;
        }//if secondplayer wins they become x player and go first
        else{
            this.xPlayer = secondPlayer;
            this.oPlayer = firstPlayer;
        }//assigning tokens
        setTokens();
        //announcing the result to the users
        System.out.println(xPlayer.returnName()+" has won the coin toss and will play first with Xs");
    }

    //method to figure out who's turn it is.  
    public Player whosTurn(){
        //if it's an odd number of turns x plays
        if ((numberOfTurns % 2 ) != 0) {
            return xPlayer;
        }else{
            return oPlayer;
        }
    }
    //method to swap who has x and o so that the loser can go first the subsequent round
    public void reverseTurns() {
        if(oPlayer == firstPlayer) {
            this.xPlayer = firstPlayer;
            this.oPlayer = secondPlayer;
        }else{
            this.oPlayer = firstPlayer;
            this.xPlayer = secondPlayer;
        }
        //assigning the tokens again as they've been overwritten
        setTokens();
    }
    
    //method to get user input
    public String getMove(Player activePlayer){
        //showing the board so users can plan thei rmove
        ttcBoard.displayBoard();
        //reminding them of the rules
        System.out.println(activePlayer.returnName()+ " it's your move. You are "+activePlayer.returnToken()+". Please enter a row number (1-3) and a column number (1-3) with no spaces (ex:13, 21).");
        System.out.println("You may not place a token where one has already been played.");
        //getting user input and reminding them what they played
        String moveData = sc.nextLine();
        System.out.println("You've entered "+moveData);
        //sending the input back to the overall move method
        return moveData;
        
    }

    //method to validate user data takes data from the move method
    public boolean validateInput(String userInput){
        //making sure it's the right length
        if (userInput.length()!= 2) {
            return false;
        }
        //extracting the row and column into ints by converting to char and subtracting a zero
        int r = userInput.charAt(0)-'0';
        int c = userInput.charAt(1)-'0';
        //making sure the input isn't too big or small
        if(r < 1 || r > 3){
            return false;
        }else if (c < 1 || c > 3){
            return false;
        }//if the input is valid, call the board's legalMove method to make sure no one's played that move yet
        //and if no one has return true
        else return ttcBoard.legalMove(r, c);
    }

    //method to get an implement a move using whosTurn and getMove
    public void move(){
        //checking who's turn it is with the whosTurn method
        Player activePlayer = whosTurn();
        //getting the move from the user
        String moveData = getMove(activePlayer);
        //if it comes back valid, convert to char, send the move to the move method and add a turn to the counter
        if (validateInput(moveData)) {
            int r = Character.getNumericValue(moveData.charAt(0));
            int c = Character.getNumericValue(moveData.charAt(1));
            ttcBoard.Move(activePlayer.returnToken(), r, c);
            numberOfTurns++;
        }//otherwise inform the user their move isn't valid
        else{
            System.out.println("Sorry that isn't a valid move. Please try again");
        }        
    }

    //method with loop to initiate moves if victory/ drew conditions aren't met 
    public void gameLoop(){
        //run Board's method to check for winner and save it
        char winnerCheckToken = ttcBoard.winnerCheck();
        //check for draw (every space used up)
        if (this.numberOfTurns == 10){
            this.draw(winnerCheckToken);
        }//if winner check method returns blank get another move and check again
        else if (winnerCheckToken == ' ') {
            this.move();
            this.gameLoop();
        }//otherwise someone's won so run the win method
        else {
            this.win(winnerCheckToken);
        }
    }

    //method to display a draw 
    public void draw(char winnerCheckToken){
        //informing the user, showing them on the board 
        System.out.println("It's a draw.");
        ttcBoard.displayBoard();
        //running the play again method and sending it the draw token 
        playAgain(winnerCheckToken);
    }

    //method to display a draw 
    public void win(char winningToken){
        //use token to inform user of the big win
        if (winningToken =='X'){
            System.out.println(xPlayer.returnName() +" is the winner with "+winningToken+"!");
        }else{
            System.out.println(oPlayer.returnName() +" is the winner with "+winningToken+"!");
        }//show the user with the display board method
        ttcBoard.displayBoard();
        //running the play again method and sending it the winning token 
        playAgain(winningToken);
    }
    //method to restart the game with the loser going first
    public void playAgain(char winningToken){
        String response = playAgainYN();
        switch (response) {
            case "Y" -> {
                if(winningToken ==' ') {
                    //if the method receives a blank token call coin toss again and start a new game
                    System.out.println("First move will be randomly decided");
                    this.coinToss();
                    this.startNewGame();
                }if (winningToken == this.oPlayer.returnToken()) {
                    //if an O token is received X goes first next so inform user and restart game as is
                    System.out.println(this.oPlayer.returnName() +" won with Os, so tokens remain the same. "+this.xPlayer.returnName()+" plays first with X");
                    this.startNewGame();
                }else{
                    //if X wins, then players need to switch spots with the reverse turns method so the loser goes first. then restart the game
                    System.out.println(this.xPlayer.returnName()+" won with Xs, so "+this.oPlayer.returnName()+" plays first with X");
                    this.reverseTurns();
                    this.startNewGame();
                }
            }
            //if the don't want to play again the program ends
            case "N" -> System.out.println("Thanks for playing!");

        }
    }
    //method to gather user Y/N input
    public String playAgainYN(){
        //asking the user if they'd like to play again
        System.out.println("Would you like to play again? (Y/N)");
        String response = sc.nextLine();
        System.out.println("You've entered "+response);
        //running method to validate reponse
        if (YNValidation(response)==false) {
            playAgainYN();
        }
        return response;
    }
    //method to make sure they enter Y or N
    public boolean YNValidation(String response){
        if(!"Y".equals(response) && !"N".equals(response)) {
            System.out.println("Please enter 'Y' or 'N'");
            return false;
        }return true;
    }

}
