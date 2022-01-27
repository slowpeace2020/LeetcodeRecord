package unlock;

import java.util.*;

public class DPString {
    public int palindromePartition(String s, int k) {
        Map<String,Integer> map =new HashMap<>();
        int n = s.length();

        //0-i划分为k块的最小代价
        int[][] dp = new int[k+1][n+1];


        //0-i区间的字符串划分成一整块，所需要的最小代价
        for(int i=0;i<n;i++){
            dp[1][i+1]=getChanges(s,0,i+1,map);
        }

        //将字符串划分为2，3，。。。。，k块
        for(int i=2;i<=k;i++){
            //子串结束的位置
            for(int end=i-1;end<n;end++){
                dp[i][end]=n;

                //0-start视为前i-1块，start-end视为最后一块
                for(int start=end-1;start>=i-2;start--){
                    dp[i][end] = Math.min(dp[i][end],dp[i-1][start]+getChanges(s,start+1,end,map));
                }
            }
        }

        return dp[k][n-1];
    }

    private int getChanges(String s, int start, int end,Map<String,Integer> map) {
        String cur = s.substring(start,end);
        if(map.containsKey(cur)){
            return map.get(cur);
        }
        int changes =0;
        while (start<end){
            if(s.charAt(start++)!=s.charAt(end--)){
                changes++;
            }
        }
        map.put(cur,changes);
        return changes;
    }

  
      public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }


    public List<TreeNode> generateTrees(int n) {
        List<TreeNode>[] res = new List[n+1];
        res[0]=new ArrayList<>();
        if(n==0){
            return res[0];
        }
        res[0].add(null);
        for(int len=1;len<=n;len++){
            res[len] = new ArrayList<>();
            for(int leftLen =0;leftLen<len;leftLen++){
                for(TreeNode left:res[leftLen]){
                    for(TreeNode right:res[len-leftLen-1]){
                        TreeNode root = new TreeNode(leftLen+1);
                        root.left=left;
                        //右子树起点的位置
                        root.right = cloneNode(right,leftLen+1);
                        //dp累计结果
                        res[len].add(root);
                    }
                }
            }
        }

        return res[n];
    }

    //递归复制树结构，仅仅是值的偏移
    private TreeNode cloneNode(TreeNode source, int offset) {
        if(source==null){
            return null;
        }
        TreeNode copy = new TreeNode(source.val+offset);
        copy.left = cloneNode(source.left,offset);
        copy.right=cloneNode(source.right,offset);
        return copy;
    }


    public int rob(TreeNode root) {
        int[] res = robHouse(root);
        return Math.max(res[0],res[1]);
    }

    private int[] robHouse(TreeNode root){
        if(root==null){
            return new int[2];
        }

        //先去算左右子树，抢和不抢的结果
        int[] left = robHouse(root.left);
        int[] right = robHouse(root.right);
        int[] ans = new int[2];
        //当前节点不抢，左右子树抢不抢都行，取最大的结果
        ans[0] = Math.max(left[0],left[1])+Math.max(right[0],right[1]);
        //当前节点抢，左右字数都不能抢
        ans[1] = root.val+left[0]+right[0];
        return ans;
    }


    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
       Map<Integer,List<int[]>> map=new HashMap<>();
       for(int[] flight:flights){
           map.putIfAbsent(flight[0],new ArrayList<>());
           map.get(flight[0]).add(new int[]{flight[1],flight[2]});
       }

       PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->(a[0]-b[0]));
       //这里把限制的步数作为属性传进去，控制循环
       queue.offer(new int[]{0,src,k+1});

       while (!queue.isEmpty()){
           int[] cur = queue.poll();
           int price = cur[0];
           int point = cur[1];
           int stop = cur[2];
           if(point==dst){
               return price;
           }

           if(stop>0){
               if(!map.containsKey(point)){
                   continue;
               }

               for(int[] next:map.get(point)){
                   queue.add(new int[]{price+next[1],next[0],stop-1});
               }
           }


       }

       return -1;
    }

    class ResultType{
        int subNodesCoverd;// all nodes below its level are covered (not including itself)
        int coveredNoCamera;// the node itself is covered by its children
        int coveredCamera; // the node is covered by itself
        ResultType(int subNodesCoverd, int coveredNoCamera, int coveredCamera){
            this.subNodesCoverd = subNodesCoverd;
            this.coveredNoCamera = coveredNoCamera;
            this.coveredCamera = coveredCamera;
        }
    }

    public int minCameraCover(TreeNode root) {
        ResultType res = helperMinCameraCover(root);
        return Math.min(res.coveredCamera,res.coveredNoCamera);
    }


    public ResultType helperMinCameraCover(TreeNode root){
        if(root==null){
            return new ResultType(0,0,9999);
        }

        ResultType left = helperMinCameraCover(root.left);
        ResultType right = helperMinCameraCover(root.right);
        int leftMin = Math.min(left.coveredCamera,left.coveredNoCamera);
        int rightMin = Math.min(right.coveredCamera,right.coveredNoCamera);
        int suNodesCoverd = left.coveredNoCamera+right.coveredNoCamera;
        int coveredCamera = Math.min(left.coveredCamera+rightMin,right.coveredCamera+leftMin);
        int coveredNoCamera = 1+Math.min(left.subNodesCoverd,leftMin)+Math.min(right.subNodesCoverd,rightMin);
        return new ResultType(suNodesCoverd,coveredNoCamera,coveredCamera);
    }


    long maxPro=0;
    long total=0;
    public int maxProduct(TreeNode root) {
        total = sumNode(root);
        sumNode(root);
        return (int) (maxPro%(1e9+7));
    }


    public long sumNode(TreeNode root){
        if(root==null){
            return 0;
        }
        long sub = root.val+sumNode(root.left)+sumNode(root.right);
        maxPro = Math.max(maxPro,sub*(total-sub));
        return sub;
    }

    public int longestZigZag(TreeNode root) {
        return dfsZigZag(root)[2];
    }

    public int[] dfsZigZag(TreeNode root) {
        //因为上一节点+1，若当前节点为空便不能算拐点，方便抵消回去
        if(root==null){
            return new int[]{-1,-1,-1};
        }

        int[] left = dfsZigZag(root.left);
        int[] right=dfsZigZag(root.right);
        //当前节点的全部最优解，左拐||右拐||或者不参与
        int res = Math.max(Math.max(left[1],right[0])+1,Math.max(left[2],right[2]));
        return new int[]{left[1]+1,right[0]+1,res};
    }


    public int numWays(int steps, int arrLen) {
        int arraySize =arrLen;
        long[][] dp = new long[arraySize][steps+1];
        dp[0][0]=1;
//        for(int i=0;i<arraySize && i<=steps;i++){
//            dp[i][i]=1;
//        }


        for(int j=1; j<=steps;j++){
            for(int i=0;i<arraySize;i++){
                if(i>j){
                    continue;
                }
                dp[i][j]+=dp[i][j-1]; //dp[0][1]=dp[0][0];
                if(i>0){
                    dp[i][j]+=dp[i-1][j-1];
                }
                if(i+1<arraySize){
                    dp[i][j]+=dp[i+1][j-1];
                }
                dp[i][j]= (long) (dp[i][j]%(1e9+7));
//                showArray(dp);
            }
        }

        int res = (int)(dp[0][steps]/(1e9+7));

        return res;

    }


    public int numWaysI(int steps, int arrLen) {
        //最大的位置不会超过数组长度，但是也不会超过能走的最远位置
        int maxPos = Math.min(steps,arrLen);
        //经过i步最终位置在j的路径数量
        long[][] dp = new long[steps+1][maxPos+1];
        dp[0][0]=1;
        for(int i=1;i<=steps;i++){
            for(int j=0;j<maxPos;j++){
                dp[i][j]=dp[i-1][j];
                if(j>0){
                    dp[i][j]+=dp[i-1][j-1];
                }

                dp[i][j]+=dp[i-1][j+1];

                dp[i][j]= (long) (dp[i][j]%(1e9+7));
            }
        }

        return (int) dp[steps][0];
    }



    int res=0;

    public int maxSumBS(TreeNode root) {
        findMaxSum(root);
        return res;
    }

    //遍历二叉树的同时记录子节点的各种性质，方便复用
    public int[] findMaxSum(TreeNode root){
        if(root==null){
            //空视为有效
            //是否有效，最大值，最小值，以当前节点为根的和
            return new int[]{1,Integer.MIN_VALUE,Integer.MAX_VALUE,0};
        }

        int[] left = findMaxSum(root.left);
        int[] right = findMaxSum(root.right);
        //利用子节点的性质判断以当前节点为根节点是否为有效的二叉树
        boolean isValid = left[0]==1&&right[0]==1&&root.val>left[1]&&root.val<right[2];
        int sum=root.val+left[3]+right[3];
        //有效的二叉树才参与最终结果的比较
        if(isValid){
            res =Math.max(sum,res);
        }
        //缩小有效子树的值范围，方便遍历判断
        return new int[]{isValid?1:0,Math.max(root.val,right[1]),Math.min(root.val,left[2]),sum};
    }

    public int maxSumBST(TreeNode root) {
        isValidBST(root,-4 * 10000,4 * 10000);
        return res;
    }

    public int isValidBST(TreeNode root,int lower,int upper){
        if(root==null){
            return 0;
        }

        if(root.val<lower||root.val>upper){
            return -1;
        }
        int sum = root.val;
        boolean isValid=true;
        if(root.left!=null){
            int left = isValidBST(root.left,lower,root.val-1);
            if(left==-1){
                isValid=false;
                isValidBST(root.left,-4 * 10000,4 * 10000);
            }
            sum+=left;
        }

        if(root.right!=null){
            int right = isValidBST(root.right,root.val+1,upper);
            if(right==-1){
                isValid=false;
                isValidBST(root.right,-4 * 10000,4 * 10000);
            }
            sum+=right;
        }

         if(isValid){
            res = Math.max(res,sum);
            return sum;
        }
        return -1;
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] infos = new int[len][2];

        for(int i=0;i<strs.length;i++){
            infos[i] = countZeroOne(strs[i]);
        }

        int[][] dp = new int[m+1][n+1];
        dp[0][0]=0;
        for(int[] element:infos){
            for(int i=m;i>=element[0];i++){
                    for(int j=n;j>=element[1];j++){
                    dp[i][j]=Math.max(dp[i-element[0]][j-element[1]]+1,dp[i][j]);
                }
                showArray(dp);
            }
        }

        return dp[m][n];

    }

    public int[] countZeroOne(String str){
        int zero = 0;
        int one=0;
        for(char c:str.toCharArray()){
            if(c=='0'){
                zero++;
            }else {
                one++;
            }
        }

        return new int[]{zero,one};
    }



    public TreeNode constructTree(){
        TreeNode root = new TreeNode(1);
        root.left=new TreeNode(2);
        root.left.left=new TreeNode(4);
        root.left.left.left=new TreeNode(7);
        root.left.right=new TreeNode(5);
        root.right=new TreeNode(3);
//        root.right.left=new TreeNode(2);
        root.right.right=new TreeNode(6);
//        root.right.right.left=new TreeNode(4);
        root.right.right.right=new TreeNode(8);


//        TreeNode root = new TreeNode(4);
//        root.left=new TreeNode(3);
//        root.left.left=new TreeNode(1);
//        root.left.right=new TreeNode(2);
        return root;
    }

    public int findTargetSumWays(int[] nums, int s) {
        //因为s可能为负数，用数组不好表示，改用map
        Map<Integer,Integer> dp = new HashMap<>();
        //最开始相当于结果中只有一个0
        dp.putIfAbsent(0,1);
        for(int num:nums){
            Map<Integer,Integer> temp = new HashMap<>();
            //把每个数字追加当当前的的结果后面去计算
            for(int tempSum:dp.keySet()){
                //分别对之前数字嘴和后的结果
                // 进行加减操作之后的新结果组合数量
                int key1 = tempSum+num;
                int key2 = tempSum-num;
                temp.put(key1,temp.getOrDefault(key1,0)+dp.get(tempSum));
                temp.put(key2,temp.getOrDefault(key2,0)+dp.get(tempSum));
            }
            dp = temp;
        }

        return dp.get(s);
    }

    public int shoppingOffersI(int[] price, int[][] special, int[] needs) {
           int[][] dp = new int[4][3];
           for(int i=0;i<dp.length;i++){
               for(int j=0;j<dp[0].length;j++){
                   dp[i][j]=price[0]*i+price[1]*j;
               }
           }

           for(int[] sp:special){

               for(int i=sp[0];i<dp.length;i++){
                   for(int j=sp[1];j<dp[0].length;j++){
                       dp[i][j]=Math.min(dp[i-sp[0]][j-sp[1]]+sp[2],dp[i][j]);
                   }
               }
           }

           return dp[3][2];
    }

    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
            return backtrace(price,special,needs,0);
    }

    private int backtrace(List<Integer> price, List<List<Integer>> special, List<Integer> needs, int start) {
            int res = directPurchase(price,needs);
            //以每个offer为遍历第一层递归下去
            for(int i=start;i<special.size();i++){
                List<Integer> offer = special.get(i);
                if(isValidOffer(offer,needs)){
                    List<Integer> tempNeeds = new ArrayList<>();
                    for(int j=0;j<needs.size();j++){
                        tempNeeds.add(needs.get(j)-offer.get(j));
                    }

                    //递归剩余的
                    res = Math.min(res,backtrace(price,special,tempNeeds,start));

                }
            }

            return res;
      }

    private boolean isValidOffer(List<Integer> offer, List<Integer> needs) {
        for(int i=0;i<needs.size();i++){
            if(needs.get(i)<offer.get(i)){
                return false;
            }
        }
        return true;
    }

    private int directPurchase(List<Integer> price, List<Integer> needs) {
        int sum =0;
        for(int i=0;i<needs.size();i++){
            sum+=needs.get(i)*price.get(i);
        }
        return sum;
    }

    public int minSteps(int n) {
        int[] dp = new int[n+1];
        for(int i=2;i<=n;i++){
            dp[i]=i;
            for(int j=i-1;j>1;j--){
                if(i%j==0){
                    dp[i]=dp[j]+(i/j);
                    break;
                }
            }
        }
        return dp[n];
    }

    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int mod = (int) (1e9+7);
        //不超过i个成员，获利为j的方案数量
        long[][] dp=new long[n+1][minProfit+1];
        dp[0][0]=1;
        //for将每个团队追加进去
        for(int i=0;i<group.length;i++){
            //追加到可以追加的方案中去，加入之后的人数不会超过上限
            for(int j=n-group[i];j>0;j--){
                //获利从大到小遍历，保证不重复计算
                for(int k=minProfit;k>=0;k--){
                    if(dp[i][k]==0){
                        continue;
                    }
                    //当获利超过minProfit时，视为minProfit
                    int tp = Math.min(k+profit[i],minProfit);
                    //累计有效的组合数量
                    dp[j+group[i]][tp] =(dp[j+group[i]][tp]+dp[j][k])%mod;
                }
            }

        }

        return (int) (dp[n][minProfit]%(1e9+7));
    }


    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        Map<Integer, Boolean> map = new HashMap<>();
        return dfsWin(map,0,0, maxChoosableInteger,desiredTotal);
    }


    public boolean dfsWin(Map<Integer, Boolean> map, int state,int sum,int maxChoosableInteger, int desiredTotal){

        if(sum>=desiredTotal){
            map.put(state,true);
            return true;
        }
        if(map.containsKey(state)){
            return map.get(state);
        }

        for(int i=1;i<=maxChoosableInteger;i++){
            if((state&(1<<i))>0){
                continue;
            }
            int curState = state+(1<<i);
            if(!dfsWin(map,curState,sum+i,maxChoosableInteger,desiredTotal)){
                map.put(state,true);
                return true;
            }

        }

        map.put(state,false);
        return false;
    }

    int ans =0;
    int depth=0;
    public int deepestLeavesSum(TreeNode root) {
        if(root==null){
            return 0;
        }

        depthSum(0,root);
        return ans;
    }

    public void depthSum(int curdepth, TreeNode root){
        if(root==null){
            return;
        }
        if(root.left==null&&root.right==null){
            if(curdepth==depth){
                ans+=root.val;
            }else if(curdepth>depth){
                depth=curdepth;
                ans = root.val;
            }
            return;
        }
        depthSum(curdepth+1,root.left);

        depthSum(curdepth+1,root.right);

    }

    public int minRefuelStops(int target, int startFuel, int[][] stations) {
            Queue<Integer> queue = new PriorityQueue<>();
            int i=0;
            int res;
            //能到达的距离小于目的地时，需要加油
            for(res=0;startFuel<target;res++){
                //把所有能到达的加油站的油都放进队列
                while (i< stations.length&&stations[i][0]<=startFuel){
                    queue.offer(-stations[i++][1]);
                }
                //还没到目的地，无油可加
                if(queue.isEmpty()){
                    return -1;
                }
                //加油就加在这些能到的加油站中提供最多的油的站
                startFuel+=-queue.poll();
            }

            return res;
    }

    public int numRollsToTarget(int n, int k, int target) {
        int[][] dp = new int[n+1][target+1];
        for(int i=1;i<=k;i++){
            dp[1][i]=1;
        }
        int mod = (int)(1e9+7);
        for(int i=2;i<=n;i++){
            for(int j=2;j<=target;j++){
                for(int val=1;val<j&&val<=k;val++){
                    dp[i][j] =(dp[i][j]+dp[i-1][j-val]*dp[1][val])%mod;
                }
            }

        }

        return dp[n][target];
    }

    public int countVowelPermutation(int n) {
        long[] dp = new long[5];
        Arrays.fill(dp,1);
        int mod=(int)(1e9+7);
        for(int i=2;i<=n;i++){
            long[] temp = new long[5];
            temp[0] = dp[1]%mod;
            temp[1] = (dp[0]+dp[2])%mod;
            temp[2] = (dp[0]+dp[1]+dp[3]+dp[4])%mod;
            temp[3] = (dp[2]+dp[4])%mod;
            temp[4] = dp[0]%mod;
            dp=temp;
        }

        long res = 0;
        for(long num:dp){
            res+=num;
        }

        res = res%mod;

        return (int)res;

    }

    public int dieSimulator(int n, int[] rollMax) {
        return (int)dfs(n, rollMax, 6, 0, new Long[n + 1][7][16]);//6 is dummy value for prev
    }

    /**
     * 递归查找n个数字组合的数量
     * @param n  数字组合长度
     * @param rollMax  每个数字连续出现的限制
     * @param prev   前一个数字的值
     * @param count  前一个数字连续出现的长度
     * @param memo   存储i个数字组合长度结尾为j+1, j+1连续出现次数为k时的有效组合数量，避免重复计算
     * @return
     */
    private long dfs(int n, int[] rollMax, int prev, int count, Long[][][] memo){//prev -> previous face, count -> count of continuous occurrence of prev face
        if(n == 0) return 1;

        if(memo[n][prev][count] != null) return memo[n][prev][count];//memoized result
        long res = 0;

        for(int i = 0; i < 6; i++){
            if(i == prev && count >= rollMax[i]) continue;//we can't use this face more than rollMax[i]

            if(i == prev)//using same face as previous then count + 1
                res = (res + dfs(n - 1, rollMax, i, count + 1, memo)) % 1000000007;
            else//otherwise set count to 1
                res = (res + dfs(n - 1, rollMax, i, 1, memo)) % 1000000007;
        }
        return memo[n][prev][count] = res;//memoize result
    }


    public int lastStoneWeightII(int[] stones) {
        //能否挑选出石子重量和为i
         boolean[] dp = new boolean[1501];
         dp[0] =true;
         int sum = 0;
         //遍历石子
         for(int stone:stones){
             //累计石子总重量
             sum+=stone;
             //每加入一个石子，更新dp
             for(int i=Math.min(sum,1500);i>=stone;i++){
                 dp[i]=dp[i]||dp[i-stone];
             }
         }

         //两个石子相减的差，相当于将石子分为尽可能接近的两堆之差
         for(int i=sum/2;i>=0;i--){
             if(dp[i]){
                 return sum-2*i;
             }
         }
         return 0;
    }


    public int coinChange(int[] coins, int amount) {
        if(amount==0){
            return 0;
        }
        int[] dp = new int[amount+1];
        Arrays.fill(dp,-1);
        dp[0] = 0;
        for(int coin:coins){
                for(int i=coin;i<=amount;i++){
                dp[i]=dp[i]==-1?dp[i-coin]+1:Math.min(dp[i],dp[i-coin]+1);
            }
        }

        return dp[amount];
    }

    public int change(int amount, int[] coins) {
        int[] dp = new int[amount+1];
        dp[0]=1;
        for(int coin:coins){
            for(int i=coin;i<=amount;i++){
                dp[i]+=dp[i-coin];
            }
        }

        return dp[amount];
    }


    public int maxProfit(int[] prices) {
        //当前价格下，处于卖出，持有，冷冻状态下的最大收益
        int sold = 0, hold = Integer.MIN_VALUE, rest = 0;
        for (int i=0; i<prices.length; ++i)
        {
            //保存之前卖出状态下的收益
            int prvSold = sold;
            //当前卖出状态下的最大收益为之前持有的收益加上当前卖出动作的收益
            sold = hold + prices[i];
            //当前持有状态下的最大收益，
            // 可能是继续持有，也可能是之前是冷冻状态然后买进持有
            hold = Math.max(hold, rest-prices[i]);
            //当前状态是冷冻状态，可以是之前的冷冻状态继续保持，也可以是之前卖出，现在冷冻期
            rest = Math.max(rest, prvSold);
        }
        return Math.max(sold, rest);
    }


    private int[][] m = new int[1001][1001];
    public int minHeightShelves(int[][] books, int shelfWidth) {
        return minHeightShelves(books, shelfWidth, 0, 0, 0);
    }

    private int minHeightShelves(int[][] books, int maxWidth, int idx, int w, int h) {
        if (idx >= books.length) return h;
        if (m[idx][w] != 0) return m[idx][w];
        return m[idx][w] = Math.min(
                // min of placing book in new shelf, updating latest h and w of current book
                h + minHeightShelves(books, maxWidth, idx+1, books[idx][0], books[idx][1]),
                // or placing book in same shelf and check min h
                w + books[idx][0] > maxWidth ? Integer.MAX_VALUE : minHeightShelves(books, maxWidth, idx+1, w + books[idx][0], Math.max(h, books[idx][1]))
        );
    }




        public static void main(String[] args) {
        System.out.println((2&(1<<1)));
        DPString test = new DPString();

//        System.out.println(test.canIWin(10,11));
        test.coinChange(new int[]{1,2,5}, 11);
    }

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        if(maxMove<=0){
            return 0;
        }
        int mod = (int) (1e9+7);
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //只记录当前步数状态下，到达坐标i,j的方法数量，不用三维记录所有，理不清楚
        int[][] dp=new int[m][n];
        dp[startRow][startColumn] = 1;
        int res =0;
        for(int step=0;step<maxMove;step++){
            //下一个步数状态下的路径数量
            //避免更新干扰
            int[][] temp = new int[m][n];
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    for(int[] dir:dirs){
                        int x = i+dir[0];
                        int y = i+dir[1];
                        if(x<0||y<0||x>=m||y>=n){
                            res = (res+dp[i][j])%mod;
                        }else {
                            temp[x][y]=(temp[x][y]+dp[i][j])%mod;
                        }
                    }
                }
            }
            dp = temp;
        }

        return res;
    }



        public void  showArray(int[][] dp){
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                System.out.print(dp[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println();

    }
}
