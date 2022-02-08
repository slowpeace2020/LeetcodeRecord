import java.util.*;

public class Jan {
    class Trie {
        Trie[] child = new Trie[26];
        String word;
    }


    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        int m = board.length, n = board[0].length;
        //构建树，共用起点相同的单词，减少重复搜索
        Trie root = buildTrie(words);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int index = board[i][j] - 'a';
                if (root.child[index] == null) {
                    continue;
                }

                Trie cur = root;
                boolean[][] visited = new boolean[m][n];
                dfsFindWords(cur, i, j, res, visited, board);
            }
        }

        return res;
    }

    private void dfsFindWords(Trie cur, int i, int j, List<String> res, boolean[][] visited, char[][] board) {
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int m = board.length, n = board[0].length;
        if (i >= m || i < 0 || j >= n || j < 0) {
            return;
        }

        if (cur.child[board[i][j] - 'a'] == null) {
            return;
        }

        if (cur.child[board[i][j] - 'a'].word != null) {
            //能找到当前单词，加入结果集
            res.add(cur.child[board[i][j] - 'a'].word);
            //清空单词标记，避免重复
            cur.child[board[i][j] - 'a'].word = null;
        }

        visited[i][j] = true;
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            dfsFindWords(cur.child[board[x][y] - 'a'], x, y, res, visited, board);
        }

    }

    private Trie buildTrie(String[] words) {
        Trie root = new Trie();
        for (String word : words) {
            Trie cur = root;
            for (char c : word.toCharArray()) {
                if (cur.child[c - 'a'] == null) {
                    cur.child[c - 'a'] = new Trie();
                }
                cur = cur.child[c - 'a'];
            }
            cur.word = word;
        }
        return root;
    }

    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>>  res = new ArrayList<>();
        dfsGetFactors(2,n,new ArrayList<>(),res);
        return res;
    }

    private void dfsGetFactors(int start,int n,List<Integer> path,List<List<Integer>> res){
        if(n==1){
            if(path.size()==0){
                return;
            }
            res.add(new ArrayList<>(path));
            return;
        }



        for(int i=start;i<=n;i++){
            if(n%i!=0){
                continue;
            }

            if(n/i<1){
                break;
            }

            path.add(i);
            dfsGetFactors(i,n/i,path,res);
            path.remove(path.size()-1);

        }

    }

    public List<String> generatePalindromes(String s) {
        // write your code here
        int[] count = new int[26];
        for(char c:s.toCharArray()){
            count[c-'a']++;
        }
        List<String> res = new ArrayList<>();
        int odd=0;
        int oddIndex=-1;
        for(int i=0;i<26;i++){
            if(count[i]%2==1){
                odd++;
                oddIndex = i;
            }
            if(odd>1){
                return res;
            }
        }

        StringBuilder sb = new StringBuilder();

        int len =0;
        if(oddIndex!=-1){
            count[oddIndex]--;
        }

        for(int i=0;i<26;i++){
            count[i]/=2;
            len+=count[i];
        }

        dfsGeneratePalindromes(len,count,sb,res);
        List<String> ans = new ArrayList<>();
        if(oddIndex!=-1){
            for(String word:res){
                sb = new StringBuilder(word);
                ans.add(word+(char)(oddIndex+'a')+sb.reverse().toString());
            }
        }else{
            for(String word:res){
                sb = new StringBuilder(word);
                ans.add(word+sb.reverse().toString());
            }
        }

        return ans;

    }


    private void dfsGeneratePalindromes(int len,int[] count,StringBuilder sb,List<String> res){
        if(len==0){
            res.add(new String(sb.toString()));
            return;
        }

        for(int i=0;i<26;i++){
            if(count[i]==0){
                continue;
            }
            count[i]--;
            sb.append((char)(i+'a'));
            dfsGeneratePalindromes(len-1,count,sb,res);
            sb.deleteCharAt(sb.length()-1);
            count[i]++;
        }
    }

    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        dfsAddOperators(res,"",num,target,0,0,0);
        return res;
    }


    public void dfsAddOperators(List<String> res,String path,String num,int target,int pos,long cur,long pre){
        if(pos ==num.length()){
            if(target==cur){
                res.add(path);
            }
            return;
        }

        for(int i=pos;i<num.length();i++){
            if(i!=pos&&num.charAt(pos)=='0'){
                continue;
            }

           long next = Long.parseLong(num.substring(pos,i+1));
            if(pos==0){
                dfsAddOperators(res,""+next,num,target,i+1,next,next);
            }else {
                dfsAddOperators(res,path+"+"+next,num,target,i+1,next,next+cur);
                dfsAddOperators(res,path+"-"+next,num,target,i+1,next,next+cur);
                long x = pre*next;
                dfsAddOperators(res,path+"*"+next,num,target,i+1,next,next);

            }
        }



    }

    public int maxSumSubmatrix(int[][] matrix, int k) {
        //固定上下界，拍扁成一维的数组，找两个前缀和之差为K
        int m = matrix.length;
        int n = matrix[0].length;
        int res =Integer.MIN_VALUE;
        for(int i=0;i<m;i++){//上界
            int[] temp = new int[n];
            for(int j=i;j<m;j++){//下界
                for(int K=0;K<n;K++){//前k列前缀和,也就是确定上下界和固定左，遍历右边界的每个矩形的和
                    temp[K]=temp[K]+matrix[i][K];
                }
                res = Math.max(res,helperMaxSumSubmatrix(temp,k));
            }

        }

        return res;
    }

    private int helperMaxSumSubmatrix(int[] nums,int k){
        int n = nums.length;
        TreeSet<Integer> set = new TreeSet<>();
        set.add(0);
        int preSum =0;
        int res = Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            preSum+=nums[i];
            Integer upper = set.ceiling(preSum-k);
            if(upper!=null){
                res = Math.max(res,preSum-upper);
            }
            set.add(preSum);
        }

        return res;
    }

    public int maxSumSubmatrixI(int[][] mat, int k) {
        int m = mat.length, n = mat[0].length;
        boolean isRight = n > m;
        int[] sum = isRight ? new int[n + 1] : new int[m + 1];
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i <= (isRight ? m : n); i++) {
            Arrays.fill(sum, 0);
            for (int j = i; j <= (isRight ? m : n); j++) {
                TreeSet<Integer> ts = new TreeSet<>();
                ts.add(0);
                int a = 0;
                for (int fixed = 1; fixed <= (isRight ? n : m); fixed++) {
                    sum[fixed] += isRight ? mat[j - 1][fixed - 1] : mat[fixed - 1][j - 1] ;
                    a += sum[fixed];
                    Integer b = ts.ceiling(a - k);
                    if (b != null) {
                        int cur = a - b;
                        ans = Math.max(ans, cur);
                    }
                    ts.add(a);
                }
            }
        }
        return ans;
    }

    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        if(n<=2){
            return false;
        }
        int max = num.startsWith("0")?1:n-2;
        for(int i=1;i<=max;i++){
            Long pre = Long.valueOf(num.substring(0,i));
            for(int j=i+1;j<=n-1;j++){
                if(num.substring(i,j).startsWith("0")&&j>i+1){
                    continue;
                }
                Long cur = Long.valueOf(num.substring(i,j));
                Long next = pre+cur;
                if(num.substring(j).startsWith(next+"")){
                    if(additiveNumber(cur,next,j+(next+"").length(),num)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean additiveNumber(Long pre,Long cur,int start,String num){
        if(start==num.length()){
            return true;
        }

        long next = pre+cur;
        if(num.substring(start).startsWith(next+"")){
            return additiveNumber(cur,next,start+(next+"").length(),num);
        }

        return false;
    }


    public List<String> generateAbbreviations(String word) {
        // Write your code here
        Set<String> res = new HashSet<>();
        List<String> ans = new ArrayList<>();
        int n = word.length();
        if(n==1){
            ans.add("1");
            ans.add(word);
            return ans;
        }
        for(int i=1;i<n;i++){
            List<String> pre = generateAbbreviations(word.substring(0,i));
            List<String> next = generateAbbreviations(word.substring(i));
            List<String> list = combineAbbr(pre,next);
            res.addAll(list);
        }
        ans.addAll(res);
        return ans;

    }

    public List<String> combineAbbr(List<String> pre,List<String> next){
        List<String> res = new ArrayList<>();
        for(String word:pre){
            int i = word.length();
            while(i>=1&&Character.isDigit(word.charAt(i-1))){
                i--;
            }
            int end =0;
            if(i<word.length()){
                end = Integer.valueOf(word.substring(i));
                word = word.substring(0,i);
            }

            for(String str:next){
                int j=-1;
                while(j+1<str.length()&&Character.isDigit(str.charAt(j+1))){
                    j++;
                }

                int start = 0;
                if(j>=0){
                    start= Integer.valueOf(str.substring(0,j+1));
                    str = str.substring(j+1);
                }
                int mid = (start+end);
                String ans = word+str;
                if(mid!=0){
                    ans = word+mid+str;
                }
                res.add(ans);
            }
        }

        return res;

    }

    public boolean judgePoint24(int[] nums) {
        //转成double计算，丢坑里了
        //[8,1,6,6] 6÷(1-6÷8) =6÷(1-6/8) =6÷2/8 =6x8/2 =6x4 =24
//        double[] doubles = Arrays.stream(nums).asDoubleStream().toArray();


        double[] doubles = new double[nums.length];
        int i = 0;
        for(int num:nums){
            doubles[i++]=num;
        }

       return helperJudgePoint24(doubles);
    }

    private boolean helperJudgePoint24(double[] nums) {
        if(nums.length==1){
            return Math.abs(nums[0]-24)<1e-6;
        }

        // 每次选择两个不同的数进行回溯
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                // 将选择出来的两个数的计算结果和原数组剩下的数加入 next 数组
                double[] next = new double[nums.length-1];
                for(int k=0,pos=0;k<nums.length;k++){
                    if(k!=i&&k!=j){
                        next[pos++]=nums[k++];
                    }
                }

                for(double num:calculate(nums[i],nums[j])){
                    next[next.length-1] = num;
                    if(helperJudgePoint24(next)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private List<Double> calculate(double num1, double num2) {
        List<Double> list = new ArrayList<>();
        list.add(num1+num2);
        list.add(num1-num2);
        list.add(num2-num1);
        list.add(num1*num2);
        if(!(Math.abs(num2)<1e-6)){
            list.add(num1/num2);
        }

        if(!(Math.abs(num1)<1e-6)){
            list.add(num2/num1);
        }

        return list;
    }


    public static void main(String[] args) {
        Jan jan = new Jan();
        jan.generateAbbreviations("word");
//        jan.isAdditiveNumber("112358");
//        jan.isAdditiveNumber("199100199");
    }

}
