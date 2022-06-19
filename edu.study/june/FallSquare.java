package june;

import java.util.*;

public class FallSquare {
    private class Interval{
        int start,end,height;
        public Interval(int start,int end,int height){
            this.start = start;
            this.end = end;
            this.height = height;
        }
    }

    public List<Integer> fallingSquares(int[][] positions){
        List<Interval> intervals = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        int h = 0;
        for(int[] pos:positions){
            Interval cur = new Interval(pos[0],pos[0]+pos[1],pos[1]);
            h = Math.max(h,getHeight(intervals,cur));
            res.add(h);
        }

        return res;
    }

    //在掉落的区间找最高值
    private int getHeight(List<Interval> intervals, Interval cur) {
        int preMaxHeight = 0;
        for(Interval i: intervals){
            if(i.end<cur.start){
                continue;
            }
            if(i.start>cur.end){
                continue;
            }

            preMaxHeight = Math.max(preMaxHeight,i.height);
        }
        cur.height+=preMaxHeight;
        intervals.add(cur);
        return cur.height;
    }

    public int findSubstringInWraproundString(String p) {
            int[] count = new int[26];

            int maxLengthCur = 0;
            for(int i=0;i<p.length();i++){
                if (i>0 && (p.charAt(i) - p.charAt(i-1)==1||(p.charAt(i-1)-p.charAt(i)==25))){
                    maxLengthCur++;
                }else {
                    maxLengthCur = 1;
                }

                int index = p.charAt(i)-'a';
                count[index] = Math.max(count[index],maxLengthCur);
            }

            int sum =0;
            for(int i=0;i<26;i++){
                sum+=count[i];
            }
            return sum;
      }


    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {

        TreeMap<Long,Integer> map = new TreeMap<>();
        HashMap<Long,Integer> mapCount = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            long key = nums[i];
            Long highKey = map.ceilingKey(key);
            Long lowerKey = map.floorKey(key);
            if(highKey!=null&&highKey-key<=t){
                return true;
            }

            if(lowerKey!=null&&key-lowerKey<=t){
                return true;
            }

            map.put(key,i);
            mapCount.put(key,mapCount.getOrDefault(key,0)+1);

            if(i>=k){
                long value = nums[i-k];
                if(mapCount.get(value)==1){
                    map.remove(value);
                }else{
                    mapCount.put(value,mapCount.get(value)-1);
                }
            }

        }


        return false;
    }

    public int latestDayToCross(int row, int col, int[][] cells) {
        boolean[][] visited = new boolean[row][col];
        visited[cells[0][0]-1][cells[0][1]-1] = true;
        Queue<int[]> queue = new LinkedList<>();
        for(int j=0;j<col;j++){
            if(visited[0][j]){
                continue;
            }
            queue.add(new int[]{0,j});
        }

        int[][] directions = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        int step = 0;
        int res =-1;
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            if(cur[0]==row-1){
                res=step;
            }
            if(step+1<cells.length){
                int[] cell = cells[++step];
                visited[cell[0]-1][cell[1]-1] = true;
            }
            for(int[] direct:directions){
                int x = cur[0]+direct[0];
                int y = cur[1]+direct[1];
                if(x<0||y<0||x==row||y==col||visited[x][y]){
                    continue;
                }
                visited[x][y]=true;
                queue.add(new int[]{x,y});
            }


        }

        return res;


    }


    public static void main(String[] args) {
        FallSquare square = new FallSquare();
        square.latestDayToCross(2,2,new int[][]{{1,1},{2,1},{1,2},{2,2}});
//        square.latestDayToCross(2,2,new int[][]{{1,1},{1,2},{2,1},{2,2}});
    }


    }
