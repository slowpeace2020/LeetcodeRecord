package november;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Segment2022 {


    public List<Integer> countSmaller(int[] nums) {
        //初始化
        Integer[] res = new Integer[nums.length];
        Arrays.fill(res, 0);
        List<Integer> list = new ArrayList<>();
        //反向构造二叉树，统计右边的较小数
        TreeNode root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = addAndCount(root, new TreeNode(nums[i]), res, i);
        }
        return Arrays.asList(res);
    }

    public TreeNode addAndCount(TreeNode root, TreeNode node, Integer[] res, int i) {
        if (root == null) {
            root = node;
            return root;
        }
        //根节点的左边保存不大于根节点的元素
        if (root.val >= node.val) {
            //统计左节点的元素个数
            root.count++;
            root.left = addAndCount(root.left, node, res, i);
        } else {
            //走到右边获取该元素左边的个数（根节点 1 + 左节点 root.count）
            res[i] += 1 + root.count;
            //统计右边是否还有更小的元素
            root.right = addAndCount(root.right, node, res, i);
        }
        return root;
    }
}

class TreeNode {
    int val;
    int count;
    TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.count = 0;
        left = null;
        right = null;
    }


    public static void main(String[] args) {
        Segment2022 te = new Segment2022();
        te.countSmaller(new int[]{-1, -2});
    }
}
