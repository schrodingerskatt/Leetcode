class Solution{

    public int KnightMoves(int x, int y){

        x = Math.abs(x);
        y = Math.abs(y);
        int[][] directions = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        Queue<int[]>queue = new LinkedList<>();
        Set<String>visited = new HashSet<>();
        queue.offer(new int{0, 0});
        visited.add("0,0");
        int moves = 0;

        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                if(r == x && c == y) return moves;
                for(int[] dir : directions){
                    int nr = r+dir[0];
                    int nc = c+dir[1];
                    String key = nr+","+nc;
                    if(nr >= -2 && nc >= -2 && !visited.contains(key)){
                        queue.offer(new int[]{nr, nc});
                        visited.add(key);
                    }
                }
            }
        moves++;
        }
    reeturn -1;
    }
}