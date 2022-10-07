package core;

public class CheckersComputerPlayer{
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
