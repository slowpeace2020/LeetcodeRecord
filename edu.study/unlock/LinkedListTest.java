package unlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class LinkedListTest {
  class ListNode{
    int val;
    ListNode next;
    ListNode(){

    }

    ListNode(int val){
      this.val =val;
    }

    ListNode(int val,ListNode next){
      this.val = val;
      this.next = next;
    }
  }

  public boolean hasCycle(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast != null && fast.next != null) {
      fast = fast.next.next;
      slow = slow.next;
      if (fast == slow) {
        return true;
      }
    }

    return false;
  }

  public ListNode partition(ListNode head, int x) {
    ListNode start = new ListNode(-1);
    ListNode end = new ListNode(-1);
    ListNode left = start;
    ListNode right = end;
    while (head!=null){
      if(head.val<x){
        left.next = head;
        head = head.next;
        left = left.next;
        left.next = null;
      }else {
        right.next = head;
        head = head.next;
        right = right.next;
        right.next = null;
      }
    }

    start = start.next;
    end = end.next;
    if(start==null){
      return end;
    }

    if(end==null){
      return start;
    }
    left.next = right;

    return start;

  }

  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    Set<ListNode> set = new HashSet<>();
    while (headA != null) {
      set.add(headA);
      headA = headA.next;
    }
    while (headB != null) {
      if (!set.add(headB)) {
        return headB;
      }
      headB = headB.next;
    }
    return null;
  }

  public boolean isPalindrome(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast != null && fast.next != null) {
      fast = fast.next.next;
      slow = slow.next;
    }

    ListNode terminate = slow;

    ListNode right = reverse(slow);
    ListNode left = head;
    while (left != terminate && right != null) {
      if (left.val != right.val) {
        return false;
      }

      left = left.next;
      right = right.next;
    }

    return true;
  }

  public ListNode reverse(ListNode head) {
    ListNode pre = null;
    while (head != null) {
      ListNode next = head.next;
      head.next = pre;
      pre = head;
      head = next;
    }
    return pre;
  }

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    Stack<ListNode> stack1 = new Stack<>();
    Stack<ListNode> stack2 = new Stack<>();

    while (l1 != null) {
      stack1.push(l1);
      l1 = l1.next;
    }

    while (l2 != null) {
      stack2.push(l2);
      l2 = l2.next;
    }

    ListNode res = null;
    int sum = 0;
    while (!stack1.isEmpty() || !stack2.isEmpty()) {
      if (!stack1.isEmpty()) {
        sum += stack1.pop().val;
      }
      if (!stack2.isEmpty()) {
        sum += stack2.pop().val;
      }

      ListNode pre = new ListNode(sum % 10);
      sum = sum / 10;
      pre.next = res;
      res = pre;
    }

    if (sum != 0) {
      ListNode pre = new ListNode(sum % 10);
      pre.next = res;
      res = pre;
    }

    return res;
  }


  public int findDuplicate(int[] nums) {
    int fast = 0;
    int slow = 0;

    while (true) {
      fast = nums[nums[fast]];
      slow = nums[slow];
      if (fast == slow) {
        break;
      }
    }

    fast = 0;
    while (true) {
      slow = nums[slow];
      fast = nums[fast];
      if (slow == fast) {
        break;
      }
    }

    return fast;
  }


  public int findDuplicateI(int[] nums) {
    int left = 1;
    int right = nums.length;
    while (left < right) {
      int mid = left + (right - left) / 2;
      int count = 0;
      for (int num : nums) {
        if (num < mid) {
          count++;
        }
        if (count <= mid) {
          left = mid + 1;
        } else {
          right = mid;
        }
      }
    }

    return right;
  }

  class Interval{
    int start;
    int end;
  }

  class Node{
    int time;
    int cost;
    public Node(int time,int cost){
      this.time = time;
      this.cost = cost;
    }
  }

  public int countOfAirplanes(List<Interval> airplanes) {
    List<Node> list = new ArrayList<>();
    for(Interval item : airplanes){
      list.add(new Node(item.start,1));
      list.add(new Node(item.end,-1));
    }
    Collections.sort(list,new Comparator<Node>(){
      @Override
      public int compare(Node a,Node b){
        if(a.time!=b.time){
          return a.time-b.time;
        }
        return a.cost-b.cost;
      }
    });

    int res = 0;
    int temp = 0;
    for(int i=0;i<list.size();i++){
      temp +=list.get(i).cost;
      res = Math.max(res,temp);
    }

    return res;
  }

  class Point{
    int start;
    int end;
    public Point(int start,int end){
      this.start = start;
      this.end= end;
    }
  }
  public boolean canAttendMeetings(Interval[] intervals) {
    List<Point> list= new ArrayList<>();
    for(Interval interval:intervals){
      list.add(new Point(interval.start,interval.end));
    }

    Collections.sort(list,new Comparator<Point>(){
      @Override
      public int compare(Point a, Point b){
        if(a.start!=b.start){
          return a.start-b.start;
        }

        return a.end-b.end;
      }
    });

    Point point = list.get(0);

    for(int i=1;i<list.size();i++){
      Point current = list.get(i);
      if(current.start<=point.end){
        return false;
      }

      point = current;
    }

    return true;
  }


  public int minMeetingRooms(Interval[] intervals) {
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    for (Interval interval : intervals) {
      if (!queue.isEmpty() && queue.peek() < interval.start) {
        queue.poll();
      }
      queue.offer(interval.end);
    }

    return queue.size();
  }



  public int minMeetingRoomsII(Interval[] intervals) {
    int n = intervals.length;
    int[] starts = new int[n];
    int[] ends = new int[n];
    int index = 0;
    for (Interval interval : intervals) {
      starts[index] = interval.start;
      ends[index++] = interval.end;
    }
    Arrays.sort(starts);
    Arrays.sort(ends);

    int endsPos = 0;
    int res = 0;
    for (int i = 0; i < n; i++) {
      if (starts[i] < ends[endsPos]) {
        res++;
      } else {
        endsPos++;
      }
    }

    return res;
  }


  public int minMeetingRoomsI(Interval[] intervals) {
    List<Node> list = new ArrayList<>();
    for(Interval item:intervals){
      list.add(new Node(item.start,1));
      list.add(new Node(item.end,-1));
    }

    Collections.sort(list, new Comparator<Node>() {
      @Override
      public int compare(Node o1, Node o2) {
        if(o1.time!=o2.time){
          return o1.time-o2.time;
        }

        return o1.cost-o2.cost;
      }
    });

    int count =0;
    int res = 0;
    for(Node node:list){
      count+=node.cost;
      res = Math.max(res,count);
    }

    return res;

  }


  public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    List<int[]> list = new ArrayList<>();

    int i = 0;
    int j = 0;

    while (i < firstList.length && j < secondList.length) {
      int[] first = firstList[i];
      int[] second = secondList[j];
      int low = Math.max(first[0], second[0]);
      int high = Math.min(first[1], second[1]);
      if (low <= high) {
        list.add(new int[]{low, high});
      }
      if (first[1] < second[1]) {
        i++;
      } else {
        j++;
      }
    }

    int[][] res = new int[list.size()][2];
    for (i = 0; i < list.size(); i++) {
      int[] ans = list.get(i);
      res[i] = new int[]{ans[0], ans[1]};
    }

    return res;
  }
  public int[][] intervalIntersectionI(int[][] firstList, int[][] secondList) {
    if (firstList.length == 0 || secondList.length == 0) {
      return null;
    }

    List<int[]> list = new ArrayList<>();

    int i = 0;
    int j = 0;

    while (i < firstList.length && j < secondList.length) {
      int[] first = firstList[i];
      int[] second = secondList[j];
      if (first[1] >= second[0] && first[0] <= second[0]) {
        int[] point = new int[2];
        point[0] = second[0];
        if (first[1] > second[1]) {
          point[1] = second[1];
          j++;
        } else {
          point[1] = first[1];
          i++;
        }
        list.add(point);
      } else if (second[1] >= first[0] && second[0] <= first[0]) {
        int[] point = new int[2];
        point[0] = first[0];
        if (second[1] > first[1]) {
          point[1] = first[1];
          i++;
        } else {
          point[1] = second[1];
          j++;
        }
        list.add(point);
      } else {

        if (first[1] < second[0]) {
          i++;
        } else if (second[1] < first[0]) {
          j++;
        }

      }

    }

    int[][] res = new int[list.size()][2];
    for(i=0;i<list.size();i++){
      int[] ans = list.get(i);
      res[i] = new int[]{ans[0],ans[1]};
    }

    return res;


  }

  public static void main(String[] args) {
    LinkedListTest test = new LinkedListTest();
    test.intervalIntersection(new int[][]{{0,2},{5,10},{13,23},{24,25}},new int[][]{{1,5},{8,12},{15,24},{25,26}});
  }




  }
