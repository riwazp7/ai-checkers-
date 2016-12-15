/* RedPiece.java
 * A  red colored game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

public class RedPiece extends Piece {

  public RedPiece(int row, int col, Board board) {
    this.row = row;
    this.col = col;
    this.board = board;
    this.red = true;
  }

  // Clone a red piece
  public RedPiece(Piece redPiece, Board board) {
    this.row = redPiece.getRow();
    this.col = redPiece.getCol();
    this.red = true;
    this.board = board;
  }

  public void getObligationMoves() {

  }

  public static void main(String[] args) {
    // UNIT TESTS
  }

}
