package unlock;

import java.util.*;

public class TimeMap {
    class Data{
        int timestamp;
        String value;
        public Data(String value, int timestamp){
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    Map<String, List<Data>> map;
    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        Data node = new Data(value,timestamp);
        map.putIfAbsent(key,new ArrayList<>());
        map.get(key).add(node);

    }

    public String get(String key, int timestamp) {
        if(!map.containsKey(key)){
            return "";
        }else {
            List<Data> list = map.get(key);
            int left =0;
            int right = list.size();
            //二分查找，找到插入位置最右边界，
            // 左边的值<=target,右边的值严格大于target
            while (left<right){
                int mid = left+(right-left)/2;
                if(list.get(mid).timestamp<=timestamp){
                    left = mid+1;
                }else {
                    right = mid;
                }
            }

            //没有比当前值更小的存储记录，说明这个时间点之前没有值
            if(left==0){
                return "";
            }

            //找到<=当前时间点的最大时间点对应的值
            return list.get(left-1).value;
        }
    }


    class StationTime{
        int time;
        String stationName;
        public StationTime(int time,String stationName){
            this.time = time;
            this.stationName = stationName;
        }
    }

    class StationCount{
        int sum;
        int count;
        public StationCount(int sum,int count){
            this.sum = sum;
            this.count = count;
        }
    }

    public int singleNonDuplicate(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        //数组长度一定是奇数
        while (low<=high){
            int mid = low+(high-low)/2;
            //对于成对出现的数字，
            if(mid%2==0){
                //mid is even
                //假如mid 是偶数index，那么和它成对的数一定出现在它后面，检查后面这个值就行
                if(nums[mid]==nums[mid+1]){//相等，说明只出现一次的数一定在mid后面，因为只有在后面才不打乱规律

                    low = mid+2;
                }else {//不然的话，就在前面，同时mid自己也不能排除嫌疑
                    high = mid;
                }
            }else {//假如mid 是奇数index，同理
                //mid is odd
                if(nums[mid]==nums[mid-1]){
                    low = mid-1;
                }else {
                    high = mid;
                }
            }
        }

        return nums[low];
    }

    public int searchInsert(int[] nums, int target) {
        int start =0;
        int end = nums.length;
        while(start<end){
            int mid = start+(end-start)/2;
            if(nums[mid]>=target){
                end = mid;
            }else{
                start = mid+1;
            }
        }
        return start;
    }
//    ["TimeMap","set","set","get","get","get","get","get"]
//            [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
//


    public int findRadius(int[] houses, int[] heaters) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for(int heater:heaters){
            treeSet.add(heater);
        }

        int res = 0;

        for(int house:houses){
            Integer upper = treeSet.ceiling(house);
            Integer lower = treeSet.floor(house);
            res =Math.max(res,Math.min(upper==null?Integer.MAX_VALUE:upper-house,lower==null? Integer.MAX_VALUE:house-lower));
        }

        return res;
    }
        public static void main(String[] args) {
//        ["TimeMap","set","get","get","set","get","get"]
//[[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
      TimeMap test = new TimeMap();
      test.set("foo","bar",1);
      String res =test.get("foo",1);
      System.out.println(res);
      test.get("foo",3);
      test.set("foo","bar2",4);
      test.get("foo",4);
      test.get("foo",5);


    }
}
