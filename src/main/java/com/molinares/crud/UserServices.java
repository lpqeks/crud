package com.molinares.crud;

import com.molinares.crud.dtos.AuthenticateUserDTO;
import com.molinares.crud.dtos.UserDTO;
import com.molinares.crud.models.User;
import com.molinares.crud.repositories.UserRepository;
import com.molinares.crud.views.AuthenticateUserDTOView;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServices {

    private UserRepository userRepository;
    private AuthenticateUserDTO authenticateUserDTO;
    private UserDTO userDTO;

    public UserServices(UserRepository userRepository, AuthenticateUserDTO authenticateUserDTO) {
        this.userRepository = userRepository;
        this.authenticateUserDTO = authenticateUserDTO;

    }

    public Map<String, Object> updateUser(User user, Long id) throws Exception {

        Map<String, Object> response = new LinkedHashMap<>();

        if (this.userRepository.existsById(id)) {



            User updatedUser = this.userRepository.findById(id).get();

            response.put("id", updatedUser.getId());
            ReflectionUtils.doWithFields(user.getClass(), field -> {
                //System.out.println(field.getName());
                //the statement below is necessary
                field.setAccessible(true);
            if(field.get(user) != null) {

                if (field.getName().equals("lastName")) {
                    updatedUser.setLastName(user.getLastName());
                    response.put(field.getName(), user.getLastName() );
                }
                if (field.getName().equals("firstName")) {
                    updatedUser.setFirstName(user.getFirstName());
                    response.put(field.getName(), user.getFirstName() );
                }
                if (field.getName().equals("email")) {
                    updatedUser.setEmail(user.getEmail());
                    response.put(field.getName(), user.getEmail() );
                }
                if (field.getName().equals("boardingDate")) {

                    updatedUser.setBoardingDate(user.getBoardingDate());
                    response.put(field.getName(), updatedUser.getBoardingDate() );
                }
                if (field.getName().equals("active")) {
                    //System.out.println(field.get(user));
                    updatedUser.setActive(user.isActive());
                    response.put(field.getName(), user.isActive() );
                }

                if (field.getName().equals("salary")) {
                    //System.out.println(field.get(user));
                    updatedUser.setSalary(user.getSalary());
                    response.put(field.getName(), user.getSalary());

                }
            }
        });

        this.userRepository.save(updatedUser);

        }

            return response;

    }

    public Map<String, String> deleteUser(Long id) {
        Map<String, String> response = new HashMap<>();

        if (this.userRepository.existsById(id)) {
            User userToDelete = this.userRepository.findById(id).get();
            this.userRepository.delete(userToDelete);
            Long newCount = this.userRepository.count();
            response.put("count", String.valueOf(newCount));
            return response;
            //return String.format("{\"Count\": %d", this.userRepository.count());
        } else {
            response.put("Message: ", "User does not exist");
            return response;
        }

    }

    public MappingJacksonValue validateUser(User user) {

        if (this.userRepository.existsByEmail(user.getEmail())) {
            User userToAuthenticate = this.userRepository.findByEmail(user.getEmail());

            if (userToAuthenticate.getPassword().equals(user.getPassword())) {
                this.authenticateUserDTO.getUser().setEmail(userToAuthenticate.getEmail());
                this.authenticateUserDTO.getUser().setId(userToAuthenticate.getId());
                this.authenticateUserDTO.setAuthenticate(true);
               // message = String.format("{\"authenticated\": \"true\", \"user\" : {\"id\": %d, \"email\" : \"%s\"}}", userToAuthenticate.getId(), userToAuthenticate.getEmail());
            } else {
                this.authenticateUserDTO.setAuthenticate(false);
            }
        }

        MappingJacksonValue value = new MappingJacksonValue(authenticateUserDTO);
        if (this.authenticateUserDTO.getAuthenticate()) {
            value.setSerializationView(AuthenticateUserDTOView.FullView.class);
        } else {
            value.setSerializationView(AuthenticateUserDTOView.RestrictedView.class);
        }
        return value;
    }





}

