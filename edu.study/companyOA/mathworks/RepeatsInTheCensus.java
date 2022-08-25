package companyOA.mathworks;

import java.util.HashMap;
import java.util.Map;

public class RepeatsInTheCensus {

    public int repeate(int[] nums,int size){
        int res = 0;
        int[] counts = new int[size];
        for(int num:nums){
            counts[num]++;
            if(counts[num]==2){
                res++;
            }
        }
        System.out.println(res);
        return res;
    }


    public static void main(String[] args) {
        RepeatsInTheCensus census = new RepeatsInTheCensus();
        census.repeate(new int[]{1,3,1,4,5,6,3,2},8);
    }
}
