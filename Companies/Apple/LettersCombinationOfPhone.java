/* Problem Link : https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/ */

class Solution {
    public List<String> letterCombinations(String digits) {

        List<String> ans = new ArrayList<>();
        if(digits == null || digits.length() == 0){
            return ans;
        }

        String[] map = {
            "", //0
            "", //1
            "abc",//2
            "def",//3
            "ghi",//4
            "jkl",//5
            "mno",//6
            "pqrs",//7
            "tuv",//8
            "wxyz"//9
        };
        backtrack(digits, 0, new StringBuilder(), ans, map);
        return ans;
        
    }

    private void backtrack(String digits, int index, StringBuilder current, List<String>ans, String[]                         map){

        if(index == digits.length()){
            ans.add(current.toString());
            return;
        }
      String letters = map[digits.charAt(index)- '0'];
      for(char ch : letters.toCharArray()){
        current.append(ch);
        backtrack(digits, index+1, current, ans, map);
        current.deleteCharAt(current.length()-1);
      }
    }
}