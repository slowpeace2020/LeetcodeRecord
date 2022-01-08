package unlock;

import java.util.ArrayList;
import java.util.List;

class RangeModule {
    static class SegmentTree{
      int start;
      int end;
      boolean tracked;
      SegmentTree left;
      SegmentTree right;

      public SegmentTree(int start,int end,boolean tracked){
        this.start = start;
        this.end = end;
        this.tracked = tracked;
      }

    }

    SegmentTree root;
  public RangeModule() {
    root = new SegmentTree(0,(int)10e9,false);
  }

  public void addRange(int left, int right) {
    update(root,left,right,true);
  }

  public boolean queryRange(int left, int right) {
    return query(root,left,right);
  }

  private boolean query(SegmentTree root, int start, int end) {
    if(root==null||start>=root.end||end<=root.start){//不在当前区间范围，默认为TRUE，去别处找
      return true;
    }

    //当前节点的范围是查找区间的子集，或者当前节点区间和查找区间有交集并且状态统一，直接返回当前区间状态
    if((start<=root.start&&end>=root.end)||(root.left==null)){
      return root.tracked;
    }

    //当前节点区间和查找区间有交集并且状态不统一
    //左右子区间分别查找
    int mid = root.start+(root.end-root.start)/2;
    if(end<=mid){
      return query(root.left,start,end);
    }else if(start>=mid){
      return query(root.right,start,end);
    }
    return query(root.left,start,end)&&query(root.right,start,end);
  }

  public void removeRange(int left, int right) {
    update(root,left,right,false);
  }

  public boolean update(SegmentTree node,int start,int end,boolean tracked){
    if(start<=node.start&&end>=node.end){//当前节点在子区间，一个状态，子节点状态无须区分
      node.tracked = tracked;
      node.left = null;   //清空左右子节点，节省空间
      node.right = null;
      return node.tracked;
    }

    if(start>=node.end||end<=node.start){//更新范围在当前节点范围之外，直接返回当前节点状态
      return node.tracked;
    }

    //当前节点范围和更新范围有交集，需要划分子节点区间去更新
    int mid = start+(end-start)/2;
    if(node.left==null){//区间内部状态分化，划分子节点区间去更新
      //构造左右子节点,还是保持原先的状态
      node.left = new SegmentTree(node.start,mid,node.tracked);
      node.right = new SegmentTree(mid,node.end,node.tracked);
    }

    //分别去左右子节点区间寻找更新范围
    boolean leftStatus = update(node.left,start,end,tracked);
    boolean rightStatus = update(node.right,start,end,tracked);
    node.tracked = leftStatus&&rightStatus;//左右子节点均在范围内，parent节点的状态在
    if(node.tracked){
      node.left = null;
      node.right = null;
    }
    return node.tracked;
  }

  class TreeNode{
    int val;
    int rank;//表示当前节点的左子树节点数量，包括自己
    TreeNode left;
    TreeNode right;
    public  TreeNode(int val){
      this.val = val;
      this.rank = 1;
    }
  }

  public int insertTreeNode(TreeNode root,int val){
    int res  = 0;
    while (true){//一直找下去
      if(val<=root.val){//在当前节点的左半边
        root.rank++;//小于等于当前节点的数量又多了一个，更新
        if(root.left==null){//找到头都比当前的值大
          root.left = new TreeNode(val);//增加新节点
          break;
        }else {
          root = root.left;
        }
      }else{
        res+=root.rank;//当前节点的值以及左子树都比val小，说明val的排名至少在当前节点之后
        if(root.right==null){//到头了，添加新节点
          root.right = new TreeNode(val);
          break;
        }else {//继续找
          root = root.right;
        }
      }
    }

    return res;
  }


  public List<Integer> countSmaller(int[] nums) {
    int n = nums.length;
    List<Integer> res = new ArrayList<>();
    if(n==0){
      return res;
    }
    TreeNode root = new TreeNode(nums[n-1]);
    for(int i=n-2;i>=0;i--){
      int count = insertTreeNode(root,nums[i]);
      res.add(0,count);

    }
    return res;
  }


  public int getIndex(List<Integer> list, int target){
    int left = 0;
    int right=list.size();
    while (left<right){
      int mid = left+(right-left)/2;
      if(list.get(mid)<target){
        left = mid+1;
      }else {
        right = mid;
      }
    }
    list.add(left,target);
    return left;
  }


    public static void main(String[] args) {
    RangeModule rangeModule = new RangeModule();
    rangeModule.addRange(8,9);
    rangeModule.queryRange(1,8);
    rangeModule.removeRange(1,8);
    rangeModule.queryRange(5,8);
    rangeModule.removeRange(3,9);
    rangeModule.addRange(8,9);
    rangeModule.queryRange(4,5);
    rangeModule.removeRange(2,9);
    rangeModule.addRange(5,6);

  }

}




