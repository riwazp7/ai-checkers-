import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julianvera on 12/14/16.
 */
public class Board implements Comparable<Board> {
	// board size for most Checkers games
	private final int DEF_HEIGHT = 8;
	private final int DEF_WIDTH = 8;

	// spots that cannot be accessed during the game will be tagged with this string
	private final String invalid = " ";

	// this string will be used for jumping
	private final String empty = "#";

	// ArrayList<Pieces>
	List<Piece> blackPieces = new ArrayList<Piece>();

	// ArrayList<Pieces>
	List<Piece> redPieces = new ArrayList<Piece>();

	String[][] board = new String[DEF_HEIGHT][DEF_WIDTH];

	// true if it's red's true
	private boolean redsTurn;

	Board parent;

	// can a piece be captured in this board?
	private boolean canCapture = false;


    public Board(Board parent, String[][] board, ArrayList<Piece> blackPieces, ArrayList<Piece> redPieces, boolean redsTurn) {
        this.parent = parent;
	    this.board = board;
        this.blackPieces = blackPieces;
        this.redPieces = redPieces;
	    this.redsTurn = redsTurn;
    }

    public Board(Board parent, ArrayList<Piece> blackPieces, ArrayList<Piece> redPieces, boolean redsTurn) {
        this.parent = parent;
        this.blackPieces = blackPieces;
        this.redPieces = redPieces;
        this.redsTurn = redsTurn;
        setBlackPieces(blackPieces);
        setRedPieces(redPieces);
    }

    // in case we need to go back up in a tree
    public Board parent() {
        return this.parent;
    }
    
    public String[][] board() {
		return this.board;
	}

	public void setBlackPieces(ArrayList<Piece> pieces) {
		for(Piece p : pieces) { 
			board[p.x][p.y] = "b";
	 	}

	}

	public void setRedPieces(ArrayList<Piece> pieces) {
		for(Piece p : pieces) { 
			board[p.x][p.y] = "r";
		}
	}

