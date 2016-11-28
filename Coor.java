/* Coor.java
 * (c) 2016 Riwaz Poudyal, Mark Talbot, Julian Vera
 * Immutable POJO to hold a coordinate
 */

public class Coor {

  private final int row;
  private final int col;

  public Coor(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public String toString() {
    return ("("+row+","+col+")");
  }

  public boolean equals(Coor coor) {
    return (this.row == coor.getRow() && this.col == coor.getCol());
  }
}
