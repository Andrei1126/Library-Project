package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.services.BookService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/book")
@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book addBook(@NotNull @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "{id}")
    public Book getBookById(@PathVariable("id") Integer id) {
        return bookService.getBookById(id).
                orElse(null);
    }

    @PutMapping(path = "{id}")
    public void updateBook(@PathVariable("id") Integer id, @NotNull @RequestBody Book bookToUpdate) {
        bookService.updateBook(id, bookToUpdate.getPrice());
    }

    @GetMapping(path = "available/{id}")
    public Long availableBooks(@PathVariable("id") Integer id) {
        return bookService.countAvailableBooks(id);
    }
}
