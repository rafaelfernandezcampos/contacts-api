package org.dimensa.service;

import org.dimensa.entity.Address;
import org.dimensa.entity.Contact;
import org.dimensa.payload.AddressModel;
import org.dimensa.payload.ContactModel;
import org.dimensa.repository.AddressRepository;
import org.dimensa.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        System.out.println(contactRepository.findAll());
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

            System.out.println(addresses);
            addresses.forEach(address -> {
                Address addressToSave = new Address(address.getStreet(), address.getNumber(), address.getCep(), createdContact);

                addressRepository.save(addressToSave);
            });
        }

        return createdContact;
    }

    public void updateOne(long id, ContactModel contact) {
        Contact databaseContact = contactRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        databaseContact.setName(contact.getName());
        databaseContact.setEmail(contact.getEmail());
        databaseContact.setPhone(contact.getPhone());
        databaseContact.setBirth(contact.getBirth());

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


