/* Piece.java
 * A game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

public class Piece {

  // Coordinate of this piece in the board.
  Coor coor;

  // True if this is a red piece. False if this is a black piece.
  boolean red;

  //True if this piece is a king. Kings can move forward and backward. Pieces are crowned (kinged?) when they reach the other side of the board
  boolean king;

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

  public boolean isKing() {
    return king;
  }

  public Board getBoard() {
    return board;
  }

  public void movePiece(Coor dest) {
    if (board.getPieceAt(dest) != null) {
      throw new RuntimeException("DEST COOR NOT EMPTY");
    }
    board.setPieceAt(null, coor);
    board.setPieceAt(this, dest);
    this.coor = dest;
  }

  public void makeKing() {
    king = true;
  }

  @Override
  public String toString() {
    return "PIECE: " + coor.toString() + (red ? " RED" : " BLACK") + "\n";
  }
}
