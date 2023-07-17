package org.dimensa.service;

import org.dimensa.entity.Address;
import org.dimensa.entity.Contact;
import org.dimensa.payload.AddressModel;
import org.dimensa.payload.ContactModel;
import org.dimensa.repository.AddressRepository;
import org.dimensa.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import javax.persistence.EntityNotFoundException;


@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;

    public ContactService(ContactRepository contactRepository, AddressRepository addressRepository) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findOne(long id) {
        return contactRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Contact create(ContactModel contact) {
        Contact contactToSave = new Contact(
            contact.getName(),
            contact.getEmail(),
            contact.getPhone(),
            contact.getBirth()
        );


        Contact createdContact = contactRepository.save(contactToSave);

        if(contact.getAddresses().size() > 0) {
            List<AddressModel> addresses = contact.getAddresses();
            List<Address> addressesSaved = new ArrayList<>();

            addresses.forEach(address -> {
                Address addressToSave = new Address(address.getStreet(), address.getNumber(), address.getCep(), createdContact);

                addressesSaved.add(addressRepository.save(addressToSave));
            });

            createdContact.setAddressList(addressesSaved);
        }

        return createdContact;
    }

    @Transactional
    public void updateOne(long id, ContactModel contact) {
        Contact databaseContact = contactRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (contact.getName() != null)
            databaseContact.setName(contact.getName());
        if (contact.getEmail() != null)
            databaseContact.setEmail(contact.getEmail());
        if (contact.getPhone() != null)
            databaseContact.setPhone(contact.getPhone());
        if (contact.getBirth() != null)
            databaseContact.setBirth(contact.getBirth());

        if (contact.getAddresses().size() > 0) {
            List<AddressModel> addresses = contact.getAddresses();

            addresses.forEach(address -> {
                addressRepository.deleteByContactId(databaseContact.getId());

                Address addressToSave = new Address(address.getStreet(), address.getNumber(), address.getCep(), databaseContact);

                addressRepository.save(addressToSave);
            });
        }
        contactRepository.save(databaseContact);
    }

    public void deleteById (long id){
        Optional<Contact> databaseContact = contactRepository.findById(id);

        if (databaseContact.isPresent()) {
            contactRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}


