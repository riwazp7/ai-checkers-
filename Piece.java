/* Piece.java
 * A game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

public class Piece {

  // Coordinate of this piece in the board.
  Coor coor;

  // True if this is a red piece. False if this is a black piece.
  boolean red;

  public Piece(Coor coor) {
    this.coor = coor;
  }

}
