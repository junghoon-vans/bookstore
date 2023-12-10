package io.vanslog.bookstore.loan;

import io.vanslog.bookstore.book.Book;
import io.vanslog.bookstore.user.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

	private final LoanRepository loanRepository;

	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@Transactional(readOnly = true)
	public List<Loan> getLoanHistory(Long bookId) {

		return loanRepository.findByBookId(bookId);
	}

	@Transactional
	public Loan borrowBook(User user, Book book) {

		Loan loan = new Loan(user, book);
		return loanRepository.save(loan);
	}

	@Transactional
	public void returnBook(Long id) {

		loanRepository.findById(id).ifPresentOrElse(loan -> loan.setReturnedAt(LocalDateTime.now()), () -> {
			throw new RuntimeException("Loan not found");
		});
	}

}
