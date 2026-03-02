/*
Problem Link : https://leetcode.com/problems/longest-substring-without-repeating-characters
*/

class Solution {
    public int lengthOfLongestSubstring(String s) {

        int n = s.length();
        HashMap<Character, Integer> map = new HashMap<>();
        int i = 0, ans = 0;
        for(int j = 0; j < n; j++){
            char ch = s.charAt(j);
            if(map.containsKey(ch)){
                i = Math.max(i, map.get(ch)+1);
            }
            ans = Math.max(ans, j-i+1);
            map.put(ch, j);
        }
    return ans;
    }
}