	/**
	 * There are spaces on the board no piece can ever be. This method marks those spaces
	 */
	public void setInvalidSpaces() {

		for(int i = 0; i < DEF_HEIGHT; i+=2) {
			for(int j = 0; j < DEF_WIDTH; j+=2) {
			   board[i][j] = invalid;
			}
		}

		for(int x = 1; x < DEF_HEIGHT; x+=2) {
			for(int y = 1; y < DEF_WIDTH; y+=2) {
				board[x][y] = invalid;
			}
		}

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
	public boolean redCaptureLeft(Piece p) {
		// if it goes off the board at any point, return false
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x - 1 < 0 || p.x - 2 < 0) { return false; }
		// if the adjacent left piece is red, return false
		if(board[p.x - 1][p.y - 1].toLowerCase().equals("r")) { return false; }
		// if the adjacent piece is black, but the square to jump in is blocked, return false
		if( (board[p.x - 1][p.y - 1].toLowerCase().equals("b"))
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
	public boolean redCaptureRight(Piece p) {
		// if it goes off the board, return false
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x - 1 < 0 || p.x - 2 < 0) { return false; }
		// if adjacent piece is red, return false
		if(board[p.x - 1][p.y + 1].toLowerCase().equals("r")) { return false; }
		// if the adjacent piece is black, but the square to jump in is blocked, return false
		if( (board[p.x - 1][p.y + 1].toLowerCase().equals("b"))
				&& (!board[p.x - 2][p.y + 2].equals(empty)) ) {
				return false;
		}
		// if no adjacent piece, return false
		if(board[p.x - 1][p.y + 1].equals(empty)) { return false; }
		// if all other cases fail
		return true;
	}

	public boolean redKingCaptureLeft(Piece p) {
		// if it goes off the board at any point, return false
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
		// if the adjacent left piece is black, return false
		if(board[p.x + 1][p.y - 1].toLowerCase().equals("r")) { return false; }
		// if the adjacent piece is red, but the square to jump in is blocked, return false
		if( (board[p.x + 1][p.y - 1].toLowerCase().equals("b"))
				&& (!board[p.x + 2][p.y - 2].equals(empty)) ) {
			return false;
		}
		// if there is no adjacent piece return false
		if(board[p.x + 1][p.y - 1].equals(empty)) { return false; }
		// all else fails
		return true;
	}

	public boolean redKingCaptureRight(Piece p) {
		// if it goes off the board, return false
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
		// if adjacent piece is black, return false
		if(board[p.x + 1][p.y + 1].toLowerCase().equals("r")) { return false; }
		// if the adjacent piece is red, but the square to jump in is blocked, return false
		if( (board[p.x + 1][p.y + 1].toLowerCase().equals("b"))
				&& (!board[p.x + 2][p.y + 2].equals(empty)) ) {
			return false;
		}
		// if no adjacent piece, return false
		if(board[p.x + 1][p.y + 1].equals(empty)) { return false; }
		// all else fails
		return true;
	}
	/**
	 * must check whether any red pieces on the board
	 * can jump a black piece
	 * @return true if no red piece can jump
	 */
	public boolean redCannotCapture() {
		for(Piece p : redPieces) {
			if(redCaptureLeft(p)){ return false; }
			if(redCaptureRight(p)){ return false; }
			if(p.isKing() && redKingCaptureLeft(p)) { return false; }
			if(p.isKing() && redKingCaptureRight(p)) { return false; }
		}
		return true;
	}


	/**
	 * @param p
	 * @return true if a black piece can jump a red piece diagonally left
	 */

	public boolean blackCaptureLeft(Piece p) {
		// if it goes off the board at any point, return false
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
		// if the adjacent left piece is black, return false
		if(board[p.x + 1][p.y - 1].toLowerCase().equals("b")) { return false; }
		// if the adjacent piece is red, but the square to jump in is blocked, return false
		if( (board[p.x + 1][p.y - 1].toLowerCase().equals("r"))
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
	public boolean blackCaptureRight(Piece p) {
		// if it goes off the board, return false
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
		// if adjacent piece is black, return false
		if(board[p.x + 1][p.y + 1].toLowerCase().equals("b")) { return false; }
		// if the adjacent piece is red, but the square to jump in is blocked, return false
		if( (board[p.x + 1][p.y + 1].toLowerCase().equals("r"))
				&& (!board[p.x + 2][p.y + 2].equals(empty)) ) {
			return false;
		}
		// if no adjacent piece, return false
		if(board[p.x + 1][p.y + 1].equals(empty)) { return false; }
		// all else fails
		return true;
	}

	public boolean blackKingCaptureLeft(Piece p) {
		// if it goes off the board at any point, return false
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x - 1 < 0 || p.x - 2 < 0) { return false; }
		// if the adjacent left piece is red, return false
		if(board[p.x - 1][p.y - 1].toLowerCase().equals("b")) { return false; }
		// if the adjacent piece is black, but the square to jump in is blocked, return false
		if( (board[p.x - 1][p.y - 1].toLowerCase().equals("r"))
				&& (!board[p.x - 2][p.y - 2].equals(empty)) ) {
				return false;
		}
		// if there is no adjacent piece return false
		if(board[p.x - 1][p.y - 1].equals(empty)) { return false; }
		// if all the other cases fail
		return true;
	}

	public boolean blackKingCaptureRight(Piece p) {
		// if it goes off the board, return false
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7) { return false; }
		// if adjacent piece is black, return false
		if(board[p.x + 1][p.y + 1].toLowerCase().equals("b")) { return false; }
		// if the adjacent piece is red, but the square to jump in is blocked, return false
		if( (board[p.x + 1][p.y + 1].toLowerCase().equals("r"))
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
	public boolean blackCannotCapture() {
		for(Piece p : blackPieces) {
			if(blackCaptureLeft(p)){ return false; }
			if(blackCaptureRight(p)){ return false; }
			if(p.isKing() && blackKingCaptureLeft(p)) { return false; }
			if(p.isKing() && blackKingCaptureRight(p)) { return false; }
		}
		return true;
	}

	private void setCanCapture() {
		if ( redsTurn ) { canCapture = !redCannotCapture(); }
		else { canCapture = !blackCannotCapture(); }
	}

	// crowing a piece ends the turn, even if there are possible jumps
	// always check if a piece needs to be crowned?
	public void kingRed(Piece p) {
		if(p.x == 0 && (p.y >= 0 && p.y < DEF_HEIGHT)) { 
			p.isKing = true;
			board[p.x][p.y] = "R"; }
		redsTurn = false;
	}

	public void kingBlack(Piece p) {
		if(p.x == 7 && (p.y >= 0 && p.y < DEF_HEIGHT)) { 
			p.isKing = true;
			board[p.x][p.y] = "B"; }
		redsTurn = true;
	}

	/**
	 * RED PIECES ARE MOVING UP THE BOARD!
	 */

	public boolean moveRedLeft(Piece p) {
		// if it goes off the board at any point, return false
		if(p.y - 1 < 0 || p.x - 1 < 0) { return false; }

		// if there is no adjacent piece return false
		return (board[p.x - 1][p.y - 1].equals(empty));
	}

	public boolean moveRedRight(Piece p) {
		if(p.y + 1 > 7 || p.x - 1 < 0){ return false; }
		return(board[p.x - 1][p.y + 1].equals(empty));
	}

	/**
	 * BLACK PIECES ARE MOVING DOWN THE BOARD!
	 */
	public boolean moveBlackLeft(Piece p) {
		// if blackJumpLeft returns true, make the jump
		if(p.y - 1 < 0 || p.x + 1 > 7) { return false; }
		return(board[p.x + 1][p.y - 1].equals(empty));
	}

	public boolean moveBlackRight(Piece p) {
		// if blackJumpRight returns true, make the jump
		if(p.y + 1 > 7 || p.x + 1 > 7) { return false; }
		return(board[p.x + 1][p.y + 1].equals(empty));
	}

	// Red = -1, Black = +1, left = -1, right = +1
	public String[][] makeMove(Piece p, String[][] cur, String direction, String pieceColor) {
		String[][] newBoard = copyBoard(cur);
		int dir = (direction.equals("left") ? -1 : 1);
		int color = (pieceColor.equals("red") ? -1 : 1);
		newBoard[p.x + 1*color][p.y + 1 * dir] = cur[p.x][p.y];
		newBoard[p.x][p.y] = "#";
		return newBoard;
	}

	// Red = -1, Black = +1, left = -1, right = +1
	public String[][] makeCapture(Piece p, String[][] cur, String direction, String pieceColor) {
		String[][] newBoard = copyBoard(cur);
		int dir = (direction.equals("left") ? -1 : 1);
		int color = (pieceColor.equals("red") ? -1 : 1);

		newBoard[p.x + 2*color][p.y + 2*dir] = cur[p.x][p.y];
		newBoard[p.x][p.y] = "#";
		newBoard[p.x + 1*color][p.y + 1*dir] = "#";
		return newBoard;
	}

	public ArrayList<Board> possibleMoves() {

		ArrayList<Board> children = new ArrayList<Board>();
		String[][] newBoard;
		// need ArrayLists for black and red pieces that will be used to create new boards

		// red's turn
		if (redsTurn) {
			// if redCannotCapture, then just add all the board states with possible red moves
			if (!canCapture) {
				for (Piece piece : redPieces) {
					if (moveRedLeft(piece)) {
						newBoard = makeMove(piece, board, "left", "red");
						/*copyBoard(board);
						newBoard[piece.x - 1][piece.y - 1] = board[piece.x][piece.y];
						newBoard[piece.x][piece.y] = "#"; */
						children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
					} 
					if (moveRedRight(piece)) {
						newBoard = makeMove(piece, board, "right", "red");
						children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
					}
					if (piece.isKing()) {
						if(moveBlackLeft(piece)) {
							newBoard = makeMove(piece, board, "left", "black");
							children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
						}
						if(moveBlackRight(piece)) {
							newBoard = makeMove(piece, board, "right", "black");
							children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
						}
					}

				}
				

			} else {
				// TODO: Needs to handle multijumps
				for (Piece piece : redPieces) {
					if (redCaptureLeft(piece)) {
						newBoard = makeCapture(piece, board, "left", "red");
						redMultiJump(piece, new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
						/*Board newState = new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
						piece = new Piece(piece.x - 2, piece.y - 2);
						while (newState.redCaptureLeft(piece) || newState.redCaptureRight(piece)) {
							piece = new Piece(piece.x - 2, piece.y - 2);
							if (newState.redCaptureLeft(piece)) {
								newBoard = makeCapture(piece, newState.board(), "left", "red");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							} else if (newState.redCaptureRight(piece)) {
								newBoard = makeCapture(piece, newState.board(), "right", "red");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							}
						}
						children.add(newState);*/
					} 
					if (redCaptureRight(piece)) {
						newBoard = makeCapture(piece, board, "right", "red");
						redMultiJump(piece, new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
						/*Board newState = new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
						while (!newState.redCannotCapture()) {
							piece = new Piece(piece.x - 2, piece.y + 2);
							if (newState.redCaptureLeft(piece)) {
								newBoard = makeCapture(piece, newState.board(), "left", "red");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							} else if (newState.redCaptureRight(piece)) {
								newBoard = makeCapture(piece, newState.board(), "right", "red");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							}
						}
						children.add(newState);*/
					}
				}
			}
		} else {
			if (!canCapture) {
				for (Piece piece : blackPieces) {
					if (moveBlackLeft(piece)) {
						newBoard = makeMove(piece, board, "left", "black");
						children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
					} 
					if (moveBlackRight(piece)) {
						newBoard = makeMove(piece, board, "right", "black");
						children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
					}
					if (piece.isKing()) {
						if (moveRedLeft(piece)) {
							newBoard = makeMove(piece, board, "left", "red");
							children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
						} 
						if (moveRedRight(piece)) {
							newBoard = makeMove(piece, board, "right", "red");
							children.add(new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn));
						}
					}
				}   
			} else {
				for (Piece piece : blackPieces) {
					if (blackCaptureLeft(piece)) {
						newBoard = makeCapture(piece, board, "left", "black");
						blackMultiJump(piece, new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
						/*Board newState = new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
						while (!newState.blackCannotCapture()) {
							piece = new Piece(piece.x + 2, piece.y - 2);
							if (newState.blackCaptureLeft(piece)) {
								newBoard = makeCapture(piece, newState.board(), "left", "black");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							} else if (newState.blackCaptureRight(piece)) {
								newBoard = makeCapture(piece, newState.board(), "right", "black");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							}
						}
						children.add(newState);*/
					}
					if (blackCaptureRight(piece)) {
						newBoard = makeCapture(piece, board, "right", "black");
						blackMultiJump(piece, new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
						/*Board newState = new Board(this, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
						while (!newState.blackCannotCapture()) {
							piece = new Piece(piece.x + 2, piece.y + 2);
							if (newState.blackCaptureLeft(piece)) {
								newBoard = makeCapture(piece, newState.board(), "left", "black");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							} else if (newState.blackCaptureRight(piece)) {
								newBoard = makeCapture(piece, newState.board(), "right", "black");
								newState = new Board(newState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn);
							}
						}
						children.add(newState);*/
					}
				}
			}
		}
		return children;
	}

	
	private String[][] copyBoard(String[][] b) {
	String [][] newBoard = new String[8][8];
	for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
		newBoard[i][j] = b[i][j];
		}
	}
	return newBoard;
	}


    private ArrayList<Piece> buildRedList (String[][] b) {
	    ArrayList<Piece> redList = new ArrayList<Piece>();
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (b[i][j].toLowerCase().equals("r")) {
		            redList.add(new Piece(i, j));
		        }
	        }
	    }
	    return redList;
    }

    private ArrayList<Piece> buildBlackList (String[][] b) {
	    ArrayList<Piece> blackList = new ArrayList<Piece>();
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
		        if (b[i][j].toLowerCase().equals("b")) {
		            blackList.add(new Piece(i, j));
		        }
	        }
	    }
	    return blackList;
    }

