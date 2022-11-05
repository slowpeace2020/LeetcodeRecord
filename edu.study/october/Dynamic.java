package october;

import java.util.*;

public class Dynamic {
    public int minimumWhiteTiles(String floor, int numCarpets, int carpetLen) {
            int n = floor.length();
            int nc = n * numCarpets;
            int[][] dp = new int[n+1][nc+1];
            for(int i=1;i<=n;i++){
                for(int k=0;k<=nc;k++){
                    int jump = dp[i-1][k]+floor.charAt(i-1)-'0';
                    int cover = k>0? dp[Math.max(i-carpetLen,0)][k-1]:1000;
                    dp[i][k] = Math.min(jump,cover);
                }
            }

            return dp[n][nc];
    }

    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        int[] minTimes = new int[numLaps+1];
        Arrays.fill(minTimes,Integer.MAX_VALUE);
        for(int[] tire:tires){
            calculateMinTime(minTimes,tire,changeTime,numLaps);
        }

        //For every lap i , see if you get better time by changing tires on all laps before i ie 1 to i using j loop
        //eg : for lap 10, check if you get better time by changing tire at all laps before 10 ie 1.2.3....9
        for(int i=1;i<=numLaps;i++){
            for(int j=1;j<i;j++){
                minTimes[i] = Math.min(minTimes[i],minTimes[j]+changeTime+minTimes[i-j]);
            }
        }

