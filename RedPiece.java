/* RedPiece.java
 * A  red colored game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

public class RedPiece extends Piece {

  public RedPiece(Coor coor, Board board) {
    this.coor = coor;
    this.board = board;
    this.red = true;
  }

  // Clone a red piece
  public RedPiece(Piece redPiece, Board board) {
    this.coor = redPiece.getCoor();
    this.red = true;
    this.board = board;
  }

  public static void main(String[] args) {
    // UNIT TESTS
  }

}
