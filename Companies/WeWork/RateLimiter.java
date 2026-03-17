class RateLimiter{

    private final int maxRequests;
    private final int maxWindow;

    private final ConcurrentHashMap<String, Deque<long>> map = new ConcurrentHashMap<>();
    /* Multiple threads may call allow() simultaneously in a real system (API server).
    Thread 1 -> user1 request
    Thread 2 -> user2 request
    Thread 3 -> user1 request
    ConcurrentHashMap allows thread-safe concurrent access without locking the whole map.
    If we used HashMap → race conditions could occur.
    We use Dequeue to store timestamps of requests.
    */

    public RateLimiter(int maxRequests, int maxWindow){
        this.maxRequests = maxRequests;
        this.maxWindow = maxWindow;
    }

    public boolean allow(String userId, long timeStamp){

        map.putIfAbsent(userId, new LinkedList<>());
        Deque<Long>queue = map.get(userId);

        synchronized(queue){
            /* Why synchronized ?
            Multiple threads may update the same user's queue simultaneously.
            Thread1 -> add request
            Thread2 -> remove expired request
            Without synchronization race condition, corrupt queue, wrong counts can occur
            So we lock only that user's queue, not the whole system.

            */
            while(!queue.isEmpty() && timeStamp - queue.peekFirst() >= window){
                queue.pollFirst();
            }
            if(queue.size() < maxRequests){
                queue.addLast(timestamp);
                return true;
            }
        return false;
        }
    }
}