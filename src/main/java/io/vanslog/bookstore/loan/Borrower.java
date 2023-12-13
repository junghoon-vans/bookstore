package io.vanslog.bookstore.loan;

import jakarta.validation.constraints.NotBlank;

public record Borrower(@NotBlank String username) {
}
