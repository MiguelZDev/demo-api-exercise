package com.demo.apiexercise.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSeq")
  @SequenceGenerator(name = "personSeq", sequenceName = "personSeq", allocationSize = 1)
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "cell_phone", nullable = false)
  private String cellphone;

  @Column(name = "city_name", nullable = false)
  private String cityName;

  @OneToOne(mappedBy = "person")
  private Employee employee;


}
