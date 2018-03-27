package com.hilbertinc.example.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Gary Murphy
 * <p>
 * <p>Created: 3/27/18</p>
 */
@Entity
@Table(name="tuple")
public class TupleEntity {

   private Long id;
   private String name;
   private String value;

   public TupleEntity() {
   }

   public TupleEntity(String name, String value) {
      this.name = name;
      this.value = value;
   }

   @Id @GeneratedValue
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @NotNull @Size(min=1)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }
}
