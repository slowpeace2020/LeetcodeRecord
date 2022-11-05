package october;

import com.sun.source.util.Trees;

import java.util.*;

public class Dijkstra {
//    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
//        int[] dist = new int[n];
//        Arrays.fill(dist,Integer.MAX_VALUE);
//        Map<Integer,List<Point>> map = new HashMap<>();
//        for(int[] flight:flights){
//            int from = flight[0];
//            int to = flight[1];
//            int price = flight[2];
//            List<Point> points = map.getOrDefault(from,new ArrayList<>());
//            points.add(new Point(to,price));
//            map.put(from,points);
//        }
//
//        dist[src] = 0;
//        PriorityQueue<Point> queue = new PriorityQueue<>((a,b)->(a.price-b.price));
//        queue.add(new Point(src,0));
//        int step = 0;
//        while(!queue.isEmpty()&&step<k+1){
//            int size = queue.size();
//            List<Point> list = new ArrayList<>();
//            for(int i=0;i<size;i++){
//                Point point = queue.poll();
//                int from = point.dst;
//                List<Point> nexts = map.getOrDefault(from,new ArrayList<>());
//                for(Point next:nexts){
//                    int price = point.price+next.price;
//                    if(price<dist[next.dst]){
//                        dist[next.dst] = price;
//                        list.add(new Point(next.dst,price));
//                    }
//                }
//            }
//            queue.addAll(list);
//            step++;
//
//        }
//
//        return dist[dst]==Integer.MAX_VALUE?-1:dist[dst];
//    }
//
//    class Point{
//        int dst;
//        int price;
//        public Point(int dst,int price){
//            this.price = price;
//            this.dst = dst;
//        }
//    }


//    public int minimumCost(int n, int[][] highways, int discounts) {
//        int[][] dist = new int[n][discounts+1];
//        for(int i=0;i<n;i++){
//            Arrays.fill(dist[i],Integer.MAX_VALUE);
//        }
//
//        Map<Integer,List<Point>> map = new HashMap<>();
//        for(int[] way:highways){
//            int from = way[0];
//            int to = way[1];
//            int fee = way[2];
//            List<Point> list1 = map.getOrDefault(from,new ArrayList<>());
//            list1.add(new Point(to,fee,0));
//            map.put(from,list1);
//            List<Point> list2 = map.getOrDefault(to,new ArrayList<>());
//            list2.add(new Point(from,fee,0));
//            map.put(to,list2);
//        }
//
//        dist[0][0] = 0;
//        PriorityQueue<Point> queue = new PriorityQueue<>((a,b)->(a.price-b.price));
//        queue.add(new Point(0,0,0));
//        int res = Integer.MAX_VALUE;
//        while(!queue.isEmpty()){
//            Point point = queue.poll();
//            List<Point> nexts = map.getOrDefault(point.dst,new ArrayList<>());
//            int discount = point.useDiscount;
//            if(point.dst==n-1){
//                res = Math.min(res,point.price);
//            }
//
//            for(Point next:nexts){
//                int price = point.price+next.price;
//                if(price<dist[next.dst][discount]){
//                    dist[next.dst][discount] = price;
//                    queue.add(new Point(next.dst,price,discount));
//                }
//                if(discount<discounts){
//                    price = point.price+next.price/2;
//                    if(price<dist[next.dst][discount+1]){
//                        dist[next.dst][discount+1] = price;
//                        queue.add(new Point(next.dst,price,discount+1));
//                    }
//                }
//            }
//
//        }
//        return res == Integer.MAX_VALUE ? -1:res;
//    }
//
//
//    class Point{
//        int dst;
//        int price;
//        int useDiscount;
//        public Point(int dst){
//            this.dst = dst;
//            this.price = Integer.MAX_VALUE;
//        }
//        public Point(int dst,int price){
//            this.dst = dst;
//            this.price = price;
//        }
//
//        public Point(int dst,int price,int useDiscount){
//            this.dst = dst;
//            this.price = price;
//            this.useDiscount = useDiscount;
//        }
//    }


//    public int reachableNodes(int[][] edges, int maxMoves, int n) {
//        int[][] dist = new int[n][n];
//        boolean[] visited = new boolean[n];
//        int count = 1;
//
//        Map<Integer,List<Point>> map = new HashMap<>();
//        for(int[] edge:edges){
//            int from = edge[0];
//            int to = edge[1];
//            int graph = edge[2];
//            List<Point> list1 = map.getOrDefault(from,new ArrayList<>());
//            list1.add(new Point(to,graph));
//            map.put(from,list1);
//            List<Point> list2 = map.getOrDefault(to,new ArrayList<>());
//            list2.add(new Point(from,graph));
//            map.put(to,list2);
//        }
//
//        PriorityQueue<Point> queue = new PriorityQueue<>((a,b)->(a.graph-b.graph));
//        queue.add(new Point(0,0,0));
//        visited[0] = true;
//        while(!queue.isEmpty()){
//            Point point = queue.poll();
//            int move = point.move;
//            List<Point> nexts = map.getOrDefault(point.node,new ArrayList<>());
//            for(Point next:nexts){
//                int graph = next.graph;
//                if(dist[point.node][next.node]!=0||dist[next.node][point.node]==graph){
//                    continue;
//                }
//                if(move+graph<maxMoves){
//                    count+=graph-dist[next.node][point.node];
//                    if(!visited[next.node]){
//                        count++;
//                        visited[next.node] = true;
//                    }
//                    dist[point.node][next.node] = graph;
//                    dist[next.node][point.node] = graph;
//                    queue.add(new Point(next.node,0,move+graph+1));
//
//                }else{
//                    count+=Math.min(maxMoves-move,graph-dist[next.node][point.node]);
//                    dist[point.node][next.node] = maxMoves-move;
//                }
//            }
//
//
//        }
//
//
//        return count;
//
//    }
//
//    class Point{
//        int node;
//        int graph;
//        int move;
//        public Point(int node,int graph){
//            this.node = node;
//            this.graph = graph;
//        }
//
//        public Point(int node,int graph,int move){
//            this.node = node;
//            this.graph = graph;
//            this.move = move;
//        }
//
//    }

//    public int countPaths(int n, int[][] roads) {
//        Map<Integer,List<Point>> map = new HashMap<>();
//        for(int[] road:roads){
//            int from = road[0];
//            int to = road[1];
//            int cost = road[2];
//            List<Point> list1 = map.getOrDefault(from,new ArrayList<>());
//            List<Point> list2 = map.getOrDefault(to,new ArrayList<>());
//            list1.add(new Point(to, (long) cost));
//            map.put(from,list1);
//            list2.add(new Point(from, (long) cost));
//            map.put(to,list2);
//        }
//
//        long[] dist = new long[n];
//        Arrays.fill(dist,Long.MAX_VALUE);
//
//        PriorityQueue<Point> queue = new PriorityQueue<>((a,b)->(a.time.compareTo(b.time)));
//        queue.add(new Point(0, (long) 0));
//        dist[0] = 0;
//
//        while(!queue.isEmpty()){
//            Point point = queue.poll();
//            List<Point> nexts = map.getOrDefault(point.node,new ArrayList<>());
//            for(Point next:nexts){
//
//                if(next.node==n-1){
//                    if(next.time+point.time<dist[next.node]){
//                        dist[next.node] = next.time+point.time;
//                    }
//                }else if(dist[next.node]>next.time+point.time){
//                    dist[next.node]=next.time+point.time;
//                    queue.add(new Point(next.node,next.time+point.time));
//                }
//            }
//        }
//
//        long count = dfs(0,n-1,map,dist,new Integer[n]);
//        return (int) (count%(1e9+7));
//
//    }
//
//    private int dfs(int src,int n,Map<Integer,List<Point>> map,long[] dist,Integer[] memo){
//        if(memo[src]!=null){
//            return memo[src];
//        }
//
//        if(src==n){
//            return 1;
//        }
//
//        int ans = 0;
//        for(Point next:map.getOrDefault(src,new ArrayList<>())){
//            long time = next.time+dist[src];
//            if(time==dist[next.node]){
//                ans=(ans+dfs(next.node,n,map,dist,memo)) % 1000000007;
//            }
//        }
//
//        memo[src] = ans;
//
//        return ans;
//    }
//
//    class Point{
//        int node;
//        Long time;
//        public Point(int node,Long time){
//            this.node = node;
//            this.time = time;
//        }
//
//    }


    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {

        Map<Integer,List<Point>> map =new HashMap<>();
        for(int i=0;i<edges.length;i++){
            int[] edge = edges[i];
            double possibility = succProb[i];
            Point point = new Point(edge[1],possibility);
            List<Point> list = map.getOrDefault(edge[0],new ArrayList<>());
            list.add(point);
            map.put(edge[0],list);
        }


        Double[] dist = new Double[n];
        dist[start] = 1.0;

        PriorityQueue<Point> queue = new PriorityQueue<>((a,b)->(b.possibility.compareTo(a.possibility)));

        queue.add(new Point(start,1.0));

        double res = 0.0;
        while(!queue.isEmpty()){
            Point cur = queue.poll();
            if(cur.node==end){
                res = Math.max(res,cur.possibility);
            }
            List<Point> list = map.getOrDefault(cur.node,new ArrayList<>());
            for(Point point:list){
                point.possibility*=cur.possibility;
                if(dist[point.node]==null||dist[point.node].compareTo(point.possibility)<0){
                    dist[point.node] = point.possibility;
                    queue.add(point);
                }
            }
        }

        return res;
    }

