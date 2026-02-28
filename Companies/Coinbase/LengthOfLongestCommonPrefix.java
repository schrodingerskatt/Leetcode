/*
Problem Link : https://leetcode.com/problems/find-the-length-of-the-longest-common-prefix/description/
*/

class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {

        Set<String>prefix = new HashSet<>();
        for(int num : arr1){
            String s = String.valueOf(num);
            for(int i = 1; i <= s.length(); i++){
                prefix.add(s.substring(0, i));
            }
        }
        int maxLen = 0;
        for(int num : arr2){
            String s = String.valueOf(num);
            for(int i = 1; i <= s.length(); i++){
                if(prefix.contains(s.substring(0, i))){
                    maxLen = Math.max(maxLen, i);
                }
            }
        }
    return maxLen;
    }
}