package edu.study.csye;

import java.util.Scanner;

public class MyTest {
    public static void main(String[] args) {
        Student dan = new Student();
        Scanner in = new Scanner("peters,dan,17,3.25");
        in.useDelimiter(",");
        dan.setLastName(in.next());
        dan.setFirstName(in.next());
        dan.setAge(Integer.parseInt(in.next()));
        dan.setGpa(Double.parseDouble(in.next()));
        in.close();

        System.out.println(dan);


        int a = 5;
        double b = (float)a;

    }

    public static void test(){
        try{
            int x = 0;
            int  y = 5/x;
        } catch (ArithmeticException e){
            System.out.println("Arithmetic Exception");
        } catch (Exception e){
            System.out.println("Exception");
        }
        System.out.println("finished");
    }
}
