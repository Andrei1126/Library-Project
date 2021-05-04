package com.example.library.controllers;

import com.example.library.dtos.RegistryDto;
import com.example.library.models.Registry;
import com.example.library.services.RegistryService;
import com.sun.istack.NotNull;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/registry")
@RestController
public class RegistryController {
    private final RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping
    public Registry addEntryInRegistry(@NotNull @RequestBody Registry registry) {
        return registryService.addEntryInRegistry(registry);
    }

    @GetMapping
    public List<Registry> getAllRegistries() {
        return registryService.getAllRegistries();
    }

    @GetMapping(path = "{id}")
    public Registry getRegistryById(@PathVariable("id") Integer id) {
        return registryService.getRegistryById(id)
                .orElse(null);
    }

    @PutMapping(path = "{id}")
    public void updateEntryInRegistry(@PathVariable("id") Integer id, @NotNull @RequestBody Registry registryToUpdate) {
        registryService.updateEntryInRegistry(id, registryToUpdate.getStartPeriod());
    }

    @GetMapping(path = "return/{id}")
    public RegistryDto returnBook(@PathVariable("id") Integer id, @RequestHeader String start) throws NotFoundException {
        return registryService.returnBook(id, start);
    }

    @GetMapping(path = "rent/{id}")
    public long countRent(@PathVariable("id") Integer id) {
        return registryService.countRentBooks(id);
    }

}
