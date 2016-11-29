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
  public BlackPiece(BlackPiece blackPiece) {
    this.coor = blackPiece.getCoor();
    this.red = false;
    this.board = new Board(blackPiece.getBoard());
  }

  public static void main(String[] args) {
    // UNIT TESTS
  }

}
