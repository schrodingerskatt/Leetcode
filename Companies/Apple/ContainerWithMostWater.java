/*
Problem Link : https://leetcode.com/problems/container-with-most-water/description/
*/

class Solution {
    public int maxArea(int[] height) {

        int low = 0;
        int high = height.length-1;
        int maxArea = Integer.MIN_VALUE;

        while(low < high){

            int minHeight = Math.min(height[low], height[high]);
            int area = (high - low)*minHeight;
            maxArea = Math.max(maxArea, area);

            if(height[low] < height[high]){
                low++;
            }else{
                high--;
            }
        }
    return maxArea;
    }
}