    private void redMultiJump(Piece piece, Board curState, ArrayList<Board> children) {
		if (!curState.redCaptureLeft(piece) && !curState.redCaptureRight(piece)) {
			children.add(curState);
		} else {
			if(curState.redCaptureLeft(piece)) {
				String[][] newBoard = makeCapture(piece, curState.board(), "left", "red");
				redMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
			}	
			if(curState.redCaptureRight(piece)) {
				String[][] newBoard = makeCapture(piece, curState.board(), "right", "red");
				redMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
			}	
			if (piece.isKing()) {
				if (curState.redKingCaptureLeft(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "left", "black");
					redMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
				}
				if (curState.redKingCaptureRight(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "right", "black");
					redMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
				}
			}
		}
	}

	private void blackMultiJump(Piece piece, Board curState, ArrayList<Board> children) {
		if (!curState.blackCaptureLeft(piece) && !curState.blackCaptureRight(piece)) {
			children.add(curState);
		} else {
			if(curState.blackCaptureLeft(piece)) {
				String[][] newBoard = makeCapture(piece, curState.board(), "left", "black");
				blackMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
			}	
			if(curState.blackCaptureRight(piece)) {
				String[][] newBoard = makeCapture(piece, curState.board(), "right", "black");
				blackMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
			}	
			if (piece.isKing()) {
				if (curState.blackKingCaptureLeft(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "left", "red");
					blackMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
				}
				if (curState.blackKingCaptureRight(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "right", "red");
					blackMultiJump(piece, new Board(curState, newBoard, buildBlackList(newBoard), buildRedList(newBoard), !redsTurn), children);
				}
			}
		}
	}

