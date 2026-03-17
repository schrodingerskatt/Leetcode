import java.util.*;

public class SkiRoute {

    static class Pair {
        String node;
        int time;

        Pair(String node, int time) {
            this.node = node;
            this.time = time;
        }
    }

    static Map<String, List<Pair>> graph = new HashMap<>();
    static Map<String, Integer> pointsMap = new HashMap<>();

    static Map<String, Integer> memo = new HashMap<>();
    static Map<String, String> nextNode = new HashMap<>();

    static Set<String> visiting = new HashSet<>();

    static boolean infinite = false;

    static int dfs(String node) {

        if (memo.containsKey(node))
            return memo.get(node);

        if (visiting.contains(node)) {
            // cycle detected
            infinite = true;
            return Integer.MIN_VALUE;
        }

        visiting.add(node);

        int best = Integer.MIN_VALUE;

        for (Pair p : graph.getOrDefault(node, new ArrayList<>())) {

            int nextScore = dfs(p.node);

            if (nextScore == Integer.MIN_VALUE)
                continue;

            int gain = pointsMap.get(p.node) - p.time + nextScore;

            if (gain > best) {
                best = gain;
                nextNode.put(node, p.node);
            }
        }

        visiting.remove(node);

        memo.put(node, best);

        return best;
    }

    public static void main(String[] args) {

        // ---------------- INPUT ----------------

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

        // -------- BUILD POINT MAP --------

        for (String[] p : points) {
            pointsMap.put(p[0], Integer.parseInt(p[1]));
        }

        // -------- BUILD GRAPH --------

        for (String[] t : travel_time) {

            String src = t[0];
            String dest = t[1];
            int time = Integer.parseInt(t[2]);

            graph.putIfAbsent(src, new ArrayList<>());
            graph.get(src).add(new Pair(dest, time));
        }

        // -------- RUN DFS --------

        int score = dfs("START");

        if (infinite) {
            System.out.println("Infinite score possible due to cycle.");
            return;
        }

        // -------- RECONSTRUCT PATH --------

        List<String> path = new ArrayList<>();
        String curr = "START";

        while (curr != null) {
            path.add(curr);
            curr = nextNode.get(curr);
        }

        // -------- OUTPUT --------

        System.out.println("Optimal Path: " + path);
        System.out.println("Score: " + score);
    }
}