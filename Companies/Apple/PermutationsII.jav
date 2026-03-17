/* Problem Link : https://leetcode.com/problems/permutations-ii */

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);  
        boolean[] visited = new boolean[nums.length];
        permute(nums, ans, visited, new ArrayList<>());
        return ans;
        
    }

    private void permute(int[] nums, List<List<Integer>>ans, boolean[] visited,
                         List<Integer>res){
        if(res.size() == nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i = 0; i < nums.length; i++){

            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1])
                continue;
            visited[i] = true;
            res.add(nums[i]);
            permute(nums, set, visited, res);
            visited[i] = false;
            res.remove(res.size()-1);
        }

    }
}