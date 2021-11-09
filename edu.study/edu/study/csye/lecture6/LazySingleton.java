package edu.study.csye.lecture6;

public class LazySingleton {
  private static LazySingleton instance;
  private LazySingleton(){
    instance = null;
  }

  public static synchronized LazySingleton getInstance(){
    if(instance==null){
      instance = new LazySingleton();
    }

    return instance;
  }
}
