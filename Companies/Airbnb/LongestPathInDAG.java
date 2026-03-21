import java.util.*;

class Solution{
    
    Static class Edge{
        String to;
        int cost;

        Edge(String to, int cost){
            this.to = to;
            this.cost = cost;
        }
    }

    public static class Result{
        double maxScore;
        List<String> path;

        Result(double maxScore, List<String>path){
            this.maxScore = maxScore;
            this.path = path;
        }
    }

    public Result maxScorePath(Map<String, List<Edge>> graph, Map<String, Integer> weight){

        Map<String, Integer>indegree = new HashMap<>();
        for(String node : graph.keySet()){
            indegree.putIfAbsent(node, 0);
            for(Edge e : graph.get(node)){
                indegree.put(e.to, indegree.getOrDefault(e.to, 0)+1);
            }
        }

        Queue<String>q = new LinkedList<>();
        for(String node : indegree.keySet()){
            if(indegree.get(node) == 0){
                q.offer(node);
            }
        }

        List<String>topo = new ArrayList<>();
        while(!q.isEmpty()){
            String u = q.poll();
            topo.add(u);
            for(Edge e : graph.getOrDefault(u, new ArrayList<>())){
                String v = e.to;
                indegree.put(v, indegree.get(v)-1);
                if(indegree.get(v) == 0){
                    q.offer(v);
                }
            }
        }

        // Step 2: DP initialization

        Map<String, Double>dp = new HashMap<>();
        Map<String, String>parent = new HashMap<>();

        for(String node : graph.keySet()){
            dp.put(node, Double.NEGATIVE_INFINITY);
        }
        dp.put("start", 0.0);

        // Step 3: Relax edges in topo order
        for(String u : topo){
            if(dp.getOrDefault(u, Double.NEGATIVE_INFINITY) == Double.NEGATIVE_INFINITY){
                continue;
            }
            for(Edge e : graph.getOrDefault(u, new ArrayList<>())){
                String v = e.to;
                double newScore = dp.get(u)+weight.getOrDefault(v, 0)-e.cost;
                if(newScore > dp.getOrDefault(v, Double.NEGATIVE_INFINITY)){
                    dp.put(v, newScore);
                    parent.put(v, u);
                }
            }
        }


        // Step 4: Find best terminal node
        double maxScore = Double.NEGATIVE_INFINITY;
        String bestNode = null;

        for(String node : dp.keySet()){
            if(node.startsWith("_") && dp.get(node) > maxScore){
                maxScore = dp.get(node);
                bestNode = node;
            }
        }

        if(bestNode == null){
            return null;
        }

        List<String>path = new ArrayList<>();
        String curr = bestNode;
        while(curr != null){
            path.add(curr);
            curr = parent.get(curr);
        }
        Collections.reverse(path);
        return new Result(maxScore, path);
    }
}

/*
Time Complexity :

Topological Sort : O(V+E)
DP Relaxation : O(E)
Finding Best Terminal Node : O(V)
Path Reconstruction : O(V)

Space Complexity :

Graph Storage : O(V+E)
DP Map : O(V)
Parent Map : O(V)
Indegree Map : O(V)
Topological Order List : O(V)
Queue : O(V)
*/