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
  public static Pair<List<Coor>, List<Coor>> getInitialPieces() {
    List<Coor> redPieces = new ArrayList<>();
    List<Coor> blackPieces = new ArrayList<>();
    Pair<List<Coor>, List<Coor>> result = new Pair<>(redPieces, blackPieces);
    return null;
  }

}
