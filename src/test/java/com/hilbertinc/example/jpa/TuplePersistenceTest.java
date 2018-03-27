package com.hilbertinc.example.jpa;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * This demonstrates testing JPA persistence with an injected user transaction
 *
 * @author Gary Murphy
 * <p>
 * <p>Created: 3/27/18</p>
 */
@RunWith(Arquillian.class)
public class TuplePersistenceTest {

   @Deployment
   public static WebArchive createDeployment() {
      WebArchive war = ShrinkWrap.create(WebArchive.class)
         .addPackage(TupleEntity.class.getPackage())
         .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
         .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
         ;
      System.out.println(war.toString(true));
      return war;
   }

   @PersistenceContext(name = "test-datasource")
   EntityManager entityManager;

   @Inject
   UserTransaction transaction;

   @Before
   public void setup_database() throws Exception {

      // Clear data

      getTransaction().begin();
      getEntityManager().joinTransaction();
      getEntityManager().createQuery("delete from TupleEntity").executeUpdate();
      getTransaction().commit();

      // Insert a tuple

      getTransaction().begin();
      getEntityManager().joinTransaction();
      TupleEntity entity = new TupleEntity("loglevel","info");
      getEntityManager().persist(entity);
      getTransaction().commit();

      // Clear the cache

      getEntityManager().clear();

      // Start the transaction for the unit tests

      getTransaction().begin();
      getEntityManager().joinTransaction();
   }

   @After
   public void commit() throws Exception {
      getTransaction().commit();
   }

   @Test
   public void get_tuple() throws Exception {
      List<TupleEntity> tuples = getEntityManager().createQuery("select tuple from TupleEntity tuple", TupleEntity.class).getResultList();
      Assert.assertFalse("Found tuples", tuples.isEmpty());
      System.out.printf(
         "+-----------------------------------------------------------------\n" +
         "| Name: %s Value: %s\n" +
         "+-----------------------------------------------------------------\n",
         tuples.get(0).getName(), tuples.get(0).getValue());
   }

   private EntityManager getEntityManager() {
      return entityManager;
   }

   private UserTransaction getTransaction() {
      return transaction;
   }
}
