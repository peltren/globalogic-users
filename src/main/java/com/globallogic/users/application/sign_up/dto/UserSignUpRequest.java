package com.globallogic.users.application.sign_up.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Value
public class UserSignUpRequest {

    String name;

    @Email(message = "Formato incorrecto")
    String email;

    @Pattern(
            regexp = "^(?=(?:[^A-Z]*[A-Z]){1})(?=(?:[^0-9]*[0-9]){2})([a-zA-Z0-9]{8,12})$",
            message = "Debe tener solo una mayuscula, solo dos numeros, largo maximo de 12 y minimo de 8"
    )
    String password;

    List<UserSignUpPhoneRequest> phones;

    public List<UserSignUpPhoneRequest> getPhones() {
        return phones == null ? new ArrayList<>() : phones;
    }
}
