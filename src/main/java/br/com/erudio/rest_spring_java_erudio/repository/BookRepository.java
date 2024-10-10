package br.com.erudio.rest_spring_java_erudio.repository;

import br.com.erudio.rest_spring_java_erudio.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
