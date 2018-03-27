package com.hilbertinc.example.jpa;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Gary Murphy
 * <p>
 * <p>Created: 3/27/18</p>
 */
@Stateless
@LocalBean
public class TupleManager {

   @PersistenceContext(name = "test-datasource")
   EntityManager entityManager;


   public void reset() {
      getEntityManager().createQuery("delete from TupleEntity").executeUpdate();
      getEntityManager().flush();
   }

   public TupleEntity add(String name, String value) {
      TupleEntity entity = new TupleEntity(name, value);
      getEntityManager().persist(entity);
      return entity;
   }

   public List<TupleEntity> getTuples() {
      return getEntityManager().createQuery("select tuple from TupleEntity tuple", TupleEntity.class).getResultList();
   }


   private EntityManager getEntityManager() {
      return entityManager;
   }
}
