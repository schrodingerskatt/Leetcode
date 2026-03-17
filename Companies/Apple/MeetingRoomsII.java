/* Problem Link : https://leetcode.com/problems/meeting-rooms-ii/ */

class Solution{

    public int meetingRooms(int[][] intervals){

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<Integer>pq = new PriorityQueue<>();
        pq.offer(intervals[0][1]);

        for(int i = 1; i < intervals.length; i++){
            if(intervals[i][0] >= pq.peek()){
                pq.poll();
            }
            pq.add(itnervals[i][1]);
        }
    return pq.size();
    }
}