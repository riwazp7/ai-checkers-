/* RedPiece.java
 * A  red colored game piece in a Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

import java.util.ArrayList;

public class RedPiece extends Piece {

  public RedPiece(Coor coor) {
    this.coor = coor;
    this.red = true;
  }

  public RedPiece(RedPiece redPiece) {
    this.coor = redPiece.getCoor();
    this.red = true;
  }

}
