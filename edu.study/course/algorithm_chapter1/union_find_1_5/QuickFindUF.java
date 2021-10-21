package course.algorithm_chapter1.union_find_1_5;

public class QuickFindUF {
    private int[] id;
    private int count;

    public QuickFindUF(int n){
        count = n;
        for(int i=0;i<n;i++){
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        return id[p];
    }

    public boolean connected(int p, int q){
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pID = id[p];   // needed for correctness
        int qID = id[q];   // to reduce the number of array accesses

        // p and q are already in the same component
        if (pID == qID) return;

        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) id[i] = qID;
        count--;
    }

}
