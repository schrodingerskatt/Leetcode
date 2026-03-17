/* Problem Link : https://leetcode.com/problems/combination-sum/description/ */

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        findCombination(0, candidates, target, new ArrayList<>(), ans);
        return ans;
    }

    private void findCombination(int idx, int[] candidates, int target, List<Integer> current, 
                                 List<List<Integer>>ans){

        if(target == 0){
            ans.add(new ArrayList<>(current));
            return;
        }
        if(idx == candidates.length || target < 0){
            return;
        }
        current.add(candidates[idx]);
        findCombination(idx, candidates, target-candidates[idx], current, ans);
        current.remove(current.size()-1);
        findCombination(idx+1, candidates, target, current, ans);
    }
}