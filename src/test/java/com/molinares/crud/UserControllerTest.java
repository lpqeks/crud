package com.molinares.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.molinares.crud.models.User;
import com.molinares.crud.repositories.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.core.Is.is;

import javax.transaction.Transactional;

import java.io.DataInput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    //Test POST create one user
    @Transactional
    @Rollback
    @Test
    //Passed
    public void canGetAddOneUser() throws Exception {
        //set up
        String json = "{\n" +
                "    \"lastName\": \"Castillo\",\n" +
                "    \"firstName\": \"Lissete\",\n" +
                "    \"email\": \"lisi@rmail.com\",\n" +
                "  \t\"password\" : \"plisi\",\n" +
                "    \"boardingDate\": \"2019-08-12\",\n" +
                "    \"active\": true\n" +
                "}";

        this.mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Castillo")));
    }

    //test GET request to get all users
    @Transactional
    @Rollback
    @Test
    //Passed
    public void canGetAllUsers() throws Exception {

        //set up

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");

        User user1 = new User();
        user1.setLastName("Molinares");
        user1.setFirstName("Carlos");
        user1.setEmail("carlos@email.com");
        user1.setPassword("pcarlos");
        user1.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user1.setActive(true);

        User user2 = new User();
        user2.setLastName("Castillo");
        user2.setFirstName("Lissette");
        user2.setEmail("lisi@email.com");
        user2.setPassword("plisi");
        user2.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user2.setActive(true);

        this.userRepository.save(user1);
        this.userRepository.save(user2);

        this.mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Carlos")))
                .andExpect(jsonPath("$.[1].lastName", is("Castillo")));

    }

    //test GET request get user by Id
    @Transactional
    @Rollback
    @Test
    public void canGetUserById() throws Exception {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");

        User user1 = new User();
        user1.setLastName("Molinares");
        user1.setFirstName("Carlos");
        user1.setEmail("carlos@email.com");
        user1.setPassword("pcarlos");
        user1.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user1.setActive(true);

        User user2 = new User();
        user2.setLastName("Castillo");
        user2.setFirstName("Lissette");
        user2.setEmail("lisi@email.com");
        user2.setPassword("plisi");
        user2.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user2.setActive(true);

        this.userRepository.save(user1);
        this.userRepository.save(user2);

        this.mvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Molinares")))
                .andExpect(jsonPath("$.firstName", is("Carlos")));
    }

    //test PATCH request update user
    @Transactional
    @Rollback
    @Test
    public void canUpdateRecordById() throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");

        User user1 = new User();
        user1.setLastName("Molinares");
        user1.setFirstName("Carlos");
        user1.setEmail("carlos@email.com");
        user1.setPassword("pcarlos");
        user1.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user1.setActive(true);

        String json = "{\"firstName\" : \"Castillo\"}";

        this.mvc.perform(patch("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Momo")));
                //.andExpect(jsonPath("$.id", is(1)));
    }

   //Test DELETE request to users
    @Transactional
    @Rollback
    @Test
     public void canDeleteUser() throws Exception {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");

        User user1 = new User();
        user1.setLastName("Molinares");
        user1.setFirstName("Carlos");
        user1.setEmail("carlos@email.com");
        user1.setPassword("pcarlos");
        user1.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user1.setActive(true);

        User user2 = new User();
        user2.setLastName("Castillo");
        user2.setFirstName("Lissette");
        user2.setEmail("lisi@email.com");
        user2.setPassword("plisi");
        user2.setBoardingDate(dateFormatter.parse("2020-12-12"));
        user2.setActive(true);

        this.userRepository.save(user1);
        this.userRepository.save(user2);

        this.mvc.perform(delete("/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(String.valueOf(1))));
    }
}
