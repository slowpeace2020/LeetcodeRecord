package edu.study.csye.lecture11;

public class MyThread extends Thread{

  @Override
  public void run() {
    System.out.println("Hello from thread.");
  }

  public static void main(String[] args) {
    (new MyThread()).start();
  }
}
