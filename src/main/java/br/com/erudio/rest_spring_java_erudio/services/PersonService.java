package br.com.erudio.rest_spring_java_erudio.services;

import br.com.erudio.rest_spring_java_erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id) {
        logger.info("Finding a person");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Juliana");
        person.setLastName("Girotto");
        person.setAddress("Presidente Prudente");
        person.setGender("Feminino");
        return person;
    }

    public List<Person> findAll () {
        logger.info("Finding all people");
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Girotto " + i);
        person.setAddress("Presidente Prudente " + i);
        person.setGender("Feminino");
        return person;
    }

    public Person create(Person person) {
        logger.info("Creating a person");
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating a person");
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting a person");
    }
}
