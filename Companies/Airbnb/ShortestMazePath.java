import java.util.*;

class Solution{

    static int ShortestPath(char[][] maze, int[] start, int[] end){

        int m = maze.length;
        int n = maze[0].length;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        Queue<int[]>q = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        q.offer(new int[]{start[0], start[1], 0});
        visited[start[0]][start[1]] = true;

        while(!queue.isEmpty()){
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], steps = curr[2];
            if(r == end[0] && c == end[1]){
                return steps;
            }
            for(int[] d : directions){
                int nr = r+d[0];
                int nc = c+d[1];
                if(nr >= 0 && nr < m && nc >= 0 && nc < n && maze[nr][nc] == 'O' && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, steps+1});
                }
            }
        }
        return -1;
    }
}