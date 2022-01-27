package winter;

import java.util.*;

public class SummaryRanges {
    TreeMap<Integer,Integer> treeMap;
    public SummaryRanges() {
        treeMap = new TreeMap<>();
    }

    public void addNum(int val) {
        Integer left = treeMap.floorKey(val);

        //完全覆盖在【left,-】区间
        if(left!=null&&left<val&&treeMap.get(left)>=val){
            return;
        }

        Integer right = treeMap.ceilingKey(val);

        //val<=right, right 的最小值
        if(right!=null&&right==val+1){//更新right区间的左边界
            treeMap.put(val,treeMap.get(right));
            treeMap.remove(right);
        }else {
            //不然是一个独立的区间
            treeMap.put(val,val);
        }

        //和左边的区间连起来了，扩张
        if(left!=null&&treeMap.get(left)==val-1){
            treeMap.put(left,treeMap.get(val));
            treeMap.remove(val);
        }
    }

    public int[][] getIntervals() {
        int[][] res = new int[treeMap.size()][2];
        int idx =0;
        for(Map.Entry<Integer,Integer> entry:treeMap.entrySet()){
            res[idx][0] = entry.getKey();
            res[idx][1] = entry.getValue();
            idx++;
        }

        return res;
    }

    public boolean checkPossibility(int[] nums) {
        TreeMap<Integer,Integer> treemap = new TreeMap<>();
        for(int i=0;i<nums.length;i++){
            Integer ceil = treemap.ceilingKey(nums[i]+1);
            if(ceil!=null&&ceil>nums[i]){
                if(treemap.get(ceil)>1){
                    return false;
                }else{
                    treemap.remove(ceil);
                    ceil = treemap.ceilingKey(nums[i]);
                    if(ceil!=null){
                        return false;
                    }
                    treemap.put(nums[i],treemap.getOrDefault(nums[i],0)+1);
                }
            }

            treemap.put(nums[i],treemap.getOrDefault(nums[i],0)+1);
        }

        return true;
    }

    public int kEmptySlots(int[] flowers, int k) {
        TreeSet<Integer> treeSet = new TreeSet<>();

        int n = flowers.length;
        for(int i=0;i<n;i++){
            int cur = flowers[i];//第i天开花的位置
            Integer ceiling = treeSet.ceiling(cur);//当前位置右边离它最近的已经开花的位置
            if(ceiling!=null&&ceiling-cur==k+1){
                return i+1;
            }
            Integer floor = treeSet.floor(cur);//当前位置左边离它最近的已经开花的位置
            if(floor!=null&&cur-floor==k+1){
                return i+1;
            }
            treeSet.add(cur);
        }

        return -1;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> res = new ArrayList<>();
        TreeMap<Integer,Integer> startHeight = new TreeMap<>();
        startHeight.put(0,0);
        int max = 0;
        for(int[] pos:positions){
            int start = pos[0];
            int end = pos[1];
            Integer floor = startHeight.floorKey(start);
            int height = startHeight.subMap(floor,end).values().stream().max(Integer::compare).get()+pos[1];
            max = Math.max(height,max);
            res.add(max);
            int lastHeight = startHeight.floorEntry(end).getValue();
            startHeight.put(start,height);
            startHeight.put(end,lastHeight);
            startHeight.keySet().removeAll(new HashSet<>(startHeight.subMap(start,false,end,false).keySet()));
        }

        return res;
    }

        public static void main(String[] args) {
        SummaryRanges test = new SummaryRanges();
        test.kEmptySlots(new int[]{1,3,2},1);
    }
}
