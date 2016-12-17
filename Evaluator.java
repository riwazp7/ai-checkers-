import java.util.HashMap;

/**
 * Evaluator.java
 * Riwaz Poudyal, Mark Talbot, Julian Vera
 * Class to hold static methods for different evaluation function we will use and experiment with.
 */
public class Evaluator {

    private static final int KING_VALUE = 10;
    private static final int PIECE_VALUE = 6;
    private static final int[][] RED_VALUE_ARRAY =  new int[][]{
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 7, 0, 8, 0, 8, 0, 8, 0},
            { 0, 7, 0, 7, 0, 7, 0, 6},
            { 5, 0, 6, 0, 6, 0, 6, 0},
            { 0, 5, 0, 5, 0, 5, 0, 5},
            { 5, 0, 5, 0, 5, 0, 5, 0},
            { 0, 5, 0, 5, 0, 5, 0, 5},
            { 6, 0, 7, 0, 7, 0, 7, 0}
        };
    private static final int[][] BLACK_VALUE_ARRAY =  new int[][]{
            { 6, 0, 7, 0, 7, 0, 7, 0},
            { 0, 5, 0, 5, 0, 5, 0, 5},
            { 5, 0, 5, 0, 5, 0, 5, 0},
            { 0, 5, 0, 5, 0, 5, 0, 5},
            { 5, 0, 6, 0, 6, 0, 6, 0},
            { 0, 7, 0, 7, 0, 7, 0, 6},
            { 7, 0, 8, 0, 8, 0, 8, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private HashMap<String, Integer> cache = new HashMap<>();


    /* This is a pretty basic evaluation function.
     * It gives a base value PIECE_VALUE to each remaining
     */
    public static Integer evaluate(Board board) {

    }

    public static Integer function1(Board board) {
        int blackScore = 0;
        int redScore = 0;

        for (Piece piece : board.redPieces) {
            if (isKing(piece, board)) redScore += KING_VALUE;
            else {
                redScore += PIECE_VALUE;
            }
        }

        for (Piece piece : board.blackPieces) {
            if (isKing(piece, board)) blackScore += KING_VALUE;
            else {
                blackScore += PIECE_VALUE;
            }
        }
        return blackScore - redScore;
    }

    public static Integer function2(Board board) {
        int blackScore = 0;
        int redScore = 0;

        for (Piece piece : board.redPieces) {
            if (isKing(piece, board)) redScore += KING_VALUE;
            else {
                redScore += PIECE_VALUE;
                if (piece.x < 4) {
                    redScore += ((8 - piece.x) / 2);
                }
            }
        }

        for (Piece piece : board.blackPieces) {
            if (isKing(piece, board)) blackScore += KING_VALUE;
            else {
                blackScore += PIECE_VALUE;
                if (piece.x > 3) {
                    blackScore += (piece.x / 2);
                }
            }
        }
        return blackScore - redScore;
    }

    private static Integer function3(Board board) {
        int blackScore = 0;
        int redScore = 0;

        for (Piece piece : board.redPieces) {
            if (isKing(piece, board)) redScore += KING_VALUE;
            else {
                redScore += RED_VALUE_ARRAY[piece.x][piece.y];
            }
        }

        for (Piece piece : board.blackPieces) {
            if (isKing(piece, board)) blackScore += KING_VALUE;
            else {
                blackScore += BLACK_VALUE_ARRAY[piece.x][piece.y];
            }
        }
        return blackScore - redScore;
    }

    private static Integer function4(Board board) {
        int redScore = 0;
        int blackScore = 0;

        String[][] boardArr = board.board();
        for (Piece piece : board.redPieces) {
            if (boardArr[piece.x -1][piece.y - 1].equals("b") || boardArr[piece.x -1][piece.y - 1].equals("B")) {
                if (isInsideBoard(piece.x + 1, piece.y + 1)) {
                    if (boardArr[piece.x + 1][piece.y + 1].equals("#")) {
                        redScore -= 3;
                    } else if (boardArr[piece.x + 1][piece.y + 1].equals("B") || boardArr[piece.x + 1][piece.y + 1].equals("b")) {
                        redScore -= 2;
                    } else redScore -=1;
                }
            } else if (boardArr[piece.x - 1][piece.y + 1].equals("b") || boardArr[piece.x - 1][piece.y + 1].equals("B")) {
                if (isInsideBoard(piece.x + 1, piece.y - 1)) {
                    if (boardArr[piece.x + 1][piece.y - 1].equals("#")) {
                        redScore -= 3;
                    } else if (boardArr[piece.x + 1][piece.y - 1].equals("B") || boardArr[piece.x + 1][piece.y - 1].equals("b")) {
                        redScore -= 2;
                    } else redScore -=1;
                }
            } else if (boardArr[piece.x + 1][piece.y - 1].equals("B")) {
                if (isInsideBoard(piece.x - 1, piece.y + 1)) {
                    if (boardArr[piece.x - 1][piece.y + 1].equals("#")) {
                        redScore -= 3;
                    } else if (boardArr[piece.x - 1][piece.y + 1].equals("B") || boardArr[piece.x - 1][piece.y + 1].equals("b")) {
                        redScore -= 2;
                    } else redScore -=1;
                }
            } else if (boardArr[piece.x + 1][piece.y + 1].equals("B")) {
                if (isInsideBoard(piece.x - 1, piece.y - 1)) {
                    if (boardArr[piece.x - 1][piece.y - 1].equals("#")) {
                        redScore -= 3;
                    } else if (boardArr[piece.x - 1][piece.y - 1].equals("B") || boardArr[piece.x - 1][piece.y - 1].equals("b")) {
                        redScore -= 2;
                    } else redScore -=1;
                }
            }
        }

        for (Piece piece : board.blackPieces) {
            if (boardArr[piece.x + 1][piece.y + 1].equals("r") || boardArr[piece.x + 1][piece.y + 1].equals("R")) {
                if (isInsideBoard(piece.x - 1, piece.y - 1)) {
                    if (boardArr[piece.x - 1][piece.y - 1].equals("#")) {
                        blackScore -= 3;
                    } else if (boardArr[piece.x - 1][piece.y - 1].equals("R") || boardArr[piece.x - 1][piece.y - 1].equals("r")) {
                        blackScore -= 2;
                    } else blackScore -= 1;
                }
            } else if (boardArr[piece.x + 1][piece.y - 1].equals("r") || boardArr[piece.x - 1][piece.y + 1].equals("R")) {
                if (isInsideBoard(piece.x - 1, piece.y + 1)) {
                    if (boardArr[piece.x + 1][piece.y - 1].equals("#")) {
                        blackScore -= 3;
                    } else if (boardArr[piece.x - 1][piece.y + 1].equals("R") || boardArr[piece.x - 1][piece.y + 1].equals("r")) {
                        blackScore -= 2;
                    } else blackScore -=1;
                }
            } else if (boardArr[piece.x - 1][piece.y + 1].equals("R")) {
                if (isInsideBoard(piece.x + 1, piece.y - 1)) {
                    if (boardArr[piece.x + 1][piece.y - 1].equals("#")) {
                        blackScore -= 3;
                    } else if (boardArr[piece.x + 1][piece.y - 1].equals("R") || boardArr[piece.x + 1][piece.y - 1].equals("r")) {
                        blackScore -= 2;
                    } else blackScore -=1;
                }
            } else if (boardArr[piece.x - 1][piece.y - 1].equals("R")) {
                if (isInsideBoard(piece.x + 1, piece.y + 1)) {
                    if (boardArr[piece.x + 1][piece.y + 1].equals("#")) {
                        blackScore -= 3;
                    } else if (boardArr[piece.x + 1][piece.y + 1].equals("R") || boardArr[piece.x + 1][piece.y + 1].equals("r")) {
                        blackScore -= 2;
                    } else blackScore -=1;
                }
            }
        }
        return blackScore - redScore + function3(board);
    }

    private static String getStringRpr(Board board) {
        String[][] boardArr = board.board();
        String result = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result += boardArr[i][j];
            }
        }
        result += board.redsTurn;
        return result;
    }

    private static boolean isKing(Piece piece, Board board) {
        if (board.board[piece.x][piece.y].equals("R") || board.board[piece.x][piece.y].equals("B")) {
            return true;
        }
        return false;
    }

    private static boolean isInsideBoard(int x, int y) {
        return ((x <= 7 && x >= 0) && (y <= 7 && y >= 0));
    }
}
