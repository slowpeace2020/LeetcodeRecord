package august;


import java.util.Stack;

public class DynamicProgramming {
    public int longestValidParentheses(String s) {
        int n = s.length();
        if(n<=1){
            return 0;
        }
        int res = 0;
        int[] dp = new int[n];
        if(s.charAt(0)=='('&&s.charAt(1)==')'){
            dp[1]=2;
            res = 2;
        }

        for(int i=2;i<n;i++){
            if(s.charAt(i)==')'){
                int j = i-1;
                if(s.charAt(j)=='('){
                    dp[i] = 2;
                    if(j>0){
                        dp[i]+=dp[j-1];
                    }
                }else{
                    int pre = j-dp[j];
                    if(pre>=0&&s.charAt(pre)=='('){
                        dp[i]=2+dp[j];
                        if(pre>=1){
                            dp[i]+=dp[pre-1];
                        }
                    }
                }


                res = Math.max(res,dp[i]);
            }
        }

        return res;

    }

    public static boolean checkFormat(String text){
        text = text.trim();
        if(!text.startsWith("[")){
            return false;
        }
        if(!text.endsWith("]")){
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for(char c:text.toCharArray()){
            if(c=='{'||c=='['||c=='('){
                stack.push(c);
            }else{
                if(c=='}'||c==']'||c==')'){
                    if(stack.isEmpty()){
                        return false;
                    }
                    if(c=='}'&&stack.peek()!='{'){
                        return false;
                    }
                    if(c==']'&&stack.peek()!='['){
                        return false;
                    }
                    if(c==')'&&stack.peek()!='('){
                        return false;
                    }
                    stack.pop();
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        dp.longestValidParentheses("())");
//        dp.longestValidParentheses(")()())()()(");
    }
}
