package edu.study.csye;

public enum Fruit {
  APPLE("A"),
  KIWI("K"),
  GRAPE("G"),
  PEAR("P");
  private String fruitLetter;

  private Fruit(String s){
    fruitLetter = s;
  }
  public String getFruitLetter(){
    return fruitLetter;
  }

  public static void main(String[] args) {
    System.out.println(Fruit.KIWI.getFruitLetter());
  }
}
