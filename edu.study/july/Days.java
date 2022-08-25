package july;

import java.util.Scanner;

public class Days {
    public static void main(String[] args) {
        System.out.println("Enter the year: ");
        Scanner input = new Scanner(System.in);
        int year = input.nextInt();
        System.out.println("Enter the month: ");
        int month = input.nextInt();
        int days = 0;
        if(month==2){
            if(year%4!=0||(year%100==0 && year%400!=0)){
                days=28;
            }else {
                days=29;
            }
        }
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            days = 31;
        }else if (month!=2){
            days = 30;
        }

        System.out.println(year+" " + month+" has "+days+" days");
    }
}
