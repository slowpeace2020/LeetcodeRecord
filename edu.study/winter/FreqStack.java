package winter;

import java.util.*;

public class FreqStack {
    HashMap<Integer,Integer> map;
    int index;
    PriorityQueue<Node> queue;
    public FreqStack() {
        this.map = new HashMap<>();
        queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return node1.freq==node2.freq?node2.index-node1.index:node2.freq-node1.freq
                        ;
            }
        });
        index=0;
    }

    public void push(int val) {
        map.put(val,map.getOrDefault(val,0)+1);
        Node node = new Node(val,map.get(val),index++);
        queue.offer(node);
    }

    public int pop() {
        return queue.poll().val;
    }

    class Node{
        int freq;
        int val;
        int index;
        public Node(int freq,int val,int index){
            this.freq =freq;
            this.val = val;
            this.index = index;
        }
    }

    public static void main(String[] args) {
//        ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"]
//[[],[5],[7],[5],[7],[4],[5],[],[],[],[]]

        FreqStack stack = new FreqStack();
        stack.push(5);
        stack.push(7);
        stack.push(5);
        stack.push(7);
        stack.push(4);
        stack.push(5);
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
    }
}
