package edu.study.csye;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Midterm {
  private class Person{

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    private String description=null;
    {
      description = "I'm a person";
    }

    @Override
    public String toString() {
      return "Person{" +
          "description='" + description + '\'' +
          '}';
    }
  }


  private class Student extends Person{
    {
      setDescription("I'm a student");
    }
  }

  public static void demo(){
    System.out.println("\n\t"+Midterm.class.getName()+".demo()....");

    Midterm obj = new Midterm();
    List<Person> personlist = new ArrayList<>(Arrays.asList(obj.new Person()));
  }

}
