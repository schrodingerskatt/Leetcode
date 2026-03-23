import java.util.*;

public class ConnectFour {

    private static final int ROWS = 6;
    private static final int COLS = 7;

    public static void main(String[] args) {
        int[] moves = {2,2,2,2,2,2,2,2,2,2,2};
        char[][] result = playGame(moves);
        printBoard(result);
    }

    public static char[][] playGame(int[] moves) {
        char[][] board = new char[ROWS][COLS];
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }

        char currentPlayer = 'O';

        for (int move : moves) {

            if (move < 0 || move >= COLS) break;

            int row = dropToken(board, move, currentPlayer);
            if (row == -1) break;

            if (isWinningMove(board, row, move, currentPlayer)) {
                System.out.println("Winner: " + currentPlayer);
                break;
            }

            currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
        }

        return board;
    }

    private static int dropToken(char[][] board, int col, char player) {
        for (int r = ROWS - 1; r >= 0; r--) {
            if (board[r][col] == ' ') {
                board[r][col] = player;
                return r;
            }
        }
        return -1;
    }

    private static boolean isWinningMove(char[][] board, int r, int c, char player) {

        int[][] directions = {
                {0, 1},
                {1, 0},
                {1, 1},
                {-1, 1}
        };

        for (int[] dir : directions) {
            int dr = dir[0], dc = dir[1];

            int count = 1;
            count += countDirection(board, r, c, dr, dc, player);
            count += countDirection(board, r, c, -dr, -dc, player);

            if (count >= 4) return true;
        }

        return false;
    }

    private static int countDirection(char[][] board, int r, int c, int dr, int dc, char player) {
        int count = 0;

        r += dr;
        c += dc;

        while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == player) {
            count++;
            r += dr;
            c += dc;
        }

        return count;
    }

    private static void printBoard(char[][] board) {
        for (int r = 0; r < ROWS; r++) {
            System.out.print("|");
            for (int c = 0; c < COLS; c++) {
                System.out.print(board[r][c] + "|");
            }
            System.out.println();
        }
    }
}
