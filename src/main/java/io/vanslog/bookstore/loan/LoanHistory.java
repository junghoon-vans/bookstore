package io.vanslog.bookstore.loan;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.vanslog.bookstore.user.User;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoanHistory(Long id, String username, LocalDateTime borrowedAt, LocalDateTime returnedAt) {

	public static LoanHistory from(Loan loan) {
		User user = loan.getUser();
		return new LoanHistory(loan.getId(), user.getUsername(), loan.getBorrowedAt(), loan.getReturnedAt());
	}
}
