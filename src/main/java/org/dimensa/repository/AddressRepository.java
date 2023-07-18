package org.dimensa.repository;

import org.dimensa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    /**
     * Method to delete all addresses from a contact id
     *
     * @param contactId: id of the contact that all addresses will be deleted
     */
    @Modifying
    @Query("DELETE Address a WHERE a.contact.id = ?1")
    void deleteByContactId(long contactId);
}
