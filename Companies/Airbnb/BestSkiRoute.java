public class Solution{

    Map<String, List<Pair>>graph = new HashMap<>();
    Map<String, Integer> pointsMap = new HashMap<>();
    Map<String, Integer>memo = new HashMap<>();
    Map<String, String>nextNode = new HashMap<>();

    static class Pair{
        String node;
        int time;

        Pair(String n, int t){
            node = n;
            time = t;
        }
    }

    public int dfs(String node){
        if(memo.containsKey(node)){
            return memo.get(node);
        }
        int best = Integer.MIN_VALUE;
        if(!graph.containsKey(node)){
            return pointsMap.get(node); // this is for end nodes
        }

        for(Pair p : graph.get(node)){
            int gain = pointsMap.get(p.node)-p.time+dfs(p.node);
            if(gain > best){
                best = gain;
                nextNode.put(node, p.node);
            }
        }
    memo.put(node, best);
    return best;
    }
public static void main(String[] args) {

        // ----------------------------
        // INPUT
        // ----------------------------

        String[][] travel_time = {
                {"START","A","6"},
                {"START","B","8"},
                {"A","C","3"},
                {"A","D","7"},
                {"B","D","4"},
                {"B","E","6"},
                {"C","F","2"},
                {"D","F","5"},
                {"E","F","4"},
                {"F","FINISH_1","7"},
                {"F","FINISH_2","9"}
        };

        String[][] points = {
                {"START","0"},
                {"A","18"},
                {"B","26"},
                {"C","9"},
                {"D","15"},
                {"E","20"},
                {"F","6"},
                {"FINISH_1","5"},
                {"FINISH_2","11"}
        };

        // ----------------------------
        // BUILD POINTS MAP
        // ----------------------------

        for (String[] p : points) {
            pointsMap.put(p[0], Integer.parseInt(p[1]));
        }

        // ----------------------------
        // BUILD GRAPH
        // ----------------------------

        for (String[] t : travel_time) {

            String src = t[0];
            String dest = t[1];
            int time = Integer.parseInt(t[2]);

            graph.putIfAbsent(src, new ArrayList<>());
            graph.get(src).add(new Pair(dest, time));
        }

        // ----------------------------
        // RUN DFS DP
        // ----------------------------

        int score = dfs("START");

        // ----------------------------
        // RECONSTRUCT PATH
        // ----------------------------

        List<String> path = new ArrayList<>();
        String curr = "START";

        while (curr != null) {
            path.add(curr);
            curr = nextNode.get(curr);
        }

        // ----------------------------
        // OUTPUT
        // ----------------------------

        System.out.println("Optimal Path: " + path);
        System.out.println("Score: " + score);
    }
}