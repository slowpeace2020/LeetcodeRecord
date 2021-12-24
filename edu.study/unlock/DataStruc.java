package unlock;

import java.util.*;

public class DataStruc {
  public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int sum =0;
        ListNode sumNode = new ListNode(-1);
        ListNode cur = sumNode;
        while(l1!=null||l2!=null||sum!=0){
            int val = sum;
            if(l1!=null){
                val+=l1.val;
                l1=l1.next;
            }

            if(l2!=null){
                val+=l2.val;
                l2=l2.next;
            }
            sum=val/10;
            cur.next =  new ListNode(val%10);
            cur = cur.next;
        }
        return sumNode.next;
    }




    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;

        while (end!=null){
            for(int i=0;i<k&&end!=null;i++){
                end = end.next;
            }

            if(end==null){
                break;
            }

            ListNode nextStart = end.next;
            ListNode start = pre.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = nextStart;
            pre = start;
            end = pre;
        }

        return dummy.next;
    }

    public boolean couldReverse(ListNode head, int k){
        while(k>0&&head!=null){
            head = head.next;
            k--;
        }

        return k==0;
    }

    public ListNode[] reverseNode(ListNode head, int k){
        ListNode[] res= new ListNode[3];
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;
        ListNode tail = null;
        int count = k;

        while(count>0){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            count--;
        }

        res[0] = pre;
        tail=pre;
        while (tail.next!=null){
            tail = tail.next;
        }
        tail.next = next;
        res[1] =tail;
        res[2] =next;
        return res;
    }


    public ListNode deleteDuplicates(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }

        if(head.next!=null&&head.val==head.next.val){//处理头结点重复的情况
            while (head.next!=null&&head.val==head.next.val){
                head = head.next;
            }

            //跳过重复的头结点，递归到下一个节点
            return deleteDuplicates(head.next);
        }

        //头结点不重复，以下一个节点开头去递归去除
        head.next = deleteDuplicates(head.next);
        return head;
    }
    public ListNode deleteDuplicatesI(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;//指向下一个不同的元素
        while(pre.next!=null){
            ListNode cur = pre.next;//指向当前遍历到的节点
            while (cur.next!=null&&cur.next.val==cur.val){
                cur = cur.next;
            }

            if(cur.val!=pre.next.val){
                pre.next = cur.next;
            }else {
                pre = cur.next;
            }

        }

        return dummy.next;
    }


    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        ListNode pre = dummy;
        ListNode cur = head;
        pre.next = cur;
        ListNode end = null;
        ListNode nextStart = null;

        int count =1;
        while(count<left){
            pre.next = cur;
            cur = cur.next;
            pre = pre.next;
            count++;
        }

        ListNode start = pre.next;
        end = start;
        while(count<right){
            count++;
            end = end.next;
        }

        nextStart = end.next;
        end.next = null;

        pre.next = reverse(start);
        start.next = nextStart;
        return dummy.next;
    }

    public ListNode reverse(ListNode head){
        if(head==null||head.next==null){
            return head;
        }

        ListNode pre = null;
        ListNode cur = head;
        while(cur!=null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            //获得当前节点的复制节点
            Node copy = null;
            if (map.containsKey(cur)) {//复制过了，直接从map中获取
                copy = map.get(cur);
            } else {//没复制过，创建一个新节点放入map
                copy = new Node(cur.val);
                map.put(cur, copy);
            }
            //获得当前节点的next和random节点
            Node next = cur.next;
            Node random = cur.random;
            if (next != null) {
                if (map.containsKey(next)) {//复制过了，直接从map中获取到对应的复制节点并复制
                    copy.next = map.get(next);
                } else {
                    copy.next = new Node(next.val);//否则创建next的复制节点，并加入map防止重复
                    map.put(next, copy.next);
                }
            }

            if (random != null) {//random同理
                if (map.containsKey(random)) {
                    copy.random = map.get(random);
                } else {
                    copy.random = new Node(random.val);
                    map.put(random, copy.random);
                }
            }

            cur = cur.next;
        }
        return map.get(head);
    }

    //mergesort
    public ListNode sortList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }

        ListNode mid = findMiddle(head);
        ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
        ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
        ListNode middleDummy = new ListNode(0), middleTail = middleDummy;

        while (head!=null){
            if(head.val<mid.val){
                leftTail.next = head;
                leftTail = leftTail.next;
            }else if(head.val>mid.val){
                rightTail.next = head;
                rightTail = rightTail.next;
            }else{
                middleTail.next = head;
                middleTail = middleTail.next;
            }

            head = head.next;
        }

        leftTail.next = null;
        rightTail.next = null;
        middleTail.next = null;

        ListNode left = sortList(leftDummy.next);
        ListNode right = sortList(rightDummy.next);

        return concat(left,middleDummy.next,right);
    }

    private ListNode concat(ListNode left, ListNode next, ListNode right) {
      ListNode dummy = new ListNode(0);
      ListNode cur = dummy;
      cur.next = left;
      cur = getTail(cur);
      cur.next=next;
      cur = getTail(cur);
      cur.next = right;
        return dummy.next;
    }

    private ListNode getTail(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }

        while (head.next!=null){
            head = head.next;
        }
        return head;
    }

    private ListNode merge(ListNode left, ListNode right) {
      ListNode dummy = new ListNode(-1);
      ListNode cur = dummy;
      while (left!=null&&right!=null){
          if(left.val<right.val){
              cur.next = left;
              left = left.next;
          }else {
              cur.next = right;
              right = right.next;
          }

          cur = cur.next;
      }

      if(left!=null){
          cur.next = left;
      }

      if(right!=null){
          cur.next = right;
      }
        return dummy.next;
    }

    private ListNode findMiddle(ListNode head) {
      ListNode fast = head;
      ListNode slow = head;
      while (fast!=null&&fast.next!=null){
          fast = fast.next.next;
          slow = slow.next;
      }
        return slow;
    }


    public boolean isPalindrome(ListNode head) {
        if(head==null||head.next==null){
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        Stack<ListNode> stack = new Stack<>();
        while(fast!=null&&fast.next!=null){
            stack.push(slow);
            fast = fast.next.next;
            slow =slow.next;
        }

        if(fast!=null){
            slow = slow.next;
        }


        while(slow!=null){
            ListNode cur = stack.pop();
            if(cur.val!=slow.val){
                return false;
            }
            slow = slow.next;
        }

        return true;
    }

    public int subarraySum(int[] nums, int k) {
       int count=0;
       int sum=0;
       Map<Integer,Integer> map = new HashMap<>();
       map.put(0,1);
       for(int i=0;i<nums.length;i++){
           sum+=nums[i];
           if(map.containsKey(sum-k)){
               count+=map.get(sum-k);
           }

           map.put(sum,map.getOrDefault(sum,0)+1);
       }

       return count;
    }

    public boolean isAlienSorted(String[] words, String order) {
        PriorityQueue<String> queue = new PriorityQueue<String>(new Comparator<String>() {
            @Override
            public int compare(String a,String b){
                if(a.startsWith(b)){
                    return 1;
                }else if(b.startsWith(a)){
                    return -1;
                }

                int len = Math.min(a.length(),b.length());
                for(int i=0;i<len;i++){
                    if(order.indexOf(a.charAt(i)+"")==order.indexOf(b.charAt(i)+"")){
                        continue;
                    }
                    if(order.indexOf(a.charAt(i)+"")>order.indexOf(b.charAt(i)+"")){
                        return 1;
                    }
                    if(order.indexOf(a.charAt(i)+"")<order.indexOf(b.charAt(i)+"")){
                        return -1;
                    }
                }
                return 0;
            }
        });

        queue.addAll(Arrays.asList(words));

        for(int i=0;i<words.length;i++){
            String word = words[i];
            String w = queue.poll();
            if(!w.equals(word)){
                return false;
            }
        }

        return true;
    }

    private boolean even = true;

    public double findMedian(){
        if(even){
            return (small.peek()+large.peek())/2.0;
        }else {
            return small.peek();
        }
    }

    public void addNum(int num){
        if(even){//左半边元素和右半边元素个数一样
            large.offer(num);//把新元素放入右半边的队列
            small.offer(large.poll());//把右半边最小的元素放入左半边，保持右半边的元素不多于左半边
        }else {//左半边元素比右半边元素多一个
            small.offer(num);//把新元素放入左半边
            large.offer(small.poll());//把左半边最大的元素放入右半边，保持右半边最多比左半边少一个，便于计算中值
        }
        even = !even;
    }

    public String reorganizeString(String s) {
        int[] count = new int[26];
        int max =0;
        char letter = ' ';
        for(char c:s.toCharArray()){
            count[c-'a']++;
            if(max<count[c-'a']){
                max = count[c-'a'];
                letter = c;
            }
        }

        if(max>s.length()/2){
            return "";
        }

        char[] res = new char[s.length()];
        int index  = 0;
        while (count[letter]>0){
            res[index] = letter;
            index+=2;
            count[letter]--;
        }

        for(int i=0;i<count.length;i++){
            while (count[i]>0){
                if(index>=s.length()){
                    index=1;
                }
                res[index]=(char)(i+'a');
                index+=2;
                count[i]--;
            }
        }
        return new String(res);
    }

    public int[][] kClosest(int[][] points, int k) {
        int[][] res = new int[k][2];

        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                Double res1 = Math.sqrt(Math.pow(a[0],2)+Math.pow(a[1],2));
                Double res2 = Math.sqrt(Math.pow(b[0],2)+Math.pow(b[1],2));
                return res1.compareTo(res2);
            }
        });

        for(int i=0;i<points.length;i++){
            queue.add(points[i]);
        }
        int i = 0;
        while(i<k){
            res[i++] = queue.poll();
        }

        return res;
    }


    //小顶堆存大的元素，这样堆顶是最小的元素，整个队列是右半边的元素
    private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
    //大顶堆存小的元素，这样堆顶是最大的元素，整个队列是左半边的元素
    private PriorityQueue<Integer> large = new PriorityQueue<>();




        public static void main(String[] args) {
        DataStruc test = new DataStruc();

        int[] l1 =new int[]{1,0,0,1};
        ListNode one = new ListNode(l1[0]);
        ListNode cur = one;
        for(int i=1;i<l1.length;i++){
            cur.next = new ListNode(l1[i]);
            cur = cur.next;
        }

//        test.isPalindrome(one);

        test.reorganizeString("aab");
    }
}
