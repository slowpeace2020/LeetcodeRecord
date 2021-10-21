package lock;

import java.util.*;

public class Alien_Dictionary_269 {
    // dfs
    public String alienOrder(String[] words) {

        // Step 1: build the graph
        Map<Character, Set<Character>> graph = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];
            for (int j = 0; j < currentWord.length(); j++) {
                if (!graph.containsKey(currentWord.charAt(j))) {
                    graph.put(currentWord.charAt(j), new HashSet<>());
                }
            }

            if (i > 0) {
                connectGraph(graph, words[i - 1], currentWord);
            }
        }

        // Step 2: topological sorting
        StringBuffer sb = new StringBuffer();
        Map<Character, Integer> visited = new HashMap<Character, Integer>(); // mark as visited: visited.put(vertexId, -1);

        for (Map.Entry<Character, Set<Character>> entry : graph.entrySet()) {
            char vertexId = entry.getKey();
            if (!topologicalSort(vertexId, graph, sb, visited)) {
                return "";
            }
        }

        return sb.toString();
    }

    private void connectGraph(Map<Character, Set<Character>> graph, String prev, String curr) {
        if (prev == null || curr == null) {
            return;
        }

        int len = Math.min(prev.length(), curr.length());

        for (int i = 0; i < len; i++) {
            char p = prev.charAt(i);
            char q = curr.charAt(i);
            if (p != q) { // so if same duplicated work, will not reach here and not update graph
                if (!graph.get(p).contains(q)) {
                    graph.get(p).add(q);
                }
                break;
            }
        }
    }

    private boolean topologicalSort(
            char vertexId,
            Map<Character, Set<Character>> graph,
            StringBuffer sb,
            Map<Character, Integer> visited
    ) {

        if (visited.containsKey(vertexId)) {
            // visited
            if (visited.get(vertexId) == -1) { // -1 meaning visited, cycle found
                return false;
            }

            // already in the list
            if (visited.get(vertexId) == 1) {
                return true;
            }
        }

        visited.put(vertexId, -1); // mark as visited


        Set<Character> neighbors = graph.get(vertexId);
        for (char neighbor : neighbors) {
            if (!topologicalSort(neighbor, graph, sb, visited)) {
                return false;
            }
        }

        sb.insert(0, vertexId);
        visited.put(vertexId, 1); // restore visited

        return true;
    }


    /**
     * @param words: a list of words
     * @return: a string which is correct order
     */
    public String alienOrder2(String[] words) {
        // Write your code here
        if (words == null || words.length == 0) {
            return "";
        }

        // step 1: construct the graph
        //
        Map<Character, List<Character>> adjMap = new HashMap<>();
        constructGraph2(words, adjMap);

        int numNodes = adjMap.size();

        StringBuilder result = new StringBuilder();

        // toplogical sorting
        //
        Map<Character, Integer> indegreeMap = new HashMap<>();
        for (Character node : adjMap.keySet()) {
            indegreeMap.put(node, 0);
        }

        for (Character node : adjMap.keySet()) {
            for (Character neighbor : adjMap.get(node)) {
                int indegree = indegreeMap.get(neighbor);
                indegree += 1;
                indegreeMap.put(neighbor, indegree);
            }
        }

        // start from indegree=0
        Queue<Character> queue = new PriorityQueue<>();
        for (Character node : indegreeMap.keySet()) {
            if (indegreeMap.get(node) == 0) {
                // starting node, can only be one, cannot be 2 starting with 0 indegree
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            char curNode = queue.poll();
            result.append(curNode);

            for (char neighbor : adjMap.get(curNode)) {
                int indegree = indegreeMap.get(neighbor);
                indegree -= 1;
                indegreeMap.put(neighbor, indegree);

                // @note: key is here.
                // for A->B, B->C, A-C: C will not be counted until its indgree is 0

                if (indegree == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (result.length() < numNodes) {
            return "";
        }

        return result.toString();
    }

    private void constructGraph2(String[] words, Map<Character, List<Character>> adjMap) {
        // construct nodes
        for (String word : words) {
            for (Character c : word.toCharArray()) {
                adjMap.put(c, new ArrayList<>()); // c to all its next
            }
        }

        // construct edges
        for (int i = 1; i < words.length; i++) {
            String prev = words[i - 1];
            String curr = words[i];

            for (int j = 0; j < prev.length() && j < curr.length(); j++) {
                if (prev.charAt(j) != curr.charAt(j)) {
                    adjMap.get(prev.charAt(j)).add(curr.charAt(j));
                    break;
                }
            }
        }

   }


    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        List<Integer> list = new ArrayList<>();
        int j=n-1;
        for(int i=n-1;i>=0;i--){
            int left =0, right = list.size();
            while(left<right){
                int mid = left+(right-left)/2;
                if(temperatures[list.get(mid)]>temperatures[i]){
                    right = mid;
                }else {
                    left=mid+1;
                }
            }

            list.add(right,i);
            if(right<list.size()-1){
               for(int k=right+1;k<list.size();k++){
                   res[j] =res[j]==0? list.get(k)-i:Math.min(res[j],list.get(k)-i);
               }

            }else {
                res[j] = 0;
            }
            j--;


        }
        return res;
    }


    public String getPermutation(int n, int k) {
        String res = "";
        int nums = n;
        List<Integer> list = new ArrayList<>();
        for(int i=1;i<=n;i++){
            list.add(i);
        }
        while(k>1&&nums>1){
            int divider = getGroupLength(nums-1);
            Integer index =k/divider;
            int next =  k%divider;
            if(next==0){
                Integer cur = list.get(index-1);

                res+=cur;
                list.remove(cur);
                if(list.size()!=0){
                    for(int i=list.size()-1;i>=0;i--){
                        res+=list.get(i);
                    }
                }
                System.out.println(res);

                return res;
            }
            Integer cur = list.get(index);
            res+=cur;
            list.remove(cur);
            k=next;
            nums--;
        }

        if(list.size()!=0){
            for(int i:list){
                res+=i;
            }
        }
        System.out.println(res);
        return res;
    }

    public static Integer getGroupLength(int n){
        int res =1;
        while (n>1){
            res*=n;
            n--;
        }
        return res;
    }

    public static void main(String[] args) {
        Alien_Dictionary_269 test = new Alien_Dictionary_269();
//        test.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73});
        test.getPermutation(4,1);
    }
}
