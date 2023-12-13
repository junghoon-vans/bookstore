package io.vanslog.bookstore.loan;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoanHistory(Long id, LocalDateTime borrowedAt, LocalDateTime returnedAt) {

	public static LoanHistory from(Loan loan) {
		return new LoanHistory(loan.getId(), loan.getBorrowedAt(), loan.getReturnedAt());
	}
}
