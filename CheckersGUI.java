package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**	CheckersGUI.java creates a UI for checkers with JavaFX and utilizes new Checkers logic with the use of an ArrayList
*	@author	Keana Gindlesperger
*	@version	1v Sep 13, 2022.
*/
public class CheckersGUI extends Application {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }
    
    CheckersBoard board; 

    private Button newGameButton;  
    private Button computerPlayerButton; 
    private Label message;  
    private Label compMessage;
    public boolean compPlay = false;
    
    /** Displays the 2D game board array 
     * @param stage 
     */
    public void start(Stage stage) {
        
        message = new Label("");
        message.setTextFill( Color.rgb(100,255,100) ); 
        message.setFont( Font.font(null, FontWeight.BOLD, 16) );
        
        compMessage = new Label("");
        compMessage.setTextFill( Color.rgb(100,255,100) ); 
        compMessage.setFont( Font.font(null, FontWeight.BOLD, 16) );

        newGameButton = new Button("Player vs Player");
        computerPlayerButton = new Button("Player vs Computer");

        board = new CheckersBoard(); 
        board.drawBoard();  

        newGameButton.setOnAction( e -> board.doNewGame() );
        board.setOnMousePressed( e -> board.mousePressed(e) );
        computerPlayerButton.setOnAction( e -> board.doCompGame());


        board.relocate(75,20);
        newGameButton.relocate(340, 348);
        computerPlayerButton.relocate(340, 380);
        message.relocate(20, 370);
        compMessage.relocate(20,345);
        
        
        newGameButton.setManaged(false);
        newGameButton.resize(150,30);
        computerPlayerButton.setManaged(false);
        computerPlayerButton.resize(150, 30);
        
        Pane root = new Pane();
        
        root.setPrefWidth(500);
        root.setPrefHeight(420);

        root.getChildren().addAll(board, newGameButton, message, compMessage, computerPlayerButton);
        root.setStyle("-fx-background-color: BLACK; ");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Checkers");
        stage.show();

    } 


    public static class CheckersMove {
    	//variables needed for CheckersMove
        int fromInput, fromInputC; 
        int toInput, toInputC;      
        CheckersMove(int r1, int c1, int r2, int c2) {
            fromInput = r1;
            fromInputC = c1;
            toInput = r2;
            toInputC = c2;
        }
        boolean isJump() {
            return (fromInput - toInput == 2 || fromInput - toInput == -2);
        }
    }  

    /** 
     * Sets up CheckersBoardGame according to who the user decides to play against
     *
     */
    public class CheckersBoard extends Canvas {

        CheckersData board; 
        boolean gameInProgress; 
        int currentPlayer;      
        int inputRow, inputCol;   
        CheckersMove[] legalMoves;  

        
        CheckersBoard() {
            super(324,324);  
            board = new CheckersData();
            doNewGame();
        }

        //Method for Regular Player vs Player Game
        void doNewGame() {
        	compPlay = false;
            board.setUpGame();   
            currentPlayer = CheckersData.RED;   
            legalMoves = board.getMoves(CheckersData.RED);  
            inputRow = -1;   
            compMessage.setText("");
            message.setText("Red:  Make your move.");
            gameInProgress = true;
            drawBoard();
        }
        
      //Method for Player vs Computer Game
        void doCompGame() {
        	compPlay = true;
            board.setUpGame();  
            currentPlayer = CheckersData.RED;   
            legalMoves = board.getMoves(CheckersData.RED);  
            inputRow = -1;   
            compMessage.setText("Click board for computer to move");
            message.setText("Red:  Make your move.");
            gameInProgress = true;
            drawBoard();
        }

        //Checks if game is over
        void gameWin(String str) {
            message.setText(str);
            compPlay = false;
            gameInProgress = false;
        }
        //checks if square clicked is valid
        void doClickSquare(int row, int col) {
            for (int i = 0; i < legalMoves.length; i++)
                if (legalMoves[i].fromInput == row && legalMoves[i].fromInputC == col) {
                    inputRow = row;
                    inputCol = col;
                    if (currentPlayer == CheckersData.RED)
                        message.setText("RED:  Make your move.");
                    else
                        message.setText("WHITE:  Make your move.");
                    drawBoard();
                    return;
                }
            if (inputRow < 0) {
            	if((compPlay == true) && (currentPlayer == CheckersData.WHITE)) {
            		row = (int)(Math.random() *(7+1));
                	col = (int)(Math.random() *(7+1));
                	doClickSquare(row,col);
            	}else {
            		message.setText("Click the piece you want to move.");
                    return;
            	}
            }
            for (int i = 0; i < legalMoves.length; i++)
                if (legalMoves[i].fromInput == inputRow && legalMoves[i].fromInputC == inputCol
                && legalMoves[i].toInput == row && legalMoves[i].toInputC == col) {
                    makeMove(legalMoves[i]);
                    return;
                } else if ((compPlay == true) && (currentPlayer == CheckersData.WHITE)) {
                	row = (int)(Math.random() *(7+1));
                	col = (int)(Math.random() *(7+1));
                	doClickSquare(row,col);
                }
            
            	message.setText("Click the square you want to move to.");
            
        }  
        //if move is valid, makeMove is called to change what the board looks like
        void makeMove(CheckersMove move) {
            board.makeMove(move);
            if (move.isJump()) {
                legalMoves = board.getJumps(currentPlayer,move.toInput,move.toInputC);
                if (legalMoves != null) {
                    inputRow = move.toInput;  // Since only one piece can be moved, select it.
                    inputCol = move.toInputC;
                    drawBoard();
                    return;
                }
            }
            if ((currentPlayer == CheckersData.RED) ) {
                currentPlayer = CheckersData.WHITE;
                legalMoves = board.getMoves(currentPlayer);
                if (legalMoves == null)
                    gameWin("WHITE has no moves.  RED wins.");
                else if (legalMoves[0].isJump())
                    message.setText("WHITE:  Make your move.  You must jump.");
                else
                    message.setText("WHITE:  Make your move.");
            }
            else {
                currentPlayer = CheckersData.RED;
                legalMoves = board.getMoves(currentPlayer);
                if (legalMoves == null)
                    gameWin("RED has no moves.  WHITE wins.");
                else if (legalMoves[0].isJump())
                    message.setText("RED:  Make your move.  You must jump.");
                else
                    message.setText("RED:  Make your move.");
            }
            inputRow = -1;
            if (legalMoves != null) {
                boolean startSquare = true;
                for (int i = 1; i < legalMoves.length; i++)
                    if (legalMoves[i].fromInput != legalMoves[0].fromInput
                    || legalMoves[i].fromInputC != legalMoves[0].fromInputC) {
                        startSquare = false;
                        break;
                    }
                if (startSquare) {
                    inputRow = legalMoves[0].fromInput;
                    inputCol = legalMoves[0].fromInputC;
                }
            }
            drawBoard();
        }  
       //drawBoard is called from makeMove, it changes the appearance of the board
        public void drawBoard() {
            GraphicsContext g = getGraphicsContext2D();
            g.setFont( Font.font(18) );

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 )
                        g.setFill(Color.GREEN);
                    else
                        g.setFill(Color.LIGHTGREEN);
                    g.fillRect(2 + col*40, 2 + row*40, 40, 40);
                    switch (board.pieceAt(row,col)) {
                    case CheckersData.RED:
                        g.setFill(Color.RED);
                        g.fillOval(8 + col*40, 8 + row*40, 28, 28);
                        break;
                    case CheckersData.WHITE:
                        g.setFill(Color.WHITE);
                        g.fillOval(8 + col*40, 8 + row*40, 28, 28);
                        break;
                    
                    }
                }
            }


        }  
        
        /** When mouse is clicked on the board, checks if the desired click is valid
         *  @param evt 
         */
        public void mousePressed(MouseEvent evt) {
            if (gameInProgress == false) {
                message.setText("Click \"New Game\" to start a new game.");
                if ((currentPlayer == CheckersData.RED) ) {
                    currentPlayer = CheckersData.WHITE;
                    legalMoves = board.getMoves(currentPlayer);
                    if (legalMoves == null)
                        gameWin("WHITE has no moves.  RED wins.");
                    else if (legalMoves[0].isJump())
                        message.setText("WHITE:  Make your move.  You must jump.");
                    else
                        message.setText("WHITE:  Make your move.");
                }
                else {
                    currentPlayer = CheckersData.RED;
                    legalMoves = board.getMoves(currentPlayer);
                    if (legalMoves == null)
                        gameWin("RED has no moves.  WHITE wins.");
                    else if (legalMoves[0].isJump())
                        message.setText("RED:  Make your move.  You must jump.");
                    else
                        message.setText("RED:  Make your move.");
                }
            }else if(compPlay == true){
            	if(currentPlayer == CheckersData.WHITE) {
            		int col = (int)(Math.random() *(7+1));
                    int row = (int)(Math.random() *(7+1));
                    try {
                        doClickSquare(row,col);
                	}catch(NullPointerException e) {
                		if ((currentPlayer == CheckersData.RED) ) {
                            currentPlayer = CheckersData.WHITE;
                            legalMoves = board.getMoves(currentPlayer);
                            if (legalMoves == null)
                                gameWin("WHITE has no moves.  RED wins.");
                            else if (legalMoves[0].isJump())
                                message.setText("WHITE:  Make your move.  You must jump.");
                            else
                                message.setText("WHITE:  Make your move.");
                        }
                        else {
                            currentPlayer = CheckersData.RED;
                            legalMoves = board.getMoves(currentPlayer);
                            if (legalMoves == null)
                                gameWin("RED has no moves.  WHITE wins.");
                            else if (legalMoves[0].isJump())
                                message.setText("RED:  Make your move.  You must jump.");
                            else
                                message.setText("RED:  Make your move.");
                        }
                	}
            	}else {
            		int col = (int)((evt.getX() - 2) / 40);
                    int row = (int)((evt.getY() - 2) / 40);
                    try {
                    	if (col >= 0 && col < 8 && row >= 0 && row < 8)
                            doClickSquare(row,col);
                	}catch(NullPointerException e) {
                		if ((currentPlayer == CheckersData.RED) ) {
                            currentPlayer = CheckersData.WHITE;
                            legalMoves = board.getMoves(currentPlayer);
                            if (legalMoves == null)
                                gameWin("WHITE has no moves.  RED wins.");
                            else if (legalMoves[0].isJump())
                                message.setText("WHITE:  Make your move.  You must jump.");
                            else
                                message.setText("WHITE:  Make your move.");
                        }
                        else {
                            currentPlayer = CheckersData.RED;
                            legalMoves = board.getMoves(currentPlayer);
                            if (legalMoves == null)
                                gameWin("RED has no moves.  WHITE wins.");
                            else if (legalMoves[0].isJump())
                                message.setText("RED:  Make your move.  You must jump.");
                            else
                                message.setText("RED:  Make your move.");
                        }
                	}
                    
            	}
            }else {
            	int col = (int)((evt.getX() - 2) / 40);
                int row = (int)((evt.getY() - 2) / 40);
                try {
                	if (col >= 0 && col < 8 && row >= 0 && row < 8)
                        doClickSquare(row,col);
            	}catch(NullPointerException e) {
            		if ((currentPlayer == CheckersData.RED) ) {
                        currentPlayer = CheckersData.WHITE;
                        legalMoves = board.getMoves(currentPlayer);
                        if (legalMoves == null)
                            gameWin("WHITE has no moves.  RED wins.");
                        else if (legalMoves[0].isJump())
                            message.setText("WHITE:  Make your move.  You must jump.");
                        else
                            message.setText("WHITE:  Make your move.");
                    }
                    else {
                        currentPlayer = CheckersData.RED;
                        legalMoves = board.getMoves(currentPlayer);
                        if (legalMoves == null)
                            gameWin("RED has no moves.  WHITE wins.");
                        else if (legalMoves[0].isJump())
                            message.setText("RED:  Make your move.  You must jump.");
                        else
                            message.setText("RED:  Make your move.");
                    }
            	}
            }
        }
    }  
    
    public static class CheckersData {

        static final int
                    EMPTY = 0,
                    RED = 1,
                    WHITE = 3;

        int[][] board;  

        CheckersData() {
            board = new int[8][8];
            setUpGame();
        }
        
        //creates array for initial game
        void setUpGame() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 ) {
                        if (row < 3)
                            board[row][col] = WHITE;
                        else if (row > 4)
                            board[row][col] = RED;
                        else
                            board[row][col] = EMPTY;
                    }
                    else {
                        board[row][col] = EMPTY;
                    }
                }
            }
        } 
        
        /** 
         * @param row
         * @param col
         * @return board[row][col], returns piece at certain place on board
         */
        int pieceAt(int row, int col) {
            return board[row][col];
        }
        
        /** 
         * @param move
         */
        void makeMove(CheckersMove move) {
            makeMove(move.fromInput, move.fromInputC, move.toInput, move.toInputC);
        }
        
        void makeMove(int fromInput, int fromInputC, int toInput, int toInputC) {
            board[toInput][toInputC] = board[fromInput][fromInputC];
            board[fromInput][fromInputC] = EMPTY;
            if (fromInput - toInput == 2 || fromInput - toInput == -2) {
               
                int jumpR = (fromInput + toInput) / 2;
                int jumpC = (fromInputC + toInputC) / 2; 
                board[jumpR][jumpC] = EMPTY;
            }
        }

        CheckersMove[] getMoves(int player) {
            if (player != RED && player != WHITE)
                return null;
            ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  // Moves will be stored in this list.
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == player) {
                        if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                            moves.add(new CheckersMove(row, col, row+2, col+2));
                        if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                            moves.add(new CheckersMove(row, col, row-2, col+2));
                        if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                            moves.add(new CheckersMove(row, col, row+2, col-2));
                        if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                            moves.add(new CheckersMove(row, col, row-2, col-2));
                    }
                }
            }
            if (moves.size() >= 0) {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (board[row][col] == player ) {
                            if (canMove(player,row,col,row+1,col+1))
                                moves.add(new CheckersMove(row,col,row+1,col+1));
                            if (canMove(player,row,col,row-1,col+1))
                                moves.add(new CheckersMove(row,col,row-1,col+1));
                            if (canMove(player,row,col,row+1,col-1))
                                moves.add(new CheckersMove(row,col,row+1,col-1));
                            if (canMove(player,row,col,row-1,col-1))
                                moves.add(new CheckersMove(row,col,row-1,col-1));
                        }
                    }
                }
            }
            if (moves.size() == 0)
                return null;
            else {
                CheckersMove[] moveArray = new CheckersMove[moves.size()];
                for (int i = 0; i < moves.size(); i++)
                    moveArray[i] = moves.get(i);
                return moveArray;
            }

        } 
        CheckersMove[] getJumps(int player, int row, int col) {
            if (player != RED && player != WHITE)
                return null;
            
            ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>(); 
            if (board[row][col] == player ) {
                if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                    moves.add(new CheckersMove(row, col, row+2, col+2));
                if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                    moves.add(new CheckersMove(row, col, row-2, col+2));
                if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                    moves.add(new CheckersMove(row, col, row+2, col-2));
                if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                    moves.add(new CheckersMove(row, col, row-2, col-2));
            }
            if (moves.size() == 0)
                return null;
            else {
                CheckersMove[] moveArray = new CheckersMove[moves.size()];
                for (int i = 0; i < moves.size(); i++)
                    moveArray[i] = moves.get(i);
                return moveArray;
            }
        }  
        
        public boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
            if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
                return false;  

            if (board[r3][c3] != EMPTY)
                return false;  

            if (player == RED) {
                if (board[r1][c1] == RED && r3 > r1)
                    return false;  
                if (board[r2][c2] != WHITE)
                    return false;
                
                return true; 
            }
            else {
                if (board[r1][c1] == WHITE && r3 < r1)
                    return false;  
                if (board[r2][c2] != RED)
                    return false;
                
                return true;  
            }

        }  
      
        public boolean canMove(int player, int r1, int c1, int r2, int c2) {
            if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
                return false;  
            if (board[r2][c2] != EMPTY)
                return false;  
            if (player == RED) {
                if (board[r1][c1] == RED && r2 > r1)
                    return false;  
                return true;  
            }
            else {
                if (board[r1][c1] == WHITE && r2 < r1)
                    return false;  
                return true; 
            }
        }  
    } 

} 
