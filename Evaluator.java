/**
 * Class to hold static methods for different evaluation function we will use and evaluate.
 */
public class Evaluator {

    private static final int KING_VALUE = 10;
    private static final int PIECE_VALUE = 5;

    public static Integer evaluate(Board board) {
        int redScore = 0;
        int blackScore = 0;

        for (Piece piece : board.redPieces) {
            if (isKing(piece, board)) redScore += KING_VALUE;
            else {
                redScore += PIECE_VALUE;
                if (piece.x < 4) {
                    redScore += (8 - piece.x);
                }
            }
        }

        for (Piece piece : board.blackPieces) {
            if (isKing(piece, board)) blackScore += KING_VALUE;
            else {
                blackScore += PIECE_VALUE;
                if (piece.x > 3) {
                    blackScore += (piece.x);
                }
            }
        }

        return redScore - blackScore;
    }

    private static boolean isKing(Piece piece, Board board) {
        if (board.board[piece.x][piece.y].equals("R") || board.board[piece.x][piece.y].equals("B")) {
            return true;
        }
        return false;
    }
}
