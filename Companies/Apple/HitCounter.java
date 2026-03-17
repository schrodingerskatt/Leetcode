
public class HitCounter{

    Queue<Integer>queue;

    public HitCounter(){
        queue = new LinkedList<>();
    }

    public void hit(int timestamp){
        queue.offer(timestamp);
    }

    public int gethits(int timestamp){

        while(!queue.isEmpty() && timestamp-queue.peek() >= 300){
            queue.poll();
        }
    return queue.size();
    }
}

class Main{

    public static void main(String[] args){
        HitCounter obj = new HitCounter();
        Scanner sc = new Scanner(System.in);
        int timestamp = sc.nextInt();
        obj.hit(timestamp);
        int ans = obj.gethits(timestamp);
        System.out.println(ans);
    }
}