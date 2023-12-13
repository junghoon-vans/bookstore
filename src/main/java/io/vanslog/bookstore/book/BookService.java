package io.vanslog.bookstore.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional
	public Book register(BookRequest bookRequest) {
		ObjectMapper objectMapper = new ObjectMapper();
		Book book = objectMapper.convertValue(bookRequest, Book.class);
		return bookRepository.save(book);
	}

	@Transactional
	public void update(Book origin, BookRequest bookRequest) {
		Book updatedBook = origin.update(bookRequest);
		bookRepository.save(updatedBook);
	}

	@Transactional(readOnly = true)
	public Optional<Book> findByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

}
