package lock;

import java.util.*;

public class Test {
    public static String mystery(String s) {
        int N = s.length();
        if (N <= 1) return s;
        String a = s.substring(0, N/2);
        String b = s.substring(N/2, N);
        return mystery(b) + mystery(a);
    }

    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int time = releaseTimes[0];
        char res = keysPressed.charAt(0);
        for(int i=1;i<keysPressed.length();i++){
            int currentTime = releaseTimes[i]-releaseTimes[i-1];
            if((currentTime>time)||(currentTime==time&&res<keysPressed.charAt(i))){
                res = keysPressed.charAt(i);
            }
        }
        return res;

    }

    public int smallestDistancePair(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(k,(a, b)->(b-a));

        int len = nums.length*(nums.length-1)/2-k+1;
        if(len>1){

        }
        for(int i=0;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                addMaxSize(queue,len,Math.abs(nums[i]-nums[j]));
            }
        }

        int res =  0;
        while(len>0){
            res = queue.poll();
            len--;
        }
        System.out.println(len);
        return res;
    }

    public void addMaxSize(PriorityQueue<Integer> queue, int k, int value){
        if(queue.size()<k){
            queue.add(value);
        }else{
            queue.add(value);
            queue.remove();
        }
    }


    public double maxAverageRatio(int[][] classes, int extraStudents) {
        PriorityQueue<Pair> queue = new PriorityQueue<Pair>(new Comparator<Pair>(){
            @Override
            public int compare(Pair pair1,Pair pair2){
                return pair1.passes/pair1.classes-pair2.passes/pair2.classes==0?(pair1.classes>pair2.classes?-1:1):pair1.passes/pair1.classes-pair2.passes/pair2.classes;
            }
        });

        for(int[] aclass:classes){
            Pair pair = new Pair(aclass[0],aclass[1]);
            queue.add(pair);
        }

        double res=0;
        Pair pair = queue.poll();
        res+=(pair.passes+extraStudents)/(pair.classes+extraStudents);
        while(!queue.isEmpty()){
            pair = queue.poll();
            res+=(double)pair.passes/pair.classes;
        }

        return res;
    }

    class Pair{
        int passes;
        int classes;
        public Pair(int passes,int classes){
            this.passes = passes;
            this.classes = classes;
        }
    }

    Map<Integer,Integer> visited = new HashMap<>();
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        return reachHelper(sx,sy,tx,ty);
    }

    public boolean reachHelper(int sx, int sy, int tx, int ty){
        if(visited.getOrDefault(sx,0)==sy){
            return false;
        }
        if(sx==tx&&sy==ty){
            return true;
        }

        if(sx>tx||sy>ty){
            return false;
        }
        visited.put(sx,sy);

        return reachHelper(sx+sy,sy,tx,ty)||reachHelper(sx,sx+sy,tx,ty);
    }

    public int countBinarySubstrings(String s) {
        int res = 0;

        for(int i=0;i<s.length();i++){
            for(int j=i+2;j<s.length();j=j+2){
                System.out.println(s.substring(i,j));
                if(isBinarySubstring(s.substring(i,j))){
                    res++;
                }
            }
        }

        return res;
    }

    public boolean isBinarySubstring(String s){
        int length = s.length()/2;
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for(int i=0;i<length;i++){
            s1.append("0");
            s2.append("1");
        }
        if(s.substring(0,length).equals(s1.toString())&&s.substring(length).equals(s2.toString())){
            return true;
        }

        if(s.substring(0,length).equals(s2.toString())&&s.substring(length).equals(s1.toString())){
            return true;
        }

        return false;
    }

    public static void main(String[] args) {

//        System.out.println(mystery("nchhsidjkwdklq;jadxkhjndmsmxzmBHCJ"));
        Test test = new Test();
//        test.slowestKey(new int[]{28,65,97},"gaf");
//        test.smallestDistancePair(new int[]{1,6,1},1);
//        test.smallestDistancePair(new int[]{1,6,1},2);
//        test.smallestDistancePair(new int[]{1,6,1},3);

//        test.reachingPoints(35,
//                13,
//                455955547,
//                420098884);
        test.countBinarySubstrings("00110011");


    }
}
