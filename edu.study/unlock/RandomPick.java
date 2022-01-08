package unlock;

public class RandomPick {
    private int[] prefixSum;
    private int max;
    public RandomPick(int[] w){
        int n = w.length;
        this.prefixSum = new int[n];
        prefixSum[0] = w[0];
        for(int i=1;i<n;i++){
            prefixSum[i] = prefixSum[i-1]+w[i];
        }

        this.max = prefixSum[n-1];
    }

    public int pickIndex() {
        // plus one because index does not have 0 weight
        int target = (int) (Math.random()*max+1);
        int start = 0;
        int end = prefixSum.length-1;
        while (start<=end){
            int mid = start+(end-start)/2;
            if(prefixSum[mid]==target){//正好落在一个index对应区间的右边界上
                return mid;
            }else if(prefixSum[mid]<target){
                start  = mid+1;
            }else {
                end = mid-1;
            }
        }
        //落在一个区间的范围内，非边界
        return start;
    }
}
