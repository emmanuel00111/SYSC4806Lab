package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressBookRepository extends CrudRepository<AddressBook, Integer> {
    List<AddressBook> findById(int id);
}
