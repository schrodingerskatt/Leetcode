/* Problem Link : https://leetcode.com/problems/find-median-from-data-stream/ */

class MedianFinder{

    // small (maxHeap) -> stores the smaller half
    // large (minHeap) -> stores the larger half

    private PriorityQueue<Integer>small;
    private PriorityQueue<Integer>Big;

    public MedianFinder(){
        small = new PriorityQueue<>(Collections.reverseOrder());
        large = new PriorityQueue<>();
    }

    public void addNum(int num){
        small.offer(num);
        large.offer(small.poll()); // move largest to large
        if(large.size() > small.size()){
            small.offer(large.poll());
        }
    }

    public double findMedian(){
        if(small.size() > large.size()){
            return small.peek();
        }
        return (small.peek()+large.peek())/2.0;
    }
}