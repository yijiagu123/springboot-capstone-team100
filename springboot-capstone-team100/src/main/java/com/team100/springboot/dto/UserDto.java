package net.enjoy.springboot.registrationlogin.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.enjoy.springboot.registrationlogin.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

    private Long id;
    @NotEmpty
    private String fullName;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @Enumerated(EnumType.STRING)
    private User.UserType userType = User.UserType.INDIVIDUAL;

    public void setUserType(User.UserType userType) {
        this.userType = userType;
    }
    private String companyAddress; // Add company address field
    private String businessRegistrationNumber;

}
