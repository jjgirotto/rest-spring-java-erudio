package br.com.erudio.rest_spring_java_erudio.services;

import br.com.erudio.rest_spring_java_erudio.controller.PersonController;
import br.com.erudio.rest_spring_java_erudio.data.vo.v1.PersonVO;
import br.com.erudio.rest_spring_java_erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.rest_spring_java_erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.rest_spring_java_erudio.exception.ResourceNotFoundException;
import br.com.erudio.rest_spring_java_erudio.mapper.DozerMapper;
import br.com.erudio.rest_spring_java_erudio.mapper.custom.PersonMapper;
import br.com.erudio.rest_spring_java_erudio.model.Person;
import br.com.erudio.rest_spring_java_erudio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding a user by name!");
        var user = repository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
