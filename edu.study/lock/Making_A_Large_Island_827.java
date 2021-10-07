package lock;

import java.util.Arrays;

public class Making_A_Large_Island_827 {

    /**
     * 最开始的解法
     * @param grid
     * @return
     */

    public int largestIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] size = new int[m * n];
        int[] parents = new int[m * n];
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Arrays.fill(parents, -1);

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                int pos = i * n + j;
                if (parents[pos] == -1) {
                    parents[pos] = pos;
                    size[pos]++;
                }

                int parent = find(pos, parents);
                for (int[] direct : directions) {
                    int x = i + direct[0];
                    int y = j + direct[1];
                    int neighbor = x * n + j;
                    if (x < 0 || x >= m || y < 0 || y >= n || parents[neighbor] == 0) continue;

                    if (parents[neighbor] == -1) {
                        parents[neighbor] = neighbor;
                        size[neighbor]++;
                    }
                    int nbParent = find(neighbor, parents);
                    if (nbParent == parent) {
                        continue;
                    }
                    int nbSize = size[nbParent];
                    if (nbSize <= size[parent]) {
                        size[parent] += nbSize;
                        parents[nbParent] = parent;
                        res = Math.max(res, size[parent]);
                    } else {
                        size[nbParent] += size[parent];
                        parents[parent] = nbParent;
                        res = Math.max(res, size[nbParent]);
                    }

                }


            }
        }

        return res;


    }

    public int find(int pos, int[] parent) {
        while (pos != parent[pos]) {
            parent[pos] = parent[parent[pos]];
            pos = parent[pos];
        }

        return pos;
    }

    /**
     * 连接的操作很简单，就把0变成1，数当前岛屿大小
     * 想的太复杂了
     * @param grid
     * @return
     */

    public int largestIsland2(int[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        int res =0;
        boolean hasZeero = false;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    continue;
                }
                grid[i][j]=1;
                boolean[][] visited = new boolean[m][n];
                res = Math.max(res,helperNumberLargestIsland2(grid,visited,i,j));

            }
        }

        return res;
    }

    public int helperNumberLargestIsland2(int[][] grid,boolean[][] visited,int i,int j){
        int m = grid.length;
        int n = grid[0].length;
        if(i<0||j<0||i>=m||j>=n||visited[i][j]||grid[i][j]==0){
            return 0;
        }
        visited[i][j] = true;
        return 1+helperNumberLargestIsland2(grid,visited,i-1,j)
                +helperNumberLargestIsland2(grid,visited,i+1,j)
                +helperNumberLargestIsland2(grid,visited,i,j-1)
                +helperNumberLargestIsland2(grid,visited,i,j+1);
    }
}
