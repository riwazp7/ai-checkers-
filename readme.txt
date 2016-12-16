CSCI 373 Final Project: Checkers
(c) 2016 Riwaz Poudyal, Julian Vera, and Marc Talbott


Description
This is an implementation of checkers. The goal of the game is to take all of your opponent's pieces without losing all of your own. Pieces move diagonally towards the other side of the board and can capture an opposing piece when the opposing piece is adjacent and the square behind it is unoccupied. If a piece reaches the other end of the board, it becomes a king and can now move in both directions. If a piece is available to be captured, it must be captured.


Compiling and Running the Program
- Compile Checkers.java using the standard Java compiler ("javac Checkers.java")
- Run the program ("java Checkers")
- The program will prompt you to select a piece by giving it's position on the board. You can only move the red pieces (Shown as "r")
- After you have selected a piece, the program will prompt you to select a square to move it to by giving the appropriate coordinates
- If you select the wrong piece on accident, type "reset" when prompted for a destination to return to piece selection