package com.example.library.controllers;

import com.example.library.dtos.RegistryDto;
import com.example.library.models.Registry;
import com.example.library.services.RegistryService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RegistryController.class)
class RegistryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistryService registryService;


//    @Test
//    void addEntryInRegistry() throws Exception {
//        mvc.perform(MockMvcRequestBuilders
//                .post("registry")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("bookRent")
//                .param("startPeriod")
//                .param("tombstone")
//                .sessionAttr("registry", new Registry())
//        )
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("registry", hasProperty("id", is(8))))
//                .andExpect(model().attribute("registry", hasProperty("bookRent",
//                        model().attribute("book", hasProperty("id", is(1))),
//                        model().attribute("book", hasProperty("name", is("Test2"))),
//                        model().attribute("book", hasProperty("isbn", is("123335348.0"))),
//                        model().attribute("book", hasProperty("price", is(12.5))),
//                        model().attribute("book", hasProperty("bookRent", is(null))),
//                        model().attribute("book", hasProperty("nrOfBooks", is(30))
//                        ))))
//                .andExpect(model().attribute("registry", hasProperty("startPeriod", is("2021-01-21"))))
//                .andExpect(model().attribute("registry", hasProperty("tombstone", is("false"))));
//    }

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