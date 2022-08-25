package july;

import java.util.Scanner;

public class Grade {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double score = 0;
        for(int i=1;i<=5;i++){
            System.out.println("Please Enter the "+i+" course grade:");
            double courseScore = input.nextDouble();
            score += courseScore;
        }

        double average  = score/5;
        System.out.println("the average score is "+average);
    }
}
