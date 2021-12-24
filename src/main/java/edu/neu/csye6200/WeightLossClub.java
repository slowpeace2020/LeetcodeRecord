package edu.neu.csye6200;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class WeightLossClub extends AbstractClub{

  private List<AbstractPerson> list;

  public WeightLossClub() {
    this.list = new ArrayList<>();
  }

  @Override
  public void add(AbstractPerson p) {
    list.add(p);
  }

  @Override
  public void sortAndShow(Comparator compare, Consumer<AbstractPerson> action) {
    Collections.sort(list,compare);
    list.forEach(action);
  }



  @Override
  public List<Integer> getWeightLossStats() {
    List<Integer> losstats = new ArrayList<>();
    for(AbstractPerson person:list){
      losstats.add(person.getWeightLossLbs());
    }
    return losstats;
  }

  public static void demo(){
    String[] data = {"1,25,Jim,311,11",
        "2,21,Sam,315,15",
        "3,17,Dan,314,14",
        "4,19,Bob,312,12",
        "5,16,Ann,310,10",
        "6,23,Eve,313,13"};
    List<Person> people = new ArrayList<>();
    WeightLossClub club = new WeightLossClub();

    for(String per:data){
      String[] strs = per.split(",");
      Person person = new Person();
      person.setId(Integer.parseInt(strs[0]));
      person.setAge(Integer.parseInt(strs[1]));
      person.setName(strs[2]);
      person.setWeightLbs(Integer.parseInt(strs[3]));
      person.setWeightLossLbs(Integer.parseInt(strs[4]));
      people.add(person);
      club.list.add(person);
    }

    System.out.println("sort by name");
    //sort by name
    club.sortAndShow(new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        Person one = (Person) o1;
        Person two = (Person) o2;
        return one.getName().compareTo(two.getName());
      }
    },n -> System.out.println(n.getName()));

    System.out.println();

    //sort by WeightLossLbs
    System.out.println("sort by WeightLossLbs");

    club.sortAndShow(new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        Person one = (Person) o1;
        Person two = (Person) o2;
        return two.getWeightLossLbs()-one.getWeightLossLbs();
      }
    },n -> System.out.println(n.getName()));

    System.out.println("GRAPH Current Weight Loss ascending");
    //GRAPH Current Weight Loss
    club.list.stream().sorted((a,b)->(a.getWeightLossLbs()-b.getWeightLossLbs())).forEach(person -> {
      System.out.print(person.getWeightLossLbs());
      System.out.print(" ");

      for(int i=0;i<person.getWeightLossLbs();i++){
        System.out.print("*");
      }
      System.out.println();
    });
    System.out.println("GRAPH Current Weight Loss descending");
    club.list.stream().sorted((a,b)->(b.getWeightLossLbs()-a.getWeightLossLbs())).forEach(person -> {
      System.out.print(person.getWeightLossLbs());
      System.out.print(" ");

      for(int i=0;i<person.getWeightLossLbs();i++){
        System.out.print("*");
      }
      System.out.println();
    });


    System.out.println();


    //GRAPH Projected 1  month
//    club.list.stream().sorted((a,b)->(a.getWeightLossLbs()-b.getWeightLossLbs())).map(person -> {return person.getWeightLossLbs()+10;}).forEach(losslbs -> {
//      System.out.print(losslbs);
//      for(int i=0;i<losslbs;i++){
//        System.out.print("*");
//      }
//      System.out.println();
//    });
    System.out.println("GRAPH Current Weight Loss ascending   1");

    club.sortAndShow(new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        Person one = (Person) o1;
        Person two = (Person) o2;
        return one.getWeightLossLbs()-two.getWeightLossLbs();
      }
    },n -> {      System.out.print(n.getWeightLossLbs()+10);
      System.out.print(" ");

      for(int i=0;i<n.getWeightLossLbs()+10;i++){
        System.out.print("*");
      }
      System.out.println();});


    System.out.println("GRAPH Current Weight Loss decending   1");

    //GRAPH Projected 1  month
    club.sortAndShow(new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        Person one = (Person) o1;
        Person two = (Person) o2;
        return two.getWeightLossLbs()-one.getWeightLossLbs();
      }
    },n -> {System.out.print(n.getWeightLossLbs()+10);
      System.out.print(" ");
      for(int i=0;i<n.getWeightLossLbs()+10;i++){
        System.out.print("*");
      }
      System.out.println();});
    System.out.println();

    System.out.println("GRAPH Current Weight Loss ascending   12");

//    //GRAPH Projected 12  month
    club.list.stream().sorted((a,b)->(a.getWeightLossLbs()-b.getWeightLossLbs())).map(person -> {return person.getWeightLossLbs()*10;}).forEach(losslbs -> {
      System.out.print(losslbs+" ");
      for(int i=0;i<losslbs;i++){
        System.out.print("*");
      }
      System.out.println();
    });
    System.out.println();

    System.out.println("GRAPH Current Weight Loss descending   12");

    //GRAPH Projected 12  month
    club.list.stream().sorted((a,b)->(b.getWeightLossLbs()-a.getWeightLossLbs())).map(person -> {return person.getWeightLossLbs()*10;}).forEach(losslbs -> {
      System.out.print(losslbs+" ");
      for(int i=0;i<losslbs;i++){
        System.out.print("*");
      }
      System.out.println();
    });
    System.out.println();

  }

}
