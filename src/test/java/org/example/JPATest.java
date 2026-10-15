package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JPATest {

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void buddyInfoPersist() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Emmanuel");

        BuddyInfo buddy1 = new BuddyInfo();
        buddy1.setName("Olivia");

        // replaces em.persist(...) inside tx.begin()/tx.commit()
        buddyInfoRepository.save(buddy);
        buddyInfoRepository.save(buddy1);

        // replaces em.createQuery("SELECT p FROM BuddyInfo p").getResultList()
        List<BuddyInfo> results = (List<BuddyInfo>) buddyInfoRepository.findAll();

        assertEquals(2, results.size());
        assertNotNull(buddy.getId());
        assertNotNull(buddy1.getId());

        System.out.println("List of buddies\n----------------");
        for (BuddyInfo p : results) {
            System.out.println(p.getName() + " (id=" + p.getId() + ")");
        }
    }

    @Test
    void addressBookPersist() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("John", "613-555-1234");
        BuddyInfo buddy1 = new BuddyInfo("Sarah", "623-666-5678");

        // save the buddies, then the book that refers to them
        buddyInfoRepository.save(buddy);
        buddyInfoRepository.save(buddy1);
        addressBook.addBuddy(buddy);
        addressBook.addBuddy(buddy1);
        addressBookRepository.save(addressBook);

        // replaces em.createQuery("SELECT p FROM AddressBook p").getResultList()
        List<AddressBook> results = (List<AddressBook>) addressBookRepository.findAll();

        assertEquals(1, results.size());
        Optional<AddressBook> found = addressBookRepository.findById(addressBook.getId());
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getSize());
        assertEquals("[John - 613-555-1234, Sarah - 623-666-5678]", found.get().toString());

        System.out.println("List of address books\n----------------");
        for (AddressBook p : results) {
            System.out.println(p.getBuddies() + " (id=" + p.getId() + ")");
        }
    }

    @Test
    void findBuddyByName() {
        buddyInfoRepository.save(new BuddyInfo("John", "613-555-1234"));
        buddyInfoRepository.save(new BuddyInfo("Sarah", "623-666-5678"));

        List<BuddyInfo> found = buddyInfoRepository.findByName("Sarah");

        assertEquals(1, found.size());
        assertEquals("623-666-5678", found.get(0).getPhoneNumber());
    }
}