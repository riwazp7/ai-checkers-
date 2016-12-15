/* BlackPiece.java
 * A black colored game piece in a Checkers Board
 * (c) Riwaz Poudyal, Mark Talbot, Julian Vera
 */

public class BlackPiece extends Piece {

  public BlackPiece(int row, int col, Board board) {
    this.row = row;
    this.col = col;
    this.board = board;
    this.red = false;
  }

  // Clone a black piece
  public BlackPiece(Piece blackPiece, Board board) {
    this.row = blackPiece.getRow();
    this.col = blackPiece.getCol();
    this.red = false;
    this.board = board;
  }



  public static void main(String[] args) {
    // UNIT TESTS
  }

}
