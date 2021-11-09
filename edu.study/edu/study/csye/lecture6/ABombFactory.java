package edu.study.csye.lecture6;


public class ABombFactory extends AbstractExplosionFactory{

  @Override
  public AbstractExplosion getObject() {
    return new Abomb();
  }
}
