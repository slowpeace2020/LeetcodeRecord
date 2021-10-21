package lock;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class The_Maze_II_505 {

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        return dfsDistance(maze,new boolean[maze.length][maze[0].length],start[0],start[1],destination);
    }

    //BFS
    public int bfsDistance(int[][] maze, int[] start, int[] destination) {
        Queue<int[]> queue = new LinkedList<>();
        int distance = 0;
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        queue.offer(start);
        int m = maze.length;
        int n = maze[0].length;

        return 0;

    }


    //DFS
    public int dfsDistance(int[][] maze,boolean[][] visited, int x, int y, int[] destination){
        int m = maze.length;
        int n = maze[0].length;
        if(visited[x][y]||x<0||y<0||x>=m||y>=n||maze[x][y]==1){
            return Integer.MAX_VALUE;
        }

        if(x==destination[0]&&y==destination[1]){
            return 0;
        }

        visited[x][y] = true;

       int res = Math.min(Math.min(dfsDistance(maze,visited,x+1,y,destination),dfsDistance(maze,visited,x-1,y,destination)),Math.min(dfsDistance(maze,visited,x,y+1,destination),dfsDistance(maze,visited,x,y-1,destination)));
        if(res!=Integer.MAX_VALUE){
            res++;
        }

        visited[x][y] = false;
        return res;
    }

    public int shortestDistance1(int[][] maze, int[] start, int[] dest) {
        int[][] distance = new int[maze.length][maze[0].length];
        for (int[] row : distance)
            Arrays.fill(row, Integer.MAX_VALUE);
        distance[start[0]][start[1]] = 0;
        dijkstra(maze, start, distance);
        return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
    }

    public void dijkstra(int[][] maze, int[] start, int[][] distance) {
        int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        queue.offer(new int[] { start[0], start[1], 0 });
        while (!queue.isEmpty()) {
            int[] s = queue.poll();
            if (distance[s[0]][s[1]] < s[2])
                continue;
            for (int[] dir : dirs) {
                int x = s[0] + dir[0];
                int y = s[1] + dir[1];
                int count = 0;
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }
                if (distance[s[0]][s[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                    distance[x - dir[0]][y - dir[1]] = distance[s[0]][s[1]] + count;
                    queue.offer(new int[] { x - dir[0], y - dir[1], distance[x - dir[0]][y - dir[1]] });
                }
            }
        }
    }
}
