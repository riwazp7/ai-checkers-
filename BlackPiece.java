/* BlackPiece.java
 * A black colored game piece in a Checkers Board
 * (c) Riwaz Poudyal, Mark Talbot, Julian Vera
 */

public class BlackPiece extends Piece {

  public BlackPiece(Coor coor, Board board) {
    this.coor = coor;
    this.board = board;
    this.red = false;
  }

  // Clone a black piece
  public BlackPiece(Piece blackPiece, Board board) {
    this.coor = blackPiece.getCoor();
    this.red = false;
    this.board = board;
  }

  public static void main(String[] args) {
    // UNIT TESTS
  }

}
