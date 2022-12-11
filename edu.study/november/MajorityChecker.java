package november;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MajorityChecker {
//    class SegmentTree{
//        int start,end;
//        Map<Integer,Integer> map;
//        SegmentTree left,right;
//        public SegmentTree(int start,int end){
//            this.start = start;
//            this.end = end;
//            map = new TreeMap<>();
//        }
//    }
//
//    SegmentTree root;
//
//    private void update(SegmentTree root,int index,int val){
//        root.map.put(val,root.map.getOrDefault(val,0)+1);
//        if(root.start==root.end){
//            return;
//        }
//        int mid = (root.start+root.end)>>1;
//        if(index<=mid){
//            if(root.left==null){
//                root.left = new SegmentTree(root.start,mid);
//            }
//            update(root.left,index,val);
//        }else {
//            if(root.right==null){
//                root.right = new SegmentTree(mid+1,root.end);
//            }
//            update(root.right,index,val);
//        }
//    }
//
//    private Map<Integer,Integer> querySegment(SegmentTree root,int start,int end){
//        Map<Integer,Integer> res = new TreeMap<>();
//        if(root==null||root.start>end||root.end<start){
//            return res;
//        }
//
//        if(root.start>=start&&root.end<=end){
//            res.putAll(root.map);
//            return res;
//        }
//
//        int mid = (root.start+root.end)>>1;
//        if(start>mid){
//            return querySegment(root.right,start,end);
//        }
//
//        if(end<=mid){
//            return querySegment(root.left,start,end);
//        }
//
//        res = querySegment(root.left,start,end);
//        Map<Integer,Integer> right = querySegment(root.right,start,end);
//        for(Integer key:right.keySet()){
//            res.put(key,res.getOrDefault(key,0)+right.get(key));
//        }
//        return res;
//    }
//
//
//
//    public MajorityChecker(int[] arr) {
//        int n = arr.length;
//        root = new SegmentTree(0,n-1);
//        for(int i=0;i<n;i++){
//            update(root,i,arr[i]);
//        }
//    }
//
//    public int query(int left, int right, int threshold) {
//        Map<Integer,Integer> map = querySegment(root,left,right);
//        int res = -1;
//        int count = 0;
//
//        for(Integer key:map.keySet()){
//            if(map.get(key)>=threshold&&map.get(key)>count){
//                res = key;
//                count = map.get(key);
//            }
//        }
//
//        return res;
//    }


    //  摩尔投票加线段树
    SegmentTreeNode root;
    int[] arrCopy;
    public MajorityChecker(int[] arr) {
        root = build(0, arr.length - 1, arr);
        arrCopy = arr;
    }

    public int query(int left, int right, int threshold) {
        //  调用queryTree方法 返回left,right区间众数
        SegmentTreeNode node = queryTree(root, left, right);
        if (node == null) return -1;
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (arrCopy[i] == node.candidate) count += 1;
        }
        if (count >= threshold) return node.candidate;
        return -1;
    }

    public void merge(SegmentTreeNode root) {
        SegmentTreeNode left = root.leftChild;
        SegmentTreeNode right = root.rightChild;
        if (left == null && right == null) return;
        if (left != null) {
            root.set(left.candidate, left.count);
        }
        if (right != null) {
            if (root.candidate == right.candidate) {
                root.count += right.count;
            } else if (root.count > right.count) {
                root.set(root.candidate, root.count - right.count);
            } else {
                root.set(right.candidate, right.count - root.count);
            }
        }
    }

    public SegmentTreeNode build(int left, int right, int[] nums) {
        if (left > right) return null;
        SegmentTreeNode root = new SegmentTreeNode(left, right);
        if (left == right) {
            root.set(nums[left], 1);
            return root;
        }
        int mid = left + (right - left) / 2;
        root.leftChild = build(left, mid, nums);
        root.rightChild = build(mid + 1, right, nums);
        merge(root);
        return root;
    }

    public SegmentTreeNode queryTree(SegmentTreeNode root, int left, int right) {
        if (left > right || root == null) return null;
        if (root.left == left && root.right == right) return root;
        int mid = root.left + (root.right - root.left) / 2;
        if (right <= mid) return queryTree(root.leftChild, left, right);
        if (mid < left) return queryTree(root.rightChild, left, right);

        SegmentTreeNode leftNode = queryTree(root.leftChild, left, mid);
        SegmentTreeNode rightNode = queryTree(root.rightChild, mid + 1, right);
        SegmentTreeNode temp = new SegmentTreeNode(left, right);
        temp.leftChild = leftNode;
        temp.rightChild = rightNode;
        merge(temp);
        return temp;
    }

    class SegmentTreeNode {
        SegmentTreeNode leftChild;
        SegmentTreeNode rightChild;
        int left;
        int right;
        int candidate;
        int count;

        SegmentTreeNode(int left, int right) {
            this.left = left;
            this.right = right;
            this.leftChild = null;
            this.rightChild = null;
        }

        public void set(int candidate, int count) {
            this.candidate = candidate;
            this.count = count;
        }
    }


    public static void main(String[] args) {
//["MajorityChecker","query","query","query","query","query","query","query","query","query","query"]
//[[[2,2,1,2,1,2,2,1,1,2]],
//[2,5,4],
//[0,5,6],
//[0,1,2],
//[2,3,2],
//[6,6,1],
//[0,3,3],
//[4,9,6],
//[4,8,4],
//[5,9,5],
//[0,1,2]]
        MajorityChecker checker = new MajorityChecker(new int[]{2,2,1,2,1,2,2,1,1,2});
        System.out.println(checker.query(2,5,4));
        System.out.println(checker.query(0,5,6));
        System.out.println(checker.query(0,1,2));
        System.out.println(checker.query(2,3,2));
//        System.out.println(checker.query(2,3,2));
//        System.out.println(checker.query(2,3,2));
//        System.out.println(checker.query(2,3,2));
    }


}
