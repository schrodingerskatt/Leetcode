import java.util.*;

class Solution{

    public List<List<Integer>> mewnuCombiantions(double[] prices, int target){

        int n = prices.length;
        int[] nums = new int[n];
        for(int i = 0; i < n; i++){
            nums[i] = (int)Math.round(prices[i]*100);
        }

        int targetInt = (int)Math.round(target*100);
        Arrays.sort(nums);

        List<List<Integer>>nums = new ArrayList<>();
        backtrack(nums, targetInt, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] nums, int remaining, int start, List<Integer>curr, List<List<Integer>> result){

        if(remaining == 0){
            result.add(new ArrayList<>(curr));
            return;
        }

        for(int i = start; i < nums.length; i++){
            if(nums[i] > remaining) break;

            curr.add(nums[i]);
            backtrack(nums, remaining-nums[i], i, curr, result);
            curr.remove(curr.size()-1);
        }
// O(2^n * k)

    }
}