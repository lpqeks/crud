package com.molinares.crud.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.molinares.crud.views.AuthenticateUserDTOView;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserDTO {


    @JsonView(AuthenticateUserDTOView.RestrictedView.class)
    private Boolean authenticate;

    @JsonView(AuthenticateUserDTOView.FullView.class)
    private UserDTO user;

    public AuthenticateUserDTO(UserDTO user) {
        this.user = user;
    }

    public Boolean getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(Boolean authenticate) {
        this.authenticate = authenticate;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUserDTO(UserDTO user) {
        this.user = user;
    }
}
