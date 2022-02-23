package feb;

import java.util.*;

public class BFStest {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int[] prerequisite:prerequisites){
            int pre = prerequisite[1];
            int next = prerequisite[0];
            inDegree[next]++;
            map.putIfAbsent(pre,new ArrayList<>());
            map.get(pre).add(next);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()){
            int cur = queue.poll();
            List<Integer> list = map.getOrDefault(cur,new ArrayList<>());
            for(Integer next : list){
                if(inDegree[next]>0){
                    inDegree[next]--;
                }
                if(inDegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        for(int in:inDegree){
            if(in!=0){
                return false;
            }
        }

        return true;
    }
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int[] prerequisite:prerequisites){
            int pre = prerequisite[1];
            int next = prerequisite[0];
            inDegree[next]++;
            map.putIfAbsent(pre,new ArrayList<>());
            map.get(pre).add(next);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<numCourses;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }

        List<Integer> schedule = new ArrayList<>();
        while (!queue.isEmpty()){
            int cur = queue.poll();
            schedule.add(cur);
            List<Integer> list = map.getOrDefault(cur,new ArrayList<>());
            for(Integer next : list){
                if(inDegree[next]>0){
                    inDegree[next]--;
                }
                if(inDegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        if(schedule.size()!=numCourses){
            return new int[]{};
        }

        int[] res = new int[numCourses];
        int k=0;
        for(int order:schedule){
            res[k++] = order;
        }

        return res;
    }

    public int scheduleCourse(int[][] courses) {
      PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->(b[1]==a[1]?a[0]-b[0]:a[1]-b[1]));
      for(int[] course:courses){
          queue.offer(course);
      }
      int count=0;
      int cur = 0;
      PriorityQueue<Integer> before = new PriorityQueue<>((a,b)->(b-a));
      while (!queue.isEmpty()){
          int[] course = queue.poll();
          int next = cur+course[0];
          if(next<=course[1]){
              count++;
              cur=next;
              before.offer(course[0]);
          }else {
              int max = before.peek();
              if(max>course[0]){
                  before.poll();
                  cur+=course[0]-max;
                  before.offer(course[0]);
              }
          }
      }

      return count;
    }

    public int minimumTime(int n, int[][] relations, int[] time) {
        int[] inDegree = new int[n];
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int[] rel:relations){
            inDegree[rel[1]-1]++;
            map.putIfAbsent(rel[0]-1,new ArrayList<>());
            map.get(rel[0]-1).add(rel[1]-1);
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<n;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }

        int[] completionTime = new int[n];
        while(!queue.isEmpty()){
            int cur = queue.poll();
            List<Integer> list = map.getOrDefault(cur,new ArrayList<>());
            for(int next:list){
                completionTime[next] = Math.max(completionTime[next],completionTime[cur]+time[next]);
                if(inDegree[next]>0){
                    inDegree[next]--;
                }
                if(inDegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        int res = 0;
        for(int val:completionTime){
            res = Math.max(res,val);
        }


        return res;
    }

    public static void main(String[] args) {
        BFStest test = new BFStest();
        int n = 5;
        int[][] relations = {{1,5},{2,5},{3,5},{3,4},{4,5}};
        int[] time = {1,2,3,4,5};
//        test.scheduleCourse(new int[][]{{100,200},{200,1300},{1000,1250},{2000,3200}});
       // test.minimumTime(3,new int[][]{{1,3},{2,3}}, new int[]{3,2,5});
        test.minimumTime(n,relations,time);
    }
}
