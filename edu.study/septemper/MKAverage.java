package septemper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MKAverage {
    int m=0;
    int k=0;
    List<Integer> list;
    public MKAverage(int m, int k) {
        this.m = m;
        this.k = k;
        list = new ArrayList<>();
    }

    public void addElement(int num) {
        if(list.size()>=m){
            list.remove(0);
        }
        list.add(num);
    }

    public int calculateMKAverage() {
        if(list.size()<m){
            return -1;
        }
        List<Integer> sortList = new ArrayList<>(list);
        Collections.sort(sortList);
        long sum = 0;
        for(int i=k;i<sortList.size()-k;i++){
            sum+=sortList.get(i);
        }
        System.out.println(sum);
//        System.out.println((int)((sum+list.size()-2*k-1)/(list.size()-2*k)));
        System.out.println((int)((sum)/(list.size()-2*k)));
        int res = (int) Math.floor((sum+list.size()-2*k-1)/(list.size()-2*k));
        return res;

    }




    public static void main(String[] args) {
        MKAverage obj = new MKAverage(6,1);
        obj.addElement(3);        // current elements are [3]
        obj.addElement(1);        // current elements are [3,1]
        obj.calculateMKAverage(); // return -1, because m = 3 and only 2 elements exist.
        obj.addElement(12);       // current elements are [3,1,10]
        obj.calculateMKAverage(); // The last 3 elements are [3,1,10].
        // After removing smallest and largest 1 element the container will be [3].
        // The average of [3] equals 3/1 = 3, return 3
        obj.addElement(5);        // current elements are [3,1,10,5]
        obj.addElement(3);        // current elements are [3,1,10,5,5]
        obj.addElement(4);        // current elements are [3,1,10,5,5,5]
        obj.calculateMKAverage(); // The last 3 elements are [5,5,5].
        // After removing smallest and largest 1 element the container will be [5].
        // The average of [5] equals 5/1 = 5, return 5
    }
}
