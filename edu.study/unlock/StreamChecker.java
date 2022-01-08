package unlock;

import java.util.*;

public class StreamChecker {


    class Node{
        Node[] children;
        boolean endOfWord;
        public Node() {
            children = new Node[26];
            endOfWord = false;
        }
    }

    Node root;
    StringBuilder sb;

    private void insert(String word) {
        Node cur = root;
        for(int i = word.length() - 1; i >= 0; i--) {
            char ch = word.charAt(i);
            Node next = cur.children[ch - 'a'];
            if(next == null) {
                cur.children[ch - 'a'] = next = new Node();
            }
            cur = next;
        }
        cur.endOfWord = true;
    }

    public StreamChecker(String[] words) {
        root = new Node();
        sb = new StringBuilder();
        for(String word : words) {
            insert(word);
        }
    }

    public boolean query(char letter) {
        boolean res = false;

        sb.append(letter);
        Node cur = root;
        for(int i = sb.length() - 1; i >= 0; i--) {
            char ch = sb.charAt(i);
            if(cur.children[ch - 'a'] == null) {
                return false;
            }
            cur = cur.children[ch - 'a'];
            if(cur.endOfWord) return true;
        }

        return false;
    }

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        int[] root = new int[m*n];
        Arrays.fill(root,-1);
        List<Integer> res = new ArrayList<>();
        int count = 0;//小岛数量
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};//四个方向
        for(int[] pos : positions){
            int x = pos[0];
            int y = pos[1];
            int index = x*n+y;   //二维坐标转化成一维
            root[index] = index;  //添加新岛
            count++;
            for(int[] dir:dirs){//检查四个相邻方向的岛屿情况
                int i = x+dir[0];
                int j = y+dir[1];
                int idx = i*n+j;
                if(i<0||j<0||i>=m||j>=n){//出界跳过
                    continue;
                }

                int parent = findRoot(idx,root);//相邻岛屿所属的根节点
                if(parent!=index){//非同一个岛
                    root[index] = parent;//连接起来
                    index = parent;//根节点更新为相同值
                    count--;//岛屿数量减少
                }
            }

            res.add(count);
        }

        return res;
    }

    public int findRoot(int p, int[] root){
        while (root[p]!=p){
            p=root[p];
        }

        return p;
    }

    public int countComponents(int n, int[][] edges) {
        int[] root = new int[n];

        //初始化，每个节点视为一个component
        for(int i=0;i<root.length;i++){
            root[i] = i;
        }

        int count = n;

        for(int[] edge:edges){
            int rootP = findRoot(edge[0],root);
            int rootQ = findRoot(edge[1],root);
            //边相当于连通操作
            if(rootP!=rootQ){
                root[rootQ] = rootP;
                count--;
            }
        }

        return count;
    }



    //全局变量累计满足条件的数量
    int countRange = 0;
    public int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length;
        int[] presum = new int[n+1];//转化为前缀和数组
        presum[0] = nums[0];
        for(int i=0;i<n;i++){
            presum[i+1] = presum[i]+nums[i];
        }

        //分治法递归查找
        helperCountRangeSum(presum,0,n,lower, upper);
        return countRange;
    }

    //在一定数组范围内找到满足条件的组合数量
    private void helperCountRangeSum(int[] presum, int start, int end, int lower, int upper) {
        int mid = start+(end-start)/2;
        //分治递归查找
        //找到左边内部满足条件的组合
        helperCountRangeSum(presum,start,mid,lower,upper);
        //找到右边内部满足条件的组合
        helperCountRangeSum(presum,mid+1,end,lower,upper);
        //左边的i和右边的j组合满足条件的数量
        for(int i=start;i<=mid;i++){
            //二分查找右边界
            int upperIndex = upperBound(presum,mid+1,end,upper);
            //二分查找左边界
            int lowerIndex = lowerBound(presum,mid+1,end,lower);
            countRange+=upperIndex-lowerIndex;
        }

        //找到内部组合以后，归并排序，使得更大数组范围内部有序
        merge(presum,start,mid,end);

    }

    //归并排序
    private void merge(int[] presum, int start, int mid, int end) {
        int[] temp = new int[end-start+1];
        int i=start;
        int j = mid+1;
        int index = 0;
        while (i<=mid&&j<=end){
            if(presum[i]<presum[j]){
                temp[index++] = presum[i++];
            }else {
                temp[index++] = presum[j++];
            }
        }

        while (i<=mid){
            temp[index++] = presum[i++];
        }

        while (j<=end){
            temp[index++] = presum[j++];
        }

        System.arraycopy(temp,0,presum,start,index);
    }

    //找到>=target的最左边index，包括
    private int lowerBound(int[] nums, int left, int right, int target){
        while (left<right){
            int mid = left+(right-left)/2;
            if(nums[mid]<target){
                left = mid+1;
            }else {
                right = mid;
            }
        }

        return right;
    }

    //找到<=target的最右边index，不包括
    private int upperBound(int[] nums, int left, int right, int target){
        while (left<right){
            int mid = left+(right-left)/2;
            if(nums[mid]>target){
                right = mid;
            }else {
                left = mid+1;
            }
        }

        return right;
    }


    public static void main(String[] args) {
        StreamChecker streamChecker = new StreamChecker(new String[]{"cd","f","kl"}); // 初始化字典

        int[] nums = new int[]{1,1,2,3,4,4,5};
      int upperIndex =  streamChecker.upperBound(nums,0,nums.length-1,4);
      int lowerIndex =  streamChecker.lowerBound(nums,0,nums.length-1,4);

    }
}
