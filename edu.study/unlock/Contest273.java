package unlock;

import java.util.HashMap;
import java.util.Map;

public class Contest273 {


    //    Consider 1's at different postions of an array.
//        x  y  z   p   q
//        1  1  1   1   1
//
//    consider 1 at index z: |z - x| + |z - y| + |z - p| + |z - q|
//
//    when we are looping from left to right we are storing sum and count of previous indices of num in maps.
//    |z - x| + |z - y| = z - x + z - y, since z is greater than x and y.
//    z - x + z - y = 2z - (x + y) = (count) * (currentIndex) - (sum).
//
//      Similarly we can calculate the |z - p| + |z - q| when we loop from right to left.

    public long[] getDistances(int[] arr) {

      long[] output = new long[arr.length];
        Map<Integer, Long> sumMap = new HashMap<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int i = 0; i < arr.length; ++ i) {
            if (!sumMap.containsKey(arr[i])) {
                sumMap.put(arr[i], 0l);
                countMap.put(arr[i], 0);
            }
            //左边的差值  i=index, count 相同值出现次数
            output[i] += i * (long)countMap.get(arr[i]) - sumMap.get(arr[i]);
            //更新值相同的左侧index和
            sumMap.put(arr[i], sumMap.get(arr[i]) + i);
            //更新出现次数
            countMap.put(arr[i], countMap.get(arr[i]) + 1);
        }

        //同理，右边再来一遍
        sumMap = new HashMap<>();
        countMap = new HashMap<>();
        int len = arr.length;
        for (int i = len - 1; i >= 0; -- i) {
            if (!sumMap.containsKey(arr[i])) {
                sumMap.put(arr[i], 0l);
                countMap.put(arr[i], 0);
            }

            output[i] += (len - i - 1) * (long)countMap.get(arr[i]) - sumMap.get(arr[i]);
            sumMap.put(arr[i], sumMap.get(arr[i]) + (len - i - 1));
            countMap.put(arr[i], countMap.get(arr[i]) + 1);
        }

        return output;
    }

    public int[] executeInstructions(int n, int[] pos, String s) {
        char[] arr = s.toCharArray();
        int len = s.length();
        int[] res = new int[len];

        for(int i=0;i<arr.length;i++){
           res[i] = getSteps(n,i,pos,arr);
        }

        return res;
      }

    private int getSteps(int n, int start, int[] pos, char[] arr) {
        if(start>=arr.length){
            return 0;
        }
        int x= pos[0];
        int y = pos[1];
        char c = arr[start];
        if(c=='U'){
            y+=-1;
        }else if(c=='D'){
            y+=1;
        }else if(c=='L'){
            x+=-1;
        }else {
            x+=1;
        }

        if(x>=0&&y>=0&&x<n&&y<n){
            return 1+getSteps(n,start+1,new int[]{x,y},arr);
        }

        return 0;
    }

    public static void main(String[] args) {
       int  n = 2;
       int[] startPos = new int[]{1,1};
       String s = "LURD";
       Contest273 test = new Contest273();
       test.executeInstructions(n,startPos,s);
    }


}
