package core;
/**	CheckersLogic.java validates player moves and checks if the game is over. 
*	@author	Keana Gindlesperger
*	@version	1v Aug 21, 2022.
*/
public class CheckersLogic {

    //variables neeeded to create a 2D array for the Checker's board.
    
    public static final int row = 9;
    public static final int col = 9;
    public static int count = 0;
    public static final String[][] board = {};

    
    /** 
     * @param arr, Chessboard 2D array.
     * @return boolean, returns false is game is not over, returns true if a player has won
     */
    public static boolean gameWin(String[][] arr){
        int xCount = 0;
        int oCount = 0;
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                if(arr[i][j] == "O"){
                    oCount++;
                } else if(arr[i][j] == "X"){
                    xCount++;
                }
            }
        }
        if(oCount == 0){
            System.out.println("Player X Won the Game");
            return true;
        } else if(xCount == 0){
            System.out.println("Player O Won the Game");
            return true;
        } else{
            return false;
        }
    }
    
    /** 
     * @param letterInput, The column which a player wants the piece to move.
     * @param numberInput, The row which a player wants the piece to move.
     * @param arr, Chessboard 2D array.
     * @return boolean, Returns true if move is valid. Returns false if move is invalid.
     */
    public static boolean checkMoveX(int[] letterInput, int[] numberInput, String[][] arr){
        //Changes the numberInput to match the 2D Chessboard array
        for(int i = 0; i < numberInput.length; i++){
            switch(numberInput[i]){
                case 1: 
                    numberInput[i] = 7;
                    break;
                case 2:
                    numberInput[i] = 6;
                    break;
                case 3:
                    numberInput[i] = 5;
                    break;
                case 5:
                    numberInput[i] = 3;
                    break;
                case 6:
                    numberInput[i] = 2;
                    break;
                case 7:
                    numberInput[i] = 1;
                    break;
                case 8:
                    numberInput[i] = 0;
                    break;
            }
        }

        //runs through multiple moves which would be invalid including moving in the same column or row, moving on top of a piece, and moving out of bounds.
        if(letterInput[0] == letterInput[1] || numberInput[0] == numberInput[1]){ //If selected move is the same
            System.out.println("Invalid Input: Cannot move in same column or row. Must move diagonally.");
            return false;
        } else if(arr[numberInput[1]][letterInput[1]] == "O") { //if selected move is on top
            System.out.println("Invalid Input: Jump over their piece!");
            return false;
        } else if(numberInput[1] > 7 || numberInput[0] > 7 || numberInput[1] < 0 || numberInput[0] < 0 || letterInput[0] > 8 || letterInput[1] > 8 || letterInput[0] < 1 || letterInput[1] < 1){
            System.out.println("Invalid Input: Out of bounds!");
            return false;
        } else if(((numberInput[0]-numberInput[1]) == 1 || (numberInput[0]-numberInput[1]) == -1) && ((letterInput[0]-letterInput[1]) == 1 || (letterInput[0]-letterInput[1]) == -1)){ 
            if(arr[numberInput[1]][letterInput[1]] == "_")   {
                arr[numberInput[0]][letterInput[0]] = "_";
                arr[numberInput[1]][letterInput[1]] = "X";
                return true;
            } else{
                System.out.println("Invalid Input: Spot is taken.");
                return false;
            }
        } else if(((numberInput[0]-numberInput[1]) == 2 || (numberInput[0]-numberInput[1]) == -2) && ((letterInput[0]-letterInput[1]) == 2 || (letterInput[0]-letterInput[1]) == -2)) { //if selected spot is empty
            if((letterInput[0]-letterInput[1]) == 2){ //When X wants to move to the left
                int temp = letterInput[1];
                int tempCalcLetter = temp + 1;
                int tempTwo = numberInput[1];
                int tempCalcNumber = tempTwo + 1;
                if(arr[tempCalcNumber][tempCalcLetter] == "O"){
                    arr[tempCalcNumber][tempCalcLetter] = "_";
                    arr[numberInput[0]][letterInput[0]] = "_";
                    arr[numberInput[1]][letterInput[1]] = "X";
                    return true;
                }
            } else if ((letterInput[0]-letterInput[1]) == -2){ //When X wants to move to the right and capture
                int temp = letterInput[1];
                int tempCalcLetter = temp - 1;
                int tempTwo = numberInput[1];
                int tempCalcNumber = tempTwo + 1;
                if(arr[tempCalcNumber][tempCalcLetter] == "O"){
                    arr[tempCalcNumber][tempCalcLetter] = "_";
                    arr[numberInput[1]][letterInput[1]] = "X";
                    arr[numberInput[0]][letterInput[0]] = "_";
                    return true;
                }
            }
        }
        System.out.println("Error");
        return false;
    }
    
    /** 
     * @param letterInput, The column which a player wants the piece to move.
     * @param numberInput, The row which a player wants the piece to move.
     * @param arr, Chessboard 2D array.
     * @return boolean, Returns true if move is valid. Returns false if move is invalid.
     */
    public static boolean checkMoveO(int[] letterInput, int[] numberInput, String[][] arr){
        //Changes the numberInput to match the 2D Chessboard array
        for(int i = 0; i < numberInput.length; i++){
            switch(numberInput[i]){
                case 1: 
                    numberInput[i] = 7;
                    break;
                case 2:
                    numberInput[i] = 6;
                    break;
                case 3:
                    numberInput[i] = 5;
                    break;
                case 5:
                    numberInput[i] = 3;
                    break;
                case 6:
                    numberInput[i] = 2;
                    break;
                case 7:
                    numberInput[i] = 1;
                    break;
                case 8:
                    numberInput[i] = 0;
                    break;
            }
        }
        //runs through multiple moves which would be invalid including moving in the same column or row, moving on top of a piece, and moving out of bounds.
        if(letterInput[0] == letterInput[1] || numberInput[0] == numberInput[1]){ //If selected move is the same
            System.out.println("Invalid Input: Cannot move in same column or row. Must move diagonally.");
            return false;
        } else if(arr[numberInput[1]][letterInput[1]] == "X") { //if selected move is on top
            System.out.println("Invalid Input: Jump over their piece!");
            return false;
        } else if(numberInput[1] > 7 || numberInput[0] > 7 || numberInput[1] < 0 || numberInput[0] < 0 || letterInput[0] > 8 || letterInput[1] > 8 || letterInput[0] < 1 || letterInput[1] < 1){
            System.out.println("Invalid Input: Out of bounds!");
            return false;
        } else if(((numberInput[0]-numberInput[1]) == 1 || (numberInput[0]-numberInput[1]) == -1) && ((letterInput[0]-letterInput[1]) == 1 || (letterInput[0]-letterInput[1]) == -1)){ 
            if(arr[numberInput[1]][letterInput[1]] == "_")   {
                arr[numberInput[0]][letterInput[0]] = "_";
                arr[numberInput[1]][letterInput[1]] = "O";
                return true;
            } else{
                System.out.println("Invalid Input: Spot is taken.");
                return false;
            }
        } else if(((numberInput[0]-numberInput[1]) == 2 || (numberInput[0]-numberInput[1]) == -2) && ((letterInput[0]-letterInput[1]) == 2 || (letterInput[0]-letterInput[1]) == -2)) { //if selected spot is empty
            if((letterInput[0]-letterInput[1]) == 2){ //When O wants to move to the left
                int temp = letterInput[1];
                int tempCalcLetter = temp + 1;
                int tempTwo = numberInput[1];
                int tempCalcNumber = tempTwo - 1;
                if(arr[tempCalcNumber][tempCalcLetter] == "X"){
                    arr[tempCalcNumber][tempCalcLetter] = "_";
                    arr[numberInput[1]][letterInput[1]] = "O";
                    arr[numberInput[0]][letterInput[0]] = "_";
                    return true;
                }
            } else{ //When O wants to move to the right and capture
                int temp = letterInput[1];
                int tempCalcLetter = temp - 1;
                int tempTwo = numberInput[1];
                int tempCalcNumber = tempTwo - 1;
                if(arr[tempCalcNumber][tempCalcLetter] == "X"){
                    arr[tempCalcNumber][tempCalcLetter] = "_";
                    arr[numberInput[1]][letterInput[1]] = "O";
                    arr[numberInput[0]][letterInput[0]] = "_";
                    return true;
                }
            }
        }
        System.out.println("Error");
        return false;
        }
    /** 
     * Method creates randomly generated move.
     * @param arr, Chessboard 2D array.
     * @return boolean, Returns true if move is valid. Returns false if move is invalid.
     */
    public static boolean checkCompPlayer(String[][] arr){
    	for(int row = 0; row < arr.length; row++) {
    		for(int col = 1; col < arr[row].length; col++) {
    			String x = arr[row][col];
    			String xRightUp;
    			String xLeftUp;
    		if(x.equals("O")) {
			if(row+1  >= arr.length || col+1 >= arr[row].length) {
				xRightUp = arr[row][col];
				xLeftUp = arr[row][col];
			}else {
				xRightUp = arr[row+1][col+1];
				xLeftUp = arr[row+1][col-1];
			}
				if(xRightUp.equals("_") || xRightUp.equals("X")) {
					switch(xRightUp) {
						case "_":
							arr[row+1][col+1] = "O";
    						arr[row][col]= "_";
    						return true;
						case "X":
							if(arr[row+2][col+2] == "_") {
								arr[row+2][col+2] = "O";
								arr[row][col]= "_";
								arr[row+1][col+1] = "_";
								return true;
							}
					}
				} else if(xLeftUp.equals("_") || xLeftUp.equals("X")) {
					switch(xLeftUp) {
						case "_":
							arr[row+1][col-1] = "O";
							arr[row][col]= "_";
							return true;
						case "X":
							if(arr[row+2][col-2] == "_") {
								arr[row+2][col-2] = "O";
								arr[row][col]= "_";
								arr[row+1][col-1] = "_";
								return true;
							}
					}
				}
			}
    		}
    	}
    	return false;
		}
    
    }
