import java.util.ArrayList;
import java.util.List;

/**
 * Board.java
 * Represents the Checkers board
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
	List<Piece> blackPieces = new ArrayList<>();

	// ArrayList<Pieces>
	List<Piece> redPieces = new ArrayList<>();

	String[][] board = new String[DEF_HEIGHT][DEF_WIDTH];

	// true if it's red's true
	public boolean redsTurn;

	Board parent;

	// can a piece be captured in this board?
	public boolean canCapture = false;


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
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x - 1 < 0 || p.x - 2 < 0 || p.x > 7 || p.y > 7) { return false; }
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
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x - 1 < 0 || p.x - 2 < 0 || p.x > 7 || p.y > 7) { return false; }
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
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x + 1 > 7 || p.x + 2 > 7 || p.x > 7 || p.y > 7) { return false; }
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
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7 || p.x > 7 || p.y > 7) { return false; }
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
		boolean toReturn = true;
		for(Piece p : redPieces) {
			if(redCaptureLeft(p)){ toReturn = false; }
			if(redCaptureRight(p)){ toReturn = false; }
			if(p.isKing() && redKingCaptureLeft(p)) { toReturn = false; }
			if(p.isKing() && redKingCaptureRight(p)) { toReturn = false; }
		}
		return toReturn;
	}


	/**
	 * @param p
	 * @return true if a black piece can jump a red piece diagonally left
	 */

	public boolean blackCaptureLeft(Piece p) {
		// if it goes off the board at any point, return false
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x + 1 > 7 || p.x + 2 > 7 || p.x > 7 || p.y > 7) { return false; }
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
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7 || p.x > 7 || p.y > 7) { return false; }
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
		if(p.y - 1 < 0 || p.y - 2 < 0 || p.x - 1 < 0 || p.x - 2 < 0 || p.x > 7 || p.y > 7) { return false; }
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
		if(p.y + 1 > 7 || p.y + 2 > 7 || p.x + 1 > 7 || p.x + 2 > 7 || p.x > 7 || p.y > 7) { return false; }
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
	public void kingRed() {
		for (Piece p : redPieces) {
			if(p.x == 0 && (p.y >= 0 && p.y < DEF_HEIGHT)) { 
				setRedKing(p); 
			}
		}
		//redsTurn = false;
	}

	private void setRedKing(Piece p) {
		p.isKing = true;
		board[p.x][p.y] = "R";
	}

	public void kingBlack() {
		for (Piece p : blackPieces) {
			if(p.x == 7 && (p.y >= 0 && p.y < DEF_HEIGHT)) { 
				setBlackKing(p);	
			}
		}
		//redsTurn = true;
	}

	private void setBlackKing(Piece p) {
		p.isKing = true;
		board[p.x][p.y] = "B";
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
		newBoard[p.x + color][p.y + dir] = cur[p.x][p.y];
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
		newBoard[p.x + color][p.y + dir] = "#";
		return newBoard;
	}

    /**
     * param: board
     * @return whether there are no red or black pieces on the board: game over!
     */
    public boolean gameOver() {
        return (redPieces.isEmpty() || blackPieces.isEmpty());
    }


	public ArrayList<Board> possibleMoves() {

		ArrayList<Board> children = new ArrayList<Board>();
		String[][] newBoard;

		// red's turn
		setCanCapture();
		if (redsTurn) {
			// if redCannotCapture, then just add all the board states with possible red moves
			if (!canCapture) {
				for (Piece piece : redPieces) {
					if (moveRedLeft(piece)) {
						newBoard = makeMove(piece, board, "left", "red");
						children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
					} 
					if (moveRedRight(piece)) {
						newBoard = makeMove(piece, board, "right", "red");
						children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
					}
					if (piece.isKing()) {
						if(moveBlackLeft(piece)) {
							newBoard = makeMove(piece, board, "left", "black");
							children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
						}
						if(moveBlackRight(piece)) {
							newBoard = makeMove(piece, board, "right", "black");
							children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
						}
					}

				}
				

			} else {
				for (Piece piece : redPieces) {
					if (redCaptureLeft(piece)) {
						newBoard = makeCapture(piece, board, "left", "red");
						redMultiJump(new Piece(piece.x - 2, piece.y - 2), new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
					} 
					if (redCaptureRight(piece)) {
						newBoard = makeCapture(piece, board, "right", "red");
						Board t = new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn);
						redMultiJump(new Piece(piece.x - 2, piece.y + 2), new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
					}
				}	
			}
		} else {
			if (!canCapture) {
				for (Piece piece : blackPieces) {
					if (moveBlackLeft(piece)) {
						newBoard = makeMove(piece, board, "left", "black");
						children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
					} 
					if (moveBlackRight(piece)) {
						newBoard = makeMove(piece, board, "right", "black");
						children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
					}
					if (piece.isKing()) {
						if (moveRedLeft(piece)) {
							newBoard = makeMove(piece, board, "left", "red");
							children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
						} 
						if (moveRedRight(piece)) {
							newBoard = makeMove(piece, board, "right", "red");
							children.add(new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn));
						}
					}
				}   
			} else {
				for (Piece piece : blackPieces) {
					if (blackCaptureLeft(piece)) {
						newBoard = makeCapture(piece, board, "left", "black");
						blackMultiJump(new Piece(piece.x + 2, piece.y - 2), new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
					}
					if (blackCaptureRight(piece)) {
						newBoard = makeCapture(piece, board, "right", "black");
						blackMultiJump(new Piece(piece.x + 2, piece.y + 2), new Board(this, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
					}
				}
			}
		}
		return children;
	}

	
	public String[][] copyBoard(String[][] b) {
		String [][] newBoard = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
			newBoard[i][j] = b[i][j];
			}
		}
		return newBoard;
	}


    public static ArrayList<Piece> buildRedList (String[][] b) {
	    ArrayList<Piece> redList = new ArrayList<>();
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (b[i][j].toLowerCase().equals("r")) {
		            redList.add(new Piece(i, j));
		        }
	        }
	    }
	    return redList;
    }

    public static ArrayList<Piece> buildBlackList (String[][] b) {
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
				redMultiJump(new Piece (piece.x - 2, piece.y - 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
			}	
			if(curState.redCaptureRight(piece)) {
				String[][] newBoard = makeCapture(piece, curState.board(), "right", "red");
				redMultiJump(new Piece (piece.x - 2, piece.y + 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
			}	
			if (piece.isKing()) {
				if (curState.redKingCaptureLeft(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "left", "black");
					redMultiJump(new Piece (piece.x + 2, piece.y - 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
				}
				if (curState.redKingCaptureRight(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "right", "black");
					redMultiJump(new Piece (piece.x + 2, piece.y + 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
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
				blackMultiJump(new Piece (piece.x + 2, piece.y - 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
			}	
			if(curState.blackCaptureRight(piece)) {
				String[][] newBoard = makeCapture(piece, curState.board(), "right", "black");
				blackMultiJump(new Piece (piece.x + 2, piece.y + 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
			}	
			if (piece.isKing()) {
				if (curState.blackKingCaptureLeft(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "left", "red");
					blackMultiJump(new Piece (piece.x - 2, piece.y - 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
				}
				if (curState.blackKingCaptureRight(piece)) {
					String[][] newBoard = makeCapture(piece, curState.board(), "right", "red");
					blackMultiJump(new Piece (piece.x - 2, piece.y + 2), new Board(curState, newBoard, Board.buildBlackList(newBoard), Board.buildRedList(newBoard), !redsTurn), children);
				}
			}
		}
	}

		/**
		 * TESTING
		 */

	public void printBoard() {
		System.out.println("     0  1  2  3  4  5  6  7   ");
		System.out.println("    ==========================");
		for(int i = 0; i < DEF_HEIGHT; i++) {
			System.out.print(i + "  | ");
			for(int j = 0; j < DEF_WIDTH; j++) {
				System.out.print(board[i][j] + "  ");
			}
			System.out.print(" |");
			System.out.println();
		}
		System.out.println("    ==========================");
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

	    

	}
}
