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
    private Player bPlayer;
    private Player rPlayer;
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
    //method to hand out the black and red tokens
    public void setTokens(){
        bPlayer.setToken('B');
        rPlayer.setToken('R');
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
            this.bPlayer = firstPlayer;
            this.rPlayer = secondPlayer;
        }//if secondplayer wins they become x player and go first
        else{
            this.bPlayer = secondPlayer;
            this.rPlayer = firstPlayer;
        }//assigning tokens
        setTokens();
        //announcing the result to the users
        System.out.println(bPlayer.returnName()+" has won the coin toss and will play first with blacks");
    }

    //method to figure out who's turn it is.  
    public Player whosTurn(){
        //if it's an odd number of turns x plays
        if ((numberOfTurns % 2 ) != 0) {
            return bPlayer;
        }else{
            return rPlayer;
        }
    }
    //method to swap who has x and o so that the loser can go first the subsequent round
    public void reverseTurns() {
        if(rPlayer == firstPlayer) {
            this.bPlayer = firstPlayer;
            this.rPlayer = secondPlayer;
        }else{
            this.rPlayer = firstPlayer;
            this.bPlayer = secondPlayer;
        }
        //assigning the tokens again as they've been overwritten
        setTokens();
    }
    
    //method to get user input
    public int  getMove(Player activePlayer){
        //showing the board so users can plan thei rmove
        ttcBoard.displayBoard();
        //reminding them of the rules
        System.out.println(activePlayer.returnName()+ " it's your move. You are "+activePlayer.returnToken()+". Please enter a  column number (1-7)");
        //getting user input and reminding them what they played
        int moveData = sc.nextInt();
        System.out.println("You've entered "+moveData);
        //sending the input back to the overall move method
        return moveData;
        
    }

    //method to get an implement a move using whosTurn and getMove
    public void move(){
        //checking who's turn it is with the whosTurn method
        Player activePlayer = whosTurn();
        //getting the move from the user
        int moveData = getMove(activePlayer);
        //if it comes back valid, convert to char, send the move to the move method and add a turn to the counter
        if (ttcBoard.Move(activePlayer.returnToken(), moveData)==true) {
            numberOfTurns++;
        }       
    }

    //method with loop to initiate moves if victory/ drew conditions aren't met 
    public void gameLoop(){
        System.out.println("game loop running");
        //run Board's method to check for winner and save it
        char winnerCheckToken = ttcBoard.winnerCheck();
        //if winner check method returns blank get another move and check again
        if (winnerCheckToken == ' ') {
            this.move();
            this.gameLoop();
        //check for draw (every space used up)
        }else if (winnerCheckToken =='B'||winnerCheckToken == 'R'){
            this.win(winnerCheckToken);

        }else if(this.numberOfTurns == 43){
            this.draw(winnerCheckToken);
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
        System.out.println("Someone's won!");
        //use token to inform user of the big win
        if (winningToken =='B'){
            System.out.println(bPlayer.returnName() +" is the winner with "+winningToken+"!");
        }else{
            System.out.println(rPlayer.returnName() +" is the winner with "+winningToken+"!");
        }//show the user with the display board method
        ttcBoard.displayBoard();
        //running the play again method and sending it the winning token 
        playAgain(winningToken);
    }
    //method to restart the game with the loser going first
    public void playAgain(char winningToken){
        System.out.println("launching play again method");
        System.out.println("received the winning token: " + winningToken);
        char response = playAgainInput();
        System.out.println(response);
        if (response == 'Y') {
            playAgainTurnOrder(winningToken);

        }else{
            System.out.println("Thanks for playing!");
        }
    }
    //method to gather user Y/N input
    public char playAgainInput(){
        //asking the user if they'd like to play again
        System.out.println("Would you like to play again? (Y/N)");
        char response = sc.next().charAt(0);
        System.out.println("You've entered "+response);
        //running method to validate reponse
        if (playAgainInputValidation(response)==false) {
            playAgainInput();
        }
        return response;
    }
    //method to make sure they enter Y or N
    public boolean playAgainInputValidation(char response){
        if(response == 'Y' || response == 'N') {
            return true;
        }            
        System.out.println("Please try again");
        return false;
    }
    public void playAgainTurnOrder(char winningToken ){
        System.out.println("deciding the turn order");
        if(winningToken ==' ') {
            //if the method receives a blank token call coin toss again and start a new game
            System.out.println("First move will be randomly decided");
            this.coinToss();
            this.startNewGame();
        }if (winningToken == this.rPlayer.returnToken()) {
            //if an O token is received X goes first next so inform user and restart game as is
            System.out.println(this.rPlayer.returnName() +" won with red, so tokens remain the same. "+this.bPlayer.returnName()+" plays first with black");
            this.startNewGame();
        }else{
            //if X wins, then players need to switch spots with the reverse turns method so the loser goes first. then restart the game
            System.out.println(this.bPlayer.returnName()+" won with black, so "+this.rPlayer.returnName()+" plays first with X");
            this.reverseTurns();
            this.startNewGame();
        }
    }


}
