package edu.study.csye;

public class TwoAlternatingThreads implements Runnable{
  private char c='a';

  public synchronized void printCharacters(char c){
    System.out.println(c);
  }

  @Override
  public void run() {
    System.out.print(c);
  }

  public static void main(String[] args) {
    TwoAlternatingThreads a = new TwoAlternatingThreads();
    Thread test1 = new Thread(a);
    Thread test2 = new Thread(a);
    for(int i=0;i<26;i++){
      int i1 = i + 'a';
      int i2 = i + 'A';
      a.c = (char) i1;
      test1.run();
      a.c = (char) i2;
      test2.run();

    }


  }
}
