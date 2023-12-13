package io.vanslog.bookstore.loan;

import io.vanslog.bookstore.global.response.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/api/books")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@GetMapping("/{bookId}/loans")
	public ResponseEntity<List<LoanHistory>> getLoanHistory(@PathVariable Long bookId) {

		List<Loan> loans = loanService.getLoanHistory(bookId);
		List<LoanHistory> loanHistory = loans.stream().map(LoanHistory::from).toList();
		return ResponseEntity.ok(loanHistory);
	}

	@PostMapping("/{bookId}/borrow")
	public ResponseEntity<Message> borrowBook(@PathVariable Long bookId, @RequestBody @Valid Borrower borrower) {

		loanService.borrowBook(borrower.username(), bookId);
		return ResponseEntity.ok(new Message("Book borrowed"));
	}

	@PostMapping("/{bookId}/return")
	public ResponseEntity<Message> returnBook(@PathVariable Long bookId, @RequestBody @Valid Borrower borrower) {

		loanService.returnBook(borrower.username(), bookId);
		return ResponseEntity.ok(new Message("Book returned"));
	}

}
