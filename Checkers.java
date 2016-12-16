import java.util.ArrayList;
import java.util.Scanner;

public class Checkers {
    static Board board;
    static ArrayList<Piece> redPieces;
    static ArrayList<Piece> blackPieces;
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
        while (i < 7) {
            board.printBoard();
            if (redTurn) {
                System.out.println("Which piece would you like to move?");
                System.out.print("Row: ");
                int pr  = s.nextInt();
                System.out.print("Column: ");
                int pc = s.nextInt();
                while (!checkers.validPiece(pr, pc)) {
                    System.out.println("Please select a valid piece: ");
                    System.out.print("Row: ");
                    pr  = s.nextInt();
                    System.out.print("Column: ");
                    pc = s.nextInt();
                }
                Piece p = new Piece (pr, pc);
                if (board.board()[pr][pc].equals("R")) {
                    p.isKing = true;
                }
                System.out.println("And where would you like to move it?");
                System.out.print("Row: ");
                int dr  = s.nextInt();
                System.out.print("Column: ");
                int dc = s.nextInt();
                while (!checkers.validMove(p, dr, dc)) {
                    System.out.println("Please enter a valid move: ");  
                    System.out.print("Row: ");
                    dr  = s.nextInt();
                    System.out.print("Column: ");
                    dc = s.nextInt();        
                }

                String dir = "";
                String color = "";
                String[][] nextBoard;

                if (p.y - dc > 0) {
                    dir = "left";
                } else {
                    dir = "right";
                }

                if (p.x - dr > 0) {
                    color = "red";
                } else {
                    color = "black";
                }

                if (p.y - dc == 1 || p.y - dc == -1) {
                    nextBoard = board.makeMove(p, board.board(), dir, color);
                } else {
                    nextBoard = board.makeCapture(p, board.board(), dir, color);
                }
                board = new Board(board, nextBoard, Board.buildBlackList(nextBoard), Board.buildRedList(nextBoard), !redTurn);
            } else {
                minimax = new MiniMax(board);
                board = minimax.getBestMove();
            }

            board.printBoard();
            redTurn = !redTurn;
            gameOver = board.gameOver();
            i++;
        }
        System.out.println("Game Over!");
    }

    private boolean validPiece(int r, int c) {
        return (r >= 0 && r < 8) && (c >= 0 && c < 8) && board.board()[r][c].toLowerCase().equals("r");
    }

    private boolean validMove(Piece p, int dr, int dc) {
        boolean toReturn = false;
        if (p.x - dr == 1) {
            // Move red piece left or right
            if (p.y - dc == 1) {
                toReturn = board.moveRedLeft(p);
            } else if (p.y - dc == -1){
                toReturn = board.moveRedRight(p);
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
                toReturn = board.moveBlackLeft(p);
            } else if (p.y - dc == -1) {
                toReturn = board.moveBlackRight(p);
            }
        } else if (p.x - dr == -2 && p.isKing()) {
            // king capture left or right
            if (p.y - dc == 2) {
                toReturn = board.redKingCaptureLeft(p);
            } else if (p.y - dc == -2) {
                toReturn = board.redKingCaptureRight(p);
            }
        }
        return toReturn;
        
    }

}