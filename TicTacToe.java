import java.util.Arrays;
import java.util.Scanner;

/*
* The TicTacToe program provides methods for a game of 
* Tic Tac Toe with a player and the computer. The computer
* will always win or tie. 
*
* @author  Karen Liang
*/
public class TicTacToe {
	private char[][]board; 
	private int currentTurn; 
	private int[] rowWin = {-1, -1};
	private int[] colWin = {-1, -1};
	private int[] diagWin = {-1, -1};
	private int[] checkWin = {-1,-1};
	private int[][] priority = {
		         {1, 1}, {0, 0}, {0, 2}, {2, 2}, {2, 0},
		         {0, 1}, {1, 0}, {1, 2}, {2, 1}};	
	
	 /**
	  * Constructor.
	  * Creates a game board for Tic Tac Toe as board
	  */
	public TicTacToe(){ 
		board = new char[3][3];
	}
	
	 /**
	  * Prompts user to determine the first player. 
	  * If 0, Computer starts. 
	  * If 1, Player starts.
	  * Then initializes an empty board
	  */
	public void start(){ 
		Scanner in = new Scanner (System.in); 
		System.out.println("Let's play Tic Tac Toe!"); 
		System.out.print("Who is the first player? Enter 1 for you, 0 for Computer"); 
		if (in.hasNextInt()){ 
			currentTurn = in.nextInt(); 
			if (currentTurn != 1 && currentTurn != 0){ 
				System.out.println("Invalid Value entered. Default: Computer is first player."); 
				currentTurn = 0; 
			}
		}
		else { 
			System.out.println("Invalid Value entered. Default: Computer is first player."); 
			currentTurn = 0; 
		}
		initialize(); 
	}
	
	 /**
	   * Initializes the board with empty spaces denoted by '-' 
	   */
	public void initialize(){ 
		for (int i=0; i<3; i++){ 
			for (int j=0; j<3; j++){
				board [i][j] = '-';
			}
		}
	}
	
	 /**
	   * Displays the board to the console in a user friendly way
	   */
	public void display() { 
		for (int i=0; i<3; i++){ 
			System.out.print("|"); 
			for (int j=0; j<3; j++){ 
				System.out.print(board[i][j] + "|");
			}
			System.out.println(); 
		}
		System.out.println(); 
	}
	
	 /**
	   * Returns String computer or player depending on the current turn
	   * @param turn Integer the current turn in the game
	   * @return String Computer if the turn is 0 or Player if the turn is 1
	   */
	private String playerToString(int turn){ 
		if (turn == 1) { 
			return "You";
		}
		else{ 
			return "Computer";
		}
	}
	
	 /**
	   * Changes the current turn to Computer or Player
	   */
	private void changeTurn(){ 
		if (currentTurn == 1)
			currentTurn = 0; 
		else
			currentTurn = 1; 
	}
	
	 /**
	  * Updates the game board by inserting character X for the Player or O for the Computer.
	  * Player move is determined by user input. 
	  * Computer move is generated ensure a win. 
	   */
	public void turn(){ 
		if (currentTurn == 0){ 
			System.out.println("Computer's Turn");
			nextRowWin();
			nextColWin(); 
			nextDiagWin();
			//check if next move is row win
			if (!Arrays.equals(rowWin, checkWin)){
				counter(rowWin); 
			}
			//check if next move is column win 
			else if (!Arrays.equals (colWin, checkWin)) {
				counter(colWin); 
			}
			//check if next move is diagonal win
			else if (!Arrays.equals(diagWin, checkWin)){
				counter(diagWin);
			}
			//computer moves based on priority positions
			else
				compMove(); 
			changeTurn(); 
		}
		else { 
			System.out.println("Player's Turn");
			int nextCol, nextRow; 
			Scanner in = new Scanner (System.in); 
			System.out.print ("Please enter row and column to place your piece [Enter as col row]: ");
			if (in.hasNext()) { 
				nextCol = in.nextInt(); 
				nextRow = in.nextInt(); 
				if (nextCol == 3 || nextRow == 3)
					System.out.println("Input of of range. col and row must be between 0-2.");
				else if (board[nextCol][nextRow] == '-') {
					board[nextCol][nextRow] = 'X'; 
					changeTurn(); 
				}
				else { 
					System.out.println("Invalid Input. There is already a piece in that location.");
				}
			}
			else { 
				System.out.println("Invalid Input. Please input integers for col row.");
			}
		}
	}
	
	 /**
	   * Computes the next position of the Computer's next move based on
	   * list priority that contains positions in decreasing priority.
	   */
	private void compMove(){ 
		for (int i=0; i<priority.length; i++){
			int[]move = priority[i];
			if(board[move[0]][move[1]] == '-'){
				board[move[0]][move[1]] = 'O'; 
				break;
			}
		}
	}
	
	 /**
	   * Plays the Computer's move to counter the Player or win the game. 
	   * 
	   * @param a Integer List where a[0] is the best row position and a[1] is the best column position to
	   * either win the game or block the Player from winning. 
	   */
	private void counter(int[] a){
		board[a[0]][a[1]] = 'O';
		a[0] = -1; 
		a[1] = -1;
	}
	
