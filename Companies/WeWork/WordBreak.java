/* Problem Link : https://leetcode.com/problems/word-break/ */

class Solution{

    public boolean wordBreak(String s, List<String>wordDict){
        HashSet<String> wordSet = new HashSet<>();
        int n = s.length();

        int[] dp = new int[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j <= i; j++){
                String check = s.substring(j, i+1);
                if(wordSet.contains(check)){
                    if(j > 0){
                        dp[i]+=dp[j-1]; // word starts after some prefix, prefix s[0..j-1] must already be breakable
                        /*
                        s = "leetcode"
                        dict = ["leet","code"]
                        i = 7, j = 4, substring = "code"
                        dp[3] > 0   ("leet" is valid)
                        dp[7] += dp[3]
                        */
                    }else{
                        dp[i]+=1;
                    }
                }
            }
        }
    return dp[n-1] > 0;
    }
}