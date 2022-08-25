package basic;

public class WhileLoop {
    public static void main(String[] args) {
        //初始值为1
        int count =1;
        //sum记录相加结果
        int sum = 0;
        while (count<=10){
            System.out.println("当前值为："+count);
            sum=sum+count;
            count=count+1;
        }

        System.out.println("和为："+sum);
    }
}
