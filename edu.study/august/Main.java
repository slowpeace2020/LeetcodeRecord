package august;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

/**
 * The Main class implements an application that reads lines from the standard input
 * and prints them to the standard output.
 */
public class Main {
    /**
     * Iterate through each line of input.
     */
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        StringBuilder str = new StringBuilder();
        while ((line = in.readLine()) != null) {
            str.append(line);
        }
        System.out.println(checkFormat(str.toString()));
    }

    public static boolean checkFormat(String text) {
        Stack<Character> stack = new Stack<>();
        text = text.trim();
        if (!text.startsWith("[") || !text.endsWith("]")) {
            return false;
        }

        for (char c : text.toCharArray()) {
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else if (c == '}' || c == ']' || c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                if (c == '}' && stack.peek() != '{') {
                    return false;
                }

                if (c == ']' && stack.peek() != '[') {
                    return false;
                }

                if (c == ')' && stack.peek() != '(') {
                    return false;
                }
                stack.pop();

              }
        }
            return stack.isEmpty();
    }

}


