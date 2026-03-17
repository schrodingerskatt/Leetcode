/*
https://leetcode.com/problems/subarray-product-less-than-k/description/
*/

class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {

        if(k <= 1) return 0;

        int count = 0;
        int product = 1;
        int left = 0;

        for(int i = 0; i < nums.length; i++){
            product*=nums[i];
            while(product >= k){
                product/=nums[left];
                left++;
            }
        count+=i-left+1;
        }
    return count;
    }
}