package belajar_restful.belajar_spring_restful_api.controller;

import belajar_restful.belajar_spring_restful_api.entity.Contact;
import belajar_restful.belajar_spring_restful_api.entity.User;
import belajar_restful.belajar_spring_restful_api.model.ContactResponse;
import belajar_restful.belajar_spring_restful_api.model.CreateContactRequest;
import belajar_restful.belajar_spring_restful_api.model.UpdateContactRequest;
import belajar_restful.belajar_spring_restful_api.model.WebResponse;
import belajar_restful.belajar_spring_restful_api.repository.ContactRepository;
import belajar_restful.belajar_spring_restful_api.repository.UserRepository;
import belajar_restful.belajar_spring_restful_api.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();

        user.setUsername("test");
        user.setName("test");
        user.setToken("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);
    }

    @Test
    void createContactBadRequest()  throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                 WebResponse<String> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {

                    });
                    assertNotNull(response);
                }
        );


    }

    @Test
    void createContactSuccess()  throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("dada");
        request.setLastName("salah");
        request.setEmail("salah@example.com");
        request.setPhone("921838219");

        mockMvc.perform(
                post("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ContactResponse> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals("dada", response.getData().getFirstName());
                    assertEquals("salah", response.getData().getLastName());
                    assertEquals("salah@example.com", response.getData().getEmail());
                    assertEquals("921838219", response.getData().getPhone());

                    assertTrue(contactRepository.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void getContactNotFound()  throws Exception {
        mockMvc.perform(
                get("/api/contacts/32")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<String> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {

                    });
                    assertNotNull(response);
                }
        );
    }

    @Test
    void getContactSuccess()  throws Exception {
        User user = userRepository.findById("test").orElse(null);


        Contact contact = new Contact();
        contact.setUser(user);
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName("dada");
        contact.setLastName("salah");
        contact.setEmail("salah@example.com");
        contact.setPhone("921838219");
        contactRepository.save(contact);

        mockMvc.perform(
                get("/api/contacts/"+contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ContactResponse> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals(contact.getId(), response.getData().getId());
                    assertEquals(contact.getFirstName(), response.getData().getFirstName());
                    assertEquals(contact.getLastName(), response.getData().getLastName());
                    assertEquals(contact.getEmail(), response.getData().getEmail());
                    assertEquals(contact.getPhone(), response.getData().getPhone());


                }
        );
    }

    @Test
    void updateContactBadRequest()  throws Exception {
        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("");
        request.setEmail("salah");

        mockMvc.perform(
                put("/api/contacts/123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<String> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {

                    });
                    assertNotNull(response);
                }
        );
    }

    @Test
    void updateContactSuccess()  throws Exception {
        User user = userRepository.findById("test").orElse(null);

        Contact contact = new Contact();
        contact.setUser(user);
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName("dada");
        contact.setLastName("salah");
        contact.setEmail("salah@example.com");
        contact.setPhone("921838219");
        contactRepository.save(contact);

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("budi");
        request.setLastName("nana");
        request.setEmail("walawe@example.com");
        request.setPhone("3213131");

        mockMvc.perform(
                put("/api/contacts/"+contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ContactResponse> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());
                    assertEquals(request.getFirstName(), response.getData().getFirstName());
                    assertEquals(request.getLastName(), response.getData().getLastName());
                    assertEquals(request.getEmail(), response.getData().getEmail());
                    assertEquals(request.getPhone(), response.getData().getPhone());

                    assertTrue(contactRepository.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void deleteContactNotFound()  throws Exception {
        mockMvc.perform(
                delete("/api/contacts/32")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<String> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {

                    });
                    assertNotNull(response);
                }
        );
    }

    @Test
    void deleteContactSuccess()  throws Exception {
        User user = userRepository.findById("test").orElse(null);


        Contact contact = new Contact();
        contact.setUser(user);
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName("dada");
        contact.setLastName("salah");
        contact.setEmail("salah@example.com");
        contact.setPhone("921838219");
        contactRepository.save(contact);

        mockMvc.perform(
                delete("/api/contacts/"+contact.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<String> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals("OK", response.getData());


                }
        );
    }

    @Test
    void searchNotFound()  throws Exception {
        mockMvc.perform(
                get("/api/contacts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ContactResponse>> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals(0, response.getData().size());
                    assertEquals(0,response.getPaging().getTotalPage());
                    assertEquals(0,response.getPaging().getCurrentPage());
                    assertEquals(10,response.getPaging().getSize());

                }
        );
    }

    @Test
    void searchSuccess()  throws Exception {
        User user = userRepository.findById("test").orElse(null);

        for(int i = 0 ; i<100;i++ ){
            Contact contact = new Contact();
            contact.setUser(user);
            contact.setId(UUID.randomUUID().toString());
            contact.setFirstName("dada"+i);
            contact.setLastName("salah");
            contact.setEmail("salah@example.com");
            contact.setPhone("921838219");
            contactRepository.save(contact);
        }


        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("name","dada")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ContactResponse>> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals(10, response.getData().size());
                    assertEquals(10,response.getPaging().getTotalPage());
                    assertEquals(0,response.getPaging().getCurrentPage());
                    assertEquals(10,response.getPaging().getSize());

                }
        );

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("name","salah")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ContactResponse>> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals(10, response.getData().size());
                    assertEquals(10,response.getPaging().getTotalPage());
                    assertEquals(0,response.getPaging().getCurrentPage());
                    assertEquals(10,response.getPaging().getSize());

                }
        );

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("email","example.com")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ContactResponse>> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals(10, response.getData().size());
                    assertEquals(10,response.getPaging().getTotalPage());
                    assertEquals(0,response.getPaging().getCurrentPage());
                    assertEquals(10,response.getPaging().getSize());

                }
        );

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("phone","18382")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ContactResponse>> response =   objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {

                    });
                    assertNull(response.getErrors());

                    assertEquals(10, response.getData().size());
                    assertEquals(10,response.getPaging().getTotalPage());
                    assertEquals(0,response.getPaging().getCurrentPage());
                    assertEquals(10,response.getPaging().getSize());

                }
        );

        mockMvc.perform(
                get("/api/contacts")
                        .queryParam("phone", "838")
                        .queryParam("page", "1000")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ContactResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(1000, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });

    }
}