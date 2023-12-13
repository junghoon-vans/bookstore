package io.vanslog.bookstore.loan;

import io.vanslog.bookstore.book.Book;
import io.vanslog.bookstore.book.BookRepository;
import io.vanslog.bookstore.user.User;
import io.vanslog.bookstore.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

	private final UserRepository userRepository;

	private final BookRepository bookRepository;

	private final LoanRepository loanRepository;

	public LoanService(UserRepository userRepository, BookRepository bookRepository, LoanRepository loanRepository) {

		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.loanRepository = loanRepository;
	}

	@Transactional(readOnly = true)
	public List<Loan> getLoanHistory(Long bookId) {

		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		return loanRepository.findAllByBook(book);
	}

	@Transactional
	public Loan borrowBook(String username, Long bookId) {

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

		Loan loan = new Loan(user, book);
		return loanRepository.save(loan);
	}

	@Transactional
	public Loan returnBook(String username, Long bookId) {

		Loan loan = loanRepository.findByUserUsernameAndBookId(username, bookId)
			.orElseThrow(() -> new RuntimeException("Loan not found"));
		loan.setReturnedAt(LocalDateTime.now());

		return loan;
	}

}
