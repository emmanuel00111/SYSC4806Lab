package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        AddressBook addressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("John", "613-555-1234");
        BuddyInfo buddy2 = new BuddyInfo("Sarah", "613-555-5678");

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        System.out.println(addressBook);
    }
}
