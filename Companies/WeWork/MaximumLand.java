class Solution{

    public int maxLand(int[] lands, int k){
        int left = 1;
        int right = 0;
        for(int land : lands){
            right = Math.max(right, land);
        }

        int ans = 0;
        while(left <= right){
            int mid = left+(right-left)/2;
            if(canDistribute(lands, k, mid)){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
    return ans;
    }

    public boolean canDistribute(int[] lands, int k, int size){
        int pieces = 0;
        for(land : lands){
            pieces+=land/size;
        }
    return pieces >=k;
    }
}