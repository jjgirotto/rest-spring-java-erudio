package br.com.erudio.rest_spring_java_erudio.services;

import br.com.erudio.rest_spring_java_erudio.exception.ResourceNotFoundException;
import br.com.erudio.rest_spring_java_erudio.model.Person;
import br.com.erudio.rest_spring_java_erudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public Person findById(Long id) {
        logger.info("Finding a person");
        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id"));
    }

    public List<Person> findAll () {
        logger.info("Finding all people");
        return personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating a person");
        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating a person");
        var entity = personRepository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting a person");
        var entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        personRepository.delete(entity);
    }
}
