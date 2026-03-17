/* Problem Link : https://leetcode.com/problems/group-anagrams/description/ */

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>>anagrams = new HashMap<>();
        for(String str : strs){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedWord = new String(chars);
                if(!anagrams.containsKey(sortedWord)){
                    anagrams.put(sortedWord, new ArrayList<>());
                }
            anagrams.get(sortedWord).add(word);
        }
    return new ArrayList<>(anagrams.values());
    }
}