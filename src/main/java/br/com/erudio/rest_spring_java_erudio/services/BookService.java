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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    public BookVO findById(Long id) throws Exception{
        logger.info("Finding a book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No books found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<BookVO>> findAll (Pageable pageable) {
        logger.info("Finding all books");
        var bookPage = repository.findAll(pageable);
        var booksVos = bookPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
        booksVos.map(p -> {
            try {
                return p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Link findAllLink = linkTo(methodOn(BookController.class).findAll(
                pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(booksVos, findAllLink);
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
