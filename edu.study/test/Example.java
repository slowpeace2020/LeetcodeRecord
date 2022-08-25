package test;

public class Example {


    public static void main(String[] args) {
        int age = 20;
        if(age>=18){
            System.out.println("Adult");
        }else{
            System.out.println("Minor");
        }
    }

    public static double getDiscount(int customer){
        if(customer==1){
            return 0.15;
        }else if (customer==2){
            return 0.1;
        }
        return 0;
    }

    public static int getPrice(String fruit){
        if(fruit.equals("apple")){
            return 10;
        }else if(fruit.equals("plum")){
            return 20;
        }else if(fruit.equals("litchi")){
            return 30;
        }

        return 0;
    }

}
