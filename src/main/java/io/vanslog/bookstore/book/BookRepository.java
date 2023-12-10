package io.vanslog.bookstore.book;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	Optional<Book> findByIsbn(String isbn);
}
