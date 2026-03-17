class Solution{
    public int[] findOrder(int numCourses, int[][] prerequisites){

        List<List<Integer>>adj = new ArrayList<>();
        for(int i = 0; i < numCourses; i++){
            adj.add(new ArrayList<>());
        }
        for(int[] pre : prerequisites){
            int u = pre[0];
            int v = pre[1];
            adj.get(v).add(u);
        }
        int[] indegree = new int[numCourses];
        for(int i = 0; i < numCourses; i++){
            for(int j : adj.get(i)){
                indegree[j]++;
            }
        }
        Queue<Integer>queue = new LinkedList<>();
        for(int i = 0; i < indegree.length(); i++){
            if(indegree[i] == 0){
                queue.add(i);
            }
        }
        int[] ans = new int[numCourses];
        int idx = 0;
        while(!queue.isEmpty()){
            int u = queue.poll();
            ans[idx++] = u;
            for(int v : adj.get(u)){
                indegree[v]--;
                if(indegree[v] == 0) queue.add(v);
            }
        }
        if(idx != numCourses) return new int[0];
        return ans;
    }
}