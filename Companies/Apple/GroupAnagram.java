/* Problem Link : https://leetcode.com/problems/group-anagrams/ */

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>>map = new HashMap<>();
        for(int i = 0; i < strs.length; i++){
            String str = strs[i];
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedWord = new String(chars);
            if(!map.containsKey(sortedWord)){
                map.put(sortedWord, new ArrayList<>());
            }
            map.get(sortedWord).add(str);

        }
    return new ArrayList<>(map.values());
    }
}