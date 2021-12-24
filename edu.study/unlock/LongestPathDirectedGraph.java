package unlock;

import java.util.ArrayList;

public class LongestPathDirectedGraph {
    int vertices;
    ArrayList<Integer> edge[];

    LongestPathDirectedGraph(int vertices){
        this.vertices = vertices;
        edge = new ArrayList[vertices+1];
        for(int i=1;i<=vertices;i++){
            edge[i] = new ArrayList<>();
        }
    }

    void addAedge(int a,int b){
        edge[a].add(b);
    }

    void dfs(int vertice, ArrayList<Integer> adj[],int dp[],boolean[] visited){
        //标记访问过的节点
        visited[vertice] = true;
        //遍历所有子节点
        for(int i=0;i<adj[vertice].size();i++){
            int next = adj[vertice].get(i);
            //继续dfs未访问
            if(!visited[next]){
                dfs(next,adj,dp,visited);
            }
            //更新最长路径
            dp[vertice] = Math.max(dp[vertice],1+dp[next]);
        }
    }

    //寻找最长路径
    public int findLongestPath(int n){
        ArrayList<Integer> adj[] = edge;
        //动态规划记录结果
        int[] dp = new int[n+1];

        boolean[] visited = new boolean[n+1];

        //对没有访问的节点进行DFS
        for(int i=1;i<=n;i++){
            if(!visited[i]){
                dfs(i,adj,dp,visited);
            }
        }

        int ans =0;
        for(int i=0;i<dp.length;i++){
            ans = Math.max(ans,dp[i]);
        }

        return ans;
    }

}
