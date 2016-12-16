import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class Checkers {
    Board board;
    ArrayList<Piece> redPieces;
    ArrayList<Piece> blackPieces;

    public Checkers() {
        redPieces = getRedPositions();
        blackPieces = getBlackPositions();
        board = new Board(null, new String[8][8], blackPieces, redPieces, true);
        board.setBlackPieces(blackPieces);
        board.setRedPieces(redPieces);
        board.setInvalidSpaces();
        board.noNull();

        MiniMax miniMax = new MiniMax(board);
        System.out.println(miniMax.getBestMove());

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
        Scanner s = new Scanner(System.in);
        System.out.println("Let's play Checkers! You go first.");
        while (!gameOver) {
            if (redTurn) {
                System.out.println("Which piece would you like to move?");
                System.out.print("Row: ");
                int pr  = s.nextInt();
                System.out.println("Column: ");
                int pc = s.nextInt();
                while (!validPiece(r, c)) {
                    System.out.println("Please select a valid piece: ");
                    System.out.print("Row: ");
                    pr  = s.nextInt();
                    System.out.println("Column: ");
                    pc = s.nextInt();
                }
                Piece p = new Piece (pr, pc);
                if (board.board()[pr][pc].equals("R")) {
                    p.isKing = true;
                }
                System.out.println("And where would you like to move it?");
                System.out.print("Row: ");
                int dr  = s.nextInt();
                System.out.println("Column: ");
                int dc = s.nextInt();
                while (!d.validMove(p, dr, dc)) {
                    System.out.println("Please enter a valid move: ");  
                    System.out.print("Row: ");
                    dr  = s.nextInt();
                    System.out.println("Column: ");
                    dc = s.nextInt();        
                }

                
                makeMove();
                printBoard();
            } else {
                searchMove();
                pri
            }
            redTurn = !redTurn;
            gameOver = 
        }
    }

    private boolean validPiece(int r, int c) {
        return (r >= 0 && r < 8) && (c >= 0 && c < 8) && board.board()[r][c];
    }

    private boolean validMove(Piece p, int dr, int dc) {
        boolean toReturn = false;
        if (p.x - dr == 1) {
            if (p.y - dc == 1) {
                toReturn = moveRedLeft(p);
            } else if (p.y - dc == -1){
                toReturn = moveRedRight(p);
            }
        } else if (p.x - dr == 2) {
            if (p.y - dc == 2) {
                toReturn = redCaptureLeft(p);
            } else if (p.y - dc == -2) {
                toReturn = redCaptureRight(p);
            }
        } else if (p.x - dr == -1 && p.isKing()) {
            if (p.y - dc == 1) {
                toReturn = moveBlackLeft(p);
            } else if (p.y - dc == -1) {
                toReturn = moveBlackRight(p);
            }
        } else if (p.x - dr == -2 && p.isKing()) {
            if (p.y - dc == 2) {
                toReturn = redKingCaptureLeft(p);
            } else if (p.y - dc == -2) {
                toReturn = redKingCaptureRight(p);
            }
        }
        return toReturn;
        
    }

}