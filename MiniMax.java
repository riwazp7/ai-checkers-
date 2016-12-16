/**
 * Created by Riwaz on 12/16/16.
 */
public class MiniMax {

    public static final int MAX_TREE_DEPTH = 6;

    Node root;

    public MiniMax(Board board) {
        root = new Node(board, MAX_TREE_DEPTH);
    }

}
