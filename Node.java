import java.util.ArrayList;

/**
 * Created by Riwaz on 12/16/16.
 */
public class Node {

    Board board;
    ArrayList<Node> children = new ArrayList<>();
    int depth;
    int value = -1;

    // True if this is a max node.
    boolean max;


    public Node(Board board, int depth, boolean max) {
        this.board = board;
        this.depth = depth;
        this.max = max;

        if (depth == MiniMax.MAX_TREE_DEPTH) value = Evaluator.evaluate(board);
    }

    public void generateChildren() {
        if (depth >= MiniMax.MAX_TREE_DEPTH) return;
        for (Board b : board.possibleMoves()) {
            Node n = new Node(b, depth + 1, !max);
            children.add(n);
            n.generateChildren();
        }
    }

    public int getTreeValue() {
        if (depth < MiniMax.MAX_TREE_DEPTH) {
            if (max) {
                int maxVal = Integer.MIN_VALUE;
                for (Node n : children) {
                    int childValue = n.getTreeValue();
                    if (childValue > maxVal) {
                        maxVal = childValue;
                    }
                }
                return maxVal;
            } else {
                int minVal = Integer.MAX_VALUE;
                for (Node n : children) {
                    int childValue = n.getTreeValue();
                    if (childValue > minVal) {
                        minVal = childValue;
                    }
                }
                return minVal;
            }
        } else {
            return value;
        }
    }

    public int getValueByPruning (Node node, int depth, int alpha, int beta) {
        if (depth >= MiniMax.MAX_TREE_DEPTH) {
            return Evaluator.evaluate(node.board);
        } else if (node.max) {
            for (Node n : node.children) {
                alpha = java.lang.Math.max(alpha, getValueByPruning(n, depth + 1, alpha, beta));
                if (beta <= alpha) break;
            }
            return alpha;
        } else {
            // Node is a min node
            for (Node n : node.children) {
                beta = java.lang.Math.min(beta, getValueByPruning(n, depth + 1, alpha, beta));
                if (beta <= alpha) break;
            }
            return beta;
        }
    }
}
