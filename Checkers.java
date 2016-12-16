import java.util.ArrayList;
import java.util.Scanner;

public class Checkers {
    static Board board;
    static ArrayList<Piece> redPieces;
    static ArrayList<Piece> blackPieces;
    public boolean reset;
  //  static Minimax minimax;

    public Checkers() {
        redPieces = getRedPositions();
        blackPieces = getBlackPositions();
        board = new Board(null, new String[8][8], blackPieces, redPieces, true);
        board.setBlackPieces(blackPieces);
        board.setRedPieces(redPieces);
        board.setInvalidSpaces();
        board.noNull();

       // minimax = new MiniMax(board);
        //ystem.out.println(miniMax.getBestMove());

    }

    private static ArrayList<Piece> getRedPositions() {
        ArrayList<Piece> result = new ArrayList<>();
        for (int row = 5; row < 8; row += 2) {
            for (int col = 0; col < 8; col += 2) {
                result.add(new Piece(row, col));
            }
        }

        for (int col = 1; col < 8; col += 2) {
            result.add(new Piece(6, col));
        }

        return result;
    }

    private static ArrayList<Piece> getBlackPositions() {
        ArrayList<Piece> result = new ArrayList<>();
        for (int row = 0; row < 3; row += 2) {
            for (int col = 1; col < 8; col += 2) {
                result.add(new Piece(row, col));
            }
        }

        for (int col = 0; col < 8; col += 2) {
            result.add(new Piece(1, col));
        }

        return result;
    }

    public static void main(String[] args) {
        Checkers checkers = new Checkers();
        boolean gameOver = false;
        boolean redTurn = true;
        Scanner s = new Scanner(System.in);
        MiniMax minimax = new MiniMax(board);
        int i = 0;
        System.out.println("Let's play Checkers! You go first.");
        while (!board.gameOver()) {
            board.printBoard();
            if (redTurn) {
                Pair<Integer,Integer> pieceCoor;
                Pair<Integer,Integer> destCoor;
                Piece p;
                board.kingRed();
                board.kingBlack();

                System.out.println("Which piece would you like to move?");
                pieceCoor = checkers.getCoor(s, checkers);
                checkers.reset = false;
                do {
                    if (checkers.reset) { 
                        pieceCoor = new Pair<Integer,Integer>(-1, -1);
                        checkers.reset = false;
                    }
                    while (!checkers.validPiece(pieceCoor.x, pieceCoor.y)) {
                        System.out.println("Please select a valid piece: ");
                        pieceCoor = checkers.getCoor(s, checkers);
                    }
                    p = new Piece (pieceCoor.x, pieceCoor.y);
                    if (board.board()[pieceCoor.x][pieceCoor.y].equals("R")) {
                        p.isKing = true;
                    }

                    System.out.println("And where would you like to move it?");
                    destCoor = checkers.getCoor(s, checkers);
                    while (!checkers.reset && !checkers.validMove(p, destCoor.x, destCoor.y)) {
                        System.out.println("Please enter a valid move: ");  
                        destCoor = checkers.getCoor(s, checkers);     
                    } 
                } while (checkers.reset);   
                System.out.println(checkers.reset);

                String dir = "";
                String color = "";
                String[][] nextBoard;

                if (p.y - destCoor.y > 0) {
                    dir = "left";
                } else {
                    dir = "right";
                }

                if (p.x - destCoor.x > 0) {
                    color = "red";
                } else {
                    color = "black";
                }

                if (p.y - destCoor.y == 1 || p.y - destCoor.y == -1) {
                    nextBoard = board.makeMove(p, board.board(), dir, color);
                } else {
                    nextBoard = board.makeCapture(p, board.board(), dir, color);
                }
                board = new Board(board, nextBoard, Board.buildBlackList(nextBoard), Board.buildRedList(nextBoard), !redTurn);
            } else {
                minimax = new MiniMax(board);
                board = minimax.getBestMove();
            }
            redTurn = !redTurn;
            gameOver = board.gameOver();
        }
        System.out.println("Game Over!");
    }

    private boolean validPiece(int r, int c) {
        return (r >= 0 && r < 8) && (c >= 0 && c < 8) && board.board()[r][c].toLowerCase().equals("r");
    }

    private Pair<Integer, Integer> getCoor(Scanner s, Checkers checkers) {
        System.out.print("Row: ");
        String next = s.next();
        int x = -1;
        if (next.equals("reset")) { 
            checkers.reset = true; 
        } else {
            try {
                x = Integer.parseInt(next);
            } catch (NumberFormatException e) {
                x = -1;
            }
        }
        System.out.print("Column: ");
        next = s.next();
        int y = -1;
        if (next.equals("reset")) { 
            checkers.reset = true;
        } else {
            try {
                y = Integer.parseInt(next);
            } catch (NumberFormatException e) {
                y = -1;
            }
        }
        return new Pair<Integer, Integer>(x, y);
    }

    private boolean validMove(Piece p, int dr, int dc) {
        boolean toReturn = false;
        if (p.x - dr == 1) {
            // Move red piece left or right
            if (p.y - dc == 1) {
                toReturn = board.moveRedLeft(p) && !board.canCapture;
            } else if (p.y - dc == -1){
                toReturn = board.moveRedRight(p) && !board.canCapture;
            }
        } else if (p.x - dr == 2) {
            // Capture left or right
            if (p.y - dc == 2) {
                toReturn = board.redCaptureLeft(p);
            } else if (p.y - dc == -2) {
                toReturn = board.redCaptureRight(p);
            }
        } else if (p.x - dr == -1 && p.isKing()) {
            // king movement left or right
            if (p.y - dc == 1) {
                toReturn = board.moveBlackLeft(p) && !board.canCapture;
            } else if (p.y - dc == -1) {
                toReturn = board.moveBlackRight(p) && !board.canCapture;
            }
        } else if (p.x - dr == -2 && p.isKing()) {
            // king capture left or right
            if (p.y - dc == 2) {
                toReturn = board.redKingCaptureLeft(p);
            } else if (p.y - dc == -2) {
                toReturn = board.redKingCaptureRight(p);
            }
        }
        return toReturn && dr != -1 && dc != -1;
        
    }

}