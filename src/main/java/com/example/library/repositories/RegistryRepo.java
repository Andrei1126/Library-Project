package com.example.library.repositories;

import com.example.library.models.Registry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RegistryRepo extends CrudRepository<Registry, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Registry r SET r.startPeriod = :startPeriod WHERE r.id = :idEntryRegistry AND r.tombstone = false")
    void updateEntryInRegistry(@Param("idEntryRegistry") Integer id, @Param("startPeriod") String period);

    @Query(value = "SELECT r FROM Registry r WHERE r.tombstone = false AND r.bookRent.id = :id AND r.startPeriod = :startPeriod")
    Registry find(Integer id, String startPeriod);

    @Query(value = "SELECT startPeriod FROM Registry")
    List<Registry> filterRegistriesByStartPeriod();

    @Query(value = "SELECT COUNT(1) FROM Registry r WHERE r.tombstone = false AND r.bookRent.id = :id")
    long countByBookRent_Id(Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Registry r SET r.tombstone = true WHERE r.bookRent.id = :id AND r.startPeriod = :startPeriod")
    void deleteRegistry(Integer id, String startPeriod);
}
