package br.com.erudio.rest_spring_java_erudio.services;

import br.com.erudio.rest_spring_java_erudio.controller.PersonController;
import br.com.erudio.rest_spring_java_erudio.data.vo.v1.PersonVO;
import br.com.erudio.rest_spring_java_erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.rest_spring_java_erudio.exception.ResourceNotFoundException;
import br.com.erudio.rest_spring_java_erudio.mapper.DozerMapper;
import br.com.erudio.rest_spring_java_erudio.model.Person;
import br.com.erudio.rest_spring_java_erudio.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;

    public PersonVO findById(Long id) throws Exception{
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<PersonVO>> findAll (Pageable pageable) {
        logger.info("Finding all people");
        var personPage = personRepository.findAll(pageable);
        var personVosPage = getPersonVOS(personPage);
        Link link = linkTo(methodOn(PersonController.class).findAll(
                pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personVosPage, link);
    }

    public PagedModel<EntityModel<PersonVO>> findPeopleByName (String firstName, Pageable pageable) {
        logger.info("Finding all people by first name");
        var personPage = personRepository.findPeopleByName(firstName, pageable);
        var personVos = getPersonVOS(personPage);
        Link link = linkTo(methodOn(PersonController.class).findAll(
                pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personVos, link);
    }

    public PersonVO create(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectIsNullException();
        logger.info("Creating a person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo =  DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating a person!");

        var entity = personRepository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id) throws Exception{
        logger.info("Disabling a person!");
        personRepository.disablePerson(id);
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting a person");
        var entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this id")
        );
        personRepository.delete(entity);
    }

//    public PersonVOV2 createV2(PersonVOV2 person) {
//        logger.info("Creating a person with V2");
//        var entity = mapper.convertVoToEntity(person);
//        return mapper.convertEntityToVo(personRepository.save(entity));
//    }

    private static Page<PersonVO> getPersonVOS(Page<Person> personPage) {
        var personVos = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVos.map(p -> {
            try {
                return p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new ResourceNotFoundException("No records found");
            }
        });
        return personVos;
    }

}
