package com.example.library.controllers;

import com.example.library.dtos.RegistryDto;
import com.example.library.services.RegistryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RegistryController.class)
class RegistryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistryService registryService;

    // No value at JSON path "$.registry"
    @Test
    void getAllRegistries() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/v1/registry")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.registry").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$î.registry[*].registryId").isNotEmpty());
    }

    @Test
    void returnBook() throws Exception{

        assertThat(registryService).isNotNull();

        RegistryDto dto = new RegistryDto("Test1", 0, 0.0);

        when(registryService.returnBook(1, "2021-03-21"))
                .thenReturn(dto);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/registry/return/{id}", 1).header("start", "2021-03-21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.overdueDays").value(0))
                .andExpect(jsonPath("$.penaltyPrice").value(0.0));
    }
}