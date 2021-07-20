package com.demo.apiexercise.service.mapper;

import com.demo.apiexercise.application.dto.person.PersonDto;
import com.demo.apiexercise.infrastructure.common.GenericMapper;
import com.demo.apiexercise.persistence.entity.Person;

public interface PersonMapper extends GenericMapper<PersonDto, Person> {
}
