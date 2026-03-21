import java.util.*;

public class SplitStayOptimized {

    public static void findSplitStays(Map<String, List<Integer>>availability, int start, int end){

        Map<String, Set<Integer>>availability = new HashMap<>();
        for(var e : availability.entrySet()){
            availability.put(e.getKey(), new HashSet<>(e.getValue()));
        }

        int D = end+1; // safe buffer
        Map<String, boolean[]>prefixMap = new HashMap<>();
        Map<String, boolean[]>suffixMap = new HashMap<>();

        for(String listing : availability.keySet()){
            Set<Integer> set = availability.get(listing);

            boolean[] prefix = new boolean[D];
            boolean[] suffix = new boolean[D];
            prefix[start] = set.contains(start);
            for(int i = start+1; i <= end; i++){
                prefix[i] = prefix[i-1]&&set.contains(i);
            }
            suffix[end] = set.contains(end);
            for(int i = end-1; i >= start; i--){
                suffix[i] = suffix[i+1]&&set.contains(i);
            }

            prefixMap.put(listing, prefix);
            suffixMap.put(listing, suffix);
        }

        // If we find our ans in prefix itself
        for(String l : availability.keySet()){
            if(prefixMap.get(l)[end]){
                System.out.println(l);
            }
        }

        List<String>listings = new ArrayList<>(availability.keySet());

        for(String l1 : listings){
            for(String l2 : listings){

                if(l1.equals(l2)) continue;
                boolean[]p = prefixMap.get(l1);
                boolean[]s = suffixMap.get(l2);
                for(int k = start; k < end; k++){
                    if(p[k] && s[k+1]){
                        System.out.println(l1 + " -> " + l2 + " at k=" + k);
                        break;
                    }
                }
            }
        }

    }


    public static void main(String[] args){
        Map<String, List<Integer>>availability = new HashMap<>();
        availability.put("A", List.of(1, 2, 3, 6, 7, 10, 11));
        availability.put("B", List.of(3, 4, 5, 6, 8, 9, 10, 13));
        availability.put("C", List.of(7, 8, 9, 10, 11));
        findSplitStays(availability, 3, 11);
    }
}