/* Problem Link : https://leetcode.com/problems/generate-parentheses/description/ */

class Solution {
    public List<String> generateParenthesis(int n) {
        int maxSize = 2*n;
        List<String> ans = new ArrayList<>();
        generate(0, "", maxSize, ans);
        return ans;
    }

    private void generate(int i, String curStr, int maxSize, List<String>ans){

        if(i == maxSize){
            if(isValid(curStr)){
                ans.add(curStr);
            }
        return;
        }
        generate(i+1, curStr+"(", maxSize, ans);
        generate(i+1, curStr+")", maxSize, ans);
    }

    private boolean isValid(String s){

        Stack<Character>stack = new Stack<>();
        for(char c : s.toCharArray()){
            if(c == ')' && !stack.isEmpty() && stack.peek() == '('){
                stack.pop();
            }else{
                stack.push(c);
            }
        }
    return stack.isEmpty();
    }
}