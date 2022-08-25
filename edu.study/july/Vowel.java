package july;

import java.util.Scanner;

public class Vowel {
    public static void main(String[] args) {
//        CalculateDrawdown("0.47 -.06 .33 -.04 0.28 -0.03 0.37 0.12 -.02");
        CalculateDrawdown("0.22 -0.05 0.17 -0.03 0.12 -0.02 0.41 0.22 -0.03");

    }

    public static double CalculateDrawdown(String returnRecordLine) {
        String[] records = returnRecordLine.split(" ");
        if(records.length<=1){
            return 0;
        }
        double highest = 1000;
        int times = 0;
        double pre =1000;
        for(int i=0;i<records.length;i++){
            double current = Double.valueOf(records[i]);

            double price = pre*(1+current);
            System.out.println(price);
            System.out.println(highest);
            if(price<highest){
                double drawdown = (price-highest)/highest;
                if(drawdown<=-0.1){
                    times++;
                }
            }else{
                highest = price;
            }

            pre = price;

        }
        System.out.println(times);

        double possibility = (double)times/(double)records.length;
        return possibility;
     }

    }
