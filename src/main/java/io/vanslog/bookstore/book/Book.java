package io.vanslog.bookstore.book;

import io.vanslog.bookstore.loan.Loan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books", uniqueConstraints = { @UniqueConstraint(columnNames = "isbn") })
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String isbn;

	private String title;

	@OneToMany(mappedBy = "book")
	private Set<Loan> loans = new HashSet<>();

}
