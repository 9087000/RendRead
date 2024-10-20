package com.example.demo.service;

import com.example.demo.Repository.RentalRepository;
import com.example.demo.model.Rental;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookService bookService;

    // Rent a book
    public String rentBook(User user, Long bookId) {
        List<Rental> activeRentals = rentalRepository.findByUserAndReturnDateIsNull(user);
        if (activeRentals.size() >= 2) {
            return "You already have 2 active rentals.";
        }

        return bookService.getBookById(bookId).map(book -> {
            if (!book.getAvailabilityStatus()) {
                return "Book is not available.";
            }
            book.setAvailabilityStatus(false);
            bookService.createBook(book); // Update book availability

            Rental rental = Rental.builder()
                    .book(book)
                    .user(user)
                    .rentalDate(LocalDateTime.now())
                    .build();

            rentalRepository.save(rental);
            return "Book rented successfully.";
        }).orElse("Book not found.");
    }

    // Return a book
    public String returnBook(User user, Long bookId) {
        return bookService.getBookById(bookId).map(book -> {
            Rental rental = rentalRepository.findByUserAndReturnDateIsNull(user).stream()
                    .filter(r -> r.getBook().getId().equals(bookId))
                    .findFirst()
                    .orElse(null);
            if (rental == null) {
                return "No active rental found for this book.";
            }
            rental.setReturnDate(LocalDateTime.now());
            rentalRepository.save(rental);

            book.setAvailabilityStatus(true);
            bookService.createBook(book); // Update book availability
            return "Book returned successfully.";
        }).orElse("Book not found.");
    }
}