		/**
		 * TESTING
		 */

	public void printBoard() {
		for(int i = 0; i < DEF_HEIGHT; i++) {
			for(int j = 0; j < DEF_WIDTH; j++) {
				System.out.print(board[i][j] + "  ");
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < DEF_HEIGHT; i++) {
			for(int j = 0; j < DEF_WIDTH; j++) {
				if (board[i][j] == null) str += "X ";
				else str += (board[i][j] + " ");
			}
			str += "\n";
		}
		return str;
	}

	public int compareTo(Board board) {
		return Evaluator.evaluate(board).compareTo(Evaluator.evaluate(this));
	}

	public static void main(String[] args) {

	    /**
		ArrayList<Piece> reds = new ArrayList<Piece>();
		ArrayList<Piece> blacks = new ArrayList<Piece>();

		Board a = new Board(null, new String[8][8], blacks,reds, true);
		for(int i = 0; i < 3; i += 2){
			for(int j = 0; j < a.board[0].length; j += 2 ) {
				blacks.add(new Piece(i,j));
			}
		}

		for(int i = 1; i < a.board[0].length; i += 2) {
			blacks.add(new Piece(1,i));
		}

		for(int i = 5; i < 8; i += 2){
			for(int j = 1; j < a.board[0].length; j += 2 ) {
				reds.add(new Piece(i,j));
			}
		}

		for(int i = 0; i < 8; i += 2) {
			reds.add(new Piece(6,i));
		}

		a.setBlackPieces(blacks);
		a.setRedPieces(reds);
		a.setInvalidSpaces();
		a.noNull();
		a.printBoard();
		ArrayList<Board> adjacent = a.possibleMoves();

		for (Board b : adjacent) {
			b.printBoard();
		}  
		System.out.println(a.redCannotCapture());
		System.out.println(a.blackCannotCapture());
         **/


		ArrayList<Piece> blacks = new ArrayList<Piece>();
		ArrayList<Piece> reds = new ArrayList<Piece>();
		Board b = new Board(null, new String[8][8], blacks, reds, true);

		blacks.add (new Piece (1, 2));
		blacks.add (new Piece (3, 0));
		blacks.add (new Piece (7, 2));
		blacks.add (new Piece (6, 7));



		reds.add (new Piece (1, 0));
		reds.add (new Piece (0, 3));
		reds.add (new Piece (1, 6));
		reds.add (new Piece (2, 5));
		reds.add (new Piece (7, 0));
		reds.add (new Piece (7, 4));
		reds.add (new Piece (4, 7));


		b.setBlackPieces(blacks);
		b.setRedPieces(reds);
		b.setInvalidSpaces();
		b.noNull();
		b.kingRed(reds.get(1));
		b.kingRed(reds.get(2));
		b.kingBlack(blacks.get(2));
		b.printBoard();

	}

}
