package com.demo.apiexercise.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "position")
public class Position {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "positionSeq")
  @SequenceGenerator(name = "positionSeq", sequenceName = "positionSeq", allocationSize = 1)
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

}
