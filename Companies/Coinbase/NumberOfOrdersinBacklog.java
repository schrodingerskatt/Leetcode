// For buy orders (type 0) → match with the lowest sell price
// For sell orders (type 1) → match with the highest buy price

// Lowest sell price → Min Heap
// Highest buy price → Max Heap

/*
We maintain two priority queues:

1. buyMaxHeap  → sorted by price descending
2. sellMinHeap → sorted by price ascending

Each element in heap: [price, amount]

For each order:
1. While it can match with opposite heap → match as much as possible
2. If leftover amount remains → push into its own heap

At the end:
1. Sum all remaining amounts in both heaps
2. Return modulo 1e9+7
*/

class Solution {
    public int getNumberOfBacklogOrders(int[][] orders) {

        int MOD = 1_000_000_007;

        PriorityQueue<int[]>buyHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        PriorityQueue<int[]>sellHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        for(int[] order : orders){

            int price = order[0];
            int amount = order[1];
            int type = order[2];
            if(type == 0){
                while(amount > 0 && !sellHeap.isEmpty() && sellHeap.peek()[0] <= price){
                    int[] sell = sellHeap.poll();
                    int matched = Math.min(sell[1], amount);
                    amount-= matched;
                    sell[1]-= matched;
                    if(sell[1] > 0){
                        sellHeap.offer(sell);
                    }
                }
                if(amount > 0){
                    buyHeap.offer(new int[]{price, amount});
                }
            }else{
                while(amount > 0 && !buyHeap.isEmpty() && buyHeap.peek()[0] >= price){
                    int[] buy = buyHeap.poll();
                    int matched = Math.min(amount, buy[1]);
                    amount-= matched;
                    buy[1]-= matched;
                    if(buy[1] > 0){
                        buyHeap.offer(buy);
                    }
                }
                if(amount > 0){
                    sellHeap.offer(new int[]{price, amount});
                }
            }
        }
        long total = 0;
        while(!buyHeap.isEmpty()){
            total = (total+buyHeap.poll()[1])%MOD;
        }
        while(!sellHeap.isEmpty()){
            total = (total+sellHeap.poll()[1])%MOD;
        }
    return (int)total;
    }
}