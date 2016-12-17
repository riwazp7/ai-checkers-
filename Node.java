import java.util.ArrayList;

/**
 * Node.java
 * A Node of the minimax tree
 */
public class Node {

    Board board;
    ArrayList<Node> children = null;
    int depth;
    int value = Integer.MAX_VALUE;

    // True if this is a max node.
    boolean max;


    public Node(Board board, int depth, boolean max) {
        this.board = board;
        this.depth = depth;
        this.max = max;
    }

    public int getValue() {
        if (value == Integer.MAX_VALUE) value = Evaluator.evaluate(board);
        return value;
    }

    public ArrayList<Node> getChildren() {
        if (depth >= MiniMax.MAX_TREE_DEPTH) return null;
        if (children == null) {
            generateChildren();
        }
        return children;
    }



    public void generateChildren() {
        children = new ArrayList<>();
        for (Board b : board.possibleMoves()) {
            Node n = new Node(b, depth + 1, !max);
            children.add(n);
        }
    }

    public int getTreeValue() {
        if (depth < MiniMax.MAX_TREE_DEPTH) {
            if (max) {
                int maxVal = Integer.MIN_VALUE;
                for (Node n : getChildren()) {
                    int childValue = n.getTreeValue();
                    if (childValue > maxVal) {
                        maxVal = childValue;
                    }
                }
                return maxVal;
            } else {
                int minVal = Integer.MAX_VALUE;
                for (Node n : getChildren()) {
                    int childValue = n.getTreeValue();
                    if (childValue > minVal) {
                        minVal = childValue;
                    }
                }
                return minVal;
            }
        } else {
            return getValue();
        }
    }

    public int getValueByPruning (Node node, int depth, int alpha, int beta) {
        if (depth >= MiniMax.MAX_TREE_DEPTH) {
            return Evaluator.evaluate(node.board);
        } else if (node.max) {
            for (Node n : node.getChildren()) {
                alpha = java.lang.Math.max(alpha, getValueByPruning(n, depth + 1, alpha, beta));
                if (beta <= alpha) break;
            }
            return alpha;
        } else {
            // Node is a min node
            for (Node n : node.getChildren()) {
                beta = java.lang.Math.min(beta, getValueByPruning(n, depth + 1, alpha, beta));
                if (beta <= alpha) break;
            }
            return beta;
        }
    }
}
