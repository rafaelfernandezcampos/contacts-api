package org.dimensa.controller;

import io.swagger.annotations.ApiParam;
import org.dimensa.entity.Contact;
import org.dimensa.payload.ContactModel;
import org.dimensa.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Contact>> getAllContacts() {
       return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getOneContact(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.findOne(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Contact> createContact(@RequestBody ContactModel contact) {
        return new ResponseEntity<>(service.create(contact), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public void updateContact(@ApiParam(name = "id", value = "Id of the contact") @PathVariable("id") int id, @RequestBody ContactModel contact) {
        service.updateOne(id, contact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneContact(@PathVariable("id") int id) {
        service.deleteById(id);
    }
}
