//import stuffs for things
import java.util.Random;
import java.io.Console;
import java.io.IOException;
import java.util.Arrays;

class Mastermind {
    
    //pre: The argument is an array of strings
    //post: This is the main function
    public static void main (String[] args) {
        
        //set the input function
        if (System.console() == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        
        //variable decliration
        Mastermind game = new Mastermind();
        boolean DEBUGGING = true;
        
        //create the hidden code
        game.createHiddenCode();
        
        //FOR DEBUGGING
        if(DEBUGGING) {
            game.printCode();
        }
        
        //print the rules for the game
        game.printRules();
        
        //main game loop
        while(!game.returnWinner() && game.hasTriesLeft()) {

            //reset the pegs
            game.setBlackCount();
            game.setWhiteCount();
            
            //get guess from user
            game.getUserGuess(game.promptForGuess());
            
            //get black and white pegs
            char[] tempArray = Arrays.copyOf(game.returnCode(), game.returnCode().length);
            game.getBlackCount(tempArray);
            game.getWhiteCount(tempArray);
            
            //print the pegs
            game.printPegs();
            
            //check to see if the user has won
            game.checkWinner();
            game.incrementTries();
        }
        
        //see if the user has won
        if(game.returnWinner()) {
            game.ifWin();
        } else {
            game.ifLose();
        }
    }
    
    //define variables
    private char[] userGuess = {'x','x','x','x'}; 
    private char[] code = {'X','X','X','X'};
    private int blackCount, whiteCount, triesLeft = 10;
    private boolean winner = false;
    
    //pre: None
    //post: Prints the computer generated code
    void printCode() {
        
        //set the input function
        for(int i=0; i<code.length; i++) {
            System.console().printf("%s", code[i]);
        }
        System.console().printf("%s", "\n");
    }
    
    //pre: None
    //post: Returns the computer generated code
    char[] returnCode() {
        return code;
    }
    
    //pre: None
    //post: Returns if the winner won or not
    boolean returnWinner() {
        return winner;
    }
    
    //pre: None
    //post: Prints what the user inputted. (Used for debugging)
    void printUserInput() {
        
        //set the input function
        for(int i=0; i<userGuess.length; i++) {
            System.console().printf("%s", userGuess[i]);
        }
        System.console().printf("%s", "\n");
    }
    
    //pre: Guess is a character array
    //post: Sets the user guess to the argument
    void getUserGuess(char[] guess) {
        userGuess = guess;
    }
    
    //pre: None
    //post: Increments the players turn
    void incrementTries() {
        triesLeft--;
    }
    
    //pre: None
    //post: Resets the black pegs
    void setBlackCount() {
        blackCount = 0;
    }
    
    //pre: None
    //post: Reset the white pegs
    void setWhiteCount() {
        whiteCount = 0;
    }
    
    //pre: newComputerGuess is a character array
    //post: Finds the black pegs
    void getBlackCount(char[] newComputerGuess) {
        
        for(int i=0; i<newComputerGuess.length; i++) {
            if(newComputerGuess[i] == userGuess[i]) {
                blackCount++;
                newComputerGuess[i] = 'z';
                userGuess[i] = 'Z';
            }
        }
    }
    
    //pre: newComputerGuess is a character array
    //post: Finds the white pegs               
    void getWhiteCount(char[] newComputerGuess) {
        
        for(int i=0; i<userGuess.length; i++) {
            for(int j=0; j<newComputerGuess.length; j++) {
                if(newComputerGuess[i] == userGuess[j]) {
                    whiteCount++;
                    newComputerGuess[i] = 'z';
                    userGuess[j] = 'Z';
                }
            }
        }
    }
    
    //pre: None
    //post: Prints the pegs for the user
    void printPegs() {
        System.console().printf("You got %d black pegs and %d white pegs.\n", blackCount, whiteCount);
    }
    
    //pre: None
    //post: Creates the hidden code
    void createHiddenCode() {
        
        //variables
        Random rand = new Random();
        final int RADIX = 10;
        int tempVar = 5;
        
        for(int i=0; i<4; i++) {
            tempVar = rand.nextInt(4);
            code[i] = Character.forDigit(tempVar,RADIX);
        }
    }
    
    //pre: None
    //post: Defines what the allowed characters are for the user to guess
    String allowedCharacters() {
        return "0123";
    }
    
    //pre: None
    //post: Checks to see if the user has won
    void checkWinner() {
        winner = (blackCount == 4);
    }
    
    //pre: none
    //post: Returns how many tries the user has left
    boolean hasTriesLeft() {
        return(triesLeft > 0);
    }
    
    //pre: None
    //post: Ending message if the user did not guess the code
    void ifLose() {
        System.out.println("You suck. You took too many tries and did not guess the code");
    }
    
    //pre: None
    //post: Ending message if the user guessed the code
    void ifWin() {
        System.out.println("Congrats on WINNING!!! You have won $0");
    }
    
    //pre: None
    //post: Prints the rules for the player
    void printRules() {
        System.out.println("* The objective of the game is to guess a four digit password");
        System.out.println("* You can guess the numbers 0-3");
        System.out.println("* A black peg means one of your numbers is in the right place");
        System.out.println("* A white peg menas that one of your numbers is the correct number, but is not in the right place");
    }
    
    //pre: None
    //post: Gets the user's guess
    char[] promptForGuess() {
        
        //define variables
        char[] answer = {'X','X','X','X'};
        
        //get input from user
        String tempVar = System.console().readLine("Please enter your four numbers without spaces: ");
        
        //check if input is good
        boolean goodInput = validateGuess(tempVar);
        
        //set the array and return the user input if it is good
        if(goodInput) {
            for(int i=0; i<4; i++) {
                answer[i] = tempVar.charAt(i);
            }
            return answer;
        }
        return promptForGuess();
    }
    
    //pre: aString is a string
    //post: Checks to see if the input matches the criteria
    boolean validateGuess(String aString) {
        
        //define variables
        String trueCharacters = allowedCharacters();
        boolean goodInput = true;
        
        //checks if the imput was the correct length
        if(aString.length() != 4) {
            System.out.println("Please enter four (4) characters");
            goodInput = false;
        } else {
            //checks to see if the string contains the correct characters
            for(int i=0; i<4; i++) {
                if(aString.charAt(i)!=trueCharacters.charAt(0) &&
                   aString.charAt(i)!=trueCharacters.charAt(1) && 
                   aString.charAt(i)!=trueCharacters.charAt(2) &&
                   aString.charAt(i)!=trueCharacters.charAt(3)    ) {
                    goodInput = false; 
                }
            }
            if(!goodInput) {
                System.out.println("Please enter the characters from '" + trueCharacters + "'");
            }
        }
        return goodInput;
    }
}