package io.vanslog.bookstore.book;

import io.vanslog.bookstore.global.response.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/")
	public ResponseEntity<Message> register(@RequestBody @Valid BookRequest newBook) {

		boolean isExist = bookService.findByIsbn(newBook.isbn()).isPresent();

		if (isExist) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Book already exists!"));
		}

		Book registered = bookService.register(newBook);
		return ResponseEntity.created(URI.create("/api/books/" + registered.getId()))
			.body(new Message("Book registered successfully"));
	}

	@PutMapping("/")
	public ResponseEntity<Message> update(@RequestBody @Valid BookRequest toUpdate) {

		Optional<Book> originalBook = bookService.findByIsbn(toUpdate.isbn());

		if (originalBook.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Book not found!"));
		}

		bookService.update(originalBook.get(), toUpdate);

		return ResponseEntity.ok(new Message("Book updated successfully"));
	}

}
