import java.util.*;

public class Solution{
    private static final int[] DR = {1, -1, 0, 0};
    private static final int[] DC = {0, 0, 1, -1};

    public int totalScore(String[][] board){
        int rows = board.length;
        int cols = board[0].length;

        boolean[][] visited = new boolean[rows][cols];

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(!visited[r][c]){
                    int[] res = dfs(board, visited, r, c, board[r][c].charAt(0));
                    int cells = res[0];
                    int crowns = res[1];
                    total+= cells*crowns;
                }
            }
        }
    return total;
    }

    private int dfs(String[][] board, boolean[][] visited, int r, int c, char terrain){

        int rows = board.length;
        int cols = board[0].length;

        if(r < 0 || r >= rows || c < 0 || c >= cols) return new int[]{0, 0};
        if(visited[r][c]) return new int[]{0, 0};
        if (board[r][c].charAt(0) != terrain) return new int[]{0, 0};

        visited[r][c] = true;
        int cells = 1;

        int crowns = board[r][c].charAt(1)-'0';

        for(int i = 0; i < 4; i++){
            int nr = r+DR[i];
            int nc = c+DC[i];
            int[] next = dfs(board, visited, nr, nc, terrain);
            cells+=next[0];
            crowns+=next[1];
        }
        return new int[]{cells, crowns};
    }
}