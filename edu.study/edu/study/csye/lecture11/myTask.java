package edu.study.csye.lecture11;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class myTask implements Runnable{

  @Override
  public void run() {
    System.out.println("Hello from thread!");
  }

  public void performActionBlock(){
    synchronized (this){
      //synchronized code
    }
  }

  public synchronized void anymethod(){

  }




  public static void main(String[] args) {
//    (new Thread(new myTask())).start();
    ExecutorService executor = Executors.newFixedThreadPool( 10 );
    executor.execute(()->System.out.println("execute lambda, execute!"));
    executor.execute(()->System.out.println("execute lambda, execute! again"));
    executor.execute(()->System.out.println("execute lambda, execute! three"));
//    executor.execute(System.out::println("Execute lambda, execute!"));

    try {
      executor.awaitTermination(2L, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("come here------------------");
  }
}
