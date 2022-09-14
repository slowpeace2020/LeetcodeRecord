package week6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ArrayListExercise {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        for(int i=0;i<10;i++){
            System.out.println("Please enter "+(i+1)+" number between 0 and 20:");
            int number = sc.nextInt();
            list.add(number);
        }

        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i)+" ");
        }

        int max = list.get(0);
        for(int i=0;i<list.size();i++){
            if(max>list.get(i)){
                max = list.get(i);
            }
        }
        System.out.println("the maxium value in the array is "+max);

        int sum=0;
        for(int i=0;i<list.size();i++){
            sum+=list.get(i);
        }

        System.out.println("the average value of array is "+sum/list.size());

        list.add(15);

        list.add(3,21);

        list.remove(2);

        for(int i=0;i<list.size();i++){
            if(list.get(i)==21){
                System.out.println("number 21 in the "+i+" position of the array");
            }
        }

        Collections.sort(list);

        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i)+" ");
        }

    }

}
