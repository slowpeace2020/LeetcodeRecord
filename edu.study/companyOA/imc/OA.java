package companyOA.imc;

import java.util.*;

public class OA {
    public static List<Integer> getResult(List<Integer> arrival,List<Integer> street){
        //0 main street, 1 avenue
        PriorityQueue<int[]> avenue = new PriorityQueue<>((a,b)->(a[0]-b[0]));
        PriorityQueue<int[]> main = new PriorityQueue<>((a,b)->(a[0]-b[0]));
        for(int i=0;i<arrival.size();i++){
            if(street.get(i)==0){
                avenue.add(new int[]{arrival.get(i),i});
            }else{
                main.add(new int[]{arrival.get(i),i});
            }
        }

        int time = 0;
        int previous =-1;
        Map<Integer,Integer> map = new HashMap<>();
        while (!avenue.isEmpty()&&!main.isEmpty()){
            int[] a = avenue.peek();
            int[] b = main.peek();
            time++;
            if(a[0]<b[0]){
                previous = 0;
                map.put(a[1],time);
                avenue.poll();
            }else if(a[0]>b[0]){
                previous = 1;
                map.put(b[1],time);
                main.poll();
            }else{
                if(previous<=0){
                    previous = 0;
                    map.put(a[1],time);
                    avenue.poll();
                }else{
                    previous = 1;
                    map.put(b[1],time);
                    main.poll();
                }
            }

        }
        while (!avenue.isEmpty()){
            time++;
            int[] a = avenue.poll();
            map.put(a[1],time);
        }

        while (!main.isEmpty()){
            time++;
            int[] b = main.poll();
            map.put(b[1],time);
        }

        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<arrival.size();i++){
            ans.add(map.get(i));
        }

        return ans;
    }
}
