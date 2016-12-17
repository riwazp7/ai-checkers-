/**
 * MiniMax.java
 * Creates a MiniMax tree for the given board state.
 * Riwaz Poudyal, Julian Vera, Mark Talbot
 */
public class MiniMax {

    // Max depth of the tree we need to search to.
    public static final int MAX_TREE_DEPTH = 8;

    Node root;

    public MiniMax(Board board) {
        root = new Node(board, 0, true);
        root.generateChildren();
    }

    // Minimax only
    public Board getBestMove() {
        Board best = null;
        int maxVal = Integer.MIN_VALUE;
        for (Node n : root.getChildren()) {
            int val = n.getTreeValue();
            if (val > maxVal) {
                maxVal = val;
                best = n.board;
            }
        }
        return best;
    }

    //Minimax with pruning
    public Board getBestMoveByPruning() {
        Board best = null;
        int maxVal = Integer.MIN_VALUE;
        for (Node n : root.getChildren()) {
            int val = n.getValueByPruning(n, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (val > maxVal) {
                maxVal = val;
                best = n.board;
            }
        }
        return best;
    }


}
