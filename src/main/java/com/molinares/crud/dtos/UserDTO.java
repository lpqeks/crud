package com.molinares.crud.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.molinares.crud.views.AuthenticateUserDTOView;
import org.springframework.stereotype.Service;

@Service
public class UserDTO {
    @JsonView(AuthenticateUserDTOView.FullView.class)
    private Long id;

    @JsonView(AuthenticateUserDTOView.FullView.class)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
