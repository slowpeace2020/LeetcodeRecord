package july;

import java.util.Scanner;

public class Tax {
    public static void main(String[] args) {
        System.out.println("Enter the income: ");
        Scanner input = new Scanner(System.in);
        int income = input.nextInt();
        int tax = 0;
        if(income<14000){
            tax = (int) (income*0.105);
        }else if(income<=48000){
            tax = (int) ((14000*0.105)+(income-14000)*0.175);
        }else if(income<=70000){
            tax = (int) ((14000*0.105)+(48000-14000)*0.175+(income-48000)*0.1);
        }else{
            tax =(int) ((14000*0.105)+(48000-14000)*0.175+(70000-48000)*0.1+(income-70000)*0.33);
        }

        System.out.println("the tax is "+tax);
    }
}
