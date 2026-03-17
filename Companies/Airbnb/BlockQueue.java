class BlockQueue{

    private class Block{
        int[] arr;
        int start;
        int end;
        Block next;

        Block(int size){
            arr = new int[size];
            start = 0;
            end = 0;
            next = null;
        }

        boolean isFull(){
            return end == arr.length;
        }

        boolean isEmpty(){
            return start == end;
        }
    }

    private int blockSize;
    private Block head;
    private Block tail;

    public BlockQueue(int blockSize){
        this.blockSize = blockSize;
        head = new Block(blockSize);
        tail = head;
    }

    public void enqueue(int val){
        if(tail.isFull()){
            Block newBlock = new Block(blockSize);
            tail.next = newBlock;
            tail = newBlock;
        }
        tail.arr[tail.end++] = val;
    }

    public int dequeue(){

        if(isEmpty()){
            throw new RuntimeException("Queue Empty");
        }
        int val = head.arr[head.start++];
        if(head.isEmpty() && head.next != null){
            head = head.next;
        }
        return val;
    }

    public int peek(){
        if(isEmpty()){
            throw new RuntimeException("Queue is Empty");
        }
        return head.arr[head.start];
    }
}