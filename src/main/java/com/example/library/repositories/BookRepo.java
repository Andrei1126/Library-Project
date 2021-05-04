package com.example.library.repositories;

import com.example.library.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BookRepo extends CrudRepository<Book, Integer> {

    boolean existsBookByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.price = :priceBook WHERE b.id = :idBook")
    void updateBook(@Param("idBook") Integer id, @Param("priceBook") Double price);
}
