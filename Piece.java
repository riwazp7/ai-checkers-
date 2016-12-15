/* Piece.java
 * A game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */
import java.util.ArrayList;
import java.util.List;

public class Piece {

  protected int row;
  protected int col;


  // True if this is a red piece. False if this is a black piece.
  boolean red;

  //True if this piece is a king. Kings can move forward and backward. Pieces are crowned (kinged?) when they reach the other side of the board
  boolean king;

  // Each piece is associated with the board state it belongs to. This allows
  // an additional layer of abstraction. For ex., the moves that a piece can
  // make can be obtained by calling a method in this object.
  Board board;

  public boolean isRed() {
    return red;
  }

  public boolean isKing() {
    return king;
  }

  public Board getBoard() {
    return board;
  }

  // Returns the moves available to this piece in a list
  public ArrayList<Coor> getMoves() {
    return null;
  }

  public void movePiece(int destRow, int destCol) {
    board.setPieceAt(null, row, col);
    board.setPieceAt(this, destRow, destCol);
    this.row = destRow;
    this.col = destCol;
  }


  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public void makeKing() {
    king = true;
  }

  // return all possible moves for this piece
  public List<Coor> getLegalMoves() {
    return null;
  }

  @Override
  public String toString() {
    return "PIECE: " + row + " " + col + " " + (red ? " RED" : " BLACK") + "\n";
  }
}
