package org.example;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private int createBook() throws Exception {
        String json = mockMvc.perform(post("/api/addressbooks"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return JsonPath.read(json, "$.id");
    }

    private int createBuddy(String name, String phone) throws Exception {
        String json = mockMvc.perform(post("/api/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\",\"phoneNumber\":\"" + phone + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phoneNumber").value(phone))
                .andReturn().getResponse().getContentAsString();
        return JsonPath.read(json, "$.id");
    }

    @Test
    void createAddressBookReturnsEmptyBook() throws Exception {
        mockMvc.perform(post("/api/addressbooks"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.buddies").isEmpty());
    }

    @Test
    void createBuddyAndGetItBack() throws Exception {
        int buddyId = createBuddy("John", "613-555-1234");

        mockMvc.perform(get("/api/buddies/" + buddyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void addExistingBuddyToBook() throws Exception {
        int bookId = createBook();
        int buddyId = createBuddy("Sarah", "613-555-5678");

        mockMvc.perform(post("/api/addressbooks/" + bookId + "/buddies/" + buddyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buddies", hasSize(1)))
                .andExpect(jsonPath("$.buddies[0].name").value("Sarah"));

        // adding the same buddy twice does not duplicate it
        mockMvc.perform(post("/api/addressbooks/" + bookId + "/buddies/" + buddyId))
                .andExpect(jsonPath("$.buddies", hasSize(1)));
    }

    @Test
    void createBuddyDirectlyInBook() throws Exception {
        int bookId = createBook();

        mockMvc.perform(post("/api/addressbooks/" + bookId + "/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Emmanuel\",\"phoneNumber\":\"613-555-0000\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.buddies[0].name").value("Emmanuel"));
    }

    @Test
    void removeBuddyFromBook() throws Exception {
        int bookId = createBook();
        int buddyId = createBuddy("John", "613-555-1234");
        mockMvc.perform(post("/api/addressbooks/" + bookId + "/buddies/" + buddyId));

        mockMvc.perform(delete("/api/addressbooks/" + bookId + "/buddies/" + buddyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buddies").isEmpty());
    }

    @Test
    void unknownIdsReturn404() throws Exception {
        mockMvc.perform(get("/api/addressbooks/99999")).andExpect(status().isNotFound());
        mockMvc.perform(get("/api/buddies/99999")).andExpect(status().isNotFound());
        mockMvc.perform(get("/addressbooks/99999")).andExpect(status().isNotFound());
    }

    @Test
    void thymeleafViewListsBuddies() throws Exception {
        int bookId = createBook();
        int buddyId = createBuddy("Sarah", "613-555-5678");
        mockMvc.perform(post("/api/addressbooks/" + bookId + "/buddies/" + buddyId));

        mockMvc.perform(get("/addressbooks/" + bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("addressbook"))
                .andExpect(model().attributeExists("addressBook"))
                .andExpect(content().string(containsString("Sarah")))
                .andExpect(content().string(containsString("613-555-5678")));
    }

    @Test
    void thymeleafIndexListsBooks() throws Exception {
        int bookId = createBook();

        mockMvc.perform(get("/addressbooks"))
                .andExpect(status().isOk())
                .andExpect(view().name("addressbooks"))
                .andExpect(content().string(containsString("Address Book #" + bookId)));
    }
}
