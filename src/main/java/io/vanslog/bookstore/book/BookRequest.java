package io.vanslog.bookstore.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.ISBN;

public record BookRequest(

		@ISBN @NotBlank String isbn,

		@NotBlank String title, @Min(0) int stock) {
}
