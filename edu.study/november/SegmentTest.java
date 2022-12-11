package november;

import java.util.ArrayList;
import java.util.List;

public class SegmentTest {

    class SegmentTreeNode{
        int start;
        int end;
        int count;
        SegmentTreeNode left,right;
        public SegmentTreeNode(int start,int end){
                this.start = start;
                this.end = end;
        }

    }

    // build empty map
    private SegmentTreeNode build(int start, int end) {
        if (start > end) return null;
        if (start == end) return new SegmentTreeNode(start, end);
        int mid = (start + end) / 2;
        SegmentTreeNode node = new SegmentTreeNode(start, end);
        node.left = build(start, mid);
        node.right = build(mid + 1, end);
        return node;
    }

    // modify leaf segment node with value, and update all parents alone the way.
    private void modify(SegmentTreeNode root, int value, int count) {
        if (root.start == value && root.end == value) {
            root.count += count;
            return;
        }
        int mid = (root.start + root.end) / 2;
        if (value <= mid) modify(root.left, value, count);
        else modify(root.right, value, count);
        root.count = root.left.count + root.right.count;
    }

    // start query: get count # of elements within range [start, end]
    private int query(SegmentTreeNode root, int start, int end) {
        if (root == null) return 0;
        if (root.start == start && root.end == end) return root.count;

        int mid = (root.start + root.end) / 2;
        if (end < mid) return query(root.left, start, end);
        if (start > mid) return query(root.right, start, end);
        // start <= mid < end
        return query(root.left, start, mid) + query(root.right, mid + 1, end);
    }

    private int[] findRange(int[] nums) {
        int diff = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        if (min < 0) {
            diff = Math.abs(min);
            max += diff;
            min += diff;
        }
        return new int[]{min, max, diff};
    }

    SegmentTreeNode root;
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if(nums == null || nums.length == 0)  return result;
        // Find range
        int[] range = findRange(nums);
        int n = nums.length, min = range[0], max = range[1], offset = range[2];

        // build tree
        root = build(min, max);
        for (int num : nums) modify(root, num + offset, 1);

        // remove curr element, and query for all remaining elements
        for (int num : nums) {
            modify(root, num + offset, -1);
            result.add(query(root, min, num + offset - 1));
        }

        return result;
    }
}
