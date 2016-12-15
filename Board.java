import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julianvera on 12/14/16.
 */
public class Board {

    // board size for most Checkers games
    private final int DEF_HEIGHT = 8;
    private final int DEF_WIDTH = 8;

    // spots that cannot be accessed during the game will be tagged with this string
    private final String invalid = " ";

    // this string will be used for jumping
    private final String empty = "#";

    // ArrayList<BlackPieces>
    List<BlackPiece> blackPieces = new ArrayList<BlackPiece>();

    // ArrayList<RedPieces>
    List<RedPiece> redPieces = new ArrayList<RedPiece>();

    String[][] board = new String[DEF_HEIGHT][DEF_WIDTH];

    // true if it's red's true
    private boolean redsTurn = false;

    Board parent;

    public Board(Board parent, ArrayList<BlackPiece> blackPieces, ArrayList<RedPiece> redPieces) {
        this.parent = parent;
        this.blackPieces = blackPieces;
        this.redPieces = redPieces;
    }

    // in case we need to go back up in a tree
    public Board parent() {
        return this.parent;
    }

    public void setBlackPieces(ArrayList<BlackPiece> pieces) {
        for(BlackPiece p : pieces) { board[p.x][p.y] = "B"; }
    }

    public void setRedPieces(ArrayList<RedPiece> pieces) {
        for(RedPiece p : pieces) { board[p.x][p.y] = "R"; }
    }

    /**
     * There are spaces on the board no piece can ever be. This method marks those spaces
     */
    public void setInvalidSpaces() {

        for(int i = 0; i < DEF_HEIGHT; i+=2) {
            for(int j = 1; j < DEF_WIDTH; j+=2) {
               board[i][j] = invalid;
            }
        }

        for(int x = 1; x < DEF_HEIGHT; x+=2) {
            for(int y = 0; y < DEF_WIDTH; y+=2) {
                board[x][y] = invalid;
            }
        }

    }

    public boolean validRedMove(RedPiece p) {
        // player can attempt to move left or right
        // if no jump is available and the piece isn't a king, the piece can only move one square diagonally forward to
        // an unoccupied space
        /**
        } else {
            /**
             * king can move any distance, forward or backward, along an unobstructed diagonal
             * can jump any piece in the diagonal, if there is an open space after the jump
             * When the King makes its first jump in a turn, it must land on a square that will allow it to make
             * another jump, if another jump is possible. After landing, the King can turn and jump on a different
             * diagonal, or it can jump on the same diagonal. The King must make its multiple jumps in a way that
             * gives it the most jumps.
        }
        **/
        return false;
    }

    public boolean validBlackMove(RedPiece p) {
        // player can attempt to move left or right
        // if no jump is available and the piece isn't a king, the piece can only move one square diagonally forward to
        // an unoccupied space
        /**
         } else {
         /**
         * king can move any distance, forward or backward, along an unobstructed diagonal
         * can jump any piece in the diagonal, if there is an open space after the jump
         * When the King makes its first jump in a turn, it must land on a square that will allow it to make
         * another jump, if another jump is possible. After landing, the King can turn and jump on a different
         * diagonal, or it can jump on the same diagonal. The King must make its multiple jumps in a way that
         * gives it the most jumps.
         }
         **/
        return false;
    }

    // don't want null strings on the board, will makes things harder in the long run
    public void noNull() {
        for(int i = 0; i < DEF_WIDTH; i++) {
            for(int j = 0; j < DEF_HEIGHT; j++) {
                if(board[i][j] == null){ board[i][j] = empty; }
            }
        }
    }

    /**
     * @param p
     * @return true if a red piece can jump a black piece diagonally left
     */
    public boolean redJumpLeft(RedPiece p) {
        // if it goes off the board at any point, return false
        if(p.y - 1 < 0 || p.y - 2 < 0 || p.x - 1 < 0 || p.x - 2 < 0) { return false; }
        // if the adjacent left piece is red, return false
        if(board[p.x - 1][p.y - 1].equals("R")) { return false; }
        // if the adjacent piece is black, but the square to jump in is blocked, return false
        if( (board[p.x - 1][p.y - 1].equals("B"))
                && (!board[p.x - 2][p.y - 2].equals(empty)) ) {
                return false;
        }
        // if there is no adjacent piece return false
        if(board[p.x - 1][p.y - 1].equals(empty)) { return false; }
        // if all the other cases fail
        return true;
    }

    /**
     * @param p
     * @return true if a red piece can jump a black piece diagonally right
     */
    public boolean redJumpRight(RedPiece p) {
        // if it goes off the board, return false
        if(p.y + 1 > 7 || p.y + 2 > 7 || p.x - 1 < 0 || p.x - 2 < 0) { return false; }
        // if adjacent piece is red, return false
        if(board[p.x - 1][p.y + 1].equals("R")) { return false; }
        // if the adjacent piece is black, but the square to jump in is blocked, return false
        if( (board[p.x - 1][p.y + 1].equals("B"))
                && (!board[p.x - 2][p.y + 2].equals(empty)) ) {
                return false;
        }
        // if no adjacent piece, return false
        if(board[p.x - 1][p.y + 1].equals(empty)) { return false; }
        // if all other cases fail
        return true;
    }

