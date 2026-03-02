/* Problem Link : https://leetcode.com/problems/reverse-integer/description/
*/

class Solution {
    public int reverse(int x) {
        boolean flag = false;
        if(x < 0){
            flag = true;
            x = x*-1;
        }
        int total = 0;
        while(x != 0){
            if((total > Integer.MAX_VALUE/10)||(total < Integer.MIN_VALUE/10)){
                return 0;
            }
            total = (total*10)+x%10;
            x = x/10;
        }
    if(flag){
        total = -total;
    }
    return total;
    }
}