package lock;

import algs4.src.main.java.edu.princeton.cs.algs4.In;

import java.util.*;

public class ConnectingCitiesWithMinimumCost_1135 {
    //BFS
    public int minimumCostBFS(int N, int[][] connections) {
        Map<Integer, List<CityCost>> map = new HashMap<>();
        for (int[] connection : connections) {
            int from = connection[0];
            int to = connection[1];
            int cost = connection[2];
            List<CityCost> fromlist = map.getOrDefault(from, new ArrayList<>());
            List<CityCost> tolist = map.getOrDefault(to, new ArrayList<>());
            fromlist.add(new CityCost(to, cost));
            tolist.add(new CityCost(from, cost));
        }

        int res = Integer.MAX_VALUE;
        for (Integer start : map.keySet()) {
            boolean[] visited = new boolean[N + 1];
            visited[start] = true;
            PriorityQueue<CityCost> queue = new PriorityQueue<>(new Comparator<CityCost>() {
                @Override
                public int compare(CityCost o1, CityCost o2) {
                    return o1.cost - o2.cost;
                }
            });
            queue.addAll(map.get(start));
            int sumCost = 0;

            while (!queue.isEmpty()) {
                CityCost next = queue.poll();
                if (visited[next.city]) {
                    continue;
                }
                sumCost += next.cost;
                if (map.containsKey(next.city)) {
                    queue.addAll(map.get(next.city));
                }
            }

            if (checkVisit(visited)) {
                res = Math.min(res, sumCost);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public int minimumCostDFS(int N, int[][] connections) {
        Map<Integer, List<CityCost>> map = new HashMap<>();
        for (int[] connection : connections) {
            int from = connection[0];
            int to = connection[1];
            int cost = connection[2];
            List<CityCost> fromlist = map.getOrDefault(from, new ArrayList<>());
            List<CityCost> tolist = map.getOrDefault(to, new ArrayList<>());
            fromlist.add(new CityCost(to, cost));
            tolist.add(new CityCost(from, cost));
        }

        int res = Integer.MAX_VALUE;
        for (Integer start : map.keySet()) {
            boolean[] visited = new boolean[N + 1];
            int cost = helperDFS(start, map, visited);
            if (checkVisit(visited)) {
                res = Math.min(res, cost);
            }

        }

        return res == Integer.MAX_VALUE ? -1 : res;

    }

    public boolean checkVisit(boolean[] visited) {
        for (boolean res : visited) {
            if (!res) {
                return false;
            }
        }

        return true;
    }

    public int helperDFS(int city, Map<Integer, List<CityCost>> map, boolean[] visited) {

        List<CityCost> list = map.get(city);
        visited[city] = true;
        int res = 0;

        for (CityCost next : list) {
            if (visited[next.city]) {
                continue;
            }

            visited[next.city] = true;
            if (res == 0) {
                res = next.cost + helperDFS(next.city, map, visited);
            } else {
                res = Math.min(res, helperDFS(next.city, map, visited));
            }
            visited[next.city] = false;

        }


        return res;
    }

    class CityCost {
        int city;
        int cost;

        public CityCost(int city, int cost) {
            this.city = city;
            this.cost = cost;
        }
    }

    public int minimumCostUnionFind(int N, int[][] connections) {
        UnionFind unionFind = new UnionFind(N);
        Arrays.sort(connections,(a,b)->(a[2]-b[2]));
        int res = 0;
        for(int[] conn : connections){
            if(unionFind.find(conn[0])!=unionFind.find(conn[1])){
                unionFind.union(conn[0],conn[1]);
                res+=conn[2];
            }

            if(unionFind.count==1){
                return res;
            }
        }

        return -1;

    }

    class UnionFind{
        int[] parent;
        int[] size;
        int count;
        public UnionFind(int n){
            parent = new int[n];
            size = new int[n];
            for(int i=1;i<=n;i++){
                parent[i] = i;
                size[i] = 1;
            }
            count = n;

        }

        public int find(int p){
            while (p!=parent[p]){
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public void union(int p,int q){
            int rootP = find(p);
            int rootQ = find(q);
            if(rootP==rootQ){
                return;
            }
            count--;
            if(size[rootP]<size[rootQ]){
                size[rootQ]+=size[rootP];
                parent[rootP] = rootQ;
            }else {
                size[rootP]+=size[rootQ];
                parent[rootQ] = rootP;
            }
        }
    }
}
