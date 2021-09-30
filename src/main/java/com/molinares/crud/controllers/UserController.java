package com.molinares.crud.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.molinares.crud.UserServices;
import com.molinares.crud.models.User;
import com.molinares.crud.repositories.UserRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private UserServices userServices;

    public UserController(UserRepository userRepository, UserServices userServices) {
        this.userRepository = userRepository;
        this.userServices = userServices;
    }


    //todo http requests

    //GET request to users
    //renders JSON

    @GetMapping("") //completed
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    //POST request to users
    //Input: Json
    //Output: insert user in DB and renders Json not including the password

    @PostMapping("") //completes
    public User addUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    //GET user by id
    //Input path variable id
    //Output: render JSON with record

    @GetMapping("/{id}") //completed
    public Optional<User> getUserById(@PathVariable Long id) {
        return this.userRepository.findById(id);
    }

    //PATCH request to users
    //Input: path variable JSON with update
    //Output:  Renders JSON with updated record

    @PatchMapping("/{id}") //completed
    public Map<String, Object> updateRecordById(@RequestBody User user, @PathVariable Long id) throws Exception {

        return userServices.updateUser(user, id);

    }

    //DELETE request to users
    //Input: Path variable with user id
    //Output: current users in DB

    @DeleteMapping("/{id}") //completed
    public Map<String, String> deleteUserById(@PathVariable Long id) {
        //note the get() method returns the current User
        return userServices.deleteUser(id);
    }

    //POST request authenticate user
    //Input: JSON with email and password
    //Output: JSON

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody User user) {

        String message = "";
        if (this.userRepository.existsByEmail(user.getEmail())) {
            User userToAuthenticate = this.userRepository.findByEmail(user.getEmail());

            if (userToAuthenticate.getPassword().equals(user.getPassword())) {

                message = String.format("{\"authenticated\": \"true\", \"user\" : {\"id\": %d, \"email\" : \"%s\"}}", userToAuthenticate.getId(), userToAuthenticate.getEmail());
            } else {
                message = String.format("{\"authenticated:\" %s}","false").toString();
            }
        }

        return message ;
    }
}
