package com.hilbertinc.example;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author Gary Murphy
 * <p>
 * <p>Created: 3/25/18</p>
 */
@Stateless
@LocalBean
public class SampleEnterpriseBean {

   public String ok() {
      return "ok";
   }
}
