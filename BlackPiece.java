/* BlackPiece.java
 * A black colored game piece in a Checkers Board
 * (c) Riwaz Poudyal, Mark Talbot, Julian Vera
 */

import java.util.ArrayList;

public class BlackPiece extends Piece {

  public BlackPiece(Coor coor) {
    this.coor = coor;
    this.red = false;
  }

  // Clone a black piece
  public BlackPiece(BlackPiece blackPiece) {
    this.coor = blackPiece.getCoor();
    this.red = false;
  }
}
