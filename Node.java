import java.util.ArrayList;

/**
 * Created by Riwaz on 12/16/16.
 */
public class Node {

    Board board;
    ArrayList<Node> children;
    int depth;


    public Node(Board board, int depth) {
        this.board = board;
        this.depth = depth;
    }

    public void generateChildren() {
        if (depth >= Checkers.MAX_TREE_DEPTH) return;
        for (Board b : board.possibleMoves()) {
            Node n = new Node(b, depth + 1);
            children.add(n);
            n.generateChildren();
        }
    }

}
