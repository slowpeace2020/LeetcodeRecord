package edu.study.csye.lecture6;

public class GunshotExplosionFactory extends AbstractExplosionFactory{

  @Override
  public AbstractExplosion getObject() {
    return new GunShotExplosion();
  }
}
