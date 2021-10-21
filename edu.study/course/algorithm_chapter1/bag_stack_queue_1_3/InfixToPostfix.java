package course.algorithm_chapter1.bag_stack_queue_1_3;

import algs4.src.main.java.edu.princeton.cs.algs4.StdIn;
import algs4.src.main.java.edu.princeton.cs.algs4.StdOut;

public class InfixToPostfix {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();//记录操作符号
        String test="( (( 5 + ( 7 * ( 1 + 1 ) ) ) * 3 ) + ( 2 * ( 1 + 1 ) ) )".replace(" ","");
       for (char c :test.toCharArray()){

            String s = c+"";
            if      (s.equals("+")) stack.push(s);
            else if (s.equals("*")) stack.push(s);
            else if (s.equals(")")) StdOut.print(stack.pop() + " ");//出栈标识
            else if (s.equals("(")) StdOut.print("");
            else                    StdOut.print(s + " ");//打印字母
        }
        StdOut.println();
    }
}
