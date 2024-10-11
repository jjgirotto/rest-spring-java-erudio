package br.com.erudio.rest_spring_java_erudio.services;

import br.com.erudio.rest_spring_java_erudio.controller.BookController;
import br.com.erudio.rest_spring_java_erudio.controller.PersonController;
import br.com.erudio.rest_spring_java_erudio.data.vo.v1.BookVO;
import br.com.erudio.rest_spring_java_erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.rest_spring_java_erudio.exception.ResourceNotFoundException;
import br.com.erudio.rest_spring_java_erudio.mapper.DozerMapper;
import br.com.erudio.rest_spring_java_erudio.model.Book;
import br.com.erudio.rest_spring_java_erudio.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository repository;

    public BookVO findById(Long id) throws Exception{
        logger.info("Finding a book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No books found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<BookVO> findAll () {
        logger.info("Finding all books");
        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books
                .stream()
                .forEach(p -> {
                    try {
                        p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
                    } catch (Exception e) {
                        throw new ResourceNotFoundException("No records found");
                    }
                });
        return books;
    }

    public BookVO create(BookVO book) throws Exception {
        if (book == null) throw new RequiredObjectIsNullException();
        logger.info("Creating a book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) throws Exception {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating a book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting a book");
        var entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No books found for this id")
        );
        repository.delete(entity);
    }

}
