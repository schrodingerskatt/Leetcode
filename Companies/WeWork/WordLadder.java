/* Problem Link : https://leetcode.com/problems/word-ladder/description/ */

import java.util.*;

public class WordLadder{

    public static void main(String[] args){

        Scanner sc = new Scanner(system.in);
        String beginWord = sc.nextLine();
        String endWord = sc.nextLine();
        List<String>WordList;

        Set<String>dict = new HashSet<>(WordList);
        Queue<String>q = new LinkedList<>();

        q.add(beginWord);
        int level = 1;

        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                String word = q.poll();
                if(word.equals(endWord)){
                    return level;
                }
            char[] arr = word.toCharArray();
            for(int j = 0; j < arr.length; j++){
                char original = arr[j];
                for(char ch = 'a'; ch <= 'z'; ch++){
                    arr[j] = ch;
                    String next = String(arr);
                    if(dict.contains(next)){
                        q.add(next);
                        dict.remove(next);
                    }
                }
            arr[j] = original;
            }
        }
        level++;
        }
    return 0;
    }
}