package edu.study.csye.lecture6;

public class SimpleExplosionFactory {
   enum ExplosionCriteria{
      GUNSHOT,
      GRENADE
   }

   public Explosion getObject(ExplosionCriteria criteria){
      if(criteria== ExplosionCriteria.GUNSHOT){
         return new Gunshot();
      }else {
         return new Grenade();
      }
   }

}
