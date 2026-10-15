package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Spring Data derives the queries from the method names (no JPQL needed).
 */
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Integer> {

    List<BuddyInfo> findByName(String name);

    List<BuddyInfo> findByPhoneNumber(String phoneNumber);
}
