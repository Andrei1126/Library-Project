package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepo bookRepo;
    private final RegistryService registryService;

    @Autowired
    public BookService(BookRepo bookRepo, @Lazy RegistryService registryService) {
        this.bookRepo = bookRepo;
        this.registryService = registryService;
    }

    public Book addBook(Book book) {
        if (!bookRepo.existsBookByName(book.getName())) {
           return bookRepo.save(book);
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepo.findAll();
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepo.findById(id);
    }

    public long countAvailableBooks(Integer id) {
        Optional<Book> book = bookRepo.findById(id);

        return book.map(value -> (value.getNrOfBooks() - registryService.countRentBooks(id))).orElse(0L);

    }

    public void updateBook(Integer id, Double price) {
        bookRepo.updateBook(id, price);
    }
}
