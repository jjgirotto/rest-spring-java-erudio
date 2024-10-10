package br.com.erudio.rest_spring_java_erudio.unittests.mockito.services;

import br.com.erudio.rest_spring_java_erudio.data.vo.v1.BookVO;
import br.com.erudio.rest_spring_java_erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.rest_spring_java_erudio.model.Book;
import br.com.erudio.rest_spring_java_erudio.repository.BookRepository;
import br.com.erudio.rest_spring_java_erudio.services.BookService;
import br.com.erudio.rest_spring_java_erudio.unittests.mapper.mocker.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L); //define the id

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("JK Rowling1", result.getAuthor());
        assertEquals("Harry Potter1", result.getTitle());
        assertEquals(100.00, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var bookOne = people.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("JK Rowling1", bookOne.getAuthor());
        assertEquals("Harry Potter1", bookOne.getTitle());
        assertEquals(100.00, bookOne.getPrice());
        assertNotNull(bookOne.getLaunchDate());

        var bookFour = people.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());

        assertTrue(bookFour.toString().contains("links: [</api/books/v1/4>;rel=\"self\"]"));
        assertEquals("JK Rowling4", bookFour.getAuthor());
        assertEquals("Harry Potter4", bookFour.getTitle());
        assertEquals(100.00, bookFour.getPrice());
        assertNotNull(bookFour.getLaunchDate());

        var bookSeven = people.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());

        assertTrue(bookSeven.toString().contains("links: [</api/books/v1/7>;rel=\"self\"]"));
        assertEquals("JK Rowling7", bookSeven.getAuthor());
        assertEquals("Harry Potter7", bookSeven.getTitle());
        assertEquals(100.00, bookSeven.getPrice());
        assertNotNull(bookSeven.getLaunchDate());
    }

    @Test
    void create() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("JK Rowling1", result.getAuthor());
        assertEquals("Harry Potter1", result.getTitle());
        assertEquals(100.00, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() throws Exception {
        Book entity = input.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("JK Rowling1", result.getAuthor());
        assertEquals("Harry Potter1", result.getTitle());
        assertEquals(100.00, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void delete() {
        Book entity = input.mockEntity(1); //returns a person without an id
        entity.setId(1L); //define the id

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

}