package unlock;

public class MyCircularQueue {
    final int[] a;
    int front=0;
    int rear=-1;
    int len = 0;


    public MyCircularQueue(int k) {
        this.a = new int[k];
    }

    public boolean enqueue(int val){
        if(isFull()){
            return false;
        }

        rear=(rear+1)%a.length;
        a[rear] = val;
        len++;
        return true;
    }

    public boolean dequeue(){
        if(isEmpty()){
            return false;
        }

        front=(front+1)%a.length;
        len--;
        return true;
    }

    public int Front(){
        return isEmpty()?-1:a[front];
    }

    public int Rear(){
        return isEmpty()?-1:a[rear];
    }

    public boolean isFull() {
        return len==a.length;
    }

    public boolean isEmpty() {
        return len==0;
    }
}
