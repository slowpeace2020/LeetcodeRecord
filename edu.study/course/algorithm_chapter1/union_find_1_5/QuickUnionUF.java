package course.algorithm_chapter1.union_find_1_5;

public class QuickUnionUF {
    private int[] parent;
    private int count;


    public QuickUnionUF(int n){
        parent = new int[n];
        count = n;
        for(int i=0;i<n;i++){
            parent[i] = i;
        }
    }

    public int count(){
        return count;
    }

    public int find(int p){
        while (p!=parent[p]){
            p = parent[p];
        }

        return p;
    }

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }

    /**
     * find 原来的连接方法是不同的一个个检查更新，
     * union 这里改进赋值以后
     * 只改当前点的root的parent值
     * @param p
     * @param q
     */
    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP!=rootQ){
            parent[rootP] = rootQ;
            count--;
        }

    }




}
