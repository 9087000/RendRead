package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/books")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    // Endpoint to rent a book
    @PostMapping("/{bookId}/rent")
    public ResponseEntity<String> rentBook(@AuthenticationPrincipal User user, @PathVariable Long bookId) {
        String result = rentalService.rentBook(user, bookId);
        if (result.equals("Book rented successfully.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // Endpoint to return a book
    @PostMapping("/{bookId}/return")
    public ResponseEntity<String> returnBook(@AuthenticationPrincipal User user, @PathVariable Long bookId) {
        String result = rentalService.returnBook(user, bookId);
        if (result.equals("Book returned successfully.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
