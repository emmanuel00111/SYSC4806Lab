package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/buddies")
public class BuddyInfoController {

    private final BuddyInfoRepository buddyInfoRepository;

    public BuddyInfoController(BuddyInfoRepository buddyInfoRepository) {
        this.buddyInfoRepository = buddyInfoRepository;
    }

    @PostMapping
    public BuddyInfo createBuddy(@RequestBody BuddyInfo buddy) {
        buddy.setId(null);
        return buddyInfoRepository.save(buddy);
    }

    @GetMapping
    public Iterable<BuddyInfo> getAllBuddies() {
        return buddyInfoRepository.findAll();
    }

    @GetMapping("/{id}")
    public BuddyInfo getBuddy(@PathVariable Integer id) {
        return buddyInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "BuddyInfo " + id + " not found"));
    }
}
