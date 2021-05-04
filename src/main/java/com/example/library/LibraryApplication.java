package com.example.library;

import com.example.library.models.Book;
import com.example.library.models.Registry;
import com.example.library.repositories.BookRepo;
import com.example.library.repositories.RegistryRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(LibraryApplication.class, args);

        BookRepo bookRepo =
                configurableApplicationContext.getBean(BookRepo.class);

        RegistryRepo registryRepo =
                configurableApplicationContext.getBean(RegistryRepo.class);

        Book book1 = new Book(1, "Test1", 123456678.0, 12.5, null, 30);
        Book book2 = new Book(2, "Test2", 123335348.0, 13.5, null, 25);
        Book book3 = new Book(3, "Test3", 343243444.0, 11.5, null, 20);
        bookRepo.save(book1);
        bookRepo.save(book2);
        bookRepo.save(book3);

        Registry reg1 = new Registry(4, book1, "2021-04-23", false);
        Registry reg2 = new Registry(5, book2, "2021-04-22", false);
        Registry reg3 = new Registry(6, book1, "2021-04-21", false);
        Registry reg4 = new Registry(7, book1, "2021-03-21", false);
        registryRepo.save(reg1);
        registryRepo.save(reg2);
        registryRepo.save(reg3);
        registryRepo.save(reg4);

    }

}