	 /**
	  * Checks if there is a win in a row on the next move
	  * 
	  * @return Integer List rowWin [-1,-1] if there is no winning move and [col,row] where col(column) and row are the 
	  * respective positions of the winning move
	  */
	private int[] nextRowWin(){ 
		for (int r=0; r<3; r++){ 
			if (board[r][0] == board[r][1] && board[r][2] == '-' && board[r][0] != '-') {
				rowWin[0] = r; 
				rowWin[1] = 2;
				return rowWin; 
			}
			else if (board[r][1] == board[r][2] && board[r][0] == '-' && board[r][1] != '-') {
				rowWin[0] = r;
				rowWin[1] = 0;
				return rowWin; 
			}
			else if (board[r][0] == board[r][2] && board[r][1] == '-' && board[r][0] != '-'){
				rowWin[0] = r;
				rowWin[1] = 1;
				return rowWin; 
			}
		}
		return rowWin;
	}
	
	 /**
	  * Checks if there is a win in a column on the next move
	  * 
	  * @return Integer List colWin [-1,-1] if there is no winning move and [col,row] where col(column) and row are the 
	  * respective positions of the winning move
	  */
	private int[] nextColWin(){ 
		for (int r=0; r<3; r++){ 
			if (board[0][r] == board[1][r] && board[2][r] == '-' && board[0][r] != '-'){
				colWin[0] = 2;
				colWin[1] = r;
				return colWin;
			}
			else if (board[1][r] == board[2][r] && board[0][r] == '-' && board[0][r] != '-'){
				colWin[0] = 0;
				colWin[1] = r;
				return colWin;
			}
			else if (board[0][r] == board[2][r] && board[1][r] == '-' && board[0][r] != '-'){
				colWin[0] = 1;
				colWin[1] = r;
				return colWin;
			}
		}
		return colWin;
		
	}
	
	 /**
	  * Checks if there is a win on the diagonal on the next move
	  * 
	  * @return Integer List diagWin [-1,-1] if there is no winning move and [col,row] where col(column) and row are the 
	  * respective positions of the winning move
	  */
	private int[] nextDiagWin(){ 
		//left diagonal
		if (board[0][0] == board[1][1] && board[2][2] == '-' && board[0][0] != '-'){
			diagWin[0] = 2; 
			diagWin[1] = 2;
			return diagWin;
		}
		else if (board[1][1] == board[2][2] && board[0][0] == '-' && board[1][1] != '-'){
			diagWin[0] = 0; 
			diagWin[1] = 0;
			return diagWin;
		}
		else if (board[0][0] == board[2][2] && board[1][1] == '-' && board[0][0] != '-'){
			diagWin[0] = 1; 
			diagWin[1] = 1;
			return diagWin;
		}
		//right diagonal
		else if (board[0][2] == board[2][0] && board[1][1] == '-' && board[0][2] != '-'){
			diagWin[0] = 1; 
			diagWin[1] = 1;
			return diagWin;
		}
		else if (board[0][2] == board[1][1] && board[2][0] == '-' && board[0][2] != '-'){
			diagWin[0] = 2; 
			diagWin[1] = 0;
			return diagWin;
		}		
		else if (board[1][1] == board[2][0] && board[0][2] == '-' && board[1][1] != '-'){
			diagWin[0] = 0; 
			diagWin[1] = 2;
			return diagWin;
		}
		return diagWin;
	}
	
	 /**
	  * Checks for a win on the board.
	  * 
	  * @return boolean True if there is a tie or a column, row, or diagonal win, False if not.
	  */
	public boolean winner(){ 
		if (rowWin() || colWin() || diagWin()){ 
			changeTurn(); 
			System.out.println("Winner: " + playerToString(currentTurn) + "!"); 
			return true; 
		}
		else if (tie()){
			System.out.println("There is a tie.");
			return true;
		}
		else{ 
			System.out.println("No winner yet.\n"); 
			return false;
		}
		
	}
	
	 /**
	   * Checks if all parameters are equal and not an empty space (denoted by '-')
	   * @param v1 char value of a position in the 2d array board
	   * @param v2 char value of a position in the 2d array board
	   * @param v3 char value of a position in the 2d array board
	   * @return boolean True if all parameters are equal and not an empty space (denoted by '-')
	   * 	False if parameters are not equal or if one 
	   */
	private boolean checkValue(char v1, char v2, char v3){ 
		return ((v1 != '-') && (v1 == v2) && (v2==v3));
	}
	
	 /**
	   * Checks if there is a win from a row on the board
	   * @return boolean True if all values of a row are equal and not empty (denoted by '-'), False otherwise
	   */
	private boolean rowWin (){ 
		for (int i=0; i<3; i++){ 
			if (checkValue (board[i][0], board[i][1], board[i][2])){
				return true;
			}
		}
		return false; 
	}
	
	 /**
	   * Checks if there is a win from a column on the board
	   * @return boolean True if all values of a column are equal and not empty, False otherwise
	   */
	private boolean colWin(){ 
		for (int i=0; i<3; i++){ 
			if (checkValue (board[0][i], board[1][i], board[2][i]))
				return true; 
		}
		return false; 
	}
	
	 /**
	   * Checks if there is a win from a diagonal on the board
	   * @return boolean True if all values of a diagonal are equal and not empty, False otherwise
	   */
	private boolean diagWin(){ 
		return ((checkValue (board[0][0],board[1][1],board[2][2]) == true) || 
				(checkValue (board[0][2], board[1][1], board[2][0]) == true)); 
	}
	
	 /**
	   * Checks if there is a tie on the board
	   * @return boolean True if the entire board is not empty and there is no winning column, row, or diagonal, False otherwise
	   */
	private boolean tie(){
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				if (board[i][j] == '-')
					return false;
			}
		}
		return true; 
	}
}
