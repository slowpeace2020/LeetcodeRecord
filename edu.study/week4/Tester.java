package week4;

public class Tester {
    public static void main(String[] args) {

        Explorer explorer = new Explorer("explorer");
        System.out.println(explorer.toString());
        explorer.moveRight(40);
        explorer.moveLeft(60);
        explorer.moveRight(5);
        explorer.charge();

        Scientific scientific = new Scientific("scientific");
        System.out.println(scientific.toString());
        scientific.moveRight(50);
        scientific.moveLeft(25);
        scientific.charge();
        String type = scientific.checkSubstance(2,21);
        System.out.println("the type is: "+type);


    }
}
