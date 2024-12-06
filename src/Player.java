import java.util.Scanner;

public class Player {
    /*
     * exists, holds player name and token  
     */
    //fields
    private String name;
    private char token; 
    //constructor calls get method to ask user for their name
    public Player (Scanner sc){
        this.name = getName(sc);
    }

    public String getName(Scanner sc){
        System.out.println("New Player! Welcome to the game");
        System.out.println("Please enter your name: ");
        return sc.nextLine();
    }

    public void setToken(char token){
        this.token = token;
    }

    public char returnToken(){
        return token;
    }

    public String returnName(){
        return name;
    }
}

