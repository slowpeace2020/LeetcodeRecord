package test;

public class Cat {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int age;
    private int weight;
    private int color;

    public static void main(String[] args) {
        int a=0,b=1,c=2;
//        if(a==b){
//            System.out.println("a 等于 b");
//        }else{
//            System.out.println("a 不等于 b");
//        }

        //[US]  [s1] [s2]
        //[US]



        String s1 = "US";
//        String s2 = "US";
        String s2 = new String("US");
        if(s1==s2){
            System.out.println("a 等于 b");
        }else{
            System.out.println("a 不等于 b");
        }

        if(s1.equals(s2)){
            System.out.println("a 等于 b");
        }else{
            System.out.println("a 不等于 b");
        }


//       int res = add(1,2);
//        System.out.println(res);

    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static String add(int a,int b){
        /**
         *
         */

        return a+""+b;
    }

    public static int multiple(int a,int b){
        return a*b;
    }






}
