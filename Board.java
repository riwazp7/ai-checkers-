/* Board.java
 * A state of the Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

import java.util.ArrayList;

public class Board {

  final int DEF_HEIGHT = 8;
  final int DEF_WIDTH = 8;

  ArrayList<Piece> redPieces;
  ArrayList<Piece> blackPieces;
  Piece[][] board = new Piece[DEF_HEIGHT][DEF_WIDTH];

  public Board(ArrayList<Coor> redCoors, ArrayList<Coor> blackCoors) {
    for (Coor coor : redCoors) {
      Piece piece = new RedPiece(coor, this);
      redPieces.add(piece);
      board[coor.getRow()][coor.getCol()] = piece;
    }

    for (Coor coor : blackCoors) {
      Piece piece = new BlackPiece(coor, this);
      blackPieces.add(piece);
      board[coor.getRow()][coor.getCol()] = piece;
    }
  }

  public Board(Board board) {
    // Deep clone a board
  }

  public static void main(String[] args) {
    // UNIT TESTS
  }

}
