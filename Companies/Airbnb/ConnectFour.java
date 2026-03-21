import java.util.*;
class Solution{

private static final int ROWS = 6;
private static final int COLS = 7;
private static final int[][] DIRECTIONS = {
        {0, 1},   // horizontal
        {1, 0},   // vertical
        {1, 1},   // diagonal ↘
        {-1, 1}   // diagonal ↗
};

private static void main(String[] args){
    int[] moves = {2,2,2,2,2,2,2,2,2,2,2};
    char[][] result = playGame(moves);
    printBoard(result);
}

public static char[][] playGame(int[] moves){
    char[][] board = new char[ROWS][COLS];
    for(char[] row : board){
        Arrays.fill(row, ' ');
    }
    char currentPlayer = 'O';
    for(int move : moves){

        if(move < 0 || move >= COLS) break;
        int row = dropToken(board, move, currentPlayer);
        if(row == -1) break;

        if(isWiningMove(board, row, move, currentPlayer)) break;
        currentPlayer = (currentPlayer == '0') ? 'X':'O';
    }
    return board;
}

private static int dropToken(char[][] board, int col, char player){
    for(int r = ROWS-1; r >= 0; r--){
        if(board[r][c] == ' '){
            board[r][c] = player;
            return r;
        }
    }
    return -1;
}

private static boolean isWinningMove(char[][] board, int r, int c, char player){

    int[][] directions  = {
        {0, 1},   // horizontal
        {1, 0},   // vertical
        {1, 1},   // diagonal ↘
        {-1, 1}   // diagonal ↗
    };

    for(int[] dir : directions){
        int dr = dir[0], dc = dir[1];
        int count = 1; // current cell

        // forward
        int forward = count(board, r, c, dr, dc, player);
        int backward = count(board, r, c, -dr, -dc, player);
        count+=forward+backward;
        if(count >= 4) return true;

        return false;
    }
}

private static void printBoard(char[][] board){
    for(int r = 0; r < ROWS; r++){
        System.out.print("|");
    for(int c = 0; c < COLS; c++){
        System.out.print(board[r][c]+ "|");
    }
    System.out.println();
    }
}
}