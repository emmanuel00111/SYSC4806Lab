package org.example;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JPATest {
    @Test
    void buddyInfoPersist() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Emmanuel");

        BuddyInfo buddy1 = new BuddyInfo();
        buddy1.setName("Olivia");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("address-book-test");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(buddy);
        em.persist(buddy1);

        tx.commit();

        Query q = em.createQuery("SELECT p FROM BuddyInfo p");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> results = q.getResultList();

        System.out.println("List of products\n----------------");

        for (BuddyInfo p : results) {

            System.out.println(p.getName() + " (id=" + p.getId() + ")");
        }

        em.close();

        emf.close();
    }

    @Test
    void addressBookPersist() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("John", "613-555-1234");
        BuddyInfo buddy1 = new BuddyInfo("Sarah", "623-666-5678");


        addressBook.addBuddy(buddy);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("address-book-test");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(buddy);
        em.persist(buddy1);

        tx.commit();

        Query q = em.createQuery("SELECT p FROM AddressBook p");

        @SuppressWarnings("unchecked")
        List<AddressBook> results = q.getResultList();

        System.out.println("List of products\n----------------");

        for (AddressBook p : results) {
            System.out.println(p.getBuddies() + " (id=" + p.getId() + ")");
        }

        em.close();

        emf.close();
    }
}
