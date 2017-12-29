/*
* The TicTacToeRun program runs the
* TicTacToe class to initialize and run 
* a game of TicTacToe.
*
* @author  Karen Liang
*/
public class TicTacToeRun {
	
	/**
	 * Initializes a TicTacToe object and runs a game of TicTacToe
	 * @param args unused
	 */
	public static void main(String[] args){ 
		TicTacToe d = new TicTacToe(); 
		d.start();
		d.display();
		
		do {
			d.turn(); 
			d.display();
		}
		while (!d.winner());
	}
}
