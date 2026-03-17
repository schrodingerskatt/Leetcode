

class Solution{

    public List<String> topKFrequent(String[] words, int k){

        Map<String, Integer>map = new HashMap<>();
        for(String w : words){
            map.put(w, map.getOrDefault(w, 0)+1);
        }

        PriorityQueue<String>pq = new PriorityQueue<>(
          (a, b) -> map.get(a).equals(mp.get(b)) ?
                    b.compareTo(a) :
                    map.get(a)-map.get(b)
        );

        for(String word : words){
            pq.add(word);
            if(pq.size() > k){
                pq.poll();
            }
        }

        List<String>res = new ArrayList<>();
        while(!pq.isEmpty()){
            res.add(pq.poll());
        }
        Collections.reverse(res);
        return res;
    }
}