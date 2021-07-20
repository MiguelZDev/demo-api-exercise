package com.demo.apiexercise.service.mapper.imp;

import com.demo.apiexercise.application.dto.person.PersonDto;
import com.demo.apiexercise.persistence.entity.Person;
import com.demo.apiexercise.service.mapper.PersonMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImp implements PersonMapper {

  @Override
  public Person toEntity(PersonDto dto) {
    Person person = new Person();
    person.setName(dto.getName());
    person.setLastName(dto.getLastName());
    person.setAddress(dto.getAddress());
    person.setCellphone(dto.getCellphone());
    person.setCityName(dto.getCityName());
    return person;
  }

  @Override
  public PersonDto toDTO(Person entity) {
    PersonDto response = new PersonDto();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setLastName(entity.getLastName());
    response.setAddress(entity.getAddress());
    response.setCellphone(entity.getCellphone());
    response.setCityName(entity.getCityName());
    return response;
  }
}
