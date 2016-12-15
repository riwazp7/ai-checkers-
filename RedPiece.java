/**
 * Created by julianvera on 12/14/16.
 */
public class RedPiece {

    // X and Y coordinates of piece
    int x;
    int y;

    // pieces do not start out as kings
    boolean isKing = false;

    public RedPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
