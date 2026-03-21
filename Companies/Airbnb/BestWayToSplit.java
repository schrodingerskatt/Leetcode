import java.util.*;

class Property{
    int id;
    String neighborhood;
    int capacity;

    Property(int id, String neighborhood, int capacity){
        this.id = id;
        this.neighborhood = neighborhood;
        this.capacity = capacity;
    }
}

public class Solution {

    public List<Integer> Solution(List<Property> properties, String neighborhood, int groupSize){

        List<Property>filtered = new ArrayList<>();
        for(Property p : properties){
            if(p.neighborhood.equals(neighborhood)){
                filtered.add(p);
            }
        }

        int n = filtered.size();
        if(n == 0) return new ArrayList<>();

        int bestCount = Integer.MAX_VALUE;
        int bestSum = Integer.MAX_VALUE;
        List<Integer>result = new ArrayList<>();

        // Bitmask enumeration
        for(int mask = 1; mask < (1<<n); mask++){
            int sum = 0;
            int count = 0;
            List<Integer>current = new ArrayList<>();
            for(int i = 0; i < n; i++){
                if((mask&(1<<i)) != 0){
                    sum+=filtered.get(i).capacity;
                    count++;
                    current.add(filtered.get(i).id);
                }
            }

            // Check validity
            if (sum >= groupSize) {
                if (count < bestCount || (count == bestCount && sum < bestSum)) {
                    bestCount = count;
                    bestSum = sum;
                    result = new ArrayList<>(current);
                }
            }
        }
        return result;
    }
}