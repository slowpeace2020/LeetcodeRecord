package test;

public class MobilePhone {
    int price;
    String brand;
    String type;

    public MobilePhone(){
    }

    public MobilePhone(int price, String brand,String type){
        this.price = price;
        this.brand = brand;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MobilePhone{" +
                "price=" + price +
                ", brand=" + brand +
                ", type=" + type +
                "}";

    }

    public static void main(String[] args) {
//        MobilePhone apple = new MobilePhone(6999,"apple","ios");
//        MobilePhone mobile = new MobilePhone();
//        MobilePhone xiaomi = new MobilePhone(2999,"xiaomi","android");
//        System.out.println(apple);
//        System.out.println(mobile);
//        System.out.println(xiaomi);

//        System.out.println("a,b,c");
//        System.out.println("去吃饭");

        String one = "          nice to meet you      ";
        System.out.println(one);
        System.out.println(one.trim());

//        for(int i=0;i<20;i++){
//            System.out.println(i);
//        }

        int sum = 0;
        for(int i=1;i<=10;i++){
            System.out.println("i = "+ i);
            sum+=i;
            System.out.println("sum = "+sum);

        }

        System.out.println(sum);

//        int a=0;
//        a=a+1;
//
        int i=0;
        while (i<20){
            System.out.println(i);
            i++;
//            i=i+1;
        }

        int age=20;
        if(age<18){
            System.out.println("minor");
        }else {
            System.out.println("adult");
        }

    }


}
