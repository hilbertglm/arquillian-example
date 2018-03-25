package com.hilbertinc.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.webapp31.WebAppDescriptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * @author Gary Murphy
 * <p>
 * <p>Created: 3/25/18</p>
 */
@RunWith(Arquillian.class)
public class EnterpriseBeanTest {

   @Deployment
   public static WebArchive createDeployment() {
      return ShrinkWrap.create(WebArchive.class, "EmbeddedTomEE.war")
         .addClasses(SampleEnterpriseBean.class)
         .setWebXML(
            new StringAsset(
               Descriptors.create(WebAppDescriptor.class)
                  .version("3.0")
                  .exportAsString())
         );
   }

   @EJB SampleEnterpriseBean bean;

   @Test
   public void test_ejb() {
      Assert.assertNotNull("Injected bean", bean);
      Assert.assertEquals("OK", "ok", bean.ok());
      System.out.printf("OK: %s\n", bean.ok());
   }

}
