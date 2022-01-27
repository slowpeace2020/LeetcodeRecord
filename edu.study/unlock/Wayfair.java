package unlock;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Wayfair {
    public int solution(String[] B) {

        // write your code in Java SE 8
        int n = B.length;
        int[][] dirs = new int[][]{{-1,-1},{-1,1}};
        int x=-1;
        int y=-1;
        boolean[][] isX = new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(B[i].charAt(j)=='O'){
                    x=i;
                    y=j;
                    if(x==0){
                        return 0;
                    }
                }else if(B[i].charAt(j)=='X'&&check(B,i,j)){
                    isX[i][j] = true;
                }

            }
        }

        int res =dfs(x,y,isX,n);

        return res;
    }

    public int dfs(int x,int y,boolean[][] isX,int n){
        if(x<=0){
            return 0;
        }

        if(y<=0||y>=n-1){
            return 0;
        }
        int res = isX[x][y] ? 1:0;
        res  +=Math.max(dfs(x-1,y-1,isX,n),dfs(x-1,y+1,isX,n));
        return res;
    }

    public boolean check(String[] B,int x,int y){
        int n = B.length;
        int[][] dirs = new int[][]{{-1,0},{-1,1},{1,0},{1,-1},{0,1},{0,-1},{1,1},{-1,-1}};
        for(int[] dir:dirs){
            int i = x+dir[0];
            int j=y+dir[1];
            if(i<0||j<0||i>=n||j>=n){
                continue;
            }
            if(B[i].charAt(j)=='X'){
                return false;
            }
        }

        return true;
    }

    public int bfs(String[] B) {
        int n = B.length;
        int[][] dirs = new int[][]{{-1,-1},{-1,1}};
        int x=-1;
        int y=-1;
        boolean[][] isX = new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(B[i].charAt(j)=='O'){
                    x=i;
                    y=j;
                    if(x==0){
                        return 0;
                    }
                }else if(B[i].charAt(j)=='X'){
                    if(i==0||j==0||j==n-1){
                        continue;
                    }
                    if(i-1>=0&&j+1<n&&B[i-1].charAt(j+1)=='X'){
                        continue;
                    }
                    isX[i][j] = true;
                }

            }
        }

        int res=0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x,y,0});
        while (!queue.isEmpty()){
            int size = queue.size();
            boolean hasX=false;
            while (size>0){
                size--;
                int[] cur = queue.poll();
                if(cur[2]==1){
                    hasX = true;
                }
                for(int[] dir:dirs){
                    int i = cur[0]+dir[0];
                    int j=cur[1]+dir[1];
                    if(i<0||j<0||j>=n){
                        continue;
                    }
                    int k = isX[i][j]?1:0;
                    queue.offer(new int[]{i,j,k});
                }
            }

            if(hasX){
                res++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String s = "cdeo";
        String s1 = "bytdag";
        int[] array = new int[]{3,2,0,1};
        int[] array1 = new int[]{4,3,0,1,2,5};
//        solution(s,array);
//        solution("00:00","23:59");
//        System.out.println(solution("8:45","9:16"));
//        System.out.println(solution("00:00","23:59"));
//        System.out.println(solution("23:49","06:00"));
        Wayfair test = new Wayfair();
        test.solution(new String[]{"X....",
                ".X...",
                "..O..",
                "...X.",
                "....."});

    }
}
