import java.util.*;
public class Solution{

 public long smallestPermutaion(int n){
    char[] digits = String.ValueOf(n).toCharArray();
    Arrays.sort(digits);
    return Long.parseLong(new String(digits));
 }

 // Follow Up

 public long smallestGreatestPermutation(int n, long lowerBound){

    char[] digits = String.ValueOf(n).toCharArray();
    Arrays.sort(digits);
    do{
        long num = Long.parseLong(new String(digits));
        if(num > lowerBound){
            return num;
        }
    }while(nextPermutation(digits));
    return -1;
 }

 public boolean nextPermutation(char[] arr){

    int i = arr.length-2;
    
 }

}