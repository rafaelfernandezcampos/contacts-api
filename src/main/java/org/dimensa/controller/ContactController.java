package org.dimensa.controller;

import io.swagger.annotations.ApiParam;
import org.dimensa.entity.Contact;
import org.dimensa.payload.ContactModel;
import org.dimensa.payload.ContactPatchModel;
import org.dimensa.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Contacts controller with all request from the CRUD
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    /**
     * Method to return all contacts
     *
     * @return all contacts
     */
    @GetMapping()
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Method to return a contact from an id
     *
     * @param id of the contact that will be returned
     * @return the contact
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getOneContact(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.findOne(id), HttpStatus.OK);
    }

    /**
     * Method to create a new contact
     *
     * @param contact body with the fields: name, email, phone, birth and addresses
     * @return the contact created
     */
    @PostMapping()
    public ResponseEntity<Contact> createContact(@RequestBody ContactModel contact) {
        return new ResponseEntity<>(service.create(contact), HttpStatus.CREATED);
    }

    /**
     * Method to update a contact
     *
     * @param id      of the contact that will be updated
     * @param contact body with the fields that will be updated
     */
    @PutMapping("/{id}")
    public void updateContact(@ApiParam(name = "id", value = "Id of the contact") @PathVariable("id") int id, @RequestBody ContactModel contact) {
        service.updateOne(id, contact);
    }

    /**
     * Method to update a contact partially
     *
     * @param id    of the contact that will be updated
     * @param patch body with the key and value of the field that will be updated
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Contact> patchOneContact(@PathVariable("id") int id, @RequestBody ContactPatchModel patch) {
        return new ResponseEntity<>(service.patchOne(id, patch.getKey(), patch.getValue()), HttpStatus.OK);
    }

    /**
     * Method to delete a contact from an id
     *
     * @param id of the contact that will be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneContact(@PathVariable("id") int id) {
        try {
            service.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        }
    }
}
