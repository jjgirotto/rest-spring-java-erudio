package br.com.erudio.rest_spring_java_erudio.services;

import br.com.erudio.rest_spring_java_erudio.data.vo.v1.PersonVO;
import br.com.erudio.rest_spring_java_erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.rest_spring_java_erudio.exception.ResourceNotFoundException;
import br.com.erudio.rest_spring_java_erudio.mapper.DozerMapper;
import br.com.erudio.rest_spring_java_erudio.mapper.custom.PersonMapper;
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

    @Autowired
    private PersonMapper mapper;

    public PersonVO findById(Long id) {
        logger.info("Finding a person");
        var entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll () {
        logger.info("Finding all people");
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating a person");
        var entity = DozerMapper.parseObject(person, Person.class);
        return DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating a person");
        var entity = personRepository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting a person");
        var entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        personRepository.delete(entity);
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating a person with V2");
        var entity = mapper.convertVoToEntity(person);
        return mapper.convertEntityToVo(personRepository.save(entity));
    }
}
