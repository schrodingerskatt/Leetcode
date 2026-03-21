import java.util.*;

class Solution {

    static class Bundle {
        int mask;
        int price;
        String name; // original string for output

        Bundle(int mask, int price, String name) {
            this.mask = mask;
            this.price = price;
            this.name = name;
        }
    }

    /*
    memo[mask]       → "what is the cheapest cost from this state?"
    parent[mask]     → "where did we go after this state?"
    usedBundle[mask] → "which bundle caused that transition?"
    */

    Map<Integer, Integer> memo = new HashMap<>();
    Map<Integer, Integer> parent = new HashMap<>();
    Map<Integer, Integer> usedBundle = new HashMap<>();

    List<Bundle> bundles;
    int n;

    public Result solve(List<Pair> inputBundles, List<String> wanted) {

        // STEP 1: normalize wanted
        n = wanted.size();
        Map<String, Integer> index = new HashMap<>();

        for (int i = 0; i < n; i++) {
            index.put(wanted.get(i).toLowerCase(), i);
        }

        // STEP 2: preprocess bundles → convert to mask
        bundles = new ArrayList<>();
        Set<String> available = new HashSet<>();

        for (Pair p : inputBundles) {
            String[] services = p.services.toLowerCase().split(",");
            int mask = 0;

            for (String s : services) {
                s = s.trim();
                available.add(s);

                if (index.containsKey(s)) {
                    mask |= (1 << index.get(s));
                }
            }

            // ignore useless bundles
            if (mask != 0) {
                bundles.add(new Bundle(mask, p.price, p.services));
            }
        }

        // STEP 3: early impossible check
        for (String w : wanted) {
            if (!available.contains(w.toLowerCase())) {
                return new Result(-1, new ArrayList<>());
            }
        }

        int fullMask = (1 << n) - 1;

        int minCost = dfs(fullMask);

        if (minCost == Integer.MAX_VALUE) {
            return new Result(-1, new ArrayList<>());
        }

        // STEP 4: reconstruct answer
        List<String> chosen = new ArrayList<>();
        int mask = fullMask;

        while (mask != 0) {
            int bIndex = usedBundle.get(mask);
            Bundle b = bundles.get(bIndex);

            chosen.add("(" + b.name + ", " + b.price + ")");
            mask = parent.get(mask);
        }

        Collections.reverse(chosen);

        return new Result(minCost, chosen);
    }

    private int dfs(int mask) {
        if (mask == 0) return 0;

        if (memo.containsKey(mask)) return memo.get(mask);

        int minCost = Integer.MAX_VALUE;

        for (int i = 0; i < bundles.size(); i++) {
            Bundle b = bundles.get(i);

            int newMask = mask & (~b.mask); // remove covered services

            if (newMask == mask) continue;

            int next = dfs(newMask);
            if (next == Integer.MAX_VALUE) continue;

            int cost = b.price + next;

            if (cost < minCost) {
                minCost = cost;
                parent.put(mask, newMask);
                usedBundle.put(mask, i);
            }
        }

        memo.put(mask, minCost);
        return minCost;
    }

    // Helper classes
    static class Pair {
        String services;
        int price;

        Pair(String services, int price) {
            this.services = services;
            this.price = price;
        }
    }

    static class Result {
        int cost;
        List<String> bundles;

        Result(int cost, List<String> bundles) {
            this.cost = cost;
            this.bundles = bundles;
        }
    }
}