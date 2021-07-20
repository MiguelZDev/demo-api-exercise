package com.demo.apiexercise.persistence.repository;

import com.demo.apiexercise.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  @Query("SELECT e FROM Employee e WHERE (:name is null or e.person.name = :name) and (:positionName is null"
    + " or e.position.name = :positionName)")
  List<Employee> filterByNameAndPositionIfPresent(@Param("name") String name,
                                                  @Param("positionName") String positionName);


}
