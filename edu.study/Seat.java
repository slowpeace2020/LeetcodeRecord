import java.util.LinkedList;
import java.util.Queue;

public class Seat {
    long[] solution(int rows, int cols, int[][] black) {
        long[] ans = new long[5];
        int[][] prefixSum = new int[rows+1][cols+1];
        int[][] grid = new int[rows][cols];
        for(int[] pos:black){
            int x = pos[0];
            int y= pos[1];
            grid[x][y] = 1;
        }

        for(int i=0;i<rows;i++){
            prefixSum[i+1][1]=prefixSum[i][1]+grid[i][0];
        }

        for(int j=0;j<cols;j++){
            prefixSum[1][j+1] = prefixSum[1][j]+grid[0][j];
        }

        for(int i=1;i<=rows;i++){
            for(int j=1;j<=cols;j++){
                prefixSum[i][j] = prefixSum[i][j-1]+prefixSum[i-1][j]+grid[i-1][j-1]-prefixSum[i-1][j-1];
            }
        }

        for(int i=0;i+2<=rows;i++){
            for(int j=0;j+2<=cols;j++){
                int val = prefixSum[i+2][j+2]-prefixSum[i][j];
                ans[val]++;
            }
        }
        return ans;
    }

    int solutionI(int[] deck1, int[] deck2) {
        Queue<Integer> one = new LinkedList<>();
        Queue<Integer> two = new LinkedList<>();
        for(int i=deck1.length-1;i>=0;i--){
            one.add(deck1[i]);
        }

        for(int i=deck2.length-1;i>=0;i--){
            two.add(deck2[i]);
        }

        int step = 0;
        int previous = 1;
        while(!one.isEmpty()&&!two.isEmpty()){
            step++;
            int element1 = one.peek();
            int element2 = two.peek();
            if(element1>element2){
                one.add(one.poll());
                one.add(two.poll());
                previous = 1;
            }else if(element2>element1){
                two.add(two.poll());
                two.add(one.poll());
                previous = 2;
            }else{
                if(previous==1){
                    one.add(one.poll());
                    one.add(two.poll());
                    previous = 1;
                }else{
                    two.add(two.poll());
                    two.add(one.poll());
                    previous = 2;
                }
            }
        }

        return step;

    }


    public static String test(String s){
        while(s.contains("AB")||s.contains("BA")||s.contains("ZY")||s.contains("YZ")){
            s = s.replaceAll("BA", "");
            System.out.println(s);
            s = s.replaceAll("AB", "");
            s = s.replaceAll("YZ", "");
            s = s.replaceAll("ZY", "");
            System.out.println(s);
        }
        return s;
    }

    public double calculateTax(int[][] brackets, int income) {
        double ans = income>brackets[0][0]? (double)(brackets[0][0]*brackets[0][1])/100:(double)income*brackets[0][1]/100;
        income-=brackets[0][0];
        int index =1;
        while(income>0){
            int value = brackets[index][0]-brackets[index-1][0];
            double tax = value>income? income:value;
            System.out.println(tax);
            System.out.println(tax*brackets[index][1]/100);
            ans+= (tax*brackets[index][1])/100;
//            System.out.println(ans);
            income-=value;
        }

        return ans;
    }

    public static void main(String[] args) {
        Seat seat = new Seat();
//        seat.solution(3,3,new int[][]{{0,0},{0,1},{1,0}});
        seat.calculateTax(new int[][]{{3,50},{7,10},{12,25}},10);
//        seat.calculateTax(new int[][]{{3,84},{4,91},{5,62}},4);
    }
}
