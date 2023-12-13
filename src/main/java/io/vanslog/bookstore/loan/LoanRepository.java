package io.vanslog.bookstore.loan;

import io.vanslog.bookstore.book.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	List<Loan> findAllByBook(Book book);

	Optional<Loan> findByUserUsernameAndBookId(String username, Long bookId);

}
