package io.vanslog.bookstore.loan;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	List<Loan> findByBookId(Long bookId);

}
