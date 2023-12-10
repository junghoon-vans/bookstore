package io.vanslog.bookstore.loan;

import io.vanslog.bookstore.book.Book;
import io.vanslog.bookstore.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedBy
	private LocalDateTime borrowedAt;

	private LocalDateTime returnedAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public Loan(User user, Book book) {
		this.user = user;
		this.book = book;
	}

}
