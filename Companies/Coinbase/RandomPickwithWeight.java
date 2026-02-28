/*
Problem Link : https://leetcode.com/problems/random-pick-with-weight/description/
*/

class Solution {

    int[] prefix;
    int total;

    public Solution(int[] w) {

        prefix = new int[w.length];
        prefix[0] = w[0];

        for(int i = 1; i < w.length; i++){
            prefix[i] = prefix[i - 1] + w[i];
        }

        total = prefix[prefix.length - 1];
    }
    
    public int pickIndex() {
        
        int rand = (int)(Math.random() * total) + 1;
        int low = 0, high = prefix.length - 1;

        while(low < high){

            int mid = low + (high - low) / 2;

            if(prefix[mid] < rand){
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }
}