package edu.study.csye.lecture6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Lecture8 {


  public static boolean greaterThanThree(Integer n){
    return n>3;
  }

  public void test() {
//    int sum = widges.stream().filter(w->w.getColor()==RED).mapToInt(w->w.getWeight()).sum();
    List<Integer> numbers = Arrays.asList(1,2,3,4,5);
    numbers.stream().filter(Lecture8::greaterThanThree).forEach(System.out::println);
  }

  public void useStreamFilter(){
    Predicate<Integer> greaterThan3 = (Integer n)->{return n>3;};
    List<Integer> numbers = Arrays.asList(1,2,3,4,5);
    numbers.stream().filter(greaterThan3).forEach(System.out::println);
  }

  public void practice(){
    List<String> states = Arrays.asList("ma","ny","ct","vt","ri","nh","nv","nc","nd","wa","wv"," ut","ca","az","al","ak","ok","pa","me","ms","il","id","mn","wy","mt"," wi","ia","ar","hi","sd","sc","md","nj","de","ga","fl","mi","oh","in","or", "ky","tn","va","mo","ks","co","la","tx","nm","ne");
    states.stream().filter(s->s.startsWith("m")).map(String::toUpperCase).sorted().forEach(s->System.out.print(s+", "));
  }


  public static void main(String[] args) {
    Lecture8 te = new Lecture8();
    te.practice();
  }

}