        return minTimes[numLaps];
    }

    private void calculateMinTime(int[] minTimes, int[] tire, int changeTime, int numLaps) {
        int baseTime = tire[0];
        int expTime = tire[1];

        int lapTime = baseTime;
        int totalTime = lapTime;

        minTimes[1] = Math.min(baseTime,minTimes[1]);

        for(int lap=2;lap<=numLaps;lap++){
            //time for current lap = prevLapTime*expTime instead of recalcuating entire value
            lapTime*=expTime;

            //***IMP change time is better, no point calculating further
            if(lapTime>changeTime+baseTime){
                break;
            }
            totalTime +=lapTime;
            minTimes[lap] = Math.min(minTimes[lap],totalTime);
        }


    }

    public int minimumFinishTimeI(int[][] tires, int changeTime, int numLaps) {
        int n = tires.length;
        //前面一轮的花费时间最少得最后一个index和它连续出现的圈数
        int[][] prevStatus = new int[numLaps+1][4];

        int[][] dp = new int[numLaps+1][n];
        int min = 100000;
        int min_index = -1;
        int second_index = -1;
        int second = 100000;
        for(int j=0;j<n;j++){
            dp[1][j] = tires[j][0];
            if(min>tires[j][0]){
                second = min;
                second_index = min_index;
                min = tires[j][0];
                min_index = j;
            }else if(second>tires[j][0]){
                second=tires[j][0];
                second_index = j;
            }
        }
        prevStatus[1][0] = min_index;
        prevStatus[1][1] = 1;
        prevStatus[1][2] = second_index;
        prevStatus[1][3] = 1;
        for(int i=2;i<=numLaps;i++){
            min = -1;
            min_index = -1;
            second = -1;
            second_index = -1;
            for(int j=0;j<n;j++){
                int prev_min_index = prevStatus[i-1][0];
                if(prev_min_index!=j){
                    dp[i][j] = dp[i-1][prev_min_index]+tires[j][0]*tires[j][1]+changeTime;
                    if(min==-1||dp[i][j]<min){
                        second = min;
                        second_index = min_index;
                        min = 1;
                        min_index = j;
                    }else if(second==-1||dp[i][j]<second){
                        second = 1;
                        second_index = j;
                    }
                }else{
                    int same = (int) (dp[i-1][prev_min_index]+tires[j][0]*Math.pow(tires[j][1],prevStatus[i-1][1]));
                    int prev_second_index = prevStatus[i-1][2];
                    int diff = dp[i-1][prev_second_index]+tires[j][0]*tires[j][1]+changeTime;

                    if(same<diff){
                        dp[i][j] = same;
                        if(min==-1||dp[i][j]<min){
                            second = min;
                            second_index = min_index;
                            min_index = j;
                            min = prevStatus[i-1][1]+1;
                        }else if(second==-1||dp[i][j]<second){
                            second_index = j;
                            second = prevStatus[i-1][1]+1;
                        }
                    }else{
                        dp[i][j] = diff;
                        if(min==-1||dp[i][j]<min){
                            second = min;
                            second_index = min_index;
                            min_index = j;
                            min = 1;
                        }else if(second==-1||dp[i][j]<second){
                            second_index = j;
                            second = 1;
                        }
                    }

                }
            }
            prevStatus[i][0] = min_index;
            prevStatus[i][1] = min;
            prevStatus[i][2] = second_index;
            prevStatus[i][3] = second;

        }

        min = dp[numLaps][0];
        for(int val:dp[numLaps]){
            if(val<min){
                min = val;
            }
        }
        return min;

    }

    class Point implements Comparable<Point>{
        int x,y,l;
        String s;

        public Point(int x,int y){
            this.x = x;
            this.y = y;
            this.l = Integer.MAX_VALUE;
            this.s = "";
        }

        public Point(int x, int y, int l, String s){
            this.x = x;
            this.y = y;
            this.l = l;
            this.s = s;
        }

        @Override
        public int compareTo(Point point) {
            return l==point.l ? s.compareTo(point.s):l-point.l;
        }
    }

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length;
        int n = maze[0].length;

        Point[][] points = new Point[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                points[i][j] = new Point(i,j);
            }
        }

        int[][] dir = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
        String[] ds = new String[]{"u","r","d","l"};

        PriorityQueue<Point> queue = new PriorityQueue<>();
        queue.add(new Point(ball[0],ball[1],0,""));

        while (!queue.isEmpty()){
            Point point = queue.poll();
            if(points[point.x][point.y].compareTo(point)<0){
                continue;
            }

            points[point.x][point.y] = point;

            for(int i=0;i<4;i++){
                int xx = point.x;
                int yy = point.y;
                int l = point.l;

                while (xx>=0 && xx<m &&yy>0&&yy<n&&maze[xx][yy]==0 && (xx!=hole[0] || yy!=hole[1])){
                    xx+=dir[i][0];
                    yy+=dir[i][1];
                    l++;
                }

                if(xx!=hole[0] || yy!=hole[0]){
                    xx-=dir[i][0];
                    yy-=dir[i][1];
                    l--;
                }
                queue.offer(new Point(xx,yy,l,point.s+ds[i]));
            }

        }

        return points[hole[0]][hole[1]].l==Integer.MAX_VALUE?"impossible":points[hole[0]][hole[1]].s;
    }

    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dist = new int[m][n];

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                dist[i][j] = i+j+1;
            }
        }

        dist[0][0] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>((a,b)->(a[2]-b[2]));
        queue.add(new int[]{0,0,0});

        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

        while(!queue.isEmpty()){
            int[] cur = queue.poll();

            for(int[] dir:dirs){
                int x = cur[0]+dir[0];
                int y = cur[1]+dir[1];
                int cost = cur[2]+1;
                if(x<0||x>=m||y<0||y>=n){
                    continue;
                }

                if(dir[0]==0&&dir[1]==1&&grid[cur[0]][cur[1]]==1){
                    cost--;
                }
                if(dir[0]==0&&dir[1]==-1&&grid[cur[0]][cur[1]]==2){
                    cost--;
                }
                if(dir[0]==1&&dir[1]==0&&grid[cur[0]][cur[1]]==3){
                    cost--;
                }
                if(dir[0]==-1&&dir[1]==0&&grid[cur[0]][cur[1]]==4){
                    cost--;
                }

                if(dist[x][y]>cost){
                    dist[x][y]=cost;
                    queue.add(new int[]{x,y,cost});
                }
            }

        }

        return  dist[m-1][n-1];
    }


    public List<Boolean> areConnected(int n, int threshold, int[][] queries) {
        List<Boolean> res = new LinkedList<>();
        int[] map = new int[n+1];
        for(int i = 0; i < n+1; i++){
            map[i] = i;
        }

        for(int i = threshold+1; i <= n; i++){
            for(int j = i; j <= n; j+=i){
                map[find(map,j)] = find(map, i);
            }
        }

        for(int[] query: queries){
            res.add(find(map, query[0]) == find(map,query[1]));
        }
        return res;
    }

    public int find(int[] map, int id){
        while(map[id] != id){
            map[id] = map[map[id]];
            id = map[id];
        }
        return id;
    }



        public static void main(String[] args) {
        Dynamic test = new Dynamic();
//        test.minCost(new int[][]{{2,4,2,3},{3,2,2,1}});
            System.out.println(2^1);
            System.out.println(2^2);
    }



}
