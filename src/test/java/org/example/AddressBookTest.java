package org.example;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    @Test
    void addBuddy() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("John", "613-555-1234");

        addressBook.addBuddy(buddy);

        assertEquals(1, addressBook.getSize());
        assertEquals(buddy, addressBook.getBuddy(0));
    }

    @Test
    void removeBuddy() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("John", "613-555-1234");

        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(buddy);

        assertEquals(0, addressBook.getSize());
        assertFalse(addressBook.getBuddies().contains(buddy));
    }

    @Test
    void getBuddy() {
        AddressBook addressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("John", "613-555-1234");
        BuddyInfo buddy2 = new BuddyInfo("Sarah", "613-555-5678");

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        assertEquals(buddy1, addressBook.getBuddy(0));
        assertEquals(buddy2, addressBook.getBuddy(1));
    }

    @Test
    void getBuddies() {
        AddressBook addressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("John", "613-555-1234");
        BuddyInfo buddy2 = new BuddyInfo("Sarah", "613-555-5678");

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        List<BuddyInfo> buddies = addressBook.getBuddies();

        assertEquals(2, buddies.size());
        assertTrue(buddies.contains(buddy1));
        assertTrue(buddies.contains(buddy2));
    }

    @Test
    void getSize() {
        AddressBook addressBook = new AddressBook();

        assertEquals(0, addressBook.getSize());

        addressBook.addBuddy(
                new BuddyInfo("John", "613-555-1234")
        );

        assertEquals(1, addressBook.getSize());

        addressBook.addBuddy(
                new BuddyInfo("Sarah", "613-555-5678")
        );

        assertEquals(2, addressBook.getSize());
    }

    @Test
    void clear() {
        AddressBook addressBook = new AddressBook();

        addressBook.addBuddy(
                new BuddyInfo("John", "613-555-1234")
        );

        addressBook.addBuddy(
                new BuddyInfo("Sarah", "613-555-5678")
        );

        assertEquals(2, addressBook.getSize());

        addressBook.clear();

        assertEquals(0, addressBook.getSize());
        assertTrue(addressBook.getBuddies().isEmpty());
    }

    @Test
    void testToString() {
        AddressBook addressBook = new AddressBook();

        addressBook.addBuddy(
                new BuddyInfo("John", "613-555-1234")
        );

        addressBook.addBuddy(
                new BuddyInfo("Sarah", "613-555-5678")
        );

        String expected =
                "[John - 613-555-1234, Sarah - 613-555-5678]";

        assertEquals(expected, addressBook.toString());
    }

    @Test
    void getId() {
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(
                new BuddyInfo("John", "613-555-1234")
        );

        assertNull(addressBook.getId());
    }

    @Test
    void setId() {
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(
                new BuddyInfo("John", "613-555-1234")
        );
        addressBook.setId(1);

        assertEquals(1, addressBook.getId());
    }
}