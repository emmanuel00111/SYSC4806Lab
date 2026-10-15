package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

/**
    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepository,
                                  BuddyInfoRepository buddyInfoRepository) {
        return (args) -> {
            // create an address book with a few buddies and save it
            // (buddies are saved too, thanks to cascade = CascadeType.ALL)
            AddressBook addressBook = new AddressBook();
            addressBook.addBuddy(new BuddyInfo("John", "613-555-1234"));
            addressBook.addBuddy(new BuddyInfo("Sarah", "613-555-5678"));
            addressBook.addBuddy(new BuddyInfo("Emmanuel", "613-555-0000"));
            addressBookRepository.save(addressBook);

            // fetch all address books
            log.info("AddressBooks found with findAll():");
            log.info("-------------------------------");
            for (AddressBook book : addressBookRepository.findAll()) {
                log.info("AddressBook id={} -> {}", book.getId(), book);
            }
            log.info("");

            // fetch all buddies
            log.info("Buddies found with findAll():");
            log.info("-------------------------------");
            buddyInfoRepository.findAll().forEach(buddy -> log.info(buddy.toString()));
            log.info("");

            // fetch an individual address book by ID
            addressBookRepository.findById(addressBook.getId()).ifPresent(book -> {
                log.info("AddressBook found with findById({}):", book.getId());
                log.info("--------------------------------");
                log.info(book.toString());
                log.info("");
            });

            // fetch buddies by name (derived query method)
            log.info("Buddy found with findByName('Sarah'):");
            log.info("--------------------------------------------");
            buddyInfoRepository.findByName("Sarah").forEach(buddy -> log.info(buddy.toString()));
            log.info("");
        };
    }
    **/

}
