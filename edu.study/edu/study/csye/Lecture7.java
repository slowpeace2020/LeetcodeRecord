package edu.study.csye;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lecture7 {

  public static void main(String[] args) {
    Lecture7 test = new Lecture7();
    test.simplePredict();

//    Lambda expression
//    Arrays.sort(rosterAsArray,(a, b) -> Person.compareByAge(a, b) );
//    – Method reference
//    Arrays.sort(rosterAsArray, Person::compareByAge);
  }

  public static void imperativeAndDelativeProgramming() {
    final List<String> names = Arrays.asList("jim", "len", "dan", "sue", "zac");
    boolean found = false;
    for (String name : names) {
      if (name.equals("dan")) {
        found = true;
        break;
      }
    }
    System.out.println("You find dan! " + found);
    System.out.println("You find dan! ");

//    System.out.println("You find dan! "+names.stream().filter(s -> s=="dan").forEach(System.out::print););
    names.stream().filter(s -> s == "dan").forEach(System.out::print);


  }

  @FunctionalInterface
  public interface GreatDivide {

    int divide(int t1, int t2);
  }

  public void simpleLambda() {
    GreatDivide intDivide = (int x, int y) -> x / y;
    System.out.println(intDivide.divide(21, 3));
  }

  void runnableAnonymous() {
    Runnable rAnonymous = new Runnable() {
      @Override
      public void run() {
        System.out.println("Run anonymous, run!");
      }
    };

    Thread te = new Thread(rAnonymous);
    te.start();

  }

  void runnablelambda() {
    Runnable r = () -> System.out.println("run thread in background...");
    Thread thread = new Thread(r);
    thread.start();
  }

  void runnablelambda2() {
    Thread thread = new Thread( () -> System.out.println("run thread in background..."));
    thread.start();
  }

  public void simpleStream() {
    List<Integer> list = Arrays.asList(5, 2, 1, 3, 4);
    list.forEach(n -> System.out.print(n + " "));
    System.out.println("reduce to sorted add suset");
    list.stream()
        .filter(n -> n % 2 == 1)//过滤
        .sorted()               //排序
        .map(n -> n * 100)      //对值进行操作
        .forEach(n -> System.out.print(n + ", "));//遍历打印
    System.out.println();

  }

  @FunctionalInterface
  public interface Predicate<T> {

    boolean test(T t);
  }

  public void simplePredict() {
    List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    Predicate<Integer> over5Predicate = n -> {
      return n > 5;
    };
    for (int n : ints) {
      if (over5Predicate.test(n)) {
        System.out.print(n + " <** ");
      } else {
        System.out.print(n + " ");
      }
    }

  }

  public void totalPrice() {
    final List<Double> prices
        = Arrays.asList(5.0, 10.0, 15.0, 20.0);
    final Double totalPrice = prices.stream().mapToDouble((Double price) -> price * 0.9).sum();
    System.out.println(totalPrice);
  }

  public void staticMethodReference() {
    List<String> list = Arrays.asList("Jen", "Zac", "Dan");
    list.forEach(System.out::print);
  }
  public void objectInstanceMethodReference(){

  }

}
