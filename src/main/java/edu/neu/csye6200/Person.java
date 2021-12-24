package edu.neu.csye6200;

public class Person extends AbstractPerson{
  private int id;
  private int age;
  private int weightLbs;
  private int weightLossLbs;

  public void setAge(int age) {
    this.age = age;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String name;


  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int getAge() {
    return age;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getWeightLbs() {
    return weightLbs;
  }

  @Override
  public void setWeightLbs(int weightLbs) {
    this.weightLbs =weightLbs;
  }

  @Override
  public int getWeightLossLbs() {
    return weightLossLbs;
  }

  @Override
  public void setWeightLossLbs(int weightLossLbs) {
    this.weightLossLbs = weightLossLbs;
  }
}
