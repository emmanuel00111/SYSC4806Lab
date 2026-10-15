package org.example;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressBookController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookController(AddressBookRepository addressBookRepository,
                                     BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    @PostMapping
    public AddressBook createAddressBook() {
        return addressBookRepository.save(new AddressBook());
    }

    @GetMapping
    public Iterable<AddressBook> getAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    @GetMapping("/{bookId}")
    public AddressBook getAddressBook(@PathVariable Integer bookId) {
        return findBook(bookId);
    }

    @PostMapping("/{bookId}/buddies/{buddyId}")
    public AddressBook addExistingBuddy(@PathVariable Integer bookId, @PathVariable Integer buddyId) {
        AddressBook book = findBook(bookId);
        BuddyInfo buddy = buddyInfoRepository.findById(buddyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "BuddyInfo " + buddyId + " not found"));
        if (book.getBuddies().stream().noneMatch(b -> b.getId().equals(buddyId))) {
            book.addBuddy(buddy);
        }
        return addressBookRepository.save(book);
    }

    @PostMapping("/{bookId}/buddies")
    public AddressBook createAndAddBuddy(@PathVariable Integer bookId, @RequestBody BuddyInfo buddy) {
        AddressBook book = findBook(bookId);
        buddy.setId(null);                      // always create a new buddy
        book.addBuddy(buddyInfoRepository.save(buddy));
        return addressBookRepository.save(book);
    }

    @DeleteMapping("/{bookId}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable Integer bookId, @PathVariable Integer buddyId) {
        AddressBook book = findBook(bookId);
        book.getBuddies().removeIf(b -> b.getId().equals(buddyId));
        return addressBookRepository.save(book);
    }

    @DeleteMapping("/{bookId}")
    public void deleteAddressBook(@PathVariable Integer bookId) {
        addressBookRepository.delete(findBook(bookId));
    }

    /** A buddy can only belong to one address book (the @OneToMany join table enforces it). */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleConflict() {
        return "That buddy already belongs to another address book";
    }

    private AddressBook findBook(Integer bookId) {
        return addressBookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "AddressBook " + bookId + " not found"));
    }
}
