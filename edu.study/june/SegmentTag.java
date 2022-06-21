package june;

import java.util.*;

public class SegmentTag {
    public List<List<Integer>> getSkylineI(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        List<int[]> builds = new ArrayList<>();

        for(int[] build:buildings){
            builds.add(new int[]{build[0],-build[2]});
            builds.add(new int[]{build[1],build[2]});
        }

        Collections.sort(builds,(a,b)->(a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b)->(b-a));

        int preHeight = 0;
        maxHeap.offer(preHeight);
        for(int[] build:builds){
            if(build[1]<0){
                maxHeap.offer(-build[1]);
            }else {
                maxHeap.remove(build[1]);
            }

            int cur = maxHeap.peek();
            if(cur!=preHeight){
                res.add(Arrays.asList(build[0],cur));
                preHeight = cur;
            }
        }

        return res;
    }


    public List<int[]> getSkyline(int[][] buildings)
    {
        final int n = buildings.length;
        int[][] buildingHeights = new int[n*2][];
        for(int i=0; i<n; i++)
        {                     //  building index, coord, height
            buildingHeights[2*i+0] = new int[]{i, buildings[i][0], buildings[i][2]};
            buildingHeights[2*i+1] = new int[]{i, buildings[i][1], 0};
        }
        Arrays.sort(buildingHeights, (h1,h2)->h1[1]-h2[1]);  // sort by coord


        int N=1;  // N = 2^m
        while(N<n) N*=2;
        int[] tree = new int[2*N-1];

        ArrayList<int[]> results = new ArrayList<>();
        for(int[] buildingHeight : buildingHeights)
        {
            int index  = buildingHeight[0];
            int coord  = buildingHeight[1];
            int height = buildingHeight[2];

            int maxHeight = update(tree, N, 0, index, height);

            results.add(new int[]{coord, maxHeight});
            while(results.size()>1) // merge
            {
                int[] a1 = results.get(results.size()-1);
                int[] a2 = results.get(results.size()-2);
                if(a2[0]!=a1[0] && a2[1]!=a1[1])
                    break;
                if(a2[0]==a1[0])
                    a2[1]=a1[1];
                results.remove(results.size()-1);
            }
        }
        return results;
    }

    static int update(int[] tree, int N, int node, int leaf, int value)
    {
        if(node>N-2)
            return tree[node]=value;

        if((leaf&1)==0)
            return tree[node]=Math.max(update(tree, N, 2*node+1, leaf>>1, value), tree[2*node+2]);
        else
            return tree[node]=Math.max(update(tree, N, 2*node+2, leaf>>1, value), tree[2*node+1]);
    }


    class Node{
        int start, end, maximum;
        Node left, right;

        public Node(int i, int j){
            start = i;
            end = j;
            maximum = 0;
            left = null;
            right = null;
        }
    }

    class SegmentTree{
        Node root;

        public SegmentTree(int[] nums){
            int n = nums.length;
            this.root = buildTree(nums, 0, n - 1);
        }

        private Node buildTree(int[] nums, int i, int j){
            if(i >= j){
                return null;
            }
            if(i + 1 == j){
                return new Node(nums[i], nums[j] - 1);
            }
            Node node = new Node(nums[i], nums[j] - 1);
            int mid = i + (j - i) / 2;
            node.left = buildTree(nums, i, mid);
            node.right = buildTree(nums, mid, j);
            return node;
        }

        public void update(Node root, int i, int j, int val){
            if(i > j){
                return;
            }
            if(root.start == i && root.end == j && root.left == null){
                root.maximum = Math.max(root.maximum, val);
                return;
            }
            int mid = root.left.end;
            if(mid < i){
                update(root.right, i, j, val);
            }else if(j <= mid){
                update(root.left, i, j, val);
            }else{
                update(root.left, i, mid, val);
                update(root.right, mid + 1, j, val);
            }
            root.maximum = Math.max(root.left.maximum, root.right.maximum);
        }

        public int rangeMax(Node root, int i, int j){
            if(i > j){
                return 0;
            }

            if(i <= root.start && root.end <= j){
                return root.maximum;
            }

            int mid = root.left.end;
            if(j <= mid){
                return rangeMax(root.left, i, j);
            }
            if(i > mid){
                return rangeMax(root.right, i, j);
            }
            return Math.min(rangeMax(root.left, i, mid), rangeMax(root.right, mid + 1, j));
        }
    }


    public List<List<Integer>> getSkylineII(int[][] buildings) {
        Set<Integer> set = new HashSet<>();
        for(int[] building: buildings){
            set.add(building[0]);
            set.add(building[1]);
        }
        int[] x = new int[set.size()];
        int idx = 0;
        for(int i : set){
            x[idx++] = i;
        }
        Arrays.sort(x);
        SegmentTree st = new SegmentTree(x);
        for(int[] building: buildings){
            st.update(st.root, building[0], building[1] - 1, building[2]);
        }
        int prev = 0;
        List<List<Integer>> res = new ArrayList<>();
        for(int i = 0; i < x.length - 1; i++){
            int h = st.rangeMax(st.root, x[i], x[i + 1] - 1);
            if(h != prev){
                res.add(Arrays.asList(x[i], h));
                prev = h;
            }
        }
        res.add(Arrays.asList(x[x.length - 1], 0));
        return res;
    }


}
