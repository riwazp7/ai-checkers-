/**
 * Created by julianvera on 12/14/16.
 */
public class Piece {

    // X and Y coordinates of piece
    int x;
    int y;

    // pieces do not start out as kings
    boolean isKing = false;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isKing() {
    	return isKing;
    }
}