    /**
     * must check whether any red pieces on the board
     * can jump a black piece
     * @return true if no red piece can jump
     */
    public boolean redCannotJump() {
        for(RedPiece p : redPieces) {
            if(redJumpLeft(p)){ return false; }
            if(redJumpRight(p)){ return false; }
        }
        return true;
    }


    /**
     * @param p
     * @return true if a black piece can jump a red piece diagonally left
     */

    public boolean blackJumpLeft(BlackPiece p) {
        // if it goes off the board at any point, return false
        if(p.y - 1 < 0 || p.y - 2 < 0 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
        // if the adjacent left piece is black, return false
        if(board[p.x + 1][p.y - 1].equals("B")) { return false; }
        // if the adjacent piece is red, but the square to jump in is blocked, return false
        if( (board[p.x + 1][p.y - 1].equals("R"))
                && (!board[p.x + 2][p.y - 2].equals(empty)) ) {
            return false;
        }
        // if there is no adjacent piece return false
        if(board[p.x + 1][p.y - 1].equals(empty)) { return false; }
        // all else fails
        return true;
    }


    /**
     * @param p
     * @return true if the black piece can jump a red piece diagonally right
     */
    public boolean blackJumpRight(BlackPiece p) {
        // if it goes off the board, return false
        if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
        // if adjacent piece is black, return false
        if(board[p.x + 1][p.y + 1].equals("B")) { return false; }
        // if the adjacent piece is red, but the square to jump in is blocked, return false
        if( (board[p.x + 1][p.y + 1].equals("R"))
                && (!board[p.x + 2][p.y + 2].equals(empty)) ) {
            return false;
        }
        // if no adjacent piece, return false
        if(board[p.x + 1][p.y + 1].equals(empty)) { return false; }
        // all else fails
        return true;
    }


    /**
     * must check whether any red pieces on the board can jump a black piece
     * @return true if no red piece can jump
     */
    public boolean blackCannotJump() {
        for(BlackPiece p : blackPieces) {
            if(blackJumpLeft(p)){ return false; }
            if(blackJumpRight(p)){ return false; }
        }
        return true;
    }

    // crowing a piece ends the turn, even if there are possible jumps
    // always check if a piece needs to be crowned?
    public void kingRed(RedPiece p) {
        if(p.x == 0 && (p.y >= 0 && p.y < DEF_HEIGHT)) { p.isKing = true; }
        redsTurn = false;
    }

    public void kingBlack(BlackPiece p) {
        if(p.x == 7 && (p.y >= 0 && p.y < DEF_HEIGHT)) { p.isKing = true; }
        redsTurn = true;
    }

    /**
     * RED PIECES ARE MOVING UP THE BOARD!
     */

    public void moveRedLeft(RedPiece p) {
        // if redJumpLeft returns true, make the jump
    }

    public void moveRedRight(RedPiece p) {
        // if redJumpRight returns true, make the jump
    }

    /**
     * BLACK PIECES ARE MOVING DOWN THE BOARD!
     */
    public void moveBlackLeft(BlackPiece p) {
        // if blackJumpLeft returns true, make the jump
    }

    public void moveBlackRight(BlackPiece p) {
        // if blackJumpRight returns true, make the jump
    }


    public ArrayList<Board> possibleMoves(Board parent) {
        ArrayList<Board> children = new ArrayList<Board>();
        ArrayList<BlackPiece> blacks = (ArrayList<BlackPiece>) parent.blackPieces;
        ArrayList<RedPiece> reds = (ArrayList<RedPiece>) parent.redPieces;
        /**
         * here we call find all the possible board states using other methods
         */
        return children;
    }




    

    public void printBoard() {
        for(int i = 0; i < DEF_HEIGHT; i++) {
            for(int j = 0; j < DEF_WIDTH; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }




    public static void main(String[] args) {
        ArrayList<RedPiece> reds = new ArrayList<RedPiece>();
        ArrayList<BlackPiece> blacks = new ArrayList<BlackPiece>();

        Board a = new Board(null,blacks,reds);
        for(int i = 0; i < 3; i += 2){
            for(int j = 0; j < a.board[0].length; j += 2 ) {
                blacks.add(new BlackPiece(i,j));
            }
        }

        for(int i = 1; i < a.board[0].length; i += 2) {
            blacks.add(new BlackPiece(1,i));
        }

        for(int i = 5; i < 8; i += 2){
            for(int j = 1; j < a.board[0].length; j += 2 ) {
                reds.add(new RedPiece(i,j));
            }
        }

        for(int i = 0; i < 8; i += 2) {
            reds.add(new RedPiece(6,i));
        }

        a.setBlackPieces(blacks);
        a.setRedPieces(reds);
        a.setInvalidSpaced();
        a.noNull();
        a.printBoard();

        System.out.println(a.redCannotJump());
        System.out.println(a.blackCannotJump());

        for(RedPiece p : reds){
            System.out.println(a.validRedMove(p));
        }

    }

}
