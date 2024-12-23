package vttp.ssf.mini_project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import vttp.ssf.mini_project.validation.ValidPassword;

public class User {

    @Pattern(
            regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$",
            message = "Invalid email format. Please include a valid domain (e.g., .com, .org)."
    )
    @Email(message = "Username needs to be an email!")
    @NotBlank(message = "Email required")
    private String email;
    @NotBlank (message= "Password required!")
    @ValidPassword(
            minlength = 8,
            requireSpecialCharacter = true,
            requireUppercase = true,
            requireNumber = true,
            message = "Password must be at least 8 characters, include uppercase, number, and special character."
    )
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}