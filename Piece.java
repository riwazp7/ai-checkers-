/* Piece.java
 * A game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

public class Piece {

  // Coordinate of this piece in the board.
  Coor coor;

  // True if this is a red piece. False if this is a black piece.
  boolean red;

  // Each piece is associated with the board state it belongs to. This allows
  // an additional layer of abstraction. For ex., the moves that a piece can
  // make can be obtained by calling a method in this object.
  Board board;

  public Coor getCoor() {
    return coor;
  }

  public boolean isRed() {
    return red;
  }

  public Board getBoard() {
    return board;
  }
}
