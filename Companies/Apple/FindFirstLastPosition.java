/* Problem Link : https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/ */

class Solution {
    public int[] searchRange(int[] nums, int target) {
        
        int first = FindPosition(nums, target, true);
        int last = FindPosition(nums, target, false);
        return new int[]{first, last};
    }

    private int FindPosition(int[]nums, int target, boolean flag){
        int low = 0;
        int high = nums.length-1;
        int pos = -1;
        while(low <= high){
            int mid = low+(high-low)/2;
            if(nums[mid] > target){
                high = mid-1;
            }else if(nums[mid] < target){
                low = mid+1;
            }else{
                pos = mid;
                if(flag){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }
        }
    return pos;
    }
}