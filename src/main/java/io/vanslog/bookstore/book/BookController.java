package io.vanslog.bookstore.book;

import io.vanslog.bookstore.global.response.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<Message> register(@RequestBody @Valid BookRequest book) {

		Book registered = bookService.registerOrUpdate(book);
		return ResponseEntity.created(URI.create("/api/books/" + registered.getId()))
			.body(new Message("Book registered successfully"));
	}

	@PutMapping("/")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<Message> update(@RequestBody @Valid BookRequest book) {

		boolean isExist = bookService.findByIsbn(book.isbn()).isPresent();

		if (!isExist) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Book not found!"));
		}

		bookService.registerOrUpdate(book);
		return ResponseEntity.ok(new Message("Book updated successfully"));
	}

}
