/* Problem Link : https://leetcode.com/problems/minimum-falling-path-sum/description/ */

class Solution {
    public int minFallingPathSum(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        for(int i = 1; i < n; i++){
            for(int j = 0; j < m; j++){
                if(j == 0){
                    matrix[i][j]+= Math.min(matrix[i-1][j], matrix[i-1][j+1]);
                }else if(j > 0 && j < m-1){
                matrix[i][j]+=Math.min(matrix[i-1][j], Math.min(matrix[i-1][j-1], matrix[i-1][j+1]));
                }else{
                    matrix[i][j]+=Math.min(matrix[i-1][j], matrix[i-1][j-1]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int c = 0; c < m; c++){
            ans = Math.min(ans, matrix[n-1][c]);
        }
    return ans;
    }
}