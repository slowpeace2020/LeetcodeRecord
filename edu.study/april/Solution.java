package april;

import java.util.*;

public class Solution {
    int size;
    Map<Integer,Integer> map;
    public Solution(int n, int[] blacklist) {
        int m = blacklist.length;
        this.size = n-m;
        map = new HashMap<>();
        for(int num:blacklist){
            map.put(num,-1);
        }

        for(int num:blacklist){
            if(num<size){
                while (map.containsKey(n-1)){
                    n--;
                }
                map.put(num,n-1);
                n--;
            }
        }

    }

    public Solution() {

    }

    public int pick() {
        Random random = new Random();
        int res = random.nextInt(size);
        if(map.containsKey(res)){
            return map.get(res);
        }
        return res;
    }


    public int minOperations(int[][] grid, int x) {
        int m = grid.length;
        int n = grid[0].length;

        int val =grid[0][0];
        List<Integer> list = new ArrayList<>();

        for(int i=0;i<m;i++){
            if(!canReach(grid[i],val,x)){
                return -1;
            }
            val = grid[i][0];

            for(int num:grid[i]){
                list.add(num);
            }
        }

        Collections.sort(list);

        int i =0;
        int j = list.size()-1;
        int res = 0;
        while(i<j){
            int step = (list.get(j)-list.get(i))/x;
            res+=step;
            i++;
            j--;
        }

        return res;

    }


    private boolean canReach(int[] nums,int val,int x){
        for(int num:nums){
            if((num-val)%x!=0){
                return false;
            }
        }

        return true;
    }

    public List<Integer> majorityElement(int[] nums) {
        int count1=0;
        int count2=0;
        Integer major1=null;
        Integer major2=null;

        for(int num:nums){
            if(count1!=0&&num==major1){
                count1++;
            }else if(count2!=0&&num==major2){
                count2++;
            }else if(count1==0){
                major1 = num;
                count1++;
            }else if(count2==0){
                major2=num;
                count2++;
            }else{
                count1--;
                count2--;
                if(count1==0){
                    major1=null;
                }

                if(count2==0){
                    major2=null;
                }
            }
        }

        count1=0;
        count2=0;
        for(int num:nums){
            if(num==major1){
                count1++;
            }
            if(major2!=null&&num==major2){
                count2++;
            }
        }

        List<Integer> res = new ArrayList<>();
        if(count1>nums.length/3){
            res.add(major1);
        }
        if(count2>nums.length/3){
            res.add(major2);
        }

        return res;
    }

    Set<Integer> set = new HashSet<>();
    public boolean isHappy(int n) {
        if(n==1){
            return true;
        }
        if(n==2){
            return false;
        }
        if(set.contains(n)){
            return false;
        }
        set.add(n);
        int next = 0;
        while(n>0){
            int num = n%10;
            next+=num*num;
            n=n/10;
        }

        return isHappy(next);
    }

    public int maxPoints(int[][] points) {
        int maxCount = 0;
        int same=1;
        int sameY=1;
        int n = points.length;
        for(int i=0;i<n;i++){
            same = 1;
            sameY =1;
            Map<Double,Integer> map = new HashMap<>();
            for(int j=i+1;j<n;j++){
                if(points[i][1]==points[j][1]){
                    sameY++;
                    if(points[i][0]==points[j][0]){
                        same++;
                    }
                }else{
                    double dx = points[i][0]-points[j][0];
                    double dy = points[i][1]-points[j][1];
                    map.put(dx/dy+0.0,map.getOrDefault(dx/dy+0.0,0)+1);
                }
            }

            maxCount = Math.max(maxCount,sameY);
            for(Double key:map.keySet()){
                maxCount = Math.max(maxCount,same+map.get(key));
            }

        }

        return maxCount;
    }

    public int[] advantageCount(int[] nums1, int[] nums2) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        for(int num:nums1){
            map.put(num,map.getOrDefault(num,0)+1);
        }

        int n = nums1.length;
        int[] res = new int[n];
        for(int i=0;i<n;i++){
            Integer val = map.ceilingKey(nums2[i]);
            if(val==null){
                res[i] = -1;
                continue;
            }
            res[i] = val;
            if(map.get(val)==1){
                map.remove(val);
            }else {
                map.put(val,map.get(val)-1);
            }
        }

        for(int i=0;i<n;i++){
            if(res[i]==-1){
                Integer val = map.floorKey(nums2[i]);
                res[i] = val;
                if(map.get(val)==1){
                    map.remove(val);
                }else {
                    map.put(val,map.get(val)-1);
                }
            }
        }

        return res;

    }

    private int compare(String log1, String log2){
        int s1 = log1.indexOf(' ') + 1;
        int s2 = log2.indexOf(' ') + 1;
        if(Character.isDigit(log1.charAt(s1)) && Character.isDigit(log2.charAt(s2))){
            return 0;
        }else if(Character.isDigit(log1.charAt(s1))){
            return 1;
        }else if(Character.isDigit(log2.charAt(s2))){
            return -1;
        }else{
            int cmp = log1.substring(s1).compareTo(log2.substring(s2));
            if(cmp == 0) return log1.compareTo(log2);
            else return cmp;
        }
    }

    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, new Comparator<String>() {
            @Override
            public int compare(String s, String t) {
                String[] ss = s.split(" ");
                String[] tt = t.split(" ");
                if(Character.isDigit(ss[1].trim().charAt(0))&&Character.isDigit(tt[1].trim().charAt(0))){
                    return 0;
                }else if(Character.isDigit(ss[1].trim().charAt(0))){
                    return 1;
                }else if(Character.isDigit(tt[1].trim().charAt(0))){
                    return -1;
                }else{
                    String s1 = s.replaceFirst(ss[0],"");
                    String t1 = t.replaceFirst(tt[0],"");
                    if(s1.equals(t1)){
                        return ss[0].compareTo(tt[0]);
                    }

                    return s1.compareTo(t1);
                }
            }
        });
        return logs;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.reorderLogFiles(new String[]{"dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"});
        solution.reorderLogFiles(new String[]{"j je", "b fjt", "7 zbr", "m le", "o 33"});
    }
}
