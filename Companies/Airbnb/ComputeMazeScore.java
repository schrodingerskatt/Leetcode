import java.util.*;

public class MazeSolver{

    static class Cell{
        int r, c, dist;
        Cell(int r, int c, int dist){
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    public static int shortestPath(char[][] grid){

        int R = grid.length;
        int C = grid[0].length;
        Queue<Cell>q = new LinkedList<>();
        boolean[][] visited = new boolean[R][C];

        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(grid[i][j] == 'S'){
                    q.offer(new Cell(i, j, 0));
                    visited[i][j] = true;
                }
            }
        }

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while(!q.isEmpty()){
            Cell curr = q.poll();
            if(grid[r][c] == 'E'){
                return curr.dist;
            }

            for(int[] d: dirs){
                int nr = curr.r+d[0];
                int nc = curr.c+d[1];

                if(nr >= 0 && nr < R && nc >= 0 && nc < C && !visited[nr][nc] && grid[nr][nc] != '#'){
                    visited[nr][nc] = true;
                    q.offer(new Cell(nr, nc, curr.dist+1));
                }
            }
        }
        return -1;
    }

    public static void main(String[] args){
        char[][] grid = {
            {'S', '.', '.'},
            {'#', '#', '.'},
            {'.', '.', 'E'}
        };
        System.out.println(shortestPath(grid));
    }
}