package winter;

import algs4.src.main.java.edu.princeton.cs.algs4.Stack;

public class MaxStack {
    Stack<Integer> stack;
    Stack<Integer> maxStack;
    public MaxStack() {
        // do intialization if necessary
        stack = new Stack<>();
        maxStack = new Stack<>();
    }

    /*
     * @param number: An integer
     * @return: nothing
     */
    public void push(int x) {
        // write your code here
        stack.push(x);
        int max = maxStack.isEmpty()?x:maxStack.peek();
        maxStack.push(x>max?x:max);
        stack.push(x);
    }

    public int pop() {
        // write your code here
        maxStack.pop();
        return stack.pop();
    }

    /*
     * @return: An integer
     */
    public int top() {
        // write your code here
        return stack.peek();
    }

    /*
     * @return: An integer
     */
    public int peekMax() {
        // write your code here
        return maxStack.peek();
    }

    /*
     * @return: An integer
     */
    public int popMax() {
        // write your code here
        int max = peekMax();
        Stack<Integer> temp = new Stack<>();
        while(top()!=max){
            temp.push(pop());
        }

        pop();//同步操作两个栈
        while(!temp.isEmpty()){
            push(temp.pop());
        }

        return max;
    }
}
