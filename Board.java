/* Board.java
 * A state of the Checkers Board
 * (c) Riwaz Poudyal, Julian Vera, Mark Talbot
 */

import java.util.ArrayList;
import java.util.List;

public class Board {

  // Default height and width of the board
  private final int DEF_HEIGHT = 8;
  private final int DEF_WIDTH = 8;

  /* A state of the Checker's board depends on the positions of the red and black pieces and whose turn it is.
   * Each piece's piece object is stored in both a List and in the 2D array.
   */
  private ArrayList<Piece> redPieces = new ArrayList<>();
  private ArrayList<Piece> blackPieces = new ArrayList<>();
  private Piece[][] boardArray = new Piece[DEF_HEIGHT][DEF_WIDTH];

  // True iff red player's turn to play
  private boolean redTurn;

  public Board(ArrayList<Coor> redCoors, ArrayList<Coor> blackCoors, boolean redTurn) {

    this.redTurn = redTurn;

    for (Coor coor : redCoors) {
      Piece piece = new RedPiece(coor, this);
      redPieces.add(piece);
      boardArray[coor.getRow()][coor.getCol()] = piece;
    }

    for (Coor coor : blackCoors) {
      Piece piece = new BlackPiece(coor, this);
      blackPieces.add(piece);
      boardArray[coor.getRow()][coor.getCol()] = piece;
    }
  }

  // Defaults to black player's turn as the black player starts first.
  public Board(ArrayList<Coor> redCoors, ArrayList<Coor> blackCoors) {
    this(redCoors, blackCoors, false);
  }

  // Deep clone a board state
  public Board(Board board) {
    for (Piece piece : board.getRedPieces()) {
      Piece newPiece = new RedPiece(piece, this);
      redPieces.add(newPiece);
      boardArray[newPiece.getCoor().getRow()][newPiece.getCoor().getCol()] = newPiece;
    }

    for (Piece piece : board.getBlackPieces()) {
      Piece newPiece = new BlackPiece(piece, this);
      blackPieces.add(newPiece);
      boardArray[newPiece.getCoor().getRow()][newPiece.getCoor().getCol()] = newPiece;
    }
  }

  public ArrayList<Piece> getRedPieces() {
    return redPieces;
  }

  public ArrayList<Piece> getBlackPieces() {
    return blackPieces;
  }

  public Piece[][] getBoardArray() {
    return boardArray;
  }

  public boolean isOccupied(Coor coor) {
    return (boardArray[coor.getRow()][coor.getCol()] != null);
  }

  public Piece getPieceAt(Coor coor) {
    return boardArray[coor.getRow()][coor.getCol()];
  }

  public void setPieceAt(Piece piece, Coor coor) {
    boardArray[coor.getRow()][coor.getCol()] = piece;
  }

  // WE SHOULD ALSO CHECK IF THE MOVE IS LEGAL.
  public void movePiece(Coor pieceCoor, Coor dest) {
      Piece piece = boardArray[pieceCoor.getRow()][pieceCoor.getCol()];
      if (piece == null) {
        throw new RuntimeException("NO PIECE AT " + pieceCoor.toString());
      }
      if ((piece.isRed() && !redTurn) || (!piece.isRed() && redTurn)) {
        throw new RuntimeException("WRONG TURN ORDER " + piece.toString());
      }
      if (boardArray[dest.getRow()][dest.getCol()] != null) {
	  throw new RuntimeException("DESTINATION OCCUPIED AT " + dest.toString());
      }
      piece.movePiece(dest);
  }

    // Checks if the proposed move is legal. Assumes that top left corner is (0,0) and bottom right is (7,7) && red moves bottom-top while black moves top-bottom
    private boolean legalMove(Coor pieceCoor, Coor dest) {
	Piece piece = boardArray[pieceCoor.getRow()][pieceCoor.getCol()];
	boolean legal = false;
	// Only checks adjacent spaces, doesn't handle moves that take opponent's pieces
	if (piece.isRed() || piece.isKing()) {
	    if ((dest.getRow() - pieceCoor.getRow() == -1 || dest.getRow() - pieceCoor.getRow() == 1) && (dest.getCol() - pieceCoor.getCol() == -1)) {
		legal = true;
	    }
	} else if (!piece.isBlack() || piece.isKing()) {
	    if ((dest.getRow() - pieceCoor.getRow() == -1 || dest.getRow() - pieceCoor.getRow() == 1) && (dest.getCol() - pieceCoor.getCol() == 1)) {
		legal = true;
	    }
	}
	
    }

  @Override
  public String toString() {
    String result = "";
    for (int i = 0; i < DEF_HEIGHT; i++) {
      for (int j = 0; j < DEF_WIDTH; j++) {
        Piece piece = boardArray[i][j];
        if (piece == null) {
          result += "X";
        } else if (piece.isRed()) {
          result += "R";
        } else {
          result += "B";
        }
      }
      result += "\n";
    }
    return result;
  }

  // TODO


  // Return all board states that can be reached from this board state in a single move.
  public List<Board> getAdjacentBoards() {
    return null;
  }


  public static void main(String[] args) {
    // UNIT TESTS
  }

}
