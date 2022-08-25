package august;

import java.util.*;

public class DP {
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
            int[] dp = new int[4001];
            //这个范围是

            dp[0] = 0;
            Queue<int[]> queue =new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            visited.add(0);
            queue.add(new int[]{0,0});
            for(int num:forbidden){
                visited.add(num);
            }
            while (!queue.isEmpty()){
                int[] position = queue.poll();
                if(position[0]==x){
                    return dp[x];
                }
                int forward = position[0]+a;
                int backward = position[0]-b;
                if(position[1]!=-1 && backward>0 && visited.add(backward)){
                    dp[backward] = dp[position[0]]+1;
                    queue.add(new int[]{backward,-1});
                }

                if(forward<4001&&visited.add(forward)){
                    dp[forward] = dp[position[0]]+1;
                    queue.add(new int[]{forward,0});
                }
            }
            return -1;

    }

    private int getStep(int[] source,int[] target, char[][] grid){
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        queue.add(source);
        visited[source[0]][source[1]] = true;
        int step = 0;

        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                int[] cur = queue.poll();
                if(cur[0] == target[0]&&cur[1]==target[1]){
                    return step;
                }

                for(int[] dir:dirs){
                    int x = cur[0]+dir[0];
                    int y = cur[1]+dir[1];
                    if(x<0||y<0||x>=m||y>=n||visited[x][y]||grid[x][y]=='#'){
                        continue;
                    }
                    queue.add(new int[]{x,y});
                    visited[x][y] = true;
                }
            }
            step++;

        }

        return -1;

    }

    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer,Set<int[]>> map = new HashMap<>();
        for(int[] time: times){
            Set<int[]> set = map.getOrDefault(time[0],new HashSet<>());
            set.add(new int[]{time[1],time[2]});
            map.put(time[0],set);
        }

        int[] dist = new int[n+1];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[k] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{k,0});
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            Set<int[]> set = map.getOrDefault(cur[0],new HashSet<>());
            for(int[] next:set){
                if(next[1]+cur[1]<dist[next[0]]){
                    dist[next[0]] = next[1]+cur[1];
                    queue.add(new int[]{next[0],dist[next[0]]});
                }
            }

        }

        int res = 0;

        for(int i=1;i<=n;i++){
            if(dist[i]>res){
                res = dist[i];
            }
        }

        return res==Integer.MAX_VALUE?-1:res;
    }


    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        // write your code here
        int m = maze.length;
        int n = maze[0].length;

        int[][] dirs = new int[][]{{0,1},{-1,0},{0,-1},{1,0}};
        int[][] visited = new int[m][n];
        for(int[] v:visited){
            Arrays.fill(v,Integer.MAX_VALUE);
        }
        int step = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = 0;
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            for(int[] dir:dirs){
                int x = cur[0]+dir[0];
                int y = cur[1]+dir[1];
                step = 1;
                while(x>=0&&y>=0&&x<m&&y<n&&maze[x][y]==0){
                    x+=dir[0];
                    y+=dir[1];
                    step++;
                }
                x-=dir[0];
                y-=dir[1];
                step--;
                if(visited[x][y]>step+visited[cur[0]][cur[1]]){
                    visited[x][y] = step+visited[cur[0]][cur[1]];
                    queue.add(new int[]{x,y});
                }
            }
        }

        return visited[destination[0]][destination[1]]==Integer.MAX_VALUE?-1:visited[destination[0]][destination[1]];
    }


    public int minimumEffortPath(int[][] heights) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        PriorityQueue<int[]> queue = new PriorityQueue<>((a,b)->(a[2]-b[2]));
        queue.add(new int[]{0,0,0});
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] visited = new boolean[m][n];

        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            visited[x][y] = true;

            if(x==m-1&&y==n-1){
                return cur[2];
            }

            for(int[] dir:dirs){
                int nextX = x+dir[0];
                int nextY = y+dir[1];
                if(nextX>=0&&nextY>=0&&nextX<m&&nextY<n&&!visited[nextX][nextY]){
                    int h = Math.max(cur[2],Math.abs(heights[x][y]-heights[nextX][nextY]));
                    queue.add(new int[]{nextX,nextY,h});
                }
            }

        }

        return -1;
    }


    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer,List<int[]>> map = new HashMap<>();
        for(int[] edge:edges){
            int a = edge[0];
            int b = edge[1];
            int dis = edge[2];
            List<int[]> tob = map.getOrDefault(a,new ArrayList<>());
            tob.add(new int[]{b,dis});
            map.put(a,tob);
            List<int[]> toa = map.getOrDefault(b,new ArrayList<>());
            toa.add(new int[]{a,dis});
            map.put(b,toa);
        }

        int res =n-1;
        int count =n-1;
        for(int i=n-1;i>=0;i--){
            int num = getNeibors(n,i,map,distanceThreshold);
            if(num==0){
                return i;
            }
            if(count>num){
                res = i;
                count = num;
            }
        }

        return res;
    }

    private int getNeibors(int n,int source,Map<Integer,List<int[]>> edges,int distanceThreshold)       {

        long[] dist = new long[n];
        Arrays.fill(dist,distanceThreshold+1);
        dist[source] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue((a,b)->(Double.compare(dist[(int) a],dist[(int) b])));
        queue.add(source);
        while(!queue.isEmpty()){
            int cur = queue.poll();
            List<int[]> list = edges.getOrDefault(cur,new ArrayList<>());
            for(int[] next:list){
                if(next[1]+dist[cur]<dist[next[0]]){
                    dist[next[0]] = next[1]+dist[cur];
                    queue.add(next[0]);
                }
            }
        }

        int res = 0;
        for(long num:dist){
            if(num<=distanceThreshold){
                res++;
            }
        }
        return res;
    }


    class Bomb{
        int x;
        int y;
        int r;
        public Bomb(int x,int y,int r){
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;
        Map<Bomb,List<Bomb>> map = new HashMap<>();
        Bomb[] nums = new Bomb[n];
        for(int i=0;i<n;i++){
            int[] b = bombs[i];
            Bomb bomb1 = nums[i]==null? new Bomb(b[0],b[1],b[2]):nums[i];
            nums[i] = bomb1;
            for(int j=i+1;j<n;j++){
                int[] c = bombs[j];
                Bomb bomb2 = nums[j]==null? new Bomb(c[0],c[1],c[2]):nums[j];
                nums[j] = bomb2;
                if(inCircle(bomb1,bomb2)){
                    List<Bomb> list = map.getOrDefault(bomb1,new ArrayList<>());
                    list.add(bomb2);
                    map.put(bomb1,list);
                }
                if(inCircle(bomb2,bomb1)){
                    List<Bomb> list = map.getOrDefault(bomb2,new ArrayList<>());
                    list.add(bomb1);
                    map.put(bomb2,list);
                }
            }
        }

        int res =1;
        for(Bomb source:nums){
            int count = bombNumber(map,source);
            if(count>res){
                res =count;
            }
        }

        return res;

    }

    private int bombNumber(Map<Bomb,List<Bomb>> map,Bomb source){
        Queue<Bomb> queue = new LinkedList<>();
        queue.add(source);
        Set<Bomb> visited = new HashSet<>();
        visited.add(source);
        while(!queue.isEmpty()){
            Bomb cur = queue.poll();
            List<Bomb> list = map.getOrDefault(cur,new ArrayList<>());
            for(Bomb next:list){
                if(visited.add(next)){
                    queue.add(next);
                }
            }

        }

        return visited.size();
    }

    private boolean inCircle( Bomb bomb1,Bomb bomb2){
        return ((long)(bomb1.x-bomb2.x)*(long)(bomb1.x-bomb2.x)+(long)(bomb1.y-bomb2.y)*(long)(bomb1.y-bomb2.y))<=(long)bomb1.r*bomb1.r;
    }

    public static void main(String[] args) {
        DP test = new DP();
        test.maximumDetonation(new int[][]{{1,1,5},{10,10,5}});
        test.maximumDetonation(new int[][]{{2,1,3},{6,1,4}});
//        test.networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2);
//[1998]
//        1999
//        2000
//        2000
//        test.minimumJumps(new int[]{1998},1999,2000,2000);
//        test.minimumJumps(new int[]{1,6,2,14,5,17,4},16,9,7);
//        test.minimumJumps(new int[]{8,3,16,6,12,20},15,13,11);
    }
}
