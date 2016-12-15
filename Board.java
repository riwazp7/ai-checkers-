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
  private List<Piece> redPieces = new ArrayList<>();
  private List<Piece> blackPieces = new ArrayList<>();
  private Piece[][] boardArray = new Piece[DEF_HEIGHT][DEF_WIDTH];

  // True iff red player's turn to play
  private boolean redTurn;

  /*
   * This constructor creates a board state from lists of Black and Red pieces Coordinates.
   * Will probably only use it to create the initial board state.
   */
  public Board(List<Piece> redPieces, List<Piece> blackPieces, boolean redTurn) {

    this.redTurn = redTurn;

    for (Piece piece : redPieces) {
      boardArray[piece.getRow()][piece.getCol()] = piece;
    }

    for (Piece piece : blackPieces) {
      boardArray[piece.getRow()][piece.getCol()] = piece;
    }

    this.redPieces = redPieces;
    this.blackPieces = blackPieces;
  }

  // Defaults to black player's turn as the black player starts first.
  public Board(ArrayList<Piece> redPieces, ArrayList<Piece> blackPieces) {
    this(redPieces, blackPieces, false);
  }

  // Deep clone a board state. This is used if we want new Board state equal to a given board state.
  public Board(Board board) {
    for (Piece piece : board.getRedPieces()) {
      Piece newPiece = new RedPiece(piece, this);
      redPieces.add(newPiece);
      boardArray[newPiece.getRow()][newPiece.getCol()] = newPiece;
    }

    for (Piece piece : board.getBlackPieces()) {
      Piece newPiece = new BlackPiece(piece, this);
      blackPieces.add(newPiece);
      boardArray[newPiece.getRow()][newPiece.getCol()] = newPiece;
    }
    this.redTurn = board.redTurn();
  }

  public List<Piece> getRedPieces() {
    return redPieces;
  }

  public List<Piece> getBlackPieces() {
    return blackPieces;
  }

  public Piece[][] getBoardArray() {
    return boardArray;
  }

  public Piece getPieceAt(int row, int col) {
    return boardArray[row][col];
  }

  public void setPieceAt(Piece piece, int row, int col) {
    boardArray[row][col] = piece;
  }

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
      piece.movePiece(dest.getRow(), dest.getCol());
  }

  public boolean redTurn() {
    return this.redTurn;
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
          if (piece.isKing()) result += "R";
          else result += "r";
        } else {
          if (piece.isKing()) result += "B";
          else result += "b";
        }
      }
      result += "\n";
    }
    return result;
  }

  // Return all board states that can be reached from this board state in a single move.
  // If red's turn, for each red piece, see which of the moves are possible. For each possible move,
  // create a new Board state using the clone board constructor and move the red piece to it's new position.
  // Then add this new board to the result list.
  // Same if black's turn.
  public List<Board> getAdjacentBoards() {
    return null;
  }

  public void kingRed(RedPiece p) {
    if(p.getRow() == 0 && (p.getCol() >= 0 && p.getCol() < DEF_HEIGHT)) { p.king = true; }
    redTurn = false;
  }

  public void kingBlack(BlackPiece p) {
    if(p.getRow() == 7 && (p.getCol() >= 0 && p.getCol() < DEF_HEIGHT)) { p.king = true; }
    redTurn = true;
  }

  public boolean isInsideBoard(int row, int col) {
    if ((row <= 7 && row >= 0) && (col <= 7 && col >= 0)) return true;
    return false;
  }


  public static void main(String[] args) {
    // UNIT TESTS
  }

}
