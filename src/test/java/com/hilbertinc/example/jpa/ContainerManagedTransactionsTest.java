package com.hilbertinc.example.jpa;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

/**
 * This will demonstrate container-managed persistence by enclosing the JPA calls in an EJB
 *
 * @author Gary Murphy
 * <p>
 * <p>Created: 3/27/18</p>
 */
@RunWith(Arquillian.class)
public class ContainerManagedTransactionsTest {

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

   @EJB
   TupleManager manager;

   @Before
   public void setup_database() throws Exception {
      getManager().reset();
   }

   @Test
   public void get_tuples() throws Exception {
      getManager().add("loglevel", "warning");
      List<TupleEntity> tuples = getManager().getTuples();
      Assert.assertFalse("Found tuples", tuples.isEmpty());
      System.out.printf(
         "+-----------------------------------------------------------------\n" +
         "| Name: %s Value: %s\n" +
         "+-----------------------------------------------------------------\n",
         tuples.get(0).getName(), tuples.get(0).getValue());
   }


   public TupleManager getManager() {
      return manager;
   }
}
