/*
Problem Link : https://leetcode.com/problems/string-to-integer-atoi/description/
*/

class Solution {
    public int myAtoi(String s) {

        int i = 0;
        int n = s.length();

        // skip leading whitespace
        while(i < n && s.charAt(i) == ' '){
            i++;
        }

        // string empty after whitespaces
        if(i == n) return 0;

        // Determine sign
        int sign = 1;
        if(s.charAt(i) == '-'){
            sign = -1;
            i++;
        }else if(s.charAt(i) == '+'){
            i++;
        }
        int result = 0;

        // Read Digits
        while(i < n && Character.isDigit(s.charAt(i))){

            int digit = s.charAt(i)-'0';
            if (result > Integer.MAX_VALUE / 10 ||
               (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result*10+digit;
            i++;
        }
    return result*sign;
    }
}