package edu.neu.csye6200;

public class TwoAlternatingThreads implements Runnable{
  private char c='a';

  public synchronized void printCharacters(char c){
    System.out.println(c);
  }

  @Override
  public void run() {
    System.out.println(c);
  }

  public static void main(String[] args) {
    TwoAlternatingThreads a = new TwoAlternatingThreads();
    TwoAlternatingThreads b = new TwoAlternatingThreads();
    Thread test1 = new Thread(a);
    Thread test2 = new Thread(b);
    for(int i=0;i<26;i++){
      int i1 = i + 'a';
      int i2 = i + 'A';
      a.c = (char) i1;
      b.c = (char) i2;
      test2.run();
      test1.run();

    }

  }
}
