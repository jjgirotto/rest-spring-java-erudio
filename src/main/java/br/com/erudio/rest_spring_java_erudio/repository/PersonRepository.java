package br.com.erudio.rest_spring_java_erudio.repository;

import br.com.erudio.rest_spring_java_erudio.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
