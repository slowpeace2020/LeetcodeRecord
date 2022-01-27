package winter;

import java.util.*;

public class Skyline {

    class SegmentNode{
        int start,end,height;
        SegmentNode left,right;
        public SegmentNode(int start,int end,int height){
            this.start = start;
            this.end = end;
            this.height = height;
        }
    }


    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        List<int[]> builds = new ArrayList<>();
        for(int[] build:buildings){
            //正负区分左右边界
            builds.add(new int[]{build[0],-build[2]});
            builds.add(new int[]{build[1],build[2]});
        }

        //按照边界大小，高度，从小到大排序
        Collections.sort(builds,(a,b)->(a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b)->(b-a));
        //记录重叠区间之前的最大高度
        int preHeight = 0;
        maxHeap.offer(0);
        for(int[] build:builds){
            //左边界，新建筑进来，有新高度
            if(build[1]<0){
                maxHeap.offer(-build[1]);
            }else {
                //右边界，旧建筑出去，它的高度去除
                maxHeap.remove(build[1]);
            }

            //重叠区间的当前最大高度
            int cur = maxHeap.peek();
            //和重叠区间之前的最大高度不同，天际线产生
            if(cur!=preHeight){
                res.add(Arrays.asList(build[0],cur));
                //更新重叠区间的高度
                preHeight = cur;
            }
        }

        return res;
    }
    public int[] getModifiedArray(int length, int[][] updates) {
        // Write your code here
        int[] res = new int[length];
        for(int[] up:updates){
            int start = up[0];
            int end = up[1];
            int val = up[2];
            res[start]+=val;
            if(end+1<length){
                res[end+1]-=val;
            }
        }

        for(int i=1;i<length;i++){
            res[i]=res[i-1]+res[i];
        }

        return res;
    }
}
