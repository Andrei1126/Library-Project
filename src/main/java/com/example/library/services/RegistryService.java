package com.example.library.services;

import com.example.library.dtos.RegistryDto;
import com.example.library.models.Book;
import com.example.library.models.Registry;
import com.example.library.repositories.RegistryRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistryService {
    private final RegistryRepo registryRepo;
    private final BookService bookService;

    @Autowired
    public RegistryService(RegistryRepo registryRepo, BookService bookService) {
        this.registryRepo = registryRepo;
        this.bookService = bookService;
    }

    public Registry addEntryInRegistry(Registry registry) {
        if (bookService.countAvailableBooks(registry.getBookRent().getId()) > 0) {
            return registryRepo.save(registry);
        }
        return null;
    }

    public List<Registry> getAllRegistries() {
        return ((List<Registry>) registryRepo.findAll()).stream().filter(registry -> !registry.isTombstone()).collect(Collectors.toList());
    }

    public Optional<Registry> getRegistryById(Integer id) {
        if (!registryRepo.findById(id).get().isTombstone())
            return registryRepo.findById(id);
        return null;
    }

    public void updateEntryInRegistry(Integer id, String startPeriod) {
        registryRepo.updateEntryInRegistry(id, startPeriod);
    }

    public RegistryDto returnBook(Integer id, String start) throws NotFoundException {
        Book book = bookService.getBookById(id).orElseThrow(() -> new NotFoundException("Book not found."));
        LocalDate actualDate = LocalDate.now();
        LocalDate bookDate = LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE);
        long periodBetween = Duration.between(bookDate.atStartOfDay(), actualDate.atStartOfDay()).toDays();
        Double penaltyPrice = 0.0;

        if (periodBetween > 14 && (!registryRepo.find(id, start).isTombstone())) {
            penaltyPrice = book.getPrice() * (periodBetween - 14) * 0.01;
        }

        registryRepo.deleteRegistry(id, start);

        return new RegistryDto(book.getName(), Math.max((int) (periodBetween - 14), 0), penaltyPrice);
    }

    public long countRentBooks(Integer id) {
        return registryRepo.countByBookRent_Id(id);
    }

}
