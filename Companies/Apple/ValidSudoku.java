/* Problem Link : https://leetcode.com/problems/valid-sudoku/description/ */

class Solution {
    public boolean isValidSudoku(char[][] board) {

        for(int i = 0; i < 9; i++){
            HashSet<Character>rowSet = new HashSet<>();
            for(int j = 0; j < 9; j++){
                char current = board[i][j];
                if(current != '.'){
                    if(rowSet.contains(current)){
                        return false;
                    }
                    rowSet.add(current);
                }
            }
        }

        for(int j = 0; j < 9; j++){
            HashSet<Character>colSet = new HashSet<>();
            for(int i = 0; i < 9; i++){
                char current = board[i][j];
                if(current != '.'){
                    if(colSet.contains(current)){
                        return false;
                    }
                colSet.add(current);
                }
            }
        }
    for(int boxRow = 0; boxRow < 3; boxRow++){
        for(int boxCol = 0; boxCol < 3; boxCol++){
            HashSet<Character> boxSet = new HashSet<>();
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    char current = board[boxRow * 3 + i][boxCol * 3 + j];
                    if(current != '.'){
                       if (boxSet.contains(current)) return false;
                       boxSet.add(current);
                    }
                }
            }
        }
    }
    return true;  
    }
}