/* Problem Link : https://leetcode.com/problems/maximum-subarray/description/ */

class Solution {
    public int maxSubArray(int[] nums) {

        int overall_sum = nums[0];
        int current_sum = nums[0];

        for(int i = 1; i < nums.length; i++){
            if(current_sum > 0){
                current_sum+=nums[i];
            }else{
                current_sum = nums[i];
            }
        overall_sum = Math.max(current_sum, overall_sum);
        }
    return overall_sum;
    }
}