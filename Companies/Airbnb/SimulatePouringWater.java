
public int[] pourWater(int[] heights, int V, int K){

    int n = heights.length;

    while(V-- > 0){
        int best = K;
        int i = K;

        while(i-1 >= 0 && heights[i-1] <= heights[i]){
            if(heights[i-1] < heights[best]){
                best = i-1;
            }
        i--;
        }

        if(best != K){
            heights[best]++;
            continue;
        }

        best = K;
        i = K;

        while(i+1 < n && heights[i+1] <= heights[i]){
            if(heights[i+1] < heights[i]){
                best = i+1;
            }
        i++;
        }

        heights[best]++;
    }
    return heights;
}