/*
For each state:

Option 1: Buy all remaining items individually

Option 2: Try each offer (if valid), recursively solve the reduced needs

We take the minimum cost among all choices.
*/

import java.util.*;

public class Solution {

    public int shoppingOffers(List<Integer> price, List<List<Integer>> offers, List<Integer> needs) {
        Map<String, Integer> memo = new HashMap<>();
        return dfs(price, offers, needs, memo);
    }

    private int dfs(List<Integer> price, List<List<Integer>> offers, List<Integer> needs, Map<String, Integer> memo) {
        String key = needs.toString();
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Step 1: Buy all items individually
        int minCost = 0;
        for (int i = 0; i < needs.size(); i++) {
            minCost += needs.get(i) * price.get(i);
        }

        // Step 2: Try each offer
        for (List<Integer> offer : offers) {
            List<Integer> newNeeds = new ArrayList<>();
            boolean valid = true;

            for (int i = 0; i < needs.size(); i++) {
                if (offer.get(i) > needs.get(i)) {
                    valid = false;
                    break;
                }
                newNeeds.add(needs.get(i) - offer.get(i));
            }

            // Apply offer if valid
            if (valid) {
                int offerPrice = offer.get(offer.size() - 1);
                int cost = offerPrice + dfs(price, offers, newNeeds, memo);
                minCost = Math.min(minCost, cost);
            }
        }

        memo.put(key, minCost);
        return minCost;
    }

    // Optional main for testing
    public static void main(String[] args) {
        Solution sol = new Solution();

        List<Integer> price = Arrays.asList(2, 5);
        List<List<Integer>> offers = Arrays.asList(
                Arrays.asList(3, 0, 5),
                Arrays.asList(1, 2, 10)
        );
        List<Integer> needs = Arrays.asList(3, 2);

        System.out.println(sol.shoppingOffers(price, offers, needs)); // Output: 14
    }
}