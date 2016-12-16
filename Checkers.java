import java.util.ArrayList;

public class Checkers {

    public static final int MAX_TREE_DEPTH = 10;

    public Checkers() {
        ArrayList<Piece> redPieces = getRedPositions();
        ArrayList<Piece> blackPieces = getBlackPositions();
        Board board = new Board(null, redPieces, blackPieces , false);

        Node root = new Node(board, 0);
        root.generateChildren();


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