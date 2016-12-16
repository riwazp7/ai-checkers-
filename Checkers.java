import java.util.ArrayList;

public class Checkers {

    public Checkers() {
        ArrayList<Piece> redPieces = getRedPositions();
        ArrayList<Piece> blackPieces = getBlackPositions();
        Board board = new Board(null, new String[8][8], blackPieces, redPieces , true);
        board.setBlackPieces(blackPieces);
        board.setRedPieces(redPieces);
        board.setInvalidSpaces();
        board.noNull();
        System.out.println(board);

    }

    private static ArrayList<Piece> getRedPositions() {
        ArrayList<Piece> result = new ArrayList<>();
        for (int row = 5; row < 8; row += 2) {
            for (int col = 0; col < 8; col += 2) {
                result.add(new Piece(row, col));
            }
        }

        for (int col = 1; col < 8; col += 2) {
            result.add(new Piece(6, col));
        }

        return result;
    }

    private static ArrayList<Piece> getBlackPositions() {
        ArrayList<Piece> result = new ArrayList<>();
        for (int row = 0; row < 3; row += 2) {
            for (int col = 1; col < 8; col += 2) {
                result.add(new Piece(row, col));
            }
        }

        for (int col = 0; col < 8; col += 2) {
            result.add(new Piece(1, col));
        }

        return result;
    }

    public static void main(String[] args) {
        Checkers checkers = new Checkers();
    }

}