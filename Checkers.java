/* Checkers.java
 * Main class
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

import java.util.ArrayList;
import java.util.List;

public final class Checkers {

  public static void main(String[] args) {
    // Construct the initial board state.
    Board board = new Board(getInitialPieces().x, getInitialPieces().y, false);
  }

  // Return initial coordinates for the red pieces
  public static ArrayList<Coor> getRedCoors() {
    return null;
  }

  // Return initial coordinates for the black pieces
  public static ArrayList<Coor> getBlackCoors() {
    return null;
  }

  // This method should return initial board coordinates for Red and Black pieces.
  public static Pair<List<Piece>, List<Piece>> getInitialPieces() {
      List<Piece> redPieces = new List<Piece>();
      List<Piece> blackPieces = new List<Piece>();
      for (int i = 0; i < 3; i++) {
	  for (int j = (i + 1) % 2; j < 8; j += 2) {
	      redPieces.add(new RedPiece(i, j, board));
	  }
      }

      for (int i = 5; i < 8; i++) {
	  for (int j = (i + 1) % 2; j < 8; j += 2) {
	      blackPieces.add(new BlackPiece(i, j, board));
	  }
      }
      Pair<List<Piece>, List<Piece>> result = new Pair<List<Piece>, List<Piece>>(redPieces, blackPieces);
      return result;
  }


    public static void main (String [] args) {
	Scanner s = new Scanner(System.in);
	System.out.println("Hello, Let's Play Checkers!");
	Board board = new Board(getInitialPieces().x, getInitialPieces().y, false);
	while (!gameOver) {
	    
	}
    }
}
