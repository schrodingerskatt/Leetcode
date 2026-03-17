class TopK{

    Map<Integer, Integer>freq = new HashMap<>();
    PriorityQueue<int[]>heap;

    int k;

    public TopK(){
        this.k = k;
        heap = new PriorityQueue<>(
            (a, b) -> a[1]-b[1]
        );
    }

    public void event(int value){
        freq.put(value, freq.getOrDefault(value, 0)+1);
        heap.add(new int[]{value, freq.get(value)});
        if(heap.size() > k){
            heap.poll();
        }
    }

    public List<Integer>getTopK(){
        List<Integer> res = new ArrayList<>();
        for(int[] e : heap){
            res.add(e[0]);
        }
        return res;
    }
}