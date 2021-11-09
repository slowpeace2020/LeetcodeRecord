package edu.study.csye.lecture6;

public class GrenadeExplosionFactory extends AbstractExplosionFactory{

  @Override
  public AbstractExplosion getObject() {
    return new GrenadeExplosion();
  }
}
