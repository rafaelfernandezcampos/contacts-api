package org.dimensa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dimensa.entity.Contact;
import org.dimensa.payload.ContactModel;
import org.dimensa.service.ContactService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    Contact contactOne;
    Contact contactTwo;
    List<Contact> contactList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        contactOne = new Contact(1l, "Rafael", "email@email.com", "(17) 991869702", LocalDateTime.parse("2023-05-20T12:51:00"));
        contactTwo = new Contact(2l, "Rafael2", "email2@email.com", "(17) 991869702", LocalDateTime.parse("2023-05-20T12:51:00"));
        contactList.add(contactOne);
        contactList.add(contactTwo);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllContacts() throws Exception {
        when(contactService.findAll()).thenReturn(contactList);

        this.mockMvc.perform(get("/contacts/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetOneContact() throws Exception {
        when(contactService.findOne(1l)).thenReturn(contactOne);

        this.mockMvc.perform(get("/contacts/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testCreateContact() throws Exception {
        ContactModel contactModel = new ContactModel();
        contactModel.setName(contactOne.getName());
        contactModel.setEmail(contactOne.getEmail());
        contactModel.setPhone(contactOne.getPhone());
        contactModel.setBirth(contactOne.getBirth());

        when(contactService.create(contactModel)).thenReturn(contactOne);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(contactModel);

        this.mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateContact() throws Exception {
        ContactModel contactModel = new ContactModel();
        contactModel.setName(contactOne.getName());
        contactModel.setEmail(contactOne.getEmail());
        contactModel.setPhone(contactOne.getPhone());
        contactModel.setBirth(contactOne.getBirth());

        contactService.updateOne(1l, contactModel);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(contactModel);

        this.mockMvc.perform(put("/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testPatchContact() throws Exception {
        HashMap<String, Object> object = new HashMap<String, Object>();
        object.put("key", "name");
        object.put("value", "teste");

        when(contactService.patchOne(1l, "name", "teste")).thenReturn(contactOne);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(object);

        this.mockMvc.perform(patch("/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testDeleteOneContact() throws Exception {
        contactService.deleteById(1l);

        this.mockMvc.perform(delete("/contacts/1")).andDo(print()).andExpect(status().isNoContent());
    }
}