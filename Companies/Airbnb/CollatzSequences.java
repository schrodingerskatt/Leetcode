import java.util.*;
class Solution{

    public static void main(String[] args){
        int limit = 10;
        int[] result = findMaxCollatz(limit);
        System.out.println("start "+ result[0]);
        System.out.println("steps "+result[1]);
    }

    public static int[] findMaxCollatz(int limit){
        Map<Long, Integer>memo = new HashMap<>();
        memo.put(1L, 0);
        int maxSteps = 0;
        int bestStart = 1;

        for(int i = 1; i <= limit; i++){

            int steps = collatzSteps(i, memo);
            if(steps > maxSteps){
                maxSteps = steps;
                bestStart = i;
            }
        }
        return new int[]{bestStart, maxSteps};
    }

    public static int collatzSteps(long n, Map<Long, Integer>memo){

        if(memo.containsKey(n)){
            return memo.get(n);
        }

        long next;
        if(n%2 == 0){
            next = n/2;
        }else{
            next = 3*n+1;
        }
        int steps = 1+collatzSteps(next, memo);
        memo.put(n, steps);
        return steps;
    }
}