import java.util.*;

public class ShortestPathAllKeys {

    static class State {
        int r, c, keys, steps;

        State(int r, int c, int keys, int steps) {
            this.r = r;
            this.c = c;
            this.keys = keys;
            this.steps = steps;
        }
    }

    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();

        int startR = 0, startC = 0;
        int totalKeys = 0;

        // 🔍 Find start and count keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = grid[i].charAt(j);
                if (ch == '@') {
                    startR = i;
                    startC = j;
                } else if (ch >= 'a' && ch <= 'f') {
                    totalKeys = Math.max(totalKeys, ch - 'a' + 1);
                }
            }
        }

        int allKeysMask = (1 << totalKeys) - 1;

        // visited[r][c][keysMask]
        boolean[][][] visited = new boolean[m][n][1 << totalKeys];

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startR, startC, 0, 0));
        visited[startR][startC][0] = true;

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            int r = curr.r, c = curr.c, keys = curr.keys, steps = curr.steps;

            // All keys collected
            if (keys == allKeysMask) return steps;

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;

                char ch = grid[nr].charAt(nc);

                // Wall
                if (ch == '#') continue;

                int newKeys = keys;

                // Door
                if (ch >= 'A' && ch <= 'F') {
                    int keyBit = ch - 'A';
                    if ((keys & (1 << keyBit)) == 0) continue;
                }

                // Key
                if (ch >= 'a' && ch <= 'f') {
                    int keyBit = ch - 'a';
                    newKeys = keys | (1 << keyBit);
                }

                if (!visited[nr][nc][newKeys]) {
                    visited[nr][nc][newKeys] = true;
                    queue.offer(new State(nr, nc, newKeys, steps + 1));
                }
            }
        }

        return -1;
    }
    public static void main(String[] args) {
        ShortestPathAllKeys sol = new ShortestPathAllKeys();

        String[] grid = {
            "@.a.#",
            "###.#",
            "b.A.B"
        };

        System.out.println(sol.shortestPathAllKeys(grid)); // Expected: 8
    }
}