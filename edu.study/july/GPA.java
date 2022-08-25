package july;

import java.util.Scanner;

public class GPA {
    public static void main(String[] args) {
        System.out.println("Enter the grade: ");
        Scanner input = new Scanner(System.in);
        String grade = input.nextLine().trim();
        int GPA = 0;
        if(grade.equals("A+")){
            GPA = 9;
        }else if(grade.equals("A")){
            GPA = 8;
        }else if(grade.equals("A-")){
            GPA = 7;
        }else if(grade.equals("B+")){
            GPA = 6;
        }else if(grade.equals("B")){
            GPA = 5;
        }else if(grade.equals("B-")){
            GPA = 4;
        }else if(grade.equals("C+")){
            GPA = 3;
        }else if(grade.equals("C")){
            GPA = 2;
        }else if(grade.equals("C-")){
            GPA = 1;
        }
        System.out.println("the GPA is "+GPA);
    }
}
