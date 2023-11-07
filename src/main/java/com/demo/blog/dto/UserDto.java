package com.demo.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import javax.validation.constraints.*;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long userId;

    @NotEmpty(message = "userName can't be null")
    @Size(min = 3, message = "name should be atleast 3 char long")
    private String userName;

    @NotEmpty(message = "userPassword can't be null")
    @Size(min = 6, max = 10, message = "password should be 6 - 10")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,}$")
    private String userPassword;

    @NotEmpty(message = "userEmail can't be null")
    @Email
    private String userEmail;

    @NotEmpty(message = "userAbout can't be null")
    private String userAbout;
}
