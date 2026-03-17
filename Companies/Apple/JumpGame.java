/* Problem Link : https://leetcode.com/problems/jump-game/ */

class Solution {
    public boolean canJump(int[] nums) {

        int reached = 0;
        for(int i = 0; i < nums.length; i++){
            if(i > reached) return false;
            else{
                reached = Math.max(reached, i+nums[i]);
            }
        }
    return true;
    }
}