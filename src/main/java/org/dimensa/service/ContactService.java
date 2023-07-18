package org.dimensa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.dimensa.entity.Address;
import org.dimensa.entity.Contact;
import org.dimensa.payload.AddressModel;
import org.dimensa.payload.ContactModel;
import org.dimensa.repository.AddressRepository;
import org.dimensa.repository.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.EntityNotFoundException;


/**
 * Contact service with all operations of the CRUD
 */
@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;

    public ContactService(ContactRepository contactRepository, AddressRepository addressRepository) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Method to return all contacts from database
     *
     * @return List of contacts
     */
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    /**
     * Method to return one contact from
     * and throw not found exception if the contact isn't in the database
     *
     * @param id of the contact
     * @return the contact
     */
    public Contact findOne(long id) {
        Optional<Contact> databaseContact = contactRepository.findById(id);

        if (databaseContact.isPresent()) {
            return databaseContact.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contact not found"
            );
        }
    }

    /**
     * Method to save a nem contact on database
     *
     * @param contact contact object with the fields that will be saved
     * @return created contact
     */
    public Contact create(ContactModel contact) {
        /*
          Create a Contact with the fields received
         */
        Contact contactToSave = new Contact(
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getBirth()
        );

        /*
          Save the new contact on database
         */
        Contact createdContact = contactRepository.save(contactToSave);

        /*
          If the new contact has a list of address, then is necessary to create these addresses on database
         */
        if (contact.getAddresses().size() > 0) {
            List<AddressModel> addresses = contact.getAddresses();
            List<Address> addressesSaved = new ArrayList<>();

            /*
              For each address on the list, create a new Address object and save on database with the contact object
             */
            addresses.forEach(address -> {
                Address addressToSave = new Address(address.getStreet(), address.getNumber(), address.getCep(), createdContact);

                addressesSaved.add(addressRepository.save(addressToSave));
            });

            createdContact.setAddressList(addressesSaved);
        }

        return createdContact;
    }
    
    /**
     * Method to update a contact on database
     *
     * @param id      of the contact
     * @param contact field's that will be updated
     */
    @Transactional
    public void updateOne(long id, ContactModel contact) {
        /*
          Find the contact on database and throw not found exception if the contact isn't in the database
         */
        Optional<Contact> optionalContact = contactRepository.findById(id);

        if (optionalContact.isPresent()) {
            Contact databaseContact = optionalContact.get();
            /*
              Verify which fields will be updated and set them on the database contact object
             */
            if (contact.getName() != null)
                databaseContact.setName(contact.getName());
            if (contact.getEmail() != null)
                databaseContact.setEmail(contact.getEmail());
            if (contact.getPhone() != null)
                databaseContact.setPhone(contact.getPhone());
            if (contact.getBirth() != null)
                databaseContact.setBirth(contact.getBirth());

            /*
              If contact has addresses list on updated, then is necessary delete all address from this contact and save the new list
             */
            if (contact.getAddresses().size() > 0) {
                List<AddressModel> addresses = contact.getAddresses();

                /*
                  First delete all address from this contact
                 */
                addressRepository.deleteByContactId(databaseContact.getId());

                /*
                  For each address on the list, create a new Address object and save on database with the contact object
                 */
                addresses.forEach(address -> {

                    Address addressToSave = new Address(address.getStreet(), address.getNumber(), address.getCep(), databaseContact);

                    addressRepository.save(addressToSave);
                });
            }

            /*
              Save the updated contact on database
             */
            contactRepository.save(databaseContact);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contact not found"
            );
        }
    }

    /**
     * Method to update a field of contact on database
     *
     * @param id    of the contact
     * @param patch field that will be updated
     */
    public Contact patchOne(long id, String key, String value) {
        Optional<Contact> optionalContact = contactRepository.findById(id);

        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();

            /*
             * Verify which key was sent to know which set must execute to this value
             * */
            if (key.equalsIgnoreCase("name"))
                contact.setName(value);
            if (key.equalsIgnoreCase("email"))
                contact.setEmail(value);
            if (key.equalsIgnoreCase("birth"))
                contact.setBirth(LocalDateTime.parse(value));
            if (key.equalsIgnoreCase("phone"))
                contact.setPhone(value);

            /*
             * Save the patched contact
             * */
            return contactRepository.save(contact);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contact not found"
            );
        }
    }

    /**
     * Method to delete a contact from database
     * and throw not found exception if the contact isn't in the database
     *
     * @param id of the contact
     */
    public void deleteById(long id) {
        Optional<Contact> databaseContact = contactRepository.findById(id);

        if (databaseContact.isPresent()) {
            contactRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contact not found"
            );
        }
    }
}


