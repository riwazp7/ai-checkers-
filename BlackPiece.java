/**
 * Created by julianvera on 12/14/16.
 */
public class BlackPiece {

    // X and Y coordinates of piece
    int x;
    int y;

    // pieces do not start out as kings
    boolean isKing = false;

    public BlackPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