    class Point{
        int node;
        Double possibility;
        public Point(int node,double possibility){
            this.node = node;
            this.possibility = possibility;
        }
    }

    public int[] rearrangeBarcodes(int[] barcodes) {
        Map<Integer,Integer> count = new HashMap<>();
        for(int bar:barcodes){
            count.put(bar,count.getOrDefault(bar,0)+1);
        }

        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(count.entrySet());
        Collections.sort(list,Map.Entry.<Integer,Integer>comparingByValue().reversed());
        int len = barcodes.length;
        int i = 0;
        int[] res = new int[len];
        for(Map.Entry<Integer,Integer> entry:list){
            int times = entry.getValue();
            while (times-->0){
                res[i] = entry.getKey();
                i+=2;
                if (i>=len){
                    i=1;
                }
            }
        }

        return res;
    }

    public int findMaxValueOfEquation(int[][] points, int k) {
        TreeMap<Integer, TreeSet<Integer>> map = new TreeMap<>();
        for(int[] point:points){
            TreeSet<Integer> set = map.getOrDefault(point[0],new TreeSet<>());
            set.add(point[1]);
            map.put(point[0],set);
        }

        int res = Integer.MIN_VALUE;
        for(int[] point:points){
            int x = point[0];
            int y = point[1];
            int floor = x-k;
            int ceiling = x+k;

            for(int i=floor;i<=ceiling;i++){
                if(i==x&&map.get(i).size()==1){
                    continue;
                }
                if(map.containsKey(i)){
                    int value = map.get(i).last();
                    res = Math.max(res,value+y+Math.abs(i-x));
                }
            }

        }
        return res;
    }

    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int N = source.length;
        int[] UNION = new int[N];
        for(int i=0;i<N;i++){
            UNION[i] = i;
        }

