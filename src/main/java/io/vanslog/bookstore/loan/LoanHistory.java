package io.vanslog.bookstore.loan;

import java.time.LocalDateTime;

public record LoanHistory(Long id, LocalDateTime borrowedAt, LocalDateTime returnedAt) {

	public static LoanHistory from(Loan loan) {
		return new LoanHistory(loan.getId(), loan.getBorrowedAt(), loan.getReturnedAt());
	}
}
