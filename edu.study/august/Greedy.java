package august;

import java.util.Arrays;

public class Greedy {
    public int minIncrementForUnique(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        for(int i=1;i<nums.length;i++){
            if(nums[i]==nums[i-1]){
                nums[i]++;
                count++;
            }else if(nums[i]<nums[i-1]){
                count+=nums[i-1]+1-nums[i];
                nums[i]=nums[i-1]+1;
            }
        }

        return count;
    }

    public int leastInterval(char[] tasks, int n) {
        int len = tasks.length;
        if(len==0||n==0){
            return 0;
        }
        int[] arr = new int[26];
        for(char c:tasks){
            arr[c-'A']++;
        }
        Arrays.sort(arr);
        int[] combine = new int[arr[25]*(n+1)];
        for(int i=25;i>=0;i--){
            if(arr[i]>0){
                for(int j=0;j<arr[i];j++){
                    int index = 25-i+j*(n+1);
                    combine[index] = 1;
                }
            }else{
                break;
            }
        }

        int i = arr[25]*(n+1)-1;
        for(;i>=0;i--){
            if(combine[i]==1){
                break;
            }
        }
        return i+1;
    }

    public static void main(String[] args) {
        Greedy greedy = new Greedy();
        greedy.leastInterval(new char[]{'A','A','A','B','B','B'},2);
    }
}
