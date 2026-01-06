package de.devtime.app.example.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.devtime.app.example.persistence.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, String> {

  Optional<BookEntity> findByIsbn(String isbn);

  List<BookEntity> findAllByTitle(String title);

  List<BookEntity> findAllByIsOnLoan(boolean isOnLoan);

}
