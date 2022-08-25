package companyOA.citadel;

public class OddString {
    //https://massivealgorithms.blogspot.com/2015/11/even-or-odd-string-interview-question.html

    public static  boolean checkComputation(String data[] ){
        int n = data.length;
        boolean[] even = new boolean[n];
        for(int i=0;i<n;i++){
            boolean isEven = false;
            for(char c:data[i].toCharArray()){
                if(c%2==0){
                    isEven = true;
                    break;
                }
            }
            even[i] = isEven;
        }

        int odd = 0;
        for(boolean isEven:even){
            if(!isEven){
                odd++;
            }
        }

        return odd%2==0;
    }
}
