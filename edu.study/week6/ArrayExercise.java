package week6;

import java.util.Scanner;

public class ArrayExercise {

    public static void main(String[] args) {
        int[] numberArray = new int[11];
        Scanner sc = new Scanner(System.in);

        for(int i=0;i<10;i++){
            System.out.println("Please enter "+(i+1)+" number between 0 and 20:");
            numberArray[i] = sc.nextInt();
        }

        for(int i=0;i<numberArray.length;i++){
            System.out.print(numberArray[i]+" ");
        }

        int max = numberArray[0];
        for(int i=0;i<numberArray.length;i++){
            if(max>numberArray[i]){
                max = numberArray[i];
            }
        }
        System.out.println("the maxium value in the array is "+max);

        int sum=0;
        for(int i=0;i<numberArray.length;i++){
            sum+=numberArray[i];
        }

        System.out.println("the average value of array is "+sum/numberArray.length);

        numberArray[numberArray.length-1] = 15;

        for(int i=numberArray.length-2;i>=3;i--){
            numberArray[i+1] = numberArray[i];
        }
        numberArray[3] = 21;

        for(int i=3;i<numberArray.length;i++){
            numberArray[i-1] = numberArray[i];
        }

        for(int i=0;i<numberArray.length;i++){
            if(numberArray[i]==21){
                System.out.println("number 21 in the "+i+" position of the array");
            }
        }

        for(int i=0;i<numberArray.length-1;i++){
            for(int j=0;j<numberArray.length-1-i;j++){
                if(numberArray[j]>numberArray[j+1]){
                    int temp = numberArray[j];
                    numberArray[j] = numberArray[j+1];
                    numberArray[j+1] = temp;
                }
            }
        }

        for(int i=0;i<numberArray.length;i++){
            System.out.print(numberArray[i]+" ");
        }

    }

}
