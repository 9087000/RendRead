package com.example.demo.service;

import com.example.demo.Repository.BookRepository;
import com.example.demo.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // Create a new book (Admin only)
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Update a book (Admin only)
    public Optional<Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setGenre(bookDetails.getGenre());
            book.setAvailabilityStatus(bookDetails.getAvailabilityStatus());
            return bookRepository.save(book);
        });
    }

    // Delete a book (Admin only)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Get all books (Available to all users)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
}
