/*
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description
*/

class Solution {
    public int maxProfit(int[] prices) {
        int buy = prices[0];
        int max_profit = Integer.MIN_VALUE;
        for(int i = 0; i < prices.length; i++){
            int profit = prices[i]-buy;
            max_profit = Math.max(max_profit, profit);
            buy = Math.min(buy, prices[i]);
        }
    return max_profit;
    }
}