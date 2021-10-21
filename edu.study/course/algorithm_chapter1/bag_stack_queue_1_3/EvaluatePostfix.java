package course.algorithm_chapter1.bag_stack_queue_1_3;

import algs4.src.main.java.edu.princeton.cs.algs4.StdIn;

public class EvaluatePostfix {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if(s.equals("*")){
                int pre = stack.pop();
                int next = stack.pop();
                stack.push(pre*next);
            }else if(s.equals("/")){
                int pre = stack.pop();
                int next = stack.pop();
                stack.push(next/pre);
            }else if(s.equals("+")){
                int pre = stack.pop();
                int next = stack.pop();
                stack.push(pre+next);
            }else if(s.equals("-")){
                int pre = stack.pop();
                int next = stack.pop();
                stack.push(next-pre);
            }else {
                stack.push(Integer.valueOf(s));
            }
        }

        System.out.println(stack.pop());
    }
}
