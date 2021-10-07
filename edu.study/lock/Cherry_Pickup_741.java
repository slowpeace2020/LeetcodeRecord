package lock;

import java.util.Arrays;

public class Cherry_Pickup_741 {

    /**
     * my primary solution
     * @param grid
     * @return
     */

    public int cherryPickup(int[][] grid) {
        int go = helperGo(grid,0,0,new boolean[grid.length][grid.length]);
        int back = helperBack(grid,grid.length-1,grid.length-1,new boolean[grid.length][grid.length]);
        System.out.println(go);
        System.out.println(back);

        return go>=0&&back>=0?go+back:0;
    }

    public int helperGo(int[][] grid, int x, int y, boolean[][] visited) {
        int m = grid.length;
        int n = grid[0].length;
        int res =0;
        if (x < 0 || y < 0 || x >= m || y >= n || grid[x][y] == -1 || visited[x][y]) {
            return -1;
        }

        if (x == m - 1 && y == n - 1) {
            if(grid[x][y]==1){
                grid[x][y]=0;
                res = 1;
            }

            return res;
        }

        visited[x][y] = true;



        int behind= Math.max(helperGo(grid,x+1,y,visited),helperGo(grid,x,y+1,visited));

        if(behind>=0){
            if(grid[x][y]==1){
                grid[x][y]=0;
                res = 1;
            }
            return res+behind;
        }else{
            visited[x][y] = false;
        }
        return -1;

    }

    public int helperBack(int[][] grid, int x, int y, boolean[][] visited) {
        int m = grid.length;
        int n = grid[0].length;
        int res =0;
        if (x < 0 || y < 0 || x >= m || y >= n || grid[x][y] == -1 || visited[x][y]) {
            return -1;
        }

        if (x == 0 && y == 0) {
            return res;
        }

        visited[x][y] = true;


        int behind= Math.max(helperBack(grid,x-1,y,visited),helperBack(grid,x,y-1,visited));
        if(behind>=0){
            if(grid[x][y]==1){
                grid[x][y]=0;
                res = 1;
            }
            return res+behind;
        }else {
            visited[x][y] = false;
        }

        return -1;

    }

    /**
     * DFS
     */

    int [][][] dp;
    int n;
    public int cherryPickup2(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        n = grid.length;
        dp = new int[n][n][n];
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                Arrays.fill(dp[i][j], Integer.MIN_VALUE);
            }
        }

        return Math.max(0, dfs(grid, n-1, n-1, n-1));
    }

    private int dfs(int [][] grid, int x1, int y1, int x2){
        int y2 = x1+y1-x2;
        if(x1<0 || y1<0 || x2<0 || y2<0){
            return -1;
        }

        if(grid[x1][y1]<0 || grid[x2][y2]<0){
            return -1;
        }

        if(dp[x1][y1][x2] != Integer.MIN_VALUE){
            return dp[x1][y1][x2];
        }

        if(x1==0 && y1==0){
            dp[0][0][0] = grid[0][0];
            return grid[0][0];
        }

        int res = Math.max(Math.max(dfs(grid, x1-1, y1, x2-1), dfs(grid, x1, y1-1, x2)), Math.max(dfs(grid, x1-1, y1, x2), dfs(grid, x1, y1-1, x2-1)));
        if(res < 0){
            dp[x1][y1][x2] = -1;
            return -1;
        }

        res += grid[x1][y1];
        if(x1 != x2){
            res += grid[x2][y2];
        }

        dp[x1][y1][x2] = res;
        return res;
    }


    /**
     * DP
     * @param grid
     * @return
     */
    public int cherryPickup3(int[][] grid) {
        int n = grid.length;
        int count = 2 * n - 1;
        int[][][] dp = new int[count][n][n];  // count, x1, x2

        for(int t = 0; t < count; t++) {
            for(int i = 0; i < n; ++i) {
                for(int j = 0; j < n; ++j) {
                    int x1 = i, y1 = t-x1;
                    int x2 = j, y2 = t-x2;

                    if(x1 < 0 || y1 < 0 || x1 >= n || y1 >= n) continue;
                    if(x2 < 0 || y2 < 0 || x1 >= n || y2 >= n) continue;

                    if(grid[x1][y1] == -1 || grid[x2][y2] == -1) {
                        dp[t][x1][x2] = -1;
                        continue;
                    }
                    int prev = 0;
                    if(t > 0) {
                        int prev1 = Math.max(getdp(dp, t-1,x1-1,x2), getdp(dp, t-1,x1-1,x2-1));
                        int prev2 = Math.max(getdp(dp, t-1,x1,x2), getdp(dp, t-1,x1,x2-1));
                        prev = Math.max(prev1, prev2);
                        if(prev < 0) {
                            dp[t][x1][x2] = -1;
                            continue;
                        }
                    }

                    dp[t][x1][x2] = prev + grid[x1][y1];
                    if(x1 != x2) {
                        dp[t][x1][x2] += grid[x2][y2];
                    }
                }
            }
        }
        return  Math.max(0, dp[count-1][n-1][n-1]);
    }

    int getdp(int[][][] dp, int t, int x1, int x2) {
        if(x1 < 0 || x2 < 0 || t-x1 < 0 || t-x2 < 0) return -1;
        return dp[t][x1][x2];
    }

    public static void main(String[] args) {
        Cherry_Pickup_741 test = new Cherry_Pickup_741();
       int res0= test.cherryPickup(new int[][]{{0,1,-1},{1,0,-1},{1,1,1}});
//       int res = test.cherryPickup(new int[][]{{1}});
//        System.out.println(res0);
    }
}