        for(int[] swap:allowedSwaps){
            int indexA = swap[0];
            int indexB = swap[1];
            int parentA = findParent(UNION,indexA);
            int parentB = findParent(UNION,indexB);
            if(parentA!=parentB){
                UNION[parentA] = parentB;
            }
        }

        Map<Integer,Map<Integer,Integer>> map = new HashMap<>();
        for(int i=0;i<N;i++){
            int num = source[i];
            int root = findParent(UNION,i);
            map.putIfAbsent(root,new HashMap<>());
            Map<Integer,Integer> store = map.get(root);
            store.put(num,store.getOrDefault(num,0)+1);
        }

        int res = 0;
        for(int i=0;i<N;i++){
            int num = target[i];
            int root = findParent(UNION,i);
            Map<Integer,Integer> store = map.get(root);
            if(store.getOrDefault(num,0)==0){
                res++;
            }else {
                store.put(num,store.get(num)-1);
            }
        }

        return res;
    }

    private int findParent(int[] UNION,int node){
        if(UNION[node]==node){
            return node;
        }

        return findParent(UNION,UNION[node]);
    }


        public static void main(String[] args) {
        int[][] flights = new int[][]{{0,1,1000000000},{1,2,1000000000},{2,3,1000000000},{3,4,1000000000},{4,5,1000000000},{5,6,1000000000},{6,7,1000000000},{7,8,1000000000},{8,9,1000000000},{9,10,1000000000},{10,11,1000000000},{11,12,1000000000},{12,13,1000000000},{13,14,1000000000},{14,15,1000000000},{15,16,1000000000},{16,17,1000000000},{17,18,1000000000},{18,19,1000000000},{19,20,1000000000},{20,21,1000000000},{21,22,1000000000},{22,23,1000000000},{23,24,1000000000},{24,25,1000000000},{25,26,1000000000},{26,27,1000000000},{27,28,1000000000},{28,29,1000000000},{29,30,1000000000},{30,31,1000000000},{31,32,1000000000},{32,33,1000000000},{33,34,1000000000},{34,35,1000000000},{35,36,1000000000},{36,37,1000000000},{37,38,1000000000},{38,39,1000000000},{39,40,1000000000},{40,41,1000000000},{41,42,1000000000},{42,43,1000000000},{43,44,1000000000},{44,45,1000000000},{45,46,1000000000},{46,47,1000000000},{47,48,1000000000},{48,49,1000000000},{49,50,1000000000},{50,51,1000000000},{51,52,1000000000},{52,53,1000000000},{53,54,1000000000},{54,55,1000000000},{55,56,1000000000},{56,57,1000000000},{57,58,1000000000},{58,59,1000000000},{59,60,1000000000},{60,61,1000000000},{61,62,1000000000},{62,63,1000000000},{63,64,1000000000},{64,65,1000000000},{65,66,1000000000},{66,67,1000000000},{67,68,1000000000},{68,69,1000000000},{69,70,1000000000},{70,71,1000000000},{71,72,1000000000},{72,73,1000000000},{73,74,1000000000},{74,75,1000000000},{75,76,1000000000},{76,77,1000000000},{77,78,1000000000},{78,79,1000000000},{79,80,1000000000},{80,81,1000000000},{81,82,1000000000},{82,83,1000000000},{83,84,1000000000},{84,85,1000000000},{85,86,1000000000},{86,87,1000000000},{87,88,1000000000},{88,89,1000000000},{89,90,1000000000},{90,91,1000000000},{91,92,1000000000},{92,93,1000000000},{93,94,1000000000},{94,95,1000000000},{95,96,1000000000},{96,97,1000000000},{97,98,1000000000},{98,99,1000000000},{99,100,1000000000},{100,101,1000000000},{101,102,1000000000},{102,103,1000000000},{103,104,1000000000},{104,105,1000000000},{105,106,1000000000},{106,107,1000000000},{107,108,1000000000},{108,109,1000000000},{109,110,1000000000},{110,111,1000000000},{111,112,1000000000},{112,113,1000000000},{113,114,1000000000},{114,115,1000000000},{115,116,1000000000},{116,117,1000000000},{117,118,1000000000},{118,119,1000000000},{119,120,1000000000},{120,121,1000000000},{121,122,1000000000},{122,123,1000000000},{123,124,1000000000},{124,125,1000000000},{125,126,1000000000},{126,127,1000000000},{127,128,1000000000},{128,129,1000000000},{129,130,1000000000},{130,131,1000000000},{131,132,1000000000},{132,133,1000000000},{133,134,1000000000},{134,135,1000000000},{135,136,1000000000},{136,137,1000000000},{137,138,1000000000},{138,139,1000000000},{139,140,1000000000},{140,141,1000000000},{141,142,1000000000},{142,143,1000000000},{143,144,1000000000},{144,145,1000000000},{145,146,1000000000},{146,147,1000000000},{147,148,1000000000},{148,149,1000000000},{149,150,1000000000},{150,151,1000000000},{151,152,1000000000},{152,153,1000000000},{153,154,1000000000},{154,155,1000000000},{155,156,1000000000},{156,157,1000000000},{157,158,1000000000},{158,159,1000000000},{159,160,1000000000},{160,161,1000000000},{161,162,1000000000},{162,163,1000000000},{163,164,1000000000},{164,165,1000000000},{165,166,1000000000},{166,167,1000000000},{167,168,1000000000},{168,169,1000000000},{169,170,1000000000},{170,171,1000000000},{171,172,1000000000},{172,173,1000000000},{173,174,1000000000},{174,175,1000000000},{175,176,1000000000},{176,177,1000000000},{177,178,1000000000},{178,179,1000000000},{179,180,1000000000},{180,181,1000000000},{181,182,1000000000},{182,183,1000000000},{183,184,1000000000},{184,185,1000000000},{185,186,1000000000},{186,187,1000000000},{187,188,1000000000},{188,189,1000000000},{189,190,1000000000},{190,191,1000000000},{191,192,1000000000},{192,193,1000000000},{193,194,1000000000},{194,195,1000000000},{195,196,1000000000},{196,197,1000000000},{197,198,1000000000},{198,199,1000000000}};
        Dijkstra test = new Dijkstra();
        int[][] points = new int[][]{{1,3},{2,0},{5,10},{6,-10}};

        test.findMaxValueOfEquation(points,1);

    }
}
