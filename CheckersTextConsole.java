package ui;
import java.util.Scanner;
import javafx.application.Application;
import java.util.concurrent.ThreadLocalRandom;

import core.CheckersComputerPlayer;
import core.CheckersLogic;
import java.util.Arrays;

/**	CheckersTextConsole.java displays all the user interactions with the text console, holds the entry point, and holds the main method. It is responsible for running the Chess game.
*
*	@author	Keana Gindlesperger
*	@version	1v Aug 21, 2022.
*/

public class CheckersTextConsole extends CheckersLogic{
    //variables neeeded to create a 2D array for the Checker's board.
    public static final int row = 9;
    public static final int col = 9;
    public static int count = 0;
    public static final String[][] board = {};

    
    /** Method that initliazes the game board.
     * @return String[][], Holds a starting Chess game board.
     */
    public static String[][] gameBoard(){
       String[][] boardArray = {{"8","_","O","_","O","_","O","_","O"}, {"7","O", "_", "O", "_", "O", "_", "O", "_"}, {"6","_", "O", "_", "O", "_", "O", "_", "O"}, {"5","_", "_", "_", "_", "_", "_", "_", "_"}, {"4","_", "_", "_", "_", "_", "_", "_", "_"}, {"3","X", "_", "X", "_", "X", "_", "X", "_"}, {"2","_", "X", "_", "X", "_", "X", "_", "X"}, {"1","X", "_", "X", "_", "X", "_", "X", "_"}, {" ","a","b","c","d","e","f","g","h"}};
       return boardArray;
    }

    
    /** Displays the 2D game board array including the seperation lines and correct formatting.
     * @param arr, Starting Chess game board array.
     */
    public static void gameDisplay(String[][] arr){
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                if(j == 8 || j == 16 || j == 24 || j == 32 || j == 35 || j == 42 || j == 49 || j == 56 ){
                    System.out.print(arr[i][j] + "|");
                    System.out.println("");
                } else {
                    System.out.print(arr[i][j] + "|");
                }
            }
        }
    }

    
    /** Method that allows player X to move. Checks if player's move is correct according to Chess rules.
     * @param arr, Starting Chess game board array.
     * @return int, returns how many times player X has moved.
     */
    public static int playerXTurn(String[][] arr){
        System.out.println("Player X - your turn. \n Choose a cell position of piece to be moved and the new position. e.g., 3a-4b");
        Scanner scan = new Scanner(System.in);
        String cellPosition = scan.nextLine();
        int index = cellPosition.indexOf('-');
        //If the input is formatted incorrectly, the player must submit a new input.
        if(index == -1){
            System.out.println("Incomplete Input");
            playerXTurn(arr);
        }
        //Turns player input into int arrays that match the 2D chess board array.
        String[] numberInputTemp = cellPosition.split("[a b c d e f g h -]+");
        int[] numberInput = Arrays.stream(numberInputTemp)
                        .mapToInt(Integer::parseInt)
                        .toArray();
        String[] arrofPositionLetter = cellPosition.split("[1 2 3 4 5 6 7 8 -]+");
        String placeHolder = "";
        for(String n: arrofPositionLetter){
            placeHolder+=n;
        }
        char[] letterInputTemp = placeHolder.toCharArray();
        int[] letterInput = new int[letterInputTemp.length] ;
        for(int i = 0; i<letterInputTemp.length; i++){
            int tempOne = letterInputTemp[i];
            int temp = tempOne - 96;
            letterInput[i] = temp;
        }
        //Checks if player's move is valid.
        if(checkMoveX(letterInput, numberInput, arr) == true){
            count+=1;
        } else{
            playerXTurn(arr);
        }
        return count;
    }
    
    /** Method that allows player O to move. Checks if player's move is correct according to Chess rules.
     * @param arr, Starting Chess game board array.
     * @return int, returns how many times player O has moved.
     */
    public static int playerOTurn(String[][] arr){
        System.out.println("Player O - your turn. \n Choose a cell position of piece to be moved and the new position. e.g., 3a-4b");
        Scanner scan = new Scanner(System.in);
        String cellPosition = scan.nextLine();
        int index = cellPosition.indexOf('-');
        //If the input is formatted incorrectly, the player must submit a new input.
        if(index == -1){
            System.out.println("Incomplete Input");
            playerXTurn(arr);
        }
        //Turns player input into int arrays that match the 2D chess board array.
        String[] numberInputTemp = cellPosition.split("[a b c d e f g h -]+");
        int[] numberInput = Arrays.stream(numberInputTemp)
                        .mapToInt(Integer::parseInt)
                        .toArray();
        String[] arrofPositionLetter = cellPosition.split("[1 2 3 4 5 6 7 8 -]+");
        String placeHolder = "";
        for(String n: arrofPositionLetter){
            placeHolder+=n;
        }
        char[] letterInputTemp = placeHolder.toCharArray();
        int[] letterInput = new int[letterInputTemp.length] ;
        for(int i = 0; i<letterInputTemp.length; i++){
            int tempOne = letterInputTemp[i];
            int temp = tempOne - 96;
            letterInput[i] = temp;
        }
        //Checks if player's move is valid.
        if(checkMoveO(letterInput, numberInput, arr) == true){
            count++;
        } else{
            playerOTurn(arr);
        }
        return count;
    }
    /** Method that allows computer player to move. Checks if player's move is correct according to Chess rules.
     * @param arr, Starting Chess game board array.
     * @return int, returns how many times computer player has moved.
     */
    public static int computerPlayerTurn(String[][] arr){
    	//if computerPlayer's random generated turn is valid, returns true
    			if(CheckersComputerPlayer.checkCompPlayer(arr) == true) {
    				count++;
    			}else {
    				computerPlayerTurn(arr);
    			}
		return count;
    }

    
    /** 
     * @param args, Stores Java command-line arguments
     */
    public static void main(String[] args){
        //initializing 2D Chess board
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Enter 'G' for JavaFX GUI or 'T' for text UI:");
        char boardInput = scan.next().charAt(0);
        if(boardInput == 'G') {
        	try {
        		Application.launch(CheckersGUI.class);
        	}catch(Exception e) {
        		System.out.println("Something caught");
        	}
        }else if(boardInput == 'T') {
        	try {
        		new CheckersTextConsole().runGame();
        	}catch(Exception e) {
        		System.out.println("Something caught");
        	}
        }else {
        	System.out.println("Invalid Entry!\n");
        }
    }
    
    public void runGame() {
    	Scanner scan = new Scanner(System.in);
        String [][] board = gameBoard();
        gameDisplay(board);
        System.out.println(" \nBegin Game. Enter 'P' if you want to play against another player; enter 'C' to play against computer.");
        String inputCP = scan.nextLine();
        //checks if player wants to play against computer or not
        //While a player has not won, program will switch turns between players
        if(inputCP.equals("P")) {
        	while(gameWin(board)==false){
                if(count % 2 == 0){
                    playerXTurn(board);
                    gameDisplay(board);
                    gameWin(board);
                }
                if(count % 2 == 1){
                    playerOTurn(board);
                    gameDisplay(board);
                    gameWin(board);
                }
            }
        } else {
        	System.out.println("Start game against computer.");
        	while(gameWin(board)==false){
                if(count % 2 == 0){
                	try{
                		playerXTurn(board);
                		gameDisplay(board);
                		gameWin(board);
                	}catch(Exception e) {
                		System.out.println("Exception caught");
                	}
                }
                if(count % 2 == 1){
                	try {
                    computerPlayerTurn(board);
                    gameDisplay(board);
                    System.out.println("Computer player has gone. Your turn.");
                    gameWin(board);
                	}catch(Exception e) {
                		System.out.println("Exception e");
                	}
                }
            }
        }
        
        
        //If a player has won, the winning statement will be outputted.
        try {
        	if(gameWin(board)==true){
        		gameWin(board);}
        }
        catch(Exception e){
        	System.out.println("Something caught");
        }
        
    }
